package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String password = "Kom0852@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, password);
    }
}
