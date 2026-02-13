package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
	
	public static void borrowBook(int id, int bookId, LocalDate borrowDate, LocalDate dueDate) throws SQLException {
		String sqlLoan = "INSERT INTO borrow_records (user_id, book_id, borrow_date, due_date, status) VALUES (?,?,?,?,'ISSUED')";
        String sqlBook = "UPDATE books SET available_quantity = available_quantity - 1 WHERE id = ?";
        try 
        (
        	Connection c = DatabaseDAO.getConnection(); 
        	PreparedStatement psLoan = c.prepareStatement(sqlLoan);
            PreparedStatement psBook = c.prepareStatement(sqlBook)
        ) 
        {
            psLoan.setInt(1, id);
            psLoan.setInt(2, bookId);
            psLoan.setDate(3, Date.valueOf(borrowDate));
            psLoan.setDate(4, Date.valueOf(dueDate));
            psLoan.executeUpdate();

            psBook.setInt(1, bookId);
            psBook.executeUpdate();

        }
    }
	
	public List<String[]> getBorrowHistory(int userId) throws SQLException {
        List<String[]> history = new ArrayList<>();
        String sql = "SELECT b.title, l.borrow_date, l.return_date, l.due_date, l.id " +
                     "FROM borrow_records l JOIN books b ON l.book_id = b.id WHERE l.user_id = ?";
        Connection conn= DatabaseDAO.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
        	String loanId = String.valueOf(rs.getInt("id"));
        	String title = rs.getString("title");
            String loanDate = rs.getString("borrow_date");
            String dueDate= rs.getString("due_date");
            String returnDate = rs.getString("return_date");
             
            history.add(new String[]{
            		loanId,
            		title, 
            		loanDate,
            		dueDate,
            		returnDate
                    
            });
        }
        return history;
    }
	
	public boolean updateLoanStatus(int loanId, LocalDate returnDate, String status) {
        String sql = "UPDATE borrow_records SET return_date = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setString(2, status);
            stmt.setInt(3, loanId);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public LocalDate getDueDate(int loanId) throws SQLException {
        String sql = "SELECT due_date FROM borrow_records WHERE id = ?";
        try (Connection conn = DatabaseDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDate("due_date").toLocalDate();
            }
        }
        return null;
    }
	
	public List<Loan> searchLoans(String status, String userKeyword, String bookKeyword) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT br.id, u.username, b.title, br.borrow_date, br.due_date, br.return_date, "
        		+ "br.status FROM borrow_records br "
                + "JOIN users u ON br.user_id = u.id "
                + "JOIN books b ON br.book_id = b.id WHERE 1=1");

        if (status != null && !status.equals("All")) {
            sql.append(" AND br.status = ?");
        }
        if (userKeyword != null && !userKeyword.isEmpty()) {
            sql.append(" AND u.username LIKE ?");
        }
        if (bookKeyword != null && !bookKeyword.isEmpty()) {
            sql.append(" AND b.title LIKE ?");
        }

        try (Connection c = DatabaseDAO.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (status != null && !status.equals("All")) {
                ps.setString(paramIndex++, status);
            }
            if (userKeyword != null && !userKeyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + userKeyword + "%");
            }
            if (bookKeyword != null && !bookKeyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + bookKeyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getInt("id"));
                loan.setUsername(rs.getString("username"));
                loan.setBookTitle(rs.getString("title"));
                loan.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                loan.setDueDate(rs.getDate("due_date").toLocalDate());
                Date returnDate = rs.getDate("return_date");
                loan.setReturnDate(returnDate != null ? returnDate.toLocalDate() : null);
                loan.setStatus(rs.getString("status"));
                loans.add(loan);
            }
        }
        return loans;
    }
	
}