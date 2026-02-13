package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.BorrowHistoryController;

import java.awt.*;
import java.awt.event.ActionListener;

public class BorrowHistoryView extends JFrame {
    private JTable table;
    private JButton returnBtn;
    private JButton backBtn;
    private DefaultTableModel tableModel;
    private int currentUserId;
    private BorrowHistoryController controller;

    public BorrowHistoryView() {
        setTitle("Borrow History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Borrow ID","Book Title", "Borrow Date", "Due Date", "Return Date"};
        tableModel = new DefaultTableModel(columns, 0);
       
        table = new JTable(tableModel);
        returnBtn = new JButton("Return Book");
      
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(returnBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        backBtn= new JButton("Back");
        JPanel btnPanel = new JPanel();
        buttonPanel.add(backBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        table.setBackground(Color.PINK);
        buttonPanel.setBackground(Color.PINK);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setHistoryData(java.util.List<String[]> history) {
        tableModel.setRowCount(0); 
        for (String[] row : history) {
            tableModel.addRow(row);
        }
    }

    public int getSelectedRowIndex() {
        return table.getSelectedRow();
    }
    
    public Object[] getSelectedRowData() {
        int selectedRow = getSelectedRowIndex();
        if (selectedRow == -1) {
            return null; 
        }
        int columnCount = tableModel.getColumnCount();
        Object[] rowData = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            rowData[i] = tableModel.getValueAt(selectedRow, i);
        }
        return rowData;
    }
    
    public void addReturnListener(ActionListener listener) {
        returnBtn.addActionListener(listener);
    }
    
   public void addBackListener(ActionListener listener) {
	   backBtn.addActionListener(listener);
   }
    public JFrame getFrame() {
        return this;
    }

    public void refreshTable() {
    	if (controller != null) {
            controller.loadBorrowHistory(this.currentUserId);
        }
    }
    
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }

    public int getCurrentUserId() {
        return this.currentUserId;
    }

    public void setController(BorrowHistoryController controller) {
        this.controller = controller;
    }
    
}