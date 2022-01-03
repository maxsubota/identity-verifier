package subota.max.verification.domain.repository;

import subota.max.core.confirmation.ConfirmationType;
import subota.max.verification.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subota.max.verification.domain.vo.VerificationStatus;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, String> {

    Optional<Verification> findByIdentityAndIdentityTypeAndStatus(String identity,
                                                                  ConfirmationType identityType,
                                                                  VerificationStatus status);
}
