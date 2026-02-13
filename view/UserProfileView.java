package view;

import javax.swing.*;
import java.awt.GridLayout;

public class UserProfileView extends JDialog 
{
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel roleLabel;

    public UserProfileView(JFrame parent, boolean modal) {
        super(parent, "User Profile", modal);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameLabel = new JLabel();
        add(usernameLabel);

        add(new JLabel("Email:"));
        emailLabel = new JLabel();
        add(emailLabel);

        add(new JLabel("Role:"));
        roleLabel = new JLabel();
        add(roleLabel);
        setLocationRelativeTo(parent);
        
    }

    public void setProfileData(String username, String email, String role) 
    {
        usernameLabel.setText(username);
        emailLabel.setText(email);
        roleLabel.setText(role);
    }
}