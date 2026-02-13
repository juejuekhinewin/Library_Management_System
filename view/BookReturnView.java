package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookReturnView extends JDialog {
    private JButton confirmReturnButton;
    private JLabel loanIdLabel;
    private JLabel bookTitleLabel;
    private JLabel dueDateLabel;
    private int loanId;

    public BookReturnView(JFrame parentFrame) {
        super(parentFrame, "Confirm Return", true); 
        setSize(400, 330);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        loanIdLabel = new JLabel("Loan ID: ");
        bookTitleLabel = new JLabel("Book Title: ");
        dueDateLabel = new JLabel("Due Date: ");
        confirmReturnButton = new JButton("Confirm Return");

        panel.add(loanIdLabel);
        panel.add(bookTitleLabel);
        panel.add(dueDateLabel);
        
        add(panel, BorderLayout.CENTER);
        panel.setBackground(Color.PINK);
        add(confirmReturnButton, BorderLayout.SOUTH);
    }

    public void setLoanDetails(int loanId, String bookTitle, String dueDate) {
        this.loanId = loanId;
        loanIdLabel.setText("Borrow ID: " + loanId);
        bookTitleLabel.setText("Book Title: " + bookTitle);
        dueDateLabel.setText("Due Date: " + dueDate);
    }

    public void addConfirmReturnListener(ActionListener listener) {
        confirmReturnButton.addActionListener(listener);
    }
    
    
    public int getLoanId() {
        return this.loanId;
    }
}