package controller;

import model.User;
import model.UserDAO;
import model.DatabaseDAO;
import view.LoginView;
import view.AdminDashboard;
import view.UserDashboard;
//import view.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {
    private LoginView loginView;
    private AdminDashboard adminDashboard;
    private UserDashboard userDashboard;
    private User currentUser;
   
    
    public AuthController(LoginView loginView, AdminDashboard adminDashboard, UserDashboard userDashboard, UserDAO userDAO) {
        this.loginView = loginView;
        this.adminDashboard = adminDashboard;
        this.userDashboard = userDashboard;
        
        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addRegisterListener(new RegisterListener());
        this.adminDashboard.addLogoutListener(new LogoutListener());
        this.userDashboard.addLogoutListener(new LogoutListener());
        this.userDashboard.addBorrowHistoryListener(new BorrowHistoryListener());
           
    }
    
     
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            
            if (username.isEmpty() || password.isEmpty()) {
                loginView.showMessage("Please enter both username and password");
                return;
            }
            
            try (Connection conn = DatabaseDAO.getConnection()) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    currentUser = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role")
                    );
                    
                    loginView.setVisible(false);
                    loginView.clearFields();
                    
                    if ("admin".equals(currentUser.getRole())) {
                        adminDashboard.setVisible(true);
                    } else {
                        userDashboard.setVisible(true);
                        
                    }
                } else {
                    loginView.showMessage("Invalid username or password");
                }
            } catch (SQLException ex) {
                loginView.showMessage("Database error: " + ex.getMessage());
            }
        }
    }
    
    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            String email = loginView.getUsername() + "@gmail.com";
            
            if (username.isEmpty() || password.isEmpty()) {
                loginView.showMessage("Please enter both username and password");
                return;
            }
            
            if (password.length() < 6) {
                loginView.showMessage("Password must be at least 6 characters long");
                return;
            }
            
            try (Connection conn =DatabaseDAO.getConnection()) {
                String checkSql = "SELECT id FROM users WHERE username = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    loginView.showMessage("Username already exists");
                    return;
                }
                
                // Create new user
                String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, 'user')";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    loginView.showMessage("Registration successfully. Please login.");
                    loginView.clearFields();
                }
            } catch (SQLException ex) {
                loginView.showMessage("Database error: " + ex.getMessage());
            }
        }
    }
    
    class BorrowHistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	
        }
    }
    
 
    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adminDashboard.setVisible(false);
            userDashboard.setVisible(false);
            loginView.setVisible(true);
            currentUser = null;
        }
    }
    
    public User getCurrentUser() {
        return currentUser;
    
    }
}