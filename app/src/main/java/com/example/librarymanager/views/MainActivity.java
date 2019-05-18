package com.example.librarymanager.views;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.librarymanager.R;
import com.example.librarymanager.adapters.HorizontalRecycleViewAdapter;
import com.example.librarymanager.fragments.AbstractCustomFragment;
import com.example.librarymanager.fragments.BookListFragment;
import com.example.librarymanager.models.BookModel;
import com.example.librarymanager.models.CategoryModel;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TITLE = "QUẢN LÝ THƯ VIỆN";

    private DrawerLayout drawerLayout;

    private HorizontalRecycleViewAdapter bookAdapter;
    private HorizontalRecycleViewAdapter categoryAdapter;

    private RecyclerView categoryList;
    private RecyclerView bookList;

    private ArrayList<CategoryModel> categoryDataSource;
    private ArrayList<BookModel> bookDataSource;

    private AbstractCustomFragment fragment;
    private FragmentTransaction fragmentTransaction;

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

        updateUI();
//        categoryList = findViewById(R.id.table_list);
//        bookList = findViewById(R.id.book_list);

//        initBook();
//        initCategory();

    }

    /*
    private void initCategory()
    {
        // Set up Recycle View
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this);
        horizontalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryList.setLayoutManager(horizontalLayoutManager);

        // Set up data source
        categoryDataSource = new ArrayList<>();

        // Set up database
        CategoryDatabase categoryDatabase = new CategoryDatabase();
        categoryDatabase.setValueEventListener(categoryListener);

        // Set up adapter
        categoryAdapter = new HorizontalRecycleViewAdapter(R.layout.category_view_layout, categoryDataSource);
        categoryAdapter.setClickListener(onCategoryClick);
        categoryList.setAdapter(categoryAdapter);
    }

    private void initBook()
    {
        // Setup Recycle View
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookList.setLayoutManager(verticalLayoutManager);

        // Set up data source
        bookDataSource = new ArrayList<>();

        // Set up database
        BookDatabase bookDatabase = new BookDatabase();
        bookDatabase.setValueEventListener(bookListener);

        // Set up adapter
        bookAdapter = new HorizontalRecycleViewAdapter(R.layout.book_view_layout, bookDataSource);
        bookAdapter.setClickListener(onBookClick);
        bookList.setAdapter(bookAdapter);
    }

    View.OnClickListener onCategoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentCategoryId = categoryList.getChildAdapterPosition(v);
            drawerLayout.closeDrawer(GravityCompat.START);

            // TODO User click vào category -> load lại adapter
//            fragment.updateUserInteraction(questionID);
            updateUI();
        }
    };

    View.OnClickListener onBookClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawerLayout.closeDrawer(GravityCompat.START);

            // TODO User click vào category -> load lại adapter
//            fragment.updateUserInteraction(questionID);
//            updateUI();
        }
    };

    */

    private void addDrawerToggle(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    /*
    ValueEventListener bookListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            bookDataSource.clear();

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data.getValue() != null) {
                    BookModel book = data.getValue(BookModel.class);
                    bookDataSource.add(book);
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
            categoryDataSource.clear();

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data.getValue() != null) {
                    String id = data.getKey();
                    String name = data.getValue().toString();

                    CategoryModel category = new CategoryModel(id, name);
                    categoryDataSource.add(category);
                }
            }

            categoryAdapter.notifyDataSetChanged();
            updateUI();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    */

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
//            fragment = new AddBookFragment();
//
//            fragmentTransaction = getFragmentManager().beginTransaction();
//
//            fragmentTransaction.replace(R.id.fragment_container, fragment);

            // TODO chuyển thành activity
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Add");
            dialog.setContentView(R.layout.add_book_layout);

            ArrayList<String> data = new ArrayList<>();

            for (CategoryModel category : categoryDataSource) {
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

                    BookModel book = new BookModel(edtName.getText().toString(),
                            edtAuthor.getText().toString(),
                            spnCategory.getSelectedItemId() + "",
                            "");


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
        fragment = new BookListFragment();
        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_fragment, fragment, "book_list");
        fragmentTransaction.commit();
    }
}
