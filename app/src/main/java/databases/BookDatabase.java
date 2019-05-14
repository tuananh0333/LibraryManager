package databases;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import data_modals.BookModal;

public class BookDatabase {
    private final String NODE_NAME = "books";

    private final String BOOK_NAME = "name";
    private final String BOOK_AUTHOR = "author";
    private final String BOOK_CATEGORY = "category";
    private final String BORROW_DATE = "borrowDate";
    private final String BORROWER = "borrower";
    private final String BOOK_IMAGE = "image";

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(NODE_NAME);

    public void writeNewBook(BookModal book) {
        mDatabase.child(book.getPos() + "").child(BOOK_NAME).setValue(book.getName());
        mDatabase.child(book.getPos() + "").child(BOOK_AUTHOR).setValue(book.getAuthor());
        mDatabase.child(book.getPos() + "").child(BOOK_CATEGORY).setValue(book.getCategory());
        mDatabase.child(book.getPos() + "").child(BORROW_DATE).setValue(book.getAuthor());
        mDatabase.child(book.getPos() + "").child(BORROWER).setValue(book.getAuthor());
        mDatabase.child(book.getPos() + "").child(BOOK_IMAGE).setValue(book.getImageResourceId());
    }

    public DatabaseReference getDatabase() {
        return mDatabase;
    }
}
