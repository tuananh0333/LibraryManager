package com.example.librarymanager.databases;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    protected String nodeName;
    private DatabaseReference mDatabase;

    public Database() {
        mDatabase = FirebaseDatabase.getInstance().getReference(nodeName);
    }
}
