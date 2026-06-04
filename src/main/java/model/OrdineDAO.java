package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

public class OrdineDAO {
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/buy4play");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int salvaOrdine(Ordine ordine) throws SQLException {
        String sql = "INSERT INTO ordine (utente_id, totale, indirizzo_spedizione) VALUES (?, ?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ordine.getUtenteId());
            ps.setDouble(2, ordine.getTotale());
            ps.setString(3, ordine.getIndirizzoSpedizione());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Impossibile ottenere ID ordine");
            }
        }
    }

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
                    ordini.add(o);
                }
            }
        }
        return ordini;
    }

    public List<DettaglioOrdine> getDettagliByOrdine(int ordineId) throws SQLException {
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        String sql = "SELECT * FROM dettaglio_ordine WHERE ordine_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DettaglioOrdine d = new DettaglioOrdine();
                    d.setId(rs.getInt("id"));
                    d.setOrdineId(rs.getInt("ordine_id"));
                    d.setProdottoId(rs.getInt("prodotto_id"));
                    d.setQuantita(rs.getInt("quantita"));
                    d.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    d.setIva(rs.getDouble("iva"));
                    ProdottoDAO pdao = new ProdottoDAO();
                    Prodotto p = pdao.doRetrieveById(d.getProdottoId());
                    d.setProdotto(p);
                    dettagli.add(d);
                }
            }
        }
        return dettagli;
    }
    
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
                    return o;
                }
            }
        }
        return null;
    }
    
    public List<Ordine> getOrdiniConFiltri(String dataInizio, String dataFine, String clienteId) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ordine WHERE 1=1");
        if (dataInizio != null && !dataInizio.isEmpty()) sql.append(" AND data_ordine >= '").append(dataInizio).append("'");
        if (dataFine != null && !dataFine.isEmpty()) sql.append(" AND data_ordine <= '").append(dataFine).append(" 23:59:59'");
        if (clienteId != null && !clienteId.isEmpty()) sql.append(" AND utente_id = ").append(clienteId);
        sql.append(" ORDER BY data_ordine DESC");
        try (Connection conn = ds.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql.toString())) {
            while (rs.next()) {
                Ordine o = new Ordine();
                o.setId(rs.getInt("id"));
                o.setUtenteId(rs.getInt("utente_id"));
                o.setDataOrdine(rs.getTimestamp("data_ordine"));
                o.setTotale(rs.getDouble("totale"));
                o.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
                ordini.add(o);
            }
        }
        return ordini;
    }
}
