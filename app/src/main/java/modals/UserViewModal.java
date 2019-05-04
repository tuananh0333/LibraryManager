package modals;

public class UserViewModal {
    private String id;
    private BookViewModal[] borrowedBooks;

    public UserViewModal(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BookViewModal[] getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(BookViewModal[] borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }


}
