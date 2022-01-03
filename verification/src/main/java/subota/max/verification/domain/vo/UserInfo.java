package subota.max.verification.domain.vo;

import java.io.Serializable;

public record UserInfo(
    String ip,
    String userAgent
) implements Serializable {

}
