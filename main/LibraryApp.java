package main;

import model.Book;
import model.BookDAO;
import model.BookRepository;
import model.LoanDAO;
import model.User;
import model.UserDAO;
import view.LoginView;
import view.AdminDashboard;
import view.UserDashboard;
import view.BookManagementView;
import view.UserManagementView;
import view.UserProfileView;
import view.BorrowManagementView;
import view.BookPageView;
import view.BookReturnView;
import view.BookUpdateView;
import view.BorrowDialog;
import view.BorrowHistoryView;
import view.BookDialog;
import javax.swing.JOptionPane;
import controller.AuthController;
import controller.BookController;
import controller.UserController;
import controller.UserProfileController;
import controller.BookPageController;
import controller.BorrowDialogController;
import controller.BorrowHistoryController;
import controller.BorrowManagementController;

public class LibraryApp {

    public static void main(String[] args) {
    	User user = new User();
        Book book = new Book();

        LoginView loginView = new LoginView();
        AdminDashboard adminDashboard = new AdminDashboard();
        UserDashboard userDashboard = new UserDashboard(null);
        
        BookManagementView bookManagementView = new BookManagementView();
        UserManagementView userManagementView = new UserManagementView();
        BorrowManagementView borrowManagementView = new BorrowManagementView();
        UserProfileView userProfileView = new UserProfileView(userDashboard, true);
        BookDialog bookDialog = new BookDialog(bookManagementView, "Add Book");
        BorrowHistoryView historyview = new BorrowHistoryView();

        LoanDAO loanDAO = new LoanDAO();
        BorrowDialog dialogView = new BorrowDialog(null);
        BookPageView pageview = new BookPageView(dialogView);

        //BorrowDialogController borrowController = new BorrowDialogController(dialogView, null, user, book, loanResp);

        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        BookRepository resp= new BookRepository();
        
        BookPageController bookPageController = new BookPageController(pageview, user, userDashboard, dialogView,resp);
        BookReturnView returnView = new BookReturnView(historyview); 
        BookUpdateView updateView= new BookUpdateView(bookManagementView);
        BorrowDialogController borrowController = new BorrowDialogController(dialogView, pageview, user, book, loanDAO);
        BorrowManagementController borrowManagementController = new BorrowManagementController(borrowManagementView, loanDAO);

        AuthController authController = new AuthController(loginView, adminDashboard, userDashboard, userDAO);
        BookController bookController = new BookController(bookManagementView, bookDAO, bookDialog, updateView);
        UserController userController = new UserController(userManagementView, userDAO);
        BorrowHistoryController historycontroller = new BorrowHistoryController(loanDAO, historyview,returnView);
        historyview.setController(historycontroller);
        
        UserProfileController userProfileController = new UserProfileController(userProfileView);
        
        adminDashboard.addBookManagementListener(e -> {
            adminDashboard.setVisible(false);
            bookController.loadBooks();
            bookManagementView.setVisible(true);
        });

        adminDashboard.addUserManagementListener(e -> {
            adminDashboard.setVisible(false);
            userController.loadUsers();
            userManagementView.setVisible(true);
        });

        adminDashboard.addBorrowManagementListener(e -> {
            adminDashboard.setVisible(false);
            borrowManagementView.setVisible(true);
        });

        userDashboard.addViewBooksListener(e -> {
            User loggedInUser = authController.getCurrentUser();
            BookPageController pageController = new BookPageController(pageview, loggedInUser, userDashboard, dialogView,resp);
            pageController.loadBooks(BookRepository.getAllBooks());
            pageview.setVisible(true);
        });
        
        
        
        userDashboard.addViewProfileListener(e -> {
            User loggedInUser = authController.getCurrentUser();
            userProfileController.showProfile(loggedInUser);
        });

        bookManagementView.addBackButtonListener(e -> {
            bookManagementView.setVisible(false);
            adminDashboard.setVisible(true);
        });

        userManagementView.addBackButtonListener(e -> {
            userManagementView.setVisible(false);
            adminDashboard.setVisible(true);
        });

        borrowManagementView.addBackButtonListener(e -> {
            borrowManagementView.setVisible(false);
            adminDashboard.setVisible(true);
        });

        userDashboard.addBorrowHistoryListener(e ->{
            User loggedInUser = authController.getCurrentUser();
            
            if (loggedInUser != null) {
                historycontroller.loadBorrowHistory(loggedInUser.getId());
                historyview.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(userDashboard, "No user is logged in.");
            }
        });
        
        historyview.addBackListener(e ->{
        	historyview.setVisible(false);
        	userDashboard.setVisible(true);
        });

        loginView.setVisible(true);
    }
}