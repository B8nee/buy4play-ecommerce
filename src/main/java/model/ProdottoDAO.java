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

public class ProdottoDAO {
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/buy4play");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public List<Prodotto> doRetrieveAll(int offset, int limit, String platform, String sort) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM prodotto WHERE 1=1");

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
    
    public List<Prodotto> doRetrieveAll(int offset, int limit) throws SQLException {
        return doRetrieveAll(offset, limit, null, null);
    }

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
    
    public int countProdotti() throws SQLException {
        return countProdotti(null);
    }

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

    public boolean setPopolare(int productId, boolean popolare) throws SQLException {
        String sql = "UPDATE prodotto SET popolare = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, popolare ? 1 : 0);
            ps.setInt(2, productId);
            return ps.executeUpdate() == 1;
        }
    }
    
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

    public boolean doDelete(int id) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM dettaglio_ordine WHERE prodotto_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            psCheck.setInt(1, id);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false;
                }
            }
        }
        String deleteSql = "DELETE FROM prodotto WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }
    
    public List<Prodotto> searchProducts(String query) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String sql = "SELECT * FROM prodotto WHERE nome LIKE ? LIMIT 10";
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
}