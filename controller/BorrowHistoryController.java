package controller;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.LoanDAO;
import view.BookReturnView;
import view.BorrowHistoryView;

public class BorrowHistoryController {
    private LoanDAO loandao;
    private BorrowHistoryView view;

    public BorrowHistoryController(LoanDAO loandao, BorrowHistoryView view, BookReturnView returnView) {
        this.loandao = loandao;
        this.view = view;
        this.view.addReturnListener(e -> handleReturnBook());
    }

    public void loadBorrowHistory(int userId) {
        view.setCurrentUserId(userId);
        try {
            List<String[]> history = loandao.getBorrowHistory(userId);
            view.setHistoryData(history);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void handleReturnBook() {
        Object[] selectedData = view.getSelectedRowData();
        if (selectedData == null || selectedData.length < 5) 
        { 
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a row to return a book.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int loanId = Integer.parseInt(selectedData[0].toString());
        String bookTitle = selectedData[1].toString();
        String dueDate = selectedData[3].toString();

        BookReturnView returnView = new BookReturnView(view.getFrame());
        returnView.setLoanDetails(loanId, bookTitle, dueDate);

        new BookReturnController(loandao, returnView, view);
        returnView.setVisible(true);
    }
}