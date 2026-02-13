package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementView extends JFrame 
{
    private JTable usersTable;
    private JButton deleteButton;
    private JButton backButton;
    private JButton refreshButton;
    
    public UserManagementView() 
    {
        setTitle("User Management");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Username", "Email", "Role", "Created At"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable = new JTable(model);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        deleteButton = new JButton("Delete User");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");
        
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.setBackground(Color.PINK);
        scrollPane.setBackground(Color.PINK);
        usersTable.setBackground(Color.PINK);
        buttonPanel.setBackground(Color.PINK);
        add(panel);
    }
    
    public JTable getUsersTable() {
        return usersTable;
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
    
    public int getSelectedUserId() {
        int row = usersTable.getSelectedRow();
        if (row >= 0) {
            return Integer.parseInt(usersTable.getValueAt(row, 0).toString());
        }
        return -1;
    }
    
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);
    }
    
    public void addRowToTable(Object[] rowData) {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.addRow(rowData);
    }
}