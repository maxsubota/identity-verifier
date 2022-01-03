package subota.max.core.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

@UtilityClass
public class RandomUtil {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String secureRandomNumeric(int length) {

        return secureRandom(length, false, true);
    }

    public static String secureRandom(int length, boolean letters, boolean numbers) {

        return RandomStringUtils.random(length, 0, 0, letters, numbers, null, secureRandom);
    }
}
