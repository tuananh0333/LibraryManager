package com.example.librarymanager.databases;

import android.app.ProgressDialog;
import android.util.Log;

import com.example.librarymanager.models.BookModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookDatabase {
    private final String NODE_NAME = "books";
    private DatabaseReference reference;

    public BookDatabase() {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
    }

    public void setValueEventListener(ValueEventListener valueEventListener) {
        reference.addValueEventListener(valueEventListener);
    }

    public void writeNew(BookModel book) {
        book.setId(reference.push().getKey());
        reference.child(book.getId()).setValue(book);
    }

    public void update(BookModel book) {
        reference.child(book.getId()).setValue(book);
    }

    public void delete(String id) {

    }
}
