package model;

import java.sql.*;
import java.util.UUID;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

public class RememberMeDAO {
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

    public static String generaSerie() {
        return UUID.randomUUID().toString();
    }

    public void salvaToken(int utenteId, String serie, String token, long durataSecondi) throws SQLException {
        String sql = "DELETE FROM remember_me WHERE utente_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.executeUpdate();
        }
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

    public void eliminaToken(String serie) throws SQLException {
        String sql = "DELETE FROM remember_me WHERE serie = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serie);
            ps.executeUpdate();
        }
    }

    public void eliminaTokenPerUtente(int utenteId) throws SQLException {
        String sql = "DELETE FROM remember_me WHERE utente_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.executeUpdate();
        }
    }
}