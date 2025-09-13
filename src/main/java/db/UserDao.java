package db;

import util.Password;
import java.sql.*;

public class UserDao {

    // Create a new user; returns true if success, false if duplicate/failed
    public boolean register(String email, String username, String plainPassword) {
        String sql = "INSERT INTO users (email, username, password_hash) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email.trim().toLowerCase());
            ps.setString(2, username.trim().toLowerCase());
            ps.setString(3, Password.hash(plainPassword));
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            // UNIQUE constraint violations land here
            System.err.println("[DAO] Register error: " + e.getMessage());
            return false;
        }
    }

    // Try to login using username OR email + password
    public boolean login(String usernameOrEmail, String plainPassword) {
        String sql = """
            SELECT password_hash FROM users
            WHERE email = ? OR username = ?
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = usernameOrEmail.trim().toLowerCase();
            ps.setString(1, key);
            ps.setString(2, key);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                String hash = rs.getString("password_hash");
                return Password.matches(plainPassword, hash);
            }

        } catch (SQLException e) {
            System.err.println("[DAO] Login error: " + e.getMessage());
            return false;
        }
    }
}

