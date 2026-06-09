package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Data Access Object per la gestione degli ordini e dei dettagli ordine.
 * Fornisce metodi per salvare, recuperare, filtrare, aggiornare lo stato ed
 * eliminare ordini.
 * Utilizza un DataSource configurato in JNDI per la connessione al database.
 */
public class OrdineDAO {
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
     * Salva un nuovo ordine (testata) nel database.
     * 
     * @param ordine l'oggetto Ordine da salvare (senza ID, che verrà generato)
     * @return l'ID generato per l'ordine
     * @throws SQLException in caso di errore DB
     */
    public int salvaOrdine(Ordine ordine) throws SQLException {
        String sql = "INSERT INTO ordine (utente_id, totale, indirizzo_spedizione, stato) VALUES (?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ordine.getUtenteId());
            ps.setDouble(2, ordine.getTotale());
            ps.setString(3, ordine.getIndirizzoSpedizione());
            ps.setString(4, ordine.getStato() != null ? ordine.getStato() : "in_lavorazione");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
                else
                    throw new SQLException("Impossibile ottenere ID ordine");
            }
        }
    }

    /**
     * Salva un dettaglio ordine (riga prodotto) nel database.
     * 
     * @param det l'oggetto DettaglioOrdine da salvare
     * @throws SQLException in caso di errore DB
     */
    public void salvaDettaglio(DettaglioOrdine det) throws SQLException {
        String sql = "INSERT INTO dettaglio_ordine (ordine_id, prodotto_id, quantita, prezzo_unitario, iva) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, det.getOrdineId());
            ps.setInt(2, det.getProdottoId());
            ps.setInt(3, det.getQuantita());
            ps.setDouble(4, det.getPrezzoUnitario());
            ps.setDouble(5, det.getIva());
            ps.executeUpdate();
        }
    }

    /**
     * Recupera tutti gli ordini di un dato utente (per la visualizzazione nella sua
     * area personale).
     * 
     * @param utenteId l'ID dell'utente
     * @return lista di ordini (senza i dettagli dei prodotti)
     * @throws SQLException in caso di errore DB
     */
    public List<Ordine> getOrdiniByUtente(int utenteId) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT * FROM ordine WHERE utente_id = ? ORDER BY data_ordine DESC";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = new Ordine();
                    o.setId(rs.getInt("id"));
                    o.setUtenteId(rs.getInt("utente_id"));
                    o.setDataOrdine(rs.getTimestamp("data_ordine"));
                    o.setTotale(rs.getDouble("totale"));
                    o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    o.setStato(rs.getString("stato"));
                    ordini.add(o);
                }
            }
        }
        return ordini;
    }

    /**
     * Recupera un singolo ordine per ID (senza utente associato).
     * 
     * @param ordineId ID dell'ordine
     * @return l'oggetto Ordine o null se non trovato
     * @throws SQLException in caso di errore DB
     */
    public Ordine getOrdineById(int ordineId) throws SQLException {
        String sql = "SELECT * FROM ordine WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ordine o = new Ordine();
                    o.setId(rs.getInt("id"));
                    o.setUtenteId(rs.getInt("utente_id"));
                    o.setDataOrdine(rs.getTimestamp("data_ordine"));
                    o.setTotale(rs.getDouble("totale"));
                    o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    o.setStato(rs.getString("stato"));
                    return o;
                }
            }
        }
        return null;
    }

    /**
     * Recupera i dettagli (prodotti) di un ordine, inclusi i dati del prodotto
     * (nome, prezzo, ecc.).
     * 
     * @param ordineId ID dell'ordine
     * @return lista di DettaglioOrdine
     * @throws SQLException in caso di errore DB
     */
    public List<DettaglioOrdine> getDettagliByOrdine(int ordineId) throws SQLException {
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        String sql = "SELECT * FROM dettaglio_ordine WHERE ordine_id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            try (ResultSet rs = ps.executeQuery()) {
                ProdottoDAO pdao = new ProdottoDAO();
                while (rs.next()) {
                    DettaglioOrdine d = new DettaglioOrdine();
                    d.setId(rs.getInt("id"));
                    d.setOrdineId(rs.getInt("ordine_id"));
                    d.setProdottoId(rs.getInt("prodotto_id"));
                    d.setQuantita(rs.getInt("quantita"));
                    d.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    d.setIva(rs.getDouble("iva"));
                    // Recupera il prodotto associato per avere nome, immagine, ecc.
                    Prodotto p = pdao.doRetrieveById(d.getProdottoId());
                    d.setProdotto(p);
                    dettagli.add(d);
                }
            }
        }
        return dettagli;
    }

    /**
     * Recupera tutti gli ordini con filtri (usato dall'admin). Restituisce anche i
     * dati dell'utente.
     * 
     * @param dataDa       data inizio (opzionale, formato yyyy-MM-dd)
     * @param dataA        data fine (opzionale)
     * @param clienteEmail email del cliente (opzionale, match parziale)
     * @param stato        stato dell'ordine (opzionale: in_lavorazione, spedito,
     *                     consegnato)
     * @return lista di ordini completi con utente associato
     * @throws SQLException in caso di errore DB
     */
    public List<Ordine> getAllOrdiniConFiltri(String dataDa, String dataA, String clienteEmail, String stato)
            throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT o.*, u.id as utente_id, u.email, u.nome, u.cognome, u.indirizzo, u.citta, u.provincia, u.cap, u.ruolo "
                        +
                        "FROM ordine o INNER JOIN utente u ON o.utente_id = u.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (dataDa != null && !dataDa.isEmpty()) {
            sql.append(" AND o.data_ordine >= ?");
            params.add(Timestamp.valueOf(dataDa + " 00:00:00"));
        }
        if (dataA != null && !dataA.isEmpty()) {
            sql.append(" AND o.data_ordine <= ?");
            params.add(Timestamp.valueOf(dataA + " 23:59:59"));
        }
        if (clienteEmail != null && !clienteEmail.isEmpty()) {
            sql.append(" AND u.email LIKE ?");
            params.add("%" + clienteEmail + "%");
        }
        if (stato != null && !stato.isEmpty()) {
            sql.append(" AND o.stato = ?");
            params.add(stato);
        }
        sql.append(" ORDER BY o.data_ordine DESC");

        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = new Ordine();
                    o.setId(rs.getInt("o.id"));
                    o.setUtenteId(rs.getInt("o.utente_id"));
                    o.setDataOrdine(rs.getTimestamp("o.data_ordine"));
                    o.setTotale(rs.getDouble("o.totale"));
                    o.setIndirizzoSpedizione(rs.getString("o.indirizzo_spedizione"));
                    o.setStato(rs.getString("o.stato"));

                    Utente u = new Utente();
                    u.setId(rs.getInt("u.id"));
                    u.setEmail(rs.getString("u.email"));
                    u.setNome(rs.getString("u.nome"));
                    u.setCognome(rs.getString("u.cognome"));
                    u.setIndirizzo(rs.getString("u.indirizzo"));
                    u.setCitta(rs.getString("u.citta"));
                    u.setProvincia(rs.getString("u.provincia"));
                    u.setCap(rs.getString("u.cap"));
                    u.setRuolo(rs.getString("u.ruolo"));
                    o.setUtente(u);

                    ordini.add(o);
                }
            }
        }
        return ordini;
    }

    /**
     * Aggiorna lo stato di un ordine.
     * 
     * @param ordineId ID dell'ordine
     * @param stato    nuovo stato (es. "spedito", "consegnato")
     * @return true se l'aggiornamento ha interessato una riga, false altrimenti
     * @throws SQLException in caso di errore DB
     */
    public boolean aggiornaStatoOrdine(int ordineId, String stato) throws SQLException {
        String sql = "UPDATE ordine SET stato = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stato);
            ps.setInt(2, ordineId);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Conta il numero totale di ordini (usato per statistiche admin).
     * 
     * @return numero di ordini
     * @throws SQLException in caso di errore DB
     */
    public int countOrdini() throws SQLException {
        String sql = "SELECT COUNT(*) FROM ordine";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        }
        return 0;
    }

    /**
     * Recupera gli ordini con filtri di data e cliente (versione senza stato, usata
     * da alcune servlet).
     * 
     * @param dataDa       data inizio (opzionale)
     * @param dataA        data fine (opzionale)
     * @param clienteEmail email del cliente (opzionale)
     * @return lista di ordini con utente associato
     * @throws SQLException in caso di errore DB
     */
    public List<Ordine> getOrdiniConFiltri(String dataDa, String dataA, String clienteEmail) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT o.id AS ordine_id, o.utente_id, o.data_ordine, o.totale, o.indirizzo_spedizione, o.stato, " +
                        "u.id AS utente_id, u.email, u.nome, u.cognome, u.indirizzo, u.citta, u.provincia, u.cap, u.ruolo "
                        +
                        "FROM ordine o INNER JOIN utente u ON o.utente_id = u.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (dataDa != null && !dataDa.isEmpty()) {
            sql.append(" AND o.data_ordine >= ?");
            params.add(Timestamp.valueOf(dataDa + " 00:00:00"));
        }
        if (dataA != null && !dataA.isEmpty()) {
            sql.append(" AND o.data_ordine <= ?");
            params.add(Timestamp.valueOf(dataA + " 23:59:59"));
        }
        if (clienteEmail != null && !clienteEmail.isEmpty()) {
            sql.append(" AND u.email LIKE ?");
            params.add("%" + clienteEmail + "%");
        }
        sql.append(" ORDER BY o.data_ordine DESC");

        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = new Ordine();
                    o.setId(rs.getInt("ordine_id"));
                    o.setUtenteId(rs.getInt("utente_id"));
                    o.setDataOrdine(rs.getTimestamp("data_ordine"));
                    o.setTotale(rs.getDouble("totale"));
                    o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                    o.setStato(rs.getString("stato"));

                    Utente u = new Utente();
                    u.setId(rs.getInt("utente_id"));
                    u.setEmail(rs.getString("email"));
                    u.setNome(rs.getString("nome"));
                    u.setCognome(rs.getString("cognome"));
                    u.setIndirizzo(rs.getString("indirizzo"));
                    u.setCitta(rs.getString("citta"));
                    u.setProvincia(rs.getString("provincia"));
                    u.setCap(rs.getString("cap"));
                    u.setRuolo(rs.getString("ruolo"));
                    o.setUtente(u);

                    ordini.add(o);
                }
            }
        }
        return ordini;
    }

    /**
     * Elimina un ordine e tutti i suoi dettagli associati.
     * Prima cancella i record da dettaglio_ordine (per evitare violazione di FK),
     * poi elimina l'ordine stesso.
     * 
     * @param ordineId ID dell'ordine
     * @return true se l'eliminazione ha interessato una riga, false altrimenti
     * @throws SQLException in caso di errore DB
     */
    public boolean eliminaOrdine(int ordineId) throws SQLException {
        // Prima elimina i dettagli (righe prodotto) associati all'ordine
        String sqlDet = "DELETE FROM dettaglio_ordine WHERE ordine_id = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement psDet = conn.prepareStatement(sqlDet)) {
            psDet.setInt(1, ordineId);
            psDet.executeUpdate();
        }
        // Poi elimina l'ordine stesso
        String sql = "DELETE FROM ordine WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            return ps.executeUpdate() == 1;
        }
    }
}