package Library;

import java.sql.*;
import Library.DatabaseConnection;

public class Library {
    public boolean borrowBook(String memberId, String isbn) throws SQLException {
        String checkBook = "SELECT is_available FROM books WHERE isbn = ?";
        String borrowBook = "UPDATE books SET is_available = false WHERE isbn = ?";
        String logBorrow = "INSERT INTO borrow_log (member_id, isbn) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkBook);
             PreparedStatement borrowStmt = conn.prepareStatement(borrowBook);
             PreparedStatement logStmt = conn.prepareStatement(logBorrow)) {

            checkStmt.setString(1, isbn);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getBoolean("is_available")) {
                conn.setAutoCommit(false);
                borrowStmt.setString(1, isbn);
                borrowStmt.executeUpdate();

                logStmt.setString(1, memberId);
                logStmt.setString(2, isbn);
                logStmt.executeUpdate();

                conn.commit();
                conn.setAutoCommit(true);
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(String isbn) throws SQLException {
        String returnBook = "UPDATE books SET is_available = true WHERE isbn = ?";
        String updateLog = "UPDATE borrow_log SET return_date = CURRENT_TIMESTAMP WHERE isbn = ? AND return_date IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement returnStmt = conn.prepareStatement(returnBook);
             PreparedStatement logStmt = conn.prepareStatement(updateLog)) {

            conn.setAutoCommit(false);
            returnStmt.setString(1, isbn);
            logStmt.setString(1, isbn);

            returnStmt.executeUpdate();
            logStmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        }
    }
}
