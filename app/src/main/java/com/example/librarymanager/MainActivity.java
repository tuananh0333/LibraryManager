package com.example.librarymanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import databases.BookDatabase;
import databases.CategoryDatabase;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> bookAdapter;
    ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_with_drawer);

        BookDatabase bookDatabase = new BookDatabase();
        CategoryDatabase categoryDatabase = new CategoryDatabase();

        // Init book adapter
        bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        DatabaseReference bookReference = bookDatabase.getDatabase();
        bookReference.addValueEventListener(bookListener);

        // Init category adapter
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        DatabaseReference categoryReference = categoryDatabase.getDatabase();
        categoryReference.addValueEventListener(categoryListener);
    }

    ValueEventListener bookListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            bookAdapter.clear();
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue() != null) {
                        String key = data.getKey();
                        String value = data.getValue().toString();
                        bookAdapter.add(key + "\n" + value);
                    }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener categoryListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            bookAdapter.clear();
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data != null) {
                    String key = data.getKey();
                    String value = data.getValue().toString();
                    categoryAdapter.add(key + "\n" + value);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // TODO Thêm menu trong res
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuAddBook) {
            //TODO Mở màn hình thêm
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
