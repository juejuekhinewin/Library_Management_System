package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Loan;

public class BorrowManagementView extends JFrame {
    private JTable borrowTable;
    private JButton returnButton;
    private JButton backButton;
    private JButton refreshButton;
    private DefaultTableModel model;
    private JComboBox<String> statusComboBox;
    private JTextField searchUserField;
    private JTextField searchBookField;
    private JButton filterButton;

    public BorrowManagementView() {
        setTitle("Borrow Management");
        setSize(900, 550);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] statusOptions = {"All", "Issued", "Returned", "Overdue"};
        statusComboBox = new JComboBox<>(statusOptions);

        searchUserField = new JTextField(10);
        searchBookField = new JTextField(10);

        filterButton = new JButton("Filter");

        filterPanel.add(new JLabel("Status:"));
        filterPanel.add(statusComboBox);
        filterPanel.add(new JLabel("User:"));
        filterPanel.add(searchUserField);
        filterPanel.add(new JLabel("Book:"));
        filterPanel.add(searchBookField);
        filterPanel.add(filterButton);
        panel.add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "User", "Book", "Borrow Date", "Due Date", "Return Date", "Status"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowTable = new JTable(model);
        borrowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(borrowTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        returnButton = new JButton("Mark as Returned");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");

        buttonPanel.add(returnButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        panel.setBackground(Color.PINK);
        scrollPane.setBackground(Color.PINK);
        borrowTable.setBackground(Color.PINK);
        buttonPanel.setBackground(Color.PINK);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public JTable getBorrowTable() {
        return borrowTable;
    }

    public JComboBox<String> getStatusComboBox() {
        return statusComboBox;
    }

    public JTextField getSearchUserField() {
        return searchUserField;
    }

    public JTextField getSearchBookField() {
        return searchBookField;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public void addReturnButtonListener(java.awt.event.ActionListener listener) {
        returnButton.addActionListener(listener);
    }

    public void addRefreshButtonListener(java.awt.event.ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void addBackButtonListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addFilterButtonListener(java.awt.event.ActionListener listener) {
        filterButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public int getSelectedBorrowId() {
        int row = borrowTable.getSelectedRow();
        if (row >= 0) {
            return (int) model.getValueAt(row, 0);
        }
        return -1;
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void populateTable(List<Loan> records) {
        clearTable();
        for (Loan record : records) {
            model.addRow(new Object[]{
                record.getId(),
                record.getUsername(),
                record.getBookTitle(),
                record.getBorrowDate(),
                record.getDueDate(),
                record.getReturnDate(),
                record.getStatus()
            });
        }
    }
}
