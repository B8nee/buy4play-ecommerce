package utility;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generaToken() {
        byte[] buffer = new byte[32];
        secureRandom.nextBytes(buffer);
        return encoder.encodeToString(buffer);
    }
}