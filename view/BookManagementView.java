package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookManagementView extends JFrame 
{
    private JTable booksTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JButton refreshButton;
    
    public BookManagementView() {
        setTitle("Book Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Title", "Author", "ISBN", "Genre", "Year", "Quantity", "Image"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable = new JTable(model);
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(booksTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Book");
        editButton = new JButton("Edit Book");
        deleteButton = new JButton("Delete Book");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.setBackground(Color.PINK);
        scrollPane.setBackground(Color.PINK);
        booksTable.setBackground(Color.PINK);
        buttonPanel.setBackground(Color.PINK);
        add(panel);
    }
    
    public JTable getBooksTable() {
        return booksTable;
    }
    
    public void addAddButtonListener(java.awt.event.ActionListener listener) {
        addButton.addActionListener(listener);
    }
    
    public void addEditButtonListener(java.awt.event.ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void addDeleteButtonListener(java.awt.event.ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void addRefreshButtonListener(java.awt.event.ActionListener listener) {
        refreshButton.addActionListener(listener);
    }
    
    public void addBackButtonListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public int getSelectedBookId() {
        int row = booksTable.getSelectedRow();
        if (row >= 0) {
            return Integer.parseInt(booksTable.getValueAt(row, 0).toString());
        }
        return -1;
    }
    
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);
    }
    
    public void addRowToTable(Object[] rowData) {
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.addRow(rowData);
    }
}