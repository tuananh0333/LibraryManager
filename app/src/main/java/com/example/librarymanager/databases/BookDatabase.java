package com.example.librarymanager.databases;

import com.example.librarymanager.models.BookModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookDatabase {
    private final String NODE_NAME = "books";
    private DatabaseReference reference;
    private Query query;

    public BookDatabase() {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
    }

    public BookDatabase(int categoryId) {
        String categoryName = DataStorage.categoryList.get(categoryId).getId();

        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
        query = reference.orderByChild("category").equalTo(categoryName);
    }

    public void getBooksWithName(String param) {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
        query = reference.orderByChild("name").equalTo(param);
    }

    public void addValueEventListener(ValueEventListener valueEventListener) {
        query.addValueEventListener(valueEventListener);
    }

    public void addListenerForSingleValueEventListener(ValueEventListener valueEventListener) {
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    public void writeNew(BookModel book) {
        book.setId(reference.push().getKey());
        reference.child(book.getId()).setValue(book);
    }

    public void update(BookModel book) {
        reference.child(book.getId()).setValue(book);
    }

    public void delete(String id) {
        reference.child(id).removeValue();
    }
}
