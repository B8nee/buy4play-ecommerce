package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Data Access Object per la gestione degli utenti.
 * Fornisce metodi per registrazione, login, recupero, aggiornamento,
 * cambio ruolo, eliminazione e conteggio utenti.
 * Utilizza un DataSource configurato in JNDI.
 */
public class UtenteDAO {
    private static DataSource ds;

    // Blocco statico per inizializzare il DataSource una sola volta
    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/buy4play");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra un nuovo utente nel database.
     * 
     * @param utente l'oggetto Utente (con password già hashata)
     * @return true se l'inserimento è avvenuto con successo, false altrimenti
     * @throws SQLException in caso di errore DB
     */
    public boolean registraUtente(Utente utente) throws SQLException {
        String sql = "INSERT INTO utente (email, nome, cognome, indirizzo, citta, provincia, cap, password_hash, ruolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getNome());
            ps.setString(3, utente.getCognome());
            ps.setString(4, utente.getIndirizzo());
            ps.setString(5, utente.getCitta());
            ps.setString(6, utente.getProvincia());
            ps.setString(7, utente.getCap());
            ps.setString(8, utente.getPasswordHash());
            ps.setString(9, utente.getRuolo());
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Verifica se un'email è già stata registrata.
     * 
     * @param email l'email da controllare
     * @return true se esiste, false altrimenti
     * @throws SQLException in caso di errore DB
     */
    public boolean emailEsistente(String email) throws SQLException {
        String sql = "SELECT id FROM utente WHERE email = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Esegue il login: verifica credenziali (email e hash password).
     * 
     * @param email        email dell'utente
     * @param passwordHash hash SHA-512 della password
     * @return oggetto Utente se credenziali valide, altrimenti null
     * @throws SQLException in caso di errore DB
     */
    public Utente login(String email, String passwordHash) throws SQLException {
        String sql = "SELECT * FROM utente WHERE email = ? AND password_hash = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente u = new Utente();
                    u.setId(rs.getInt("id"));
                    u.setEmail(rs.getString("email"));
                    u.setNome(rs.getString("nome"));
                    u.setCognome(rs.getString("cognome"));
                    u.setIndirizzo(rs.getString("indirizzo"));
                    u.setCitta(rs.getString("citta"));
                    u.setProvincia(rs.getString("provincia"));
                    u.setCap(rs.getString("cap"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setRuolo(rs.getString("ruolo"));
                    return u;
                }
            }
        }
        return null;
    }

    /**
     * Recupera un utente tramite ID.
     * 
     * @param id ID dell'utente
     * @return oggetto Utente se trovato, altrimenti null
     * @throws SQLException in caso di errore DB
     */
    public Utente getUtenteById(int id) throws SQLException {
        String sql = "SELECT * FROM utente WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente u = new Utente();
                    u.setId(rs.getInt("id"));
                    u.setEmail(rs.getString("email"));
                    u.setNome(rs.getString("nome"));
                    u.setCognome(rs.getString("cognome"));
                    u.setIndirizzo(rs.getString("indirizzo"));
                    u.setCitta(rs.getString("citta"));
                    u.setProvincia(rs.getString("provincia"));
                    u.setCap(rs.getString("cap"));
                    u.setRuolo(rs.getString("ruolo"));
                    return u;
                }
            }
        }
        return null;
    }

    /**
     * Aggiorna i dati anagrafici di un utente (esclusa password).
     * 
     * @param utente oggetto Utente con i nuovi dati (deve contenere l'ID)
     * @return true se l'aggiornamento ha interessato una riga
     * @throws SQLException in caso di errore DB
     */
    public boolean aggiornaUtente(Utente utente) throws SQLException {
        String sql = "UPDATE utente SET nome=?, cognome=?, indirizzo=?, citta=?, provincia=?, cap=? WHERE id=?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getIndirizzo());
            ps.setString(4, utente.getCitta());
            ps.setString(5, utente.getProvincia());
            ps.setString(6, utente.getCap());
            ps.setInt(7, utente.getId());
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Aggiorna la password di un utente.
     * 
     * @param utenteId ID dell'utente
     * @param newHash  nuovo hash SHA-512 della password
     * @return true se l'aggiornamento ha interessato una riga
     * @throws SQLException in caso di errore DB
     */
    public boolean aggiornaPassword(int utenteId, String newHash) throws SQLException {
        String sql = "UPDATE utente SET password_hash = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setInt(2, utenteId);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Restituisce la lista di tutti gli utenti (ordinata per ID).
     * Utilizzato dalla sezione admin per gestire gli utenti.
     * 
     * @return lista di oggetti Utente
     * @throws SQLException in caso di errore DB
     */
    public List<Utente> getAllUtenti() throws SQLException {
        List<Utente> utenti = new ArrayList<>();
        String sql = "SELECT * FROM utente ORDER BY id";
        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utente u = new Utente();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setIndirizzo(rs.getString("indirizzo"));
                u.setCitta(rs.getString("citta"));
                u.setProvincia(rs.getString("provincia"));
                u.setCap(rs.getString("cap"));
                u.setRuolo(rs.getString("ruolo"));
                utenti.add(u);
            }
        }
        return utenti;
    }

    /**
     * Aggiorna il ruolo di un utente (cliente o admin).
     * 
     * @param id    ID dell'utente
     * @param ruolo nuovo ruolo ("cliente" o "admin")
     * @return true se l'aggiornamento ha interessato una riga
     * @throws SQLException in caso di errore DB
     */
    public boolean aggiornaRuoloUtente(int id, String ruolo) throws SQLException {
        String sql = "UPDATE utente SET ruolo = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ruolo);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Elimina un utente dal database.
     * Se la tabella ordine ha ON DELETE CASCADE, verranno eliminati anche gli
     * ordini associati.
     * 
     * @param id ID dell'utente
     * @return true se l'eliminazione ha interessato una riga
     * @throws SQLException in caso di errore DB
     */
    public boolean eliminaUtente(int id) throws SQLException {
        String sql = "DELETE FROM utente WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Conta il numero totale di utenti registrati.
     * Utilizzato per le statistiche nella dashboard admin.
     * 
     * @return numero di utenti
     * @throws SQLException in caso di errore DB
     */
    public int countUtenti() throws SQLException {
        String sql = "SELECT COUNT(*) FROM utente";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        }
        return 0;
    }
}