package view;

import javax.swing.*;
import controller.AuthController;
import model.User;
import model.UserDAO;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserDashboard extends JFrame {
    private JButton viewBooksButton;
    private JButton borrowHistoryButton;
    private JButton viewProfileButton;
    private JButton logoutButton;
    private JLabel welcomeLabel;
    
    private AuthController authController;
    
	public UserDashboard(AuthController auth) {
		this.authController=auth;
        setTitle("Library Management System - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("User Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        viewBooksButton = new JButton("View Books");
        viewBooksButton.setPreferredSize(new Dimension(200, 50));
        panel.add(viewBooksButton, gbc);
        
        gbc.gridy = 2;
        borrowHistoryButton = new JButton("Borrow History");
        borrowHistoryButton.setPreferredSize(new Dimension(200, 50));
        panel.add(borrowHistoryButton, gbc);
        
        gbc.gridy = 3;
        viewProfileButton = new JButton("View Profile");
        viewProfileButton.setPreferredSize(new Dimension(200, 50));
        panel.add(viewProfileButton, gbc);
        
        gbc.gridy = 4;
        logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(200, 50));
        panel.add(logoutButton, gbc);
        panel.setBackground(Color.PINK);
        
        add(panel);
       
    }
    
   
    public void addViewBooksListener(ActionListener listener) {
        viewBooksButton.addActionListener(listener);
    }
    
    public void addBorrowHistoryListener(ActionListener listener) {
        borrowHistoryButton.addActionListener(listener);
    }
    
    public void addViewProfileListener(java.awt.event.ActionListener listener) {
    	viewProfileButton.addActionListener(listener);
    }
    
    public void addLogoutListener(java.awt.event.ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

	
}