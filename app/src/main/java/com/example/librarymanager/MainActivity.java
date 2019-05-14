package com.example.librarymanager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.librarymanager.adapters.BookAdapter;
import com.example.librarymanager.adapters.CategoryAdapter;
import com.example.librarymanager.data_modals.BookModal;
import com.example.librarymanager.data_modals.CategoryModal;
import com.example.librarymanager.databases.BookDatabase;
import com.example.librarymanager.databases.CategoryDatabase;

public class MainActivity extends AppCompatActivity {
    private final String TITLE = "QUẢN LÝ THƯ VIỆN";

    DatabaseReference bookReference;

    BookAdapter bookAdapter;
    CategoryAdapter categoryAdapter;

    DrawerLayout drawerLayout;
    ListView categoryList;
    ListView bookList;

    int currentCategoryId;

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
        drawerLayout = findViewById(R.id.drawer_layout);
        categoryList = findViewById(R.id.category_list);
        bookList = findViewById(R.id.book_list);

        initBook();
        initCategory();

    }

    private void initCategory()
    {
        //Set up database
        CategoryDatabase categoryDatabase = new CategoryDatabase();

        // Get reference
        DatabaseReference categoryReference = categoryDatabase.getReference();
        categoryReference.addValueEventListener(categoryListener);

        // Set up adapter
        categoryAdapter = new CategoryAdapter(this, R.layout.category_view_layout, new ArrayList<CategoryModal>());

        // Set up custom list
        categoryList.setOnItemClickListener(onCategoryItemCLick);
        categoryList.setAdapter(categoryAdapter);
    }

    private void initBook()
    {
        //Set up database
        BookDatabase bookDatabase = new BookDatabase();

        // Get reference
        bookReference = bookDatabase.getReference();
        bookReference.addValueEventListener(bookListener);

        // Set up adapter
        bookAdapter = new BookAdapter(this, R.layout.book_view_layout, new ArrayList<BookModal>());

        // Set up custom list
        bookList.setOnItemClickListener(onBookItemClick);
        bookList.setAdapter(bookAdapter);
    }

    AdapterView.OnItemClickListener onCategoryItemCLick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            currentCategoryId = position;
            drawerLayout.closeDrawer(GravityCompat.START);

            // TODO User click vào book -> Chuyển activity
//            fragment.updateUserInteraction(questionID);
            // TODO User click vào category -> load lại adapter
//            updateUI();
        }
    };

    AdapterView.OnItemClickListener onBookItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawerLayout.closeDrawer(GravityCompat.START);

            // TODO User click vào book -> Chuyển activity
//            fragment.updateUserInteraction(questionID);
            // TODO User click vào category -> load lại adapter
//            updateUI();
        }
    };

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
                    BookModal book = data.getValue(BookModal.class);
                    bookAdapter.add(book);
                }
            }

            bookAdapter.notifyDataSetChanged();
            updateUI();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener categoryListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            categoryAdapter.clear();

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data.getValue() != null) {
                    String id = data.getKey();
                    String name = data.getValue().toString();

                    CategoryModal category = new CategoryModal(id, name);
                    categoryAdapter.add(category);
                }
            }

            categoryAdapter.notifyDataSetChanged();
            updateUI();
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
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Add");
            dialog.setContentView(R.layout.add_book_layout);

            ArrayList<String> data = new ArrayList<>();

            for (CategoryModal category : categoryAdapter.getCategoryLists()) {
                data.add(category.getName());
            }

            Spinner spnCategory = dialog.findViewById(R.id.spnBookCategory);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(dialog.getContext(), android.R.layout.simple_spinner_item, data);
            spnCategory.setAdapter(spinnerAdapter);

            Button btnCancel = dialog.findViewById(R.id.btn_cancel1);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Button btnAdd = dialog.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edtName = dialog.findViewById(R.id.edtBookName);
                    EditText edtAuthor = dialog.findViewById(R.id.edtAuthor);
                    Spinner spnCategory = dialog.findViewById(R.id.spnBookCategory);
                    // TODO Thêm ảnh

                    BookModal book =new BookModal(edtName.getText().toString(), edtAuthor.getText().toString(), spnCategory.getSelectedItemId() + "", "");

                    bookReference.child("book1").setValue(book);

                }
            });

            dialog.show();

        } else {
            // Kiểm tra xem drawer có mở hay không
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
    }
}
