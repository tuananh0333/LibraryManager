package com.example.librarymanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
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
    private final String TITLE = "QUẢN LÝ THƯ VIỆN";

    ArrayAdapter<String> bookAdapter;
    ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_with_drawer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(TITLE);

            // Add drawer
            addDrawerToggle(actionBar);
        }

        // Init database
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

    private void addDrawerToggle(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
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
                if (data.getValue() != null) {
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
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        } else {
            // Kiểm tra xem drawer có mở hay không
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
