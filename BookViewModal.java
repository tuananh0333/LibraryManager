package com.example.librarymanager.modals;

import java.util.Date;

public class BookViewModal {
    private String name;
    private String author;
    private boolean status;
    private String type;
    private Date borrowDate;
    private int imageResourceId;

    public BookViewModal(String name, String author, boolean status, String type, Date borrowDate, int imageResourceId) {
        this.name = name;
        this.author = author;
        this.status = status;
        this.type = type;
        this.borrowDate = borrowDate;
        this.imageResourceId = imageResourceId;
    }

    public BookViewModal(String name, int imageResourceId, boolean status) {
        this.name = name;
        this.status = status;
        this.imageResourceId = imageResourceId;

        this.author = "";
        this.type = "";
        this.borrowDate = null;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
