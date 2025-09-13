package db;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:info.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement()) {

            st.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                    id              INTEGER PRIMARY KEY AUTOINCREMENT,
                    email           TEXT NOT NULL UNIQUE,
                    username        TEXT NOT NULL UNIQUE,
                    password_hash   TEXT NOT NULL,
                    created_at      DATENAME NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
