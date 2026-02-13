package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (
        	Connection conn = DatabaseDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                		rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getString("genre"),
                        rs.getInt("publication_year"),
                        rs.getInt("available_quantity"),
                        rs.getString("image_path")
                       
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> getBooksByGenreLetter(String letter) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE genre LIKE ?";
        try (Connection conn = DatabaseDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, letter + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                		rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getString("genre"),
                        rs.getInt("publication_year"),
                        rs.getInt("available_quantity"),
                        rs.getString("image_path")
                        
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public static int getAvailableQuantity(int bookId) {
        String sql = "SELECT available_quantity FROM books WHERE id = ?";
        try (Connection conn = DatabaseDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                return rs.getInt("available_quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }
    
   
}