package controller;


import view.BookPageView;

import view.BorrowDialog;
import view.UserDashboard;
import model.Book;
import model.BookDAO;
import model.BookRepository;
//import model.LoanRepository;
//import model.LoanRepository;
import model.User;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookPageController {
    private BookPageView view;
    private User user;
    private BookDAO bookDAO;
    private BookRepository bookResp;
    private UserDashboard userView;
    private BorrowDialog borrow;

    public BookPageController(BookPageView view, User user, UserDashboard userview,BorrowDialog borrow,BookRepository bookResp) {
        this.view = view;
        this.user = user;
        this.userView=userview;
        this.borrow=borrow;
        this.bookResp=bookResp;
       // this.add= add;

        loadBooks(BookRepository.getAllBooks());

        view.btnSearch.addActionListener(e -> {
            String letter = view.txtGenreLetter.getText();
            if(letter.isEmpty()){
                loadBooks(BookRepository.getAllBooks());
            } else {
                loadBooks(BookRepository.getBooksByGenreLetter(letter));
            }
        });
        
       
    }

    public void loadBooks(List<Book> books){
        view.panelBooks.removeAll();
        for(Book book: books){
        	JPanel card = view.createBookCard(book.getTitle(), book.getAuthor(), book.getImagePath());
            JButton btnBorrow = (JButton) card.getComponent(3);
            btnBorrow.addActionListener(e -> handleBorrowButton(book.getId(),user.getId()));

            view.panelBooks.add(card);
        }
        view.panelBooks.revalidate();
        view.panelBooks.repaint();
    }
    
    private void borrowBook(int bookId){
        if(user == null){
            JOptionPane.showMessageDialog(view,"Please login to borrow books.");
            return;
        }
        
        //LoanDAO.borrowBook(user.getId(),bookId);
        //JOptionPane.showMessageDialog(view,"Book borrowed successfully!");
    }

    public void handleBorrowButton(int bookId, int userId) {
        int availableQuantity = BookRepository.getAvailableQuantity(bookId);
        System.out.println("Available quantity for book ID " + bookId + ": " + availableQuantity);

        if (availableQuantity <= 0) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(view, "Sorry,there is no books available currently.Please wait 1 week!!");
            });
        } 
        else {
            if (this.borrow != null) 
            {
                this.borrow.setUserId(userId);
                this.borrow.setBookId(bookId);

                //BookRepository.decrementQuantity(bookId);
                SwingUtilities.invokeLater(() -> {
                    this.borrow.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(view, "Borrow feature is not available.");
                System.err.println("Error: Borrow dialog is null.");
            }
        }
    }
  
}