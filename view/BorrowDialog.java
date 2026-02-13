package view;

import javax.swing.*;
import controller.BorrowDialogController;
import model.Loan;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BorrowDialog extends JFrame {
    private JTextField userIdField;
    private JTextField bookIdField;
    private BorrowDialogController controller; 
    public JButton submitBtn;
    private JButton cancelButton;
    private final JTextField tfLoanDate = new JTextField(LocalDate.now().toString());
    private final JTextField tfDueDate = new JTextField(LocalDate.now().plusDays(5).toString());

    public BorrowDialog(BorrowDialogController controller) {
        this.controller = controller;
        setTitle("Borrow Book");
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        userIdField.setEditable(false);
        formPanel.add(userIdField);

        formPanel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        bookIdField.setEditable(false);
        formPanel.add(bookIdField);

        formPanel.add(new JLabel("Borrow Date (YYYY-MM-DD)"));
        formPanel.add(tfLoanDate);

        formPanel.add(new JLabel("Due Date (YYYY-MM-DD)"));
        formPanel.add(tfDueDate);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitBtn = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelButton);

        submitBtn.setEnabled(true);
        submitBtn.setVisible(true);
        formPanel.setBackground(Color.PINK);
        buttonPanel.setBackground(Color.PINK);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public int getUserId() {
        try {
            return Integer.parseInt(userIdField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getBookId() {
        try {
            return Integer.parseInt(bookIdField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public LocalDate getBorrowDate() {
        try {
            return LocalDate.parse(tfLoanDate.getText().trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public LocalDate getDueDate() {
        try {
            return LocalDate.parse(tfDueDate.getText().trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public void setBookId(int bookId) {
        bookIdField.setText(String.valueOf(bookId));
    }

    public void setUserId(int userId) {
        userIdField.setText(String.valueOf(userId));
    }

    public void addSubmitListener(ActionListener listener) {
        submitBtn.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
}