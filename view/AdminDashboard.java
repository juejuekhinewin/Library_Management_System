package view;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private JButton bookManagementButton;
    private JButton userManagementButton;
    private JButton borrowManagementButton;
    private JButton logoutButton;
    
    public AdminDashboard() {
        setTitle("Library Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        bookManagementButton = new JButton("Book Management");
        bookManagementButton.setPreferredSize(new Dimension(200, 50));
        panel.add(bookManagementButton, gbc);
        
        gbc.gridy = 2;
        userManagementButton = new JButton("User Management");
        userManagementButton.setPreferredSize(new Dimension(200, 50));
        panel.add(userManagementButton, gbc);
        
        gbc.gridy = 3;
        borrowManagementButton = new JButton("Borrow Management");
        borrowManagementButton.setPreferredSize(new Dimension(200, 50));
        panel.add(borrowManagementButton, gbc);
        
        gbc.gridy = 4;
        logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(200, 50));
        panel.add(logoutButton, gbc);
        panel.setBackground(Color.PINK);
        
        add(panel);
    }
    
    public void addBookManagementListener(java.awt.event.ActionListener listener) {
        bookManagementButton.addActionListener(listener);
    }
    
    public void addUserManagementListener(java.awt.event.ActionListener listener) {
        userManagementButton.addActionListener(listener);
    }
    
    public void addBorrowManagementListener(java.awt.event.ActionListener listener) {
        borrowManagementButton.addActionListener(listener);
    }
    
    public void addLogoutListener(java.awt.event.ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}