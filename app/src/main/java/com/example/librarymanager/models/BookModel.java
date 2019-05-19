package com.example.librarymanager.models;

public class BookModel {
    private String id;
    private String name;
    private String author;
    private String category;

    // TODO Change imageResource to image from file or camera
    private String image;

    public BookModel() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

//    public Date getBorrowDate() {
//        return borrowDate;
//    }
//
//    public void setBorrowDate(Date borrowDate) {
//        this.borrowDate = borrowDate;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public String getBorrower() {
//        return borrower;
//    }
//
//    public void setBorrower(String borrower) {
//        this.borrower = borrower;
//    }
}
