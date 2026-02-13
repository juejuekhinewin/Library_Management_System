package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookUpdateView extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JTextField genreField;
    private JTextField yearField;
    private JTextField quantityField;
    private JTextField imagePathField;
    private JLabel imageLabel;
    
    private JButton saveButton;
    private JButton cancelButton;
    private JButton browseButton;

    public BookUpdateView(JFrame parent) {
        super(parent, "Book Details", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(20);
        
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(20);
        
        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(20);
        
        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField(20);
        
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(20);
        
        JLabel imagePathLabel = new JLabel("Image Path:");
        imagePathField = new JTextField(20);
        imagePathField.setEditable(false); 
        
        browseButton = new JButton("Browse...");
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1; formPanel.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(authorLabel, gbc);
        gbc.gridx = 1; formPanel.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(isbnLabel, gbc);
        gbc.gridx = 1; formPanel.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(genreLabel, gbc);
        gbc.gridx = 1; formPanel.add(genreField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(yearLabel, gbc);
        gbc.gridx = 1; formPanel.add(yearField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(quantityLabel, gbc);
        gbc.gridx = 1; formPanel.add(quantityField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(imagePathLabel, gbc);
        gbc.gridx = 1;
        JPanel imagePathPanel = new JPanel(new BorderLayout(5, 0));
        imagePathPanel.add(imagePathField, BorderLayout.CENTER);
        imagePathPanel.add(browseButton, BorderLayout.EAST);
        formPanel.add(imagePathPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(imageLabel, gbc);
        
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBackground(Color.PINK);
        add(mainPanel);
    }
    
    public JTextField getTitleField() { return titleField; }
    public JTextField getAuthorField() { return authorField; }
    public JTextField getIsbnField() { return isbnField; }
    public JTextField getGenreField() { return genreField; }
    public JTextField getYearField() { return yearField; }
    public JTextField getQuantityField() { return quantityField; }
    public JTextField getImagePathField() { return imagePathField; }
    public JLabel getImageLabel() { return imageLabel; }

    public void setBookData(String title, String author, String isbn, String genre, int year, int quantity, String imagePath) {
        titleField.setText(title);
        authorField.setText(author);
        isbnField.setText(isbn);
        genreField.setText(genre);
        yearField.setText(String.valueOf(year));
        quantityField.setText(String.valueOf(quantity));
        imagePathField.setText(imagePath);
        
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        } else {
            imageLabel.setIcon(null);
        }
    }
    
    public void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        genreField.setText("");
        yearField.setText("");
        quantityField.setText("");
        imagePathField.setText("");
        imageLabel.setIcon(null);
    }
    
    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void addCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public void addBrowseButtonListener(ActionListener listener) {
        browseButton.addActionListener(listener);
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
}