package modals;

import java.util.Date;

public class BookViewModal {

    private String name;
    private String author;
    private Date publishDate;
    private String publisher;
    private String type;
    private Date borrowDate;
    private int imageResourceId;

    public BookViewModal(String name, String author, Date publishDate, String publisher, String type, Date borrowDate, int imageResourceId) {
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.type = type;
        this.borrowDate = borrowDate;
        this.imageResourceId = imageResourceId;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
