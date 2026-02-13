package view;

import javax.swing.*;
import controller.BookPageController;
import model.Book;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookPageView extends JFrame {
    public JTextField txtGenreLetter;
    public JButton btnSearch;
    public JPanel panelBooks;
    private CardLayout cardlayout;
    private BorrowDialog borrowDialog;
   
    public BookPageView(BorrowDialog borrow) {
        setTitle("Books");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Filter by Genre Letter:"));
        txtGenreLetter = new JTextField(5);
        btnSearch = new JButton("Search");
        topPanel.add(txtGenreLetter);
        topPanel.add(btnSearch);
        add(topPanel, BorderLayout.NORTH);

        panelBooks = new JPanel(new GridLayout(0,3,10,10));
        JScrollPane scrollPane = new JScrollPane(panelBooks);
        add(scrollPane, BorderLayout.CENTER);
        
        topPanel.setBackground(Color.PINK);
        scrollPane.setBackground(Color.PINK);
    }
    
    

    public JPanel createBookCard(String title, String author, String imagePath) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        ImageIcon icon = new ImageIcon(imagePath);
        Image scaled = icon.getImage().getScaledInstance(150,200,Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(scaled), JLabel.CENTER);
        
        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        JLabel lblAuthor = new JLabel("by " + author, JLabel.CENTER);
        JButton btnBorrow = new JButton("Borrow");
        //btnBorrow.addActionListener(borrowListener);
        

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblImage, BorderLayout.CENTER);
        card.add(lblAuthor, BorderLayout.SOUTH);
        card.add(btnBorrow, BorderLayout.PAGE_END);

        return card;
    }

}