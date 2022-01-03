package subota.max.verification.domain.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.core.util.RequestUtil;
import subota.max.verification.api.dto.CreateVerificationRequest;
import subota.max.verification.api.dto.CreateVerificationResponse;
import subota.max.verification.api.dto.VerificationConfirmationRequest;
import subota.max.verification.domain.Verification;
import subota.max.verification.domain.exception.DuplicatedVerificationException;
import subota.max.verification.domain.exception.VerificationExpiredException;
import subota.max.verification.domain.exception.VerificationFailedException;
import subota.max.verification.domain.exception.VerificationForbiddenException;
import subota.max.verification.domain.exception.VerificationNotFoundException;
import subota.max.verification.domain.repository.VerificationRepository;
import subota.max.verification.domain.vo.UserInfo;
import subota.max.verification.infrastructure.property.VerificationSettingsProperty;

import java.time.Duration;

import static subota.max.core.util.NullSafe.transform;
import static subota.max.verification.domain.vo.VerificationStatus.PENDING;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DefaultVerificationService {
    VerificationRepository verificationRepository;
    VerificationSettingsProperty verificationSettings;

    @Transactional
    public CreateVerificationResponse createVerification(CreateVerificationRequest createVerificationRequest) {
        UserInfo userInfo = getUserInfo();
        String identity = createVerificationRequest.subject().identity();
        ConfirmationType identityType = ConfirmationType.valueOfIgnoreCase(createVerificationRequest.subject().type());

        verificationRepository.findByIdentityAndIdentityTypeAndStatus(identity, identityType, PENDING)
            .ifPresent(verification -> {
                Duration expirationTime = Duration.ofSeconds(verificationSettings.ttlInSeconds());
                int attemptsLimit = verificationSettings.attemptsLimit();
                if (!verification.isExpired(expirationTime, attemptsLimit)) {
                    throw new DuplicatedVerificationException("Verification has been already created.");
                }
            });

        int codeLength = verificationSettings.codeLength();
        Verification verification = new Verification(identity, identityType, userInfo.ip(), userInfo.userAgent(),
            codeLength);

        verificationRepository.save(verification);

        return new CreateVerificationResponse(verification.getId());
    }

    @Transactional(noRollbackFor = {VerificationFailedException.class, VerificationExpiredException.class})
    public void confirmVerification(String verificationId, VerificationConfirmationRequest confirmation) {
        Verification verification = verificationRepository.findById(verificationId)
            .orElseThrow(() -> new VerificationNotFoundException("Verification not found."));

        if (verification.isCompleted()) {
            return;
        }

        Duration expirationTime = Duration.ofSeconds(verificationSettings.ttlInSeconds());
        int attemptsLimit = verificationSettings.attemptsLimit();
        try {
            if (verification.isExpired(expirationTime, attemptsLimit)) {
                throw new VerificationExpiredException("Verification expired.");
            }

            if (!verification.verifyUserInfo(getUserInfo())) {
                throw new VerificationForbiddenException("No permission to confirm verification.");
            }

            if (!verification.verifyCode(confirmation.code())) {
                throw new VerificationFailedException("Validation failed: invalid code supplied.");
            }
        } finally {
            verificationRepository.save(verification);
        }
    }

    private UserInfo getUserInfo() {
        return RequestUtil.getCurrentRequest()
            .map(request -> {
                String ipAddress = request.getRemoteAddr();
                String userAgent = transform(request.getAttribute("User-Agent"), Object::toString);

                return new UserInfo(ipAddress, userAgent);
            })
            .orElseThrow(() -> new IllegalStateException("Failed to get request"));
    }
}
