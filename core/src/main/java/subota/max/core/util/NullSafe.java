package subota.max.core.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class NullSafe {

    public static <I, R> R transform(I input, @NonNull Function<I, R> mapper) {
        return input == null ? null : mapper.apply(input);
    }
}
