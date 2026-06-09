package utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe di utilità per l'hashing delle password.
 * Utilizza l'algoritmo SHA-512 (sicuro e a lunghezza fissa).
 * Trasforma una stringa (password in chiaro) in una stringa esadecimale di 128
 * caratteri.
 * L'hashing non è reversibile (funzione unidirezionale).
 */
public class PasswordHasher {

    /**
     * Calcola l'hash SHA-512 di una password.
     * 
     * @param password la password in chiaro
     * @return la stringa esadecimale corrispondente all'hash (128 caratteri)
     * @throws RuntimeException se l'algoritmo SHA-512 non è disponibile (mai
     *                          dovrebbe accadere in Java moderno)
     */
    public static String toHash(String password) {
        try {
            // Ottiene un'istanza di MessageDigest per SHA-512
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            // Converte la password in array di byte (UTF-8) e calcola l'hash
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Converte l'array di byte in stringa esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // 0xff & b per ottenere un valore positivo tra 0 e 255
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // Aggiunge zero iniziale per formare due cifre
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-512 esiste sempre in Java, ma gestiamo l'eccezione per completezza
            throw new RuntimeException("Errore hashing password", e);
        }
    }
}