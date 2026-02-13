package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BookDAO {
	
	public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, genre, is_available) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseDAO.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, book.getTitle());
            pst.setString(2, book.getAuthor());
            pst.setString(3, book.getGenre());
            pst.setInt(4, book.getAvailableQuantity());
            //pst.setString(5, book.getFilePath());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, genre = ?, is_available = ?, pdf_link = ? WHERE book_id = ?";
        try (Connection con = DatabaseDAO.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, book.getTitle());
            pst.setString(2, book.getAuthor());
            pst.setString(3, book.getGenre());
            pst.setInt(4, book.getAvailableQuantity());
            //pst.setString(5, book.getFilePath());
            pst.setInt(6, book.getId());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }  
    
    public Book getBookById(int bookId) {
        String query = "SELECT * FROM books WHERE book_id = ?";
        try (Connection con = DatabaseDAO.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setGenre(rs.getString("genre"));
                    book.setAvailableQuantity(rs.getInt("available_quantity"));
                    //book.setFilePath(rs.getString("pdfFilePath"));
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    
    public String getPdfLinkByBookId(int bookId) {
        String query = "SELECT pdfFilePath FROM books WHERE id = ?";
        try (Connection con = DatabaseDAO.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("pdfFilePath");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection con = DatabaseDAO.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setAvailableQuantity(rs.getInt("available_quantity"));
                //book.setFilePath(rs.getString("pdfFilePath"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}