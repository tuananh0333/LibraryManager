package com.example.librarymanager.databases;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


public class CategoryDatabase {

    private DatabaseReference reference;

    public CategoryDatabase() {
        String NODE_NAME = "categories";
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
    }

    public void setValueEventListener(ValueEventListener valueEventListener) {
        reference.addValueEventListener(valueEventListener);
    }

    public void addListenerForSingleValueEventListener(ValueEventListener valueEventListener) {
        reference.addListenerForSingleValueEvent(valueEventListener);
    }
}
