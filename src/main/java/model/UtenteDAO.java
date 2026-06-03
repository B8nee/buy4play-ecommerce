package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UtenteDAO {
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
}