package com.example.librarymanager.views;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.librarymanager.R;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.fragments.AbstractCustomFragment;
import com.example.librarymanager.fragments.AddBookFragment;
import com.example.librarymanager.fragments.BookListFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView tableList;

    private AbstractCustomFragment fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_with_drawer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            final String TITLE = "QUẢN LÝ THƯ VIỆN";
            actionBar.setTitle(TITLE);

            // Add drawer
            addDrawerToggle(actionBar);
        }

        addControl();

        DataStorage.initData();

        updateUI();
    }

    private void addControl() {
        drawerLayout = findViewById(R.id.drawer_layout);
        tableList = findViewById(R.id.table_list);
    }

    private void addDrawerToggle(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuAddBook) {
            String tag = AbstractCustomFragment.ADD_BOOK;
            if (getFragmentManager().findFragmentByTag(tag) == null) {
                fragment = new AddBookFragment();
            }
            else {
                fragment = (AbstractCustomFragment)getSupportFragmentManager().findFragmentByTag(tag);
            }

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment, fragment, tag);

            if (getFragmentManager().findFragmentByTag(tag) == null) {
                fragmentTransaction.addToBackStack(null);
            }

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

    private void updateUI() {
        String tag = AbstractCustomFragment.LIST_BOOK;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragment = new BookListFragment();
        }
        else {
            fragment = (AbstractCustomFragment) getSupportFragmentManager().findFragmentByTag(tag);
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment, tag);

        if (getFragmentManager().findFragmentByTag(tag) == null) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
}
