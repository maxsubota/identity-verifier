package subota.max.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.Version;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    @CreatedDate
    @Temporal(TIMESTAMP)
    Date createdAt;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    Date updatedAt;

    @Version
    Long version;
}
