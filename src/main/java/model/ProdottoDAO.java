package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Data Access Object per la gestione dei prodotti (catalogo).
 * Fornisce metodi per recuperare, inserire, aggiornare, cancellare e cercare
 * prodotti.
 * Utilizza un DataSource configurato in JNDI per la connessione al database.
 */
public class ProdottoDAO {
    private static DataSource ds;

    // Blocco statico: inizializza il DataSource una sola volta
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
     * Recupera una lista di prodotti con paginazione, filtro per piattaforma e
     * ordinamento.
     * 
     * @param offset   numero di record da saltare (per paginazione)
     * @param limit    numero massimo di record da restituire
     * @param platform filtro per piattaforma (es. "PC", "PS5") – opzionale, può
     *                 essere null
     * @param sort     ordinamento: "prezzo_asc", "prezzo_desc" o null (ordinamento
     *                 per ID)
     * @return lista di oggetti Prodotto
     * @throws SQLException in caso di errore DB
     */
    public List<Prodotto> doRetrieveAll(int offset, int limit, String platform, String sort) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM prodotto WHERE 1=1");

        // Aggiunge filtro per piattaforma (LIKE per gestire piattaforme multiple come
        // "PC / PS5")
        if (platform != null && !platform.isEmpty()) {
            sql.append(" AND piattaforma LIKE ?");
        }

        // Aggiunge ordinamento in base al parametro sort
        if (sort != null) {
            switch (sort) {
                case "prezzo_asc":
                    sql.append(" ORDER BY prezzo ASC");
                    break;
                case "prezzo_desc":
                    sql.append(" ORDER BY prezzo DESC");
                    break;
                default:
                    sql.append(" ORDER BY id");
            }
        } else {
            sql.append(" ORDER BY id");
        }
        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (platform != null && !platform.isEmpty()) {
                ps.setString(idx++, "%" + platform + "%");
            }
            ps.setInt(idx++, limit);
            ps.setInt(idx, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPiattaforma(rs.getString("piattaforma"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    p.setPopolare(rs.getInt("popolare") == 1); // converte 1/0 in boolean
                    prodotti.add(p);
                }
            }
        }
        return prodotti;
    }

    /**
     * Overload per retrocompatibilità: recupera prodotti senza filtro e
     * ordinamento.
     * 
     * @param offset offset per paginazione
     * @param limit  limite
     * @return lista di prodotti
     * @throws SQLException in caso di errore DB
     */
    public List<Prodotto> doRetrieveAll(int offset, int limit) throws SQLException {
        return doRetrieveAll(offset, limit, null, null);
    }

    /**
     * Conta il numero totale di prodotti, eventualmente filtrati per piattaforma.
     * 
     * @param platform filtro per piattaforma (opzionale)
     * @return numero di prodotti
     * @throws SQLException in caso di errore DB
     */
    public int countProdotti(String platform) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM prodotto WHERE 1=1");
        if (platform != null && !platform.isEmpty()) {
            sql.append(" AND piattaforma LIKE ?");
        }
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (platform != null && !platform.isEmpty()) {
                ps.setString(idx++, "%" + platform + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Overload senza filtro di piattaforma.
     * 
     * @return numero totale di prodotti
     * @throws SQLException in caso di errore DB
     */
    public int countProdotti() throws SQLException {
        return countProdotti(null);
    }

    /**
     * Recupera un singolo prodotto per ID.
     * 
     * @param id ID del prodotto
     * @return oggetto Prodotto o null se non trovato
     * @throws SQLException in caso di errore DB
     */
    public Prodotto doRetrieveById(int id) throws SQLException {
        String sql = "SELECT * FROM prodotto WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPiattaforma(rs.getString("piattaforma"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    p.setPopolare(rs.getInt("popolare") == 1);
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Aggiorna il flag "popolare" di un prodotto.
     * 
     * @param productId ID del prodotto
     * @param popolare  nuovo valore (true/false)
     * @return true se l'aggiornamento ha interessato una riga
     * @throws SQLException in caso di errore DB
     */
    public boolean setPopolare(int productId, boolean popolare) throws SQLException {
        String sql = "UPDATE prodotto SET popolare = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, popolare ? 1 : 0);
            ps.setInt(2, productId);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Inserisce un nuovo prodotto nel database.
     * 
     * @param prodotto oggetto Prodotto da inserire (l'ID viene generato
     *                 automaticamente)
     * @throws SQLException in caso di errore DB
     */
    public void doSave(Prodotto prodotto) throws SQLException {
        String sql = "INSERT INTO prodotto (nome, piattaforma, prezzo, immagine_url, popolare) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getPiattaforma());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setString(4, prodotto.getImmagineUrl());
            ps.setInt(5, prodotto.isPopolare() ? 1 : 0);
            ps.executeUpdate();
        }
    }

    /**
     * Aggiorna i dati di un prodotto esistente.
     * 
     * @param prodotto oggetto Prodotto con i nuovi valori (l'ID deve essere già
     *                 impostato)
     * @throws SQLException in caso di errore DB
     */
    public void doUpdate(Prodotto prodotto) throws SQLException {
        String sql = "UPDATE prodotto SET nome = ?, piattaforma = ?, prezzo = ?, immagine_url = ?, popolare = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getPiattaforma());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setString(4, prodotto.getImmagineUrl());
            ps.setInt(5, prodotto.isPopolare() ? 1 : 0);
            ps.setInt(6, prodotto.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un prodotto dal database, ma solo se non è mai stato ordinato.
     * Prima verifica se esistono record in dettaglio_ordine associati al prodotto.
     * 
     * @param id ID del prodotto
     * @return true se eliminato con successo, false se il prodotto è referenziato
     *         in ordini
     * @throws SQLException in caso di errore DB
     */
    public boolean doDelete(int id) throws SQLException {
        // Controlla se il prodotto è presente in qualche ordine (integrità
        // referenziale)
        String checkSql = "SELECT COUNT(*) FROM dettaglio_ordine WHERE prodotto_id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            psCheck.setInt(1, id);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Prodotto ordinato: non si può cancellare
                }
            }
        }
        // Se non ci sono riferimenti, procede con la cancellazione
        String deleteSql = "DELETE FROM prodotto WHERE id = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Cerca prodotti per nome (match parziale, case-insensitive grazie a LIKE).
     * Utilizzato per l'autocompletamento AJAX.
     * 
     * @param query stringa di ricerca (almeno 2 caratteri di solito)
     * @return lista di prodotti (massimo 20 risultati)
     * @throws SQLException in caso di errore DB
     */
    public List<Prodotto> searchProducts(String query) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String sql = "SELECT * FROM prodotto WHERE nome LIKE ? LIMIT 20";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPiattaforma(rs.getString("piattaforma"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    p.setPopolare(rs.getInt("popolare") == 1);
                    prodotti.add(p);
                }
            }
        }
        return prodotti;
    }

    /**
     * Cerca prodotti con filtro testuale (nome) più filtri aggiuntivi (piattaforma,
     * ordinamento, paginazione).
     */
    public List<Prodotto> searchProductsWithFilters(String query, int offset, int limit, String platform, String sort)
            throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM prodotto WHERE nome LIKE ?");

        if (platform != null && !platform.isEmpty()) {
            sql.append(" AND piattaforma LIKE ?");
        }

        if (sort != null) {
            switch (sort) {
                case "prezzo_asc":
                    sql.append(" ORDER BY prezzo ASC");
                    break;
                case "prezzo_desc":
                    sql.append(" ORDER BY prezzo DESC");
                    break;
                default:
                    sql.append(" ORDER BY id");
            }
        } else {
            sql.append(" ORDER BY id");
        }
        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setString(idx++, "%" + query + "%");
            if (platform != null && !platform.isEmpty()) {
                ps.setString(idx++, "%" + platform + "%");
            }
            ps.setInt(idx++, limit);
            ps.setInt(idx, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPiattaforma(rs.getString("piattaforma"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    p.setPopolare(rs.getInt("popolare") == 1);
                    prodotti.add(p);
                }
            }
        }
        return prodotti;
    }

    /**
     * Conteggio dei prodotti che soddisfano una ricerca testuale (con eventuale
     * filtro piattaforma).
     */
    public int countSearchProducts(String query, String platform) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM prodotto WHERE nome LIKE ?");
        if (platform != null && !platform.isEmpty()) {
            sql.append(" AND piattaforma LIKE ?");
        }
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setString(idx++, "%" + query + "%");
            if (platform != null && !platform.isEmpty()) {
                ps.setString(idx++, "%" + platform + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt(1);
            }
        }
        return 0;
    }
}