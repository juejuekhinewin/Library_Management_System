package controller;

import model.Book;
import model.BookDAO;
import model.DatabaseDAO;
import view.BookManagementView;
import view.BookDialog;
import view.BookUpdateView; // Assuming BookUpdateView is the new dialog class

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.awt.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BookController {
    private BookManagementView bookView;
    private BookDAO bookDao;
    private BookDialog dialog;
    private BookUpdateView updateView;
    private boolean isEditing = false;
    private int editingBookId = -1;
    
    public BookController(BookManagementView bookView, BookDAO bookDao,BookDialog dialog,BookUpdateView updateView) {
        this.bookView = bookView;
        this.bookDao = bookDao;
        this.dialog=dialog;
        this.updateView=updateView;
        
        this.bookView.addAddButtonListener(new AddButtonListener());
        this.bookView.addEditButtonListener(new EditBookListener());
        this.bookView.addDeleteButtonListener(new DeleteBookListener());
        this.bookView.addRefreshButtonListener(new RefreshListener());        
        this.bookView.addBackButtonListener(new BackListener());
        
        //this.dialog.addSaveButtonListener(new SaveBookListener());
        //this.dialog.addCancelButtonListener(new CancelBookListener());
        //this.dialog.addBrowseButtonListener(new BrowseImageListener());      
        //this.bookDialog.addBrowseFileButtonListener(new BrowseFileListener());  
        //this.bookDialog.getBrowseFileButton().addActionListener(e -> handleBrowseFile());

        // New listener to display the PDF
        //this.bookDialog.getDisplayPdfButton().addActionListener(e -> handleDisplayPdf());

        loadBooks();
    }
    
    public void loadBooks() {
        try (Connection conn = DatabaseDAO.getConnection()) {
            String sql = "SELECT * FROM books ORDER BY id ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            bookView.clearTable();
            
            while (rs.next()) {
                bookView.addRowToTable(new Object[]{
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getString("genre"),
                    rs.getInt("publication_year"),
                    rs.getInt("available_quantity"),
                    rs.getString("image_path")
                });
            }
        } catch (SQLException e) {
            bookView.showMessage("Database error: " + e.getMessage());
        }
    }
    
    private void addBook() {
        isEditing = false;
        editingBookId = -1;
        BookDialog bookDialog = new BookDialog(bookView, "Add New Book");
        bookDialog.addSaveButtonListener(new SaveBookListener(bookDialog));
        bookDialog.addCancelButtonListener(new CancelBookListener(bookDialog));
        bookDialog.addBrowseButtonListener(new BrowseImageListener(bookDialog));
        bookDialog.setVisible(true);
    }
    
    private void editBook(int bookId) {
        isEditing = true;
        editingBookId = bookId;
        
        try (Connection conn = DatabaseDAO.getConnection()) {
            String sql = "SELECT * FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                BookDialog bookDialog = new BookDialog(bookView, "Edit Book");
                bookDialog.setBookData(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getString("genre"),
                    rs.getInt("publication_year"),
                    rs.getInt("available_quantity"),
                    rs.getString("image_path")
                );
                bookDialog.addSaveButtonListener(new SaveBookListener(bookDialog));
                bookDialog.addCancelButtonListener(new CancelBookListener(bookDialog));
                bookDialog.addBrowseButtonListener(new BrowseImageListener(bookDialog));
                bookDialog.setVisible(true);
            }
        } catch (SQLException ex) {
            bookView.showMessage("Database error: " + ex.getMessage());
        }
    }
    
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addBook();
        }
    }
    
    class EditBookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = bookView.getBooksTable().getSelectedRow();
            if (selectedRow == -1) {
                bookView.showMessage("Please select a book to edit.");
                return;
            }

            int bookId = (int) bookView.getBooksTable().getValueAt(selectedRow, 0);
            editBook(bookId);
        }
    }
    
    class DeleteBookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int bookId = bookView.getSelectedBookId();
            
            if (bookId == -1) {
                bookView.showMessage("Please select a book to delete");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(bookView, 
                "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DatabaseDAO.getConnection()) {
                    String sql = "DELETE FROM books WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, bookId);
                    
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        bookView.showMessage("Book deleted successfully");
                        loadBooks();
                    }
                } catch (SQLException ex) {
                    bookView.showMessage("Database error: " + ex.getMessage());
                }
            }
        }
    }
    
    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadBooks();
        }
    }
    
    class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            bookView.setVisible(false);
        }
    }
    
    class SaveBookListener implements ActionListener {
        private BookDialog bookDialog;
        
        public SaveBookListener(BookDialog dialog) {
            this.bookDialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String title = bookDialog.getTitleField().getText();
            String author = bookDialog.getAuthorField().getText();
            String isbn = bookDialog.getIsbnField().getText();
            String genre = bookDialog.getGenreField().getText();
            String yearText = bookDialog.getYearField().getText();
            String quantityText = bookDialog.getQuantityField().getText();
            String imagePath = bookDialog.getImagePathField().getText();
            
            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                bookDialog.showMessage("Title, author, and ISBN are required");
                return;
            }
            
            int year, quantity;
            try {
                year = Integer.parseInt(yearText);
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException ex) {
                bookDialog.showMessage("Year and quantity must be numbers");
                return;
            }
            
            try (Connection conn = DatabaseDAO.getConnection()) {
                if (isEditing) {
                    String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, genre = ?, " +
                                 "publication_year = ?, available_quantity = ?, image_path = ? WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, title);
                    stmt.setString(2, author);
                    stmt.setString(3, isbn);
                    stmt.setString(4, genre);
                    stmt.setInt(5, year);
                    stmt.setInt(6, quantity);
                    stmt.setString(7, imagePath);
                    stmt.setInt(8, editingBookId);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        updateView.showMessage("Book updated successfully");
                    } else {
                        updateView.showMessage("Failed to update book.");
                    }
                } else {
                    String sql = "INSERT INTO books (title, author, isbn, genre, publication_year, available_quantity, image_path) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, title);
                    stmt.setString(2, author);
                    stmt.setString(3, isbn);
                    stmt.setString(4, genre);
                    stmt.setInt(5, year);
                    stmt.setInt(6, quantity);
                    stmt.setString(7, imagePath);
                    int rowsAffected = stmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        dialog.showMessage("Book added successfully");
                    } else {
                        dialog.showMessage("Failed to add book.");
                    }
                }
                
                bookDialog.dispose();
                loadBooks();
            } catch (SQLException ex) {
                bookView.showMessage("Database error: " + ex.getMessage());
            }
        }
    }
    
    class CancelBookListener implements ActionListener {
        private BookDialog bookDialog;
        
        public CancelBookListener(BookDialog dialog) {
            this.bookDialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            bookDialog.dispose();
        }
    }
    
    class BrowseImageListener implements ActionListener {
        private BookDialog bookDialog;

        public BrowseImageListener(BookDialog dialog) {
            this.bookDialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Book Cover Image");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            int result = fileChooser.showOpenDialog(bookDialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();
                bookDialog.getImagePathField().setText(imagePath);
                
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                bookDialog.getImageLabel().setIcon(new ImageIcon(image));
            }
        }
    }
}