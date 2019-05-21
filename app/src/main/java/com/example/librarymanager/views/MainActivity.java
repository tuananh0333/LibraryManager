package com.example.librarymanager.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.librarymanager.R;
import com.example.librarymanager.fragments.AbstractCustomFragment;
import com.example.librarymanager.fragments.AddBookFragment;
import com.example.librarymanager.fragments.BookListFragment;
import com.example.librarymanager.fragments.BorrowBookFragment;
import com.example.librarymanager.fragments.EditBookFragment;

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

        commitFragment(AbstractCustomFragment.LIST_BOOK, null);
    }

    private void addDrawerToggle(ActionBar actionBar){
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);

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
                        commitFragment(AbstractCustomFragment.ADD_BOOK, null);
                        break;
                    case R.id.nav_borrow:
                        commitFragment(AbstractCustomFragment.BORROW_BOOK, null);
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

        if (fragment == null || fragment.getFragmentTag().equals(AbstractCustomFragment.LIST_BOOK)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.logout_confirm);
            builder.setMessage("Bạn muốn đăng xuất!");
            builder.setCancelable(false);
            builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            fragment.finish();
            return;
        }
    }

    public void commitFragment(String tag, Object data) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            switch (tag) {
                case AbstractCustomFragment.ADD_BOOK:
                    fragment = new AddBookFragment();
                    break;
                case AbstractCustomFragment.LIST_BOOK:
                    fragment = new BookListFragment();
                    break;
                case AbstractCustomFragment.BORROW_BOOK:
                    fragment = new BorrowBookFragment();
                    break;
                case AbstractCustomFragment.ADD_USER:
                    // TODO Add user fragment
//                    fragment = new AddUserFragment();
                    break;
                case AddBookFragment.EDIT_BOOK:
                    fragment = new EditBookFragment();
                    fragment.setData(data);
                    break;
            }
        } else {
            fragment = (AbstractCustomFragment)getSupportFragmentManager().findFragmentByTag(tag);
        }

        fragment.setActivity(this);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment, tag);
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
