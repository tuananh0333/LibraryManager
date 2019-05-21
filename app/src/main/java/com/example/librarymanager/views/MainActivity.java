package com.example.librarymanager.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.librarymanager.R;
import com.example.librarymanager.fragments.AbstractCustomFragment;
import com.example.librarymanager.fragments.AddBookFragment;
import com.example.librarymanager.fragments.BookListFragment;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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

        addControls();
        addEvents();

        showListBook();
    }

    private void addDrawerToggle(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    private void addControls() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void addEvents() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_add:
                        prepareAddBook(menuItem.getActionView());
                        break;
                    case R.id.nav_borrow:
                        break;
                    case R.id.nav_details:
                        break;
                    case R.id.nav_manage:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Kiểm tra xem drawer có mở hay không
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
        super.onBackPressed();
    }

    private void showListBook() {
        String tag = AbstractCustomFragment.LIST_BOOK;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragment = new BookListFragment();
        }
        else {
            fragment = (AbstractCustomFragment) getSupportFragmentManager().findFragmentByTag(tag);
        }

        commitFragment(tag);
    }

    public void prepareAddBook(View view) {
        String tag = AbstractCustomFragment.ADD_BOOK;
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragment = new AddBookFragment();
        }
        else {
            fragment = (AbstractCustomFragment)getSupportFragmentManager().findFragmentByTag(tag);
        }

        commitFragment(tag);
    }

    void commitFragment(String tag) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment, tag);

        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
}
