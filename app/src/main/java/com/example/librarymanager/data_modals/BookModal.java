package com.example.librarymanager.data_modals;

import java.util.Date;

public class BookModal {

    private String name;
    private String author;
    private String category;
//    private Date borrowDate;
//    private String borrower;
    // TODO Change imageResource to image from file or camera
    private String imageResourceUrl;

    public BookModal() {
    }

    public BookModal(String name, String author, String type, String imageResourceUrl) {
        this.name = name;
        this.author = author;
        this.category = type;
//        this.borrowDate = null;
//        this.borrower = "";
        this.imageResourceUrl = imageResourceUrl;
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

    public String getImageResourceUrl() {
        return imageResourceUrl;
    }

    public void setImageResourceUrl(String imageResourceUrl) {
        this.imageResourceUrl = imageResourceUrl;
    }

//    public String getBorrower() {
//        return borrower;
//    }
//
//    public void setBorrower(String borrower) {
//        this.borrower = borrower;
//    }
}
