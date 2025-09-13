package db;

import java.sql.*;

public class Database {
    // SQLite file will appear in your project folder when first used
    private static final String DB_URL = "jdbc:sqlite:birdsenger.db";

    // Call this once at app start (and controllers do as well, safe to call multiple times)
    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement()) {

            // Create the users table if it doesn't exist
            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                  id            INTEGER PRIMARY KEY AUTOINCREMENT,
                  email         TEXT NOT NULL UNIQUE,
                  username      TEXT NOT NULL UNIQUE,
                  password_hash TEXT NOT NULL,
                  created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
            """);

            System.out.println("[DB] Ready ✅ (using birdsenger.db)");

        } catch (SQLException e) {
            // If something is wrong with the DB path or permissions
            System.err.println("[DB] Init error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Anywhere else in code can call this to read/write
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}

