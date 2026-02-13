package controller;

import model.DatabaseDAO;
import model.User;
import model.UserDAO;
import view.BookDialog;
import view.UserDashboard;
import view.UserManagementView;
import view.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.BookController.AddButtonListener;
import controller.BookController.BrowseImageListener;
import controller.BookController.CancelBookListener;
import controller.BookController.SaveBookListener;


public class UserController 
{
    private UserManagementView userView;
    
    public UserController(UserManagementView userView, UserDAO userDAO) 
    {
        this.userView = userView; 
        this.userView.addDeleteButtonListener(new DeleteUserListener());
        this.userView.addRefreshButtonListener(new RefreshListener());
        this.userView.addBackButtonListener(new BackListener());
        
    }
    
    
    public void loadUsers() {
        try (Connection conn = DatabaseDAO.getConnection()) {
            String sql = "SELECT * FROM users WHERE role = 'user'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            userView.clearTable();
            
            while (rs.next()) {
                userView.addRowToTable(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at")
                });
            }
        } catch (SQLException e) {
            userView.showMessage("Database error: " + e.getMessage());
        }
    }
    
    
    class ProfileViewListener implements ActionListener{
    	 @Override
         public void actionPerformed(ActionEvent e) {
    		 
    		 
    	 }
    }
    
    class DeleteUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userId = userView.getSelectedUserId();
            if (userId == -1) {
                userView.showMessage("Please select a user to delete");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(userView, 
                "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DatabaseDAO.getConnection()) {
                    String sql = "DELETE FROM users WHERE id = ? AND role != 'admin'";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, userId);
                    
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        userView.showMessage("User deleted successfully");
                        loadUsers();
                    } else {
                        userView.showMessage("Cannot delete admin users");
                    }
                } catch (SQLException ex) {
                    userView.showMessage("Database error: " + ex.getMessage());
                }
            }
        }
    }
    
    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadUsers();
        }
    }
    
    class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userView.setVisible(false);
        }
    }
}