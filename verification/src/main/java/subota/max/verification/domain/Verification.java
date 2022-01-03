package subota.max.verification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import subota.max.core.confirmation.ConfirmationType;
import subota.max.core.event.DomainEvent;
import subota.max.core.util.RandomUtil;
import subota.max.verification.domain.vo.UserInfo;
import subota.max.verification.domain.vo.VerificationStatus;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Version;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.TemporalType.TIMESTAMP;
import static subota.max.core.event.DomainEventType.VERIFICATION_CONFIRMATION_FAILED;
import static subota.max.core.event.DomainEventType.VERIFICATION_CONFIRMED;
import static subota.max.core.event.DomainEventType.VERIFICATION_CREATED;
import static subota.max.core.event.DomainEventType.VERIFICATION_EXPIRED;
import static subota.max.verification.domain.vo.VerificationStatus.COMPLETED;
import static subota.max.verification.domain.vo.VerificationStatus.EXPIRED;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Verification extends AbstractAggregateRoot<Verification> {

    @Id
    String id;

    String identity;

    @Enumerated(EnumType.STRING)
    ConfirmationType identityType;

    boolean confirmed;

    String code;

    String ip;

    String userAgent;

    int attempts;

    @Enumerated(EnumType.STRING)
    VerificationStatus status;

    @CreatedDate
    @Temporal(TIMESTAMP)
    Date createdAt;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    Date updatedAt;

    @Version
    Long version;

    public Verification(String identity, ConfirmationType identityType, String ip, String userAgent, int codeLength) {
        this.id = UUID.randomUUID().toString();
        this.identity = identity;
        this.identityType = identityType;
        this.ip = ip;
        this.userAgent = userAgent;
        this.confirmed = false;
        this.status = VerificationStatus.PENDING;
        this.code = RandomUtil.secureRandomNumeric(codeLength);
        this.attempts = 0;
        registerEvent(new DomainEvent(VERIFICATION_CREATED, id, this));
    }

    public boolean verifyUserInfo(UserInfo userInfo) {
        return new UserInfo(ip, userAgent).equals(userInfo);
    }

    public boolean verifyCode(String code) {
        if (confirmed) {
            return true;
        }

        attempts++;

        if (this.code.equals(code)) {
            status = VerificationStatus.COMPLETED;
            confirmed = true;
            registerEvent(new DomainEvent(VERIFICATION_CONFIRMED, id, new Object()));
            return true;
        } else {
            status = VerificationStatus.FAILED;
            registerEvent(new DomainEvent(VERIFICATION_CONFIRMATION_FAILED, id, Map.of("invalidCode", code)));
            return false;
        }

    }

    public boolean isCompleted() {
        return status == COMPLETED;
    }

    public boolean isExpired(Duration expirationTime, int attemptLimit) {
        if (status == EXPIRED) {
            return true;
        }
        Date expireAt = new Date(createdAt.getTime() + expirationTime.toMillis());
        if (new Date().after(expireAt) || attempts > attemptLimit) {
            status = EXPIRED;
            registerEvent(new DomainEvent(VERIFICATION_EXPIRED, id, new Object()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Verification that = (Verification) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
