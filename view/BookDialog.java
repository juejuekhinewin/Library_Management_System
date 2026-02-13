package view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BookDialog extends JDialog {
	private JTextField titleField, authorField, isbnField, genreField, yearField, quantityField, imagePathField;
    private JButton saveButton, cancelButton, browseButton,browseFileButton;
    private JLabel imageLabel;
    
    public BookDialog(JFrame parent, String title) {
        super(parent, title, true);
        setSize(1000, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        browseButton = new JButton("Browse Image");
        imagePanel.add(browseButton, BorderLayout.SOUTH);
        
        browseFileButton = new JButton("Browse file");
        browseFileButton.setPreferredSize(new Dimension(100, 40));  
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 10;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(imagePanel, gbc);
        
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        
        titleField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(titleField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Author:"), gbc);
        
        authorField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(authorField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(new JLabel("ISBN:"), gbc);
        
        isbnField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(isbnField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(new JLabel("Genre:"), gbc);
        
        genreField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(genreField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(new JLabel("Published Year:"), gbc);
        
        yearField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(yearField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(new JLabel("Quantity:"), gbc);
        
        quantityField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(quantityField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(new JLabel("Image Path:"), gbc);
        
        imagePathField = new JTextField(20);
        gbc.gridx = 2;
        panel.add(imagePathField, gbc);
        
      
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        add(panel);
        panel.setBackground(Color.PINK);
        buttonPanel.setBackground(Color.PINK);
        
        /*
        browseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select PDF File");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                // Optional: set a file filter to show only PDF files
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
                    }
                    @Override
                    public String getDescription() {
                        return "PDF Documents (*.pdf)";
                    }
                });
                
                int result = fileChooser.showOpenDialog(BookDialog.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    filePathField.setText(filePath);
                }
            }

        });
        */
    }
    
    public JTextField getTitleField() {
        return titleField;
    }
    
    public JTextField getAuthorField() {
        return authorField;
    }
    
    public JTextField getIsbnField() {
        return isbnField;
    }
    
    public JTextField getGenreField() {
        return genreField;
    }
    
    public JTextField getYearField() {
        return yearField;
    }
    
    public JTextField getQuantityField() {
        return quantityField;
    }
    
    public JTextField getImagePathField() {
        return imagePathField;
    }
    /*
    
    public JTextField getDesField() {
        return descriptionField;
    }
    
    public void setFilePathFieldText(String path) {
        filePathField.setText(path);
    }
    
    public JTextField getFilePathField() 
    { 
    	return filePathField; 
    }
    */
   
    public JButton getSaveButton() {
        return saveButton;
    }
    
    public JButton getCancelButton() {
        return cancelButton;
    }
    
    public JButton getBrowseButton() {
        return browseButton;
    }
    
    public JButton getBrowseFileButton() {
        return browseFileButton;
    }
    
    public JLabel getImageLabel() {
        return imageLabel;
    }
    
    public void setBookData(String title, String author, String isbn, String genre, int year, int quantity,String image) {
        titleField.setText(title);
        authorField.setText(author);
        isbnField.setText(isbn);
        genreField.setText(genre);
        yearField.setText(String.valueOf(year));
        quantityField.setText(String.valueOf(quantity));
        imagePathField.setText(image);
        //descriptionField.setText(desc);
        //filePathField.setText(file);
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
    
    public void addBrowseFileButtonListener(ActionListener listener) {
        browseButton.addActionListener(listener);
    }
    
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
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
}
