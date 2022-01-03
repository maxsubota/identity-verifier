package subota.max.verification.domain.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import subota.max.core.util.RequestUtil;
import subota.max.verification.api.dto.CreateVerificationRequest;
import subota.max.verification.api.dto.Subject;
import subota.max.verification.api.dto.VerificationConfirmationRequest;
import subota.max.verification.domain.Verification;
import subota.max.verification.domain.exception.DuplicatedVerificationException;
import subota.max.verification.domain.exception.VerificationExpiredException;
import subota.max.verification.domain.exception.VerificationFailedException;
import subota.max.verification.domain.exception.VerificationForbiddenException;
import subota.max.verification.domain.exception.VerificationNotFoundException;
import subota.max.verification.domain.repository.VerificationRepository;
import subota.max.verification.infrastructure.property.VerificationSettingsProperty;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static subota.max.core.confirmation.ConfirmationType.EMAIL_CONFIRMATION;

class DefaultVerificationServiceTest {
    private static final String IP = "0.0.0.0";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final int CODE_LENGTH = 8;
    private static final int TTL = 300;
    private static final int ATTEMPTS_LIMIT = 1;
    private static final String verificationId = UUID.randomUUID().toString();
    private static final VerificationConfirmationRequest verificationConfirmRequest =
        new VerificationConfirmationRequest("12345678");

    static HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    VerificationSettingsProperty verificationSettingsProperty = new VerificationSettingsProperty(CODE_LENGTH, TTL,
        ATTEMPTS_LIMIT);
    VerificationRepository verificationRepository = mock(VerificationRepository.class);

    DefaultVerificationService defaultVerificationService =
        new DefaultVerificationService(verificationRepository, verificationSettingsProperty);

    @BeforeAll
    public static void setUp() {
        MockedStatic<RequestUtil> requestUtil = mockStatic(RequestUtil.class);
        requestUtil.when(RequestUtil::getCurrentRequest).thenReturn(Optional.of(httpServletRequest));
    }

    @BeforeEach
    public void setup() {
        when(httpServletRequest.getRemoteAddr()).thenReturn(IP);
        when(httpServletRequest.getAttribute("User-Agent")).thenReturn(USER_AGENT);
    }

    @Test
    void createVerification() {
        when(verificationRepository.findByIdentityAndIdentityTypeAndStatus(anyString(), any(), any()))
            .thenReturn(Optional.empty());

        var response = defaultVerificationService.createVerification(
            new CreateVerificationRequest(new Subject("email@example.com", "email_confirmation"))
        );

        assertNotNull(response.id());
    }

    @Test
    void createVerification_shouldThrowDuplicatedVerificationException() {
        var verification = new Verification(verificationId, EMAIL_CONFIRMATION, IP, USER_AGENT, CODE_LENGTH);
        verification.setCreatedAt(new Date());
        when(verificationRepository.findByIdentityAndIdentityTypeAndStatus(anyString(), any(), any()))
            .thenReturn(Optional.of(verification));

        var request = new CreateVerificationRequest(new Subject("email@example.com", "email_confirmation"));
        assertThrows(DuplicatedVerificationException.class, () -> defaultVerificationService.createVerification(request));
    }

    @Test
    void confirmVerification() {
        var verification = new Verification(verificationId, EMAIL_CONFIRMATION, IP, USER_AGENT, CODE_LENGTH);
        verification.setCreatedAt(new Date());
        when(verificationRepository.findById(anyString())).thenReturn(Optional.of(verification));

        assertDoesNotThrow(() ->
            defaultVerificationService.confirmVerification(verificationId, new VerificationConfirmationRequest(verification.getCode())));
    }

    @Test
    void confirmVerification_whenExceedLimit_shouldThrowVerificationExpired() {
        var verification = new Verification(verificationId, EMAIL_CONFIRMATION, IP, USER_AGENT, CODE_LENGTH);
        verification.setCreatedAt(new Date());
        verification.setAttempts(2);
        when(verificationRepository.findById(anyString())).thenReturn(Optional.of(verification));

        assertThrows(VerificationExpiredException.class, () ->
            defaultVerificationService.confirmVerification(verificationId, verificationConfirmRequest));
    }

    @Test
    void confirmVerification_shouldThrowVerificationNotFound() {
        when(verificationRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(VerificationNotFoundException.class, () ->
            defaultVerificationService.confirmVerification(verificationId, verificationConfirmRequest));
    }

    @Test
    void confirmVerification_shouldThrowVerificationExpired() {
        var verification = new Verification(verificationId, EMAIL_CONFIRMATION, IP, USER_AGENT, CODE_LENGTH);
        verification.setCreatedAt(new Date(0));
        when(verificationRepository.findById(anyString())).thenReturn(Optional.of(verification));

        assertThrows(VerificationExpiredException.class, () ->
            defaultVerificationService.confirmVerification(verificationId, verificationConfirmRequest));
    }

    @Test
    void confirmVerification_shouldThrowVerificationForbidden() {
        var verification = new Verification(verificationId, EMAIL_CONFIRMATION, "anotherIp", USER_AGENT, CODE_LENGTH);
        verification.setCreatedAt(new Date());
        when(verificationRepository.findById(anyString())).thenReturn(Optional.of(verification));

        assertThrows(VerificationForbiddenException.class, () ->
            defaultVerificationService.confirmVerification(verificationId, verificationConfirmRequest));
    }

    @Test
    void confirmVerification_shouldThrowVerificationFailed() {
        var verification = new Verification(verificationId, EMAIL_CONFIRMATION, IP, USER_AGENT, CODE_LENGTH);
        verification.setCreatedAt(new Date());
        when(verificationRepository.findById(anyString())).thenReturn(Optional.of(verification));

        assertThrows(VerificationFailedException.class, () ->
            defaultVerificationService.confirmVerification(verificationId, verificationConfirmRequest));
    }
}