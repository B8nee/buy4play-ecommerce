package model;

import java.sql.*;
import java.util.UUID;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Data Access Object per la gestione dei token di "remember me" (login
 * persistente).
 * Memorizza le coppie (serie, token) associate a un utente, con una data di
 * scadenza.
 * Viene utilizzato dal filtro RememberMeFilter per autenticare automaticamente
 * l'utente
 * quando torna sul sito.
 */
public class RememberMeDAO {
    private static DataSource ds;

    // Blocco statico per inizializzare il DataSource (una sola volta)
    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/buy4play");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera una serie univoca (UUID) per il cookie "remember_me_serie".
     * 
     * @return stringa UUID casuale
     */
    public static String generaSerie() {
        return UUID.randomUUID().toString();
    }

    /**
     * Salva (o aggiorna) il token per un utente.
     * Prima elimina eventuali vecchi token per lo stesso utente (sostituzione),
     * poi inserisce la nuova coppia (serie, token) con scadenza.
     * 
     * @param utenteId      ID dell'utente
     * @param serie         serie (identificatore univoco del cookie)
     * @param token         token casuale associato alla serie
     * @param durataSecondi durata di validità in secondi (es. 30 giorni)
     * @throws SQLException in caso di errore DB
     */
    public void salvaToken(int utenteId, String serie, String token, long durataSecondi) throws SQLException {
        // Rimuove eventuali token preesistenti per lo stesso utente
        String sql = "DELETE FROM remember_me WHERE utente_id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.executeUpdate();
        }
        // Inserisce il nuovo token
        sql = "INSERT INTO remember_me (utente_id, serie, token, data_scadenza) VALUES (?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setString(2, serie);
            ps.setString(3, token);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis() + durataSecondi * 1000));
            ps.executeUpdate();
        }
    }

    /**
     * Valida una coppia (serie, token) controllando che esista nel DB e non sia
     * scaduta.
     * 
     * @param serie la serie del cookie
     * @param token il token del cookie
     * @return l'ID dell'utente se valido, altrimenti -1
     * @throws SQLException in caso di errore DB
     */
    public int validaToken(String serie, String token) throws SQLException {
        String sql = "SELECT utente_id FROM remember_me WHERE serie = ? AND token = ? AND data_scadenza > NOW()";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serie);
            ps.setString(2, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("utente_id");
                }
            }
        }
        return -1;
    }

    /**
     * Elimina un token dal database data la serie (usato al logout).
     * 
     * @param serie la serie del cookie da rimuovere
     * @throws SQLException in caso di errore DB
     */
    public void eliminaToken(String serie) throws SQLException {
        String sql = "DELETE FROM remember_me WHERE serie = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serie);
            ps.executeUpdate();
        }
    }

    /**
     * Elimina tutti i token associati a un utente (ad esempio quando l'utente viene
     * eliminato).
     * 
     * @param utenteId ID dell'utente
     * @throws SQLException in caso di errore DB
     */
    public void eliminaTokenPerUtente(int utenteId) throws SQLException {
        String sql = "DELETE FROM remember_me WHERE utente_id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.executeUpdate();
        }
    }
}