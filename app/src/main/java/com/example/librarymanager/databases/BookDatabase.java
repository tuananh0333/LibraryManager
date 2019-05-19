package com.example.librarymanager.databases;

import android.util.Log;

import com.example.librarymanager.models.BookModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookDatabase {
    private final String NODE_NAME = "books";
    private DatabaseReference reference;
    private Query query;
    private String categoryName = "category1";

    public BookDatabase() {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
        query = reference.orderByChild("category").equalTo(categoryName);
    }

    public BookDatabase(String name) {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
        query = reference.orderByChild("category").equalTo(name);
    }

    public void setValueEventListener(ValueEventListener valueEventListener) {
        query.addValueEventListener(valueEventListener);
    }

    public void setChildEventListener(ChildEventListener childEventListener) {
        query.addChildEventListener(childEventListener);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
