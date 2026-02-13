package controller;

import model.LoanDAO;
import view.BookReturnView;
import view.BorrowHistoryView;

import java.time.LocalDate;
import javax.swing.JOptionPane;

public class BookReturnController {
    private LoanDAO loanDAO;
    private BookReturnView view;
    private BorrowHistoryView historyView;

    public BookReturnController(LoanDAO loanDAO, BookReturnView view, BorrowHistoryView historyView) {
        this.loanDAO = loanDAO;
        this.view = view;
        this.historyView = historyView;
        this.view.addConfirmReturnListener(e -> handleReturnBook());
    }

    private void handleReturnBook() {
        try {
            int loanId = view.getLoanId();
            if (loanId == -1) {
                JOptionPane.showMessageDialog(view, "Loan ID is missing.");
                return;
            }

            LocalDate returnDate = LocalDate.now();
            LocalDate dueDate = loanDAO.getDueDate(loanId);
            String status;

            if (dueDate != null) {
                if (returnDate.isAfter(dueDate)) {
                    status = "overdue";
                    JOptionPane.showMessageDialog(view, "The book is OVERDUE!");
                } else {
                    status = "returned";
                    JOptionPane.showMessageDialog(view, "The book has been returned successfully.");
                }

                boolean success = loanDAO.updateLoanStatus(loanId, returnDate, status);
                if (success) {
                    // Update the borrow history view by reloading its data
                    historyView.refreshTable();
                    //view.dispose(); // Close the current view (the dialog)
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update loan status.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Due date not found for this loan record.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "An error occurred: " + ex.getMessage());
        }
    }
}