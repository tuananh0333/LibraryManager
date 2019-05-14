package com.example.librarymanager.databases;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.librarymanager.data_modals.CategoryModal;

public class CategoryDatabase {
    private final String NODE_NAME = "categories";

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(NODE_NAME);

    private void writeNewCategory(CategoryModal category) {
        mDatabase.child(category.getId() + "").setValue(category.getId());
        mDatabase.child(category.getId() + "").setValue(category.getName());
    }

    public DatabaseReference getReference() {
        return mDatabase;
    }
}
