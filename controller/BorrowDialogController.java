package controller;

import view.BorrowDialog;
import view.BookPageView;
import model.Book;
import model.LoanDAO;

import model.User;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;


public class BorrowDialogController {
    private final BorrowDialog dialog;
    private final BookPageView parentView;
    private LoanDAO loan;
    private final User user;
    private final Book book;

    
    public BorrowDialogController(BorrowDialog dialog, BookPageView parentView, User user, Book book, LoanDAO loan) {
        this.dialog = dialog;
        this.parentView = parentView;
        this.user = user;
        this.book = book;
        this.loan = loan;

        dialog.setUserId(user.getId());
        dialog.setBookId(book.getId());
        dialog.addSubmitListener(e -> handleSubmit());
        dialog.addCancelListener(e -> handleCancel());
        
    }
    
    public void handleSubmit() {
        
        /*
        if (borrowDate == null) {
            JOptionPane.showMessageDialog(dialog, "Please select  date.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        */
         try {
        	 int userId = dialog.getUserId();
             int bookId= dialog.getBookId();
             LocalDate borrowDate = dialog.getBorrowDate();
             LocalDate dueDate = dialog.getDueDate();
             
			 LoanDAO.borrowBook(userId,bookId,borrowDate,dueDate);
			 JOptionPane.showMessageDialog(dialog,"Book borrowed successfully!");
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //LoanDAO.borrowBook(userId,bookId,borrowDate,dueDate);
         

	    /*
        try {
        	
            boolean success = LoanDAO.borrowBook(userId,bookId,borrowDate,dueDate);
            
            if (success) 
            {
            	//System.out.println("BorrowBook: inserting loan for userId=" + userId + " and bookId=" + bookId);

                JOptionPane.showMessageDialog(dialog, "Title:"+ book.getTitle()+ "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                //parentView.refresh(); // Refresh the book list in the parent view
            } else {
                JOptionPane.showMessageDialog(dialog, "Database error. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialog, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        */
        
    }

    
    private int getUserIdFromInput() {
		// TODO Auto-generated method stub
		return user.getId();
		
	}

	private void handleCancel() {
        dialog.dispose();
    }
}