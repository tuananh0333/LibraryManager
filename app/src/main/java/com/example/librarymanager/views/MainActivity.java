package com.example.librarymanager.views;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.librarymanager.R;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.fragments.AbstractCustomFragment;
import com.example.librarymanager.fragments.AddBookFragment;
import com.example.librarymanager.fragments.BookListFragment;

public class MainActivity extends AppCompatActivity {
    private final String TITLE = "QUẢN LÝ THƯ VIỆN";

    private DrawerLayout drawerLayout;

    private AbstractCustomFragment fragment;
    private FragmentTransaction fragmentTransaction;
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

        DataStorage.initData();

        updateUI();


    }

    private void addDrawerToggle(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }


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
            fragment = new AddBookFragment();

            fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.main_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

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

    @Override
    public void onBackPressed() {
        fragment.finish();
    }

    private void updateUI() {
        fragment = new BookListFragment();
        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_fragment, fragment, "book_list");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
