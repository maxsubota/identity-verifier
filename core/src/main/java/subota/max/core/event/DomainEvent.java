package subota.max.core.event;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DomainEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    String id;
    DomainEventType type;
    String aggregateId;
    String payload;
    Date createdAt;

    public DomainEvent(DomainEventType type, String aggregateId, @NonNull Object payload) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.type = type;
        this.aggregateId = aggregateId;
        this.payload = new Gson().toJson(payload);
    }
}
