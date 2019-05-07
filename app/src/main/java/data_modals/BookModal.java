package data_modals;

import java.util.Date;

public class BookModal {

    private int pos;
    private String name;
    private String author;
    private String category;
    private Date borrowDate;
    private String borrower;
    private int imageResourceId;

    public BookModal(String name, String author, String type, Date borrowDate, int imageResourceId) {
        this.name = name;
        this.author = author;
        this.category = type;
        this.borrowDate = borrowDate;
        this.imageResourceId = imageResourceId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }
}
