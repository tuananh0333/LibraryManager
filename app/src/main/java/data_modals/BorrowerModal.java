package data_modals;

public class BorrowerModal {
    private String id;
    private BookModal[] borrowBooks;

    public BorrowerModal(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BookModal[] getBorrowBooks() {
        return borrowBooks;
    }

    public void setBorrowBooks(BookModal[] borrowBooks) {
        this.borrowBooks = borrowBooks;
    }


}
