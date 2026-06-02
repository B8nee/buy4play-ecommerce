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

    public List<Prodotto> doRetrieveAll(int offset, int limit) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String sql = "SELECT * FROM prodotto ORDER BY id LIMIT ? OFFSET ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPiattaforma(rs.getString("piattaforma"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    prodotti.add(p);
                }
            }
        }
        return prodotti;
    }
    
    public int countProdotti() throws SQLException {
        String sql = "SELECT COUNT(*) FROM prodotto";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
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
                    return p;
                }
            }
        }
        return null;
    }
}