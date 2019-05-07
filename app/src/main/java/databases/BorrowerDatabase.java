package databases;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import data_modals.BorrowerModal;

public class BorrowerDatabase {
    private final String NODE_NAME = "borrowers";
    private final String BORROWER_NAME = "name";
    private final String BORROW_BOOK = "borrow_books";

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(NODE_NAME);

    private void writeNewBook(BorrowerModal borrower) {
        mDatabase.child(borrower.getId() + "").child(BORROWER_NAME).setValue(borrower.getName());
        mDatabase.child(borrower.getId() + "").child(BORROW_BOOK).setValue(borrower.getBorrowBooks());
    }

    public static ValueEventListener bookListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            BorrowerModal borrowerList = dataSnapshot.getValue(BorrowerModal.class);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
