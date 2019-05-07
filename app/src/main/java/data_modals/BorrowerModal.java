package data_modals;

public class BorrowerModal {
    private String id;
    private String name;
    private String[] borrowBooks;

    public BorrowerModal(String id) {
        this.id = id;
    }

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

    public String[] getBorrowBooks() {
        return borrowBooks;
    }

    public void setBorrowBooks(String[] borrowBooks) {
        this.borrowBooks = borrowBooks;
    }


}
