CREATE DATABASE IF NOT EXISTS lms_db;
USE lms_db;

-- Users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    role ENUM('admin', 'user') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `users` (`username`, `password`, `email`) VALUES
('poepoe','poepoe234','poepoe@gmail.com'),
('juejue','juejue2003','juejue@gmail.com'),
('phuephue','phuephue345','phuephue@gmail.com');


-- Books table

DROP TABLE books;
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    genre VARCHAR(100),
    publication_year INT,
    available_quantity INT,
    image_path VARCHAR(500),
    description VARCHAR(255),
    pdfFilePath VARCHAR(255)
);

drop table borrow_records;
-- Borrow records table
CREATE TABLE borrow_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE NULL,
    status ENUM('issued', 'returned', 'overdue') DEFAULT 'issued',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

DROP TABLE loans;
CREATE TABLE staff(
	staff_id INT AUTO_INCREMENT PRIMARY KEY,
    staff_name VARCHAR(100),
    password VARCHAR(200),
    email VARCHAR(255),
    role VARCHAR(100)
);

CREATE TABLE author(
	author_id INT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR (100)
);

CREATE TABLE return_record(
	return_id INT AUTO_INCREMENT PRIMARY KEY,
    borrow_id INT NOT NULL,
    book_id INT NOT NULL,
    staff_id INT NOT NULL,
    borrow_date DATE,
    return_date DATE
);
SELECT * FROM users WHERE id = 3;





