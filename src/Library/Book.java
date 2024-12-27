package Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Library.DatabaseConnection;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }

    public static void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author, is_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setBoolean(4, true);
            pstmt.executeUpdate();
        }
    }

    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author")
                );
                book.isAvailable = rs.getBoolean("is_available");
                books.add(book);
            }
        }
        return books;
    }
}
