package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int publicationYear;
    private int availableQuantity;
    private String imagePath;
    //private String des;
    //private String filePath;
    
    public Book() {}
    
    public Book(int id, String title, String author, String isbn, String genre, 
                int publicationYear, int availableQuantity, String imagePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.availableQuantity = availableQuantity;
        this.imagePath=imagePath;
        //this.des=des;
        //this.filePath=file;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
     
    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    @Override
    public String toString() {
        return title + " by " + author;
    }
}