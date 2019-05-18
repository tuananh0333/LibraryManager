package com.example.librarymanager.databases;

import com.example.librarymanager.models.BookModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookDatabase {
    private final String NODE_NAME = "books";
    private DatabaseReference reference;
    private ArrayList<BookModel> data;

    public BookDatabase() {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
//        reference.addValueEventListener(bookListener);
    }

    public void setValueEventListener(ValueEventListener valueEventListener) {
        reference.addValueEventListener(valueEventListener);
    }

    public void writeNewBook(BookModel book) {
        reference.child(book.getId()).setValue(book);
    }

    public ArrayList<BookModel> readAllBook() {
        return data != null ? data : new ArrayList<BookModel>();
    }

//    private ValueEventListener bookListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            if (data != null) {
//                data.clear();
//            } else {
//                data = new ArrayList<>();
//            }
//            ArrayList<BookModel> dataSource = new ArrayList<>();
//            for (DataSnapshot data : dataSnapshot.getChildren()) {
//                if (data.getValue() != null) {
//                    BookModel book = data.getValue(BookModel.class);
//                    dataSource.add(book);
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    };
}
