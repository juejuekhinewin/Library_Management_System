package controller;

import view.BorrowManagementView;
import model.Loan;
import model.LoanDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BorrowManagementController {
    private BorrowManagementView view;
    private LoanDAO loanDAO;

    public BorrowManagementController(BorrowManagementView view, LoanDAO loanDAO) {
        this.view = view;
        this.loanDAO = loanDAO;

        loadBorrowedData();

        view.addReturnButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsReturned();
            }
        });

        view.addRefreshButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBorrowedData();
            }
        });

        view.addFilterButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilter();
            }
        });
    }

    public void loadBorrowedData() {
        try {
            List<Loan> loans = loanDAO.searchLoans("All", "", "");
            view.populateTable(loans);
        } catch (SQLException ex) {
            view.showMessage("Error loading borrow records: " + ex.getMessage());
        }
    }

    private void applyFilter() {
        String selectedStatus = (String) view.getStatusComboBox().getSelectedItem();
        String userKeyword = view.getSearchUserField().getText().trim();
        String bookKeyword = view.getSearchBookField().getText().trim();

        try {
            List<Loan> filteredLoans = loanDAO.searchLoans(selectedStatus, userKeyword, bookKeyword);
            view.populateTable(filteredLoans);
        } catch (SQLException ex) {
            view.showMessage("Error applying filter: " + ex.getMessage());
        }
    }

    private void markAsReturned() {
        int borrowId = view.getSelectedBorrowId();
      
        if (borrowId != -1) {
            LocalDate dueDate;
            try {
                dueDate = loanDAO.getDueDate(borrowId);
            } catch (SQLException ex) {
                view.showMessage("Error retrieving due date: " + ex.getMessage());
                return;
            }

            LocalDate returnDate = LocalDate.now();
            String status = "RETURNED";
            if (returnDate.isAfter(dueDate)) {
                status = "OVERDUE";
            }

            boolean success = loanDAO.updateLoanStatus(borrowId, returnDate, status);
            if (success) {
                view.showMessage("Book successfully marked as returned.");
            } else {
                view.showMessage("Failed to mark book as returned.");
            }
            loadBorrowedData();
        } else {
            view.showMessage("Please select a record to mark as returned.");
        }
    }
}
