package utility;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Classe di utilità per la generazione di token casuali crittograficamente
 * sicuri.
 * Utilizzata per il "remember me" (login persistente) per generare il token
 * associato alla serie.
 * I token sono generati con SecureRandom (per evitare prevedibilità) e
 * codificati in Base64URL
 * senza padding, adatti per l'uso nei cookie HTTP.
 */
public class TokenGenerator {
    // Generatore di numeri casuali crittograficamente forte
    private static final SecureRandom secureRandom = new SecureRandom();
    // Encoder Base64URL (senza padding) per ottenere stringhe compatibili con URL e
    // cookie
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    /**
     * Genera un token casuale di 256 bit (32 byte) codificato in Base64URL.
     * 
     * @return stringa del token (es. "aBc123_Def-456...")
     */
    public static String generaToken() {
        byte[] buffer = new byte[32]; // 256 bit
        secureRandom.nextBytes(buffer); // Riempie il buffer con byte casuali
        return encoder.encodeToString(buffer);
    }
}