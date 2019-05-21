package com.example.librarymanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.librarymanager.R;
import com.example.librarymanager.adapters.RecycleViewAdapter;
import com.example.librarymanager.databases.BookDatabase;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.models.BookModel;
import com.example.librarymanager.models.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookListFragment extends AbstractCustomFragment {
    private RecyclerView categoryRecyclerView, bookRecyclerView;
    private ProgressBar progressBar;

    private RecycleViewAdapter bookAdapter, categoryAdapter;

    private BookDatabase bookDatabase;

    private boolean isLoaded = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.main_layout, container, false);

        addControllers(view);

        initCategory(getActivity());

        initBook(getContext(), 0);

        return view;
    }

    private void addControllers(View view) {
        bookRecyclerView = view.findViewById(R.id.book_list);
        categoryRecyclerView = view.findViewById(R.id.category_list);
        progressBar = view.findViewById(R.id.process_bar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initCategory(Context context)
    {
        // Set up Recycle View
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        // Set up adapter
        if (isLoaded) {
            categoryAdapter = new RecycleViewAdapter(R.layout.category_view_layout, DataStorage.categoryList);
        }
        else {
            categoryAdapter = new RecycleViewAdapter(R.layout.category_view_layout, new ArrayList<CategoryModel>());
        }
        categoryAdapter.setClickListener(onCategoryClick);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void initBook(Context context, int categoryId)
    {
        if (bookAdapter != null) {
            // TODO clear list and prepare for new data
        }

        // Setup Recycle View
        GridLayoutManager verticalLayoutManager = new GridLayoutManager(context, 2);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookRecyclerView.setLayoutManager(verticalLayoutManager);

        bookDatabase = new BookDatabase(categoryId);
        bookDatabase.setValueEventListener(bookValueEventListener);

        // Set up adapter
        bookAdapter = new RecycleViewAdapter(R.layout.book_view_layout, DataStorage.bookList);
        bookAdapter.setClickListener(onBookClick);
        bookRecyclerView.setAdapter(bookAdapter);
    }

    View.OnClickListener onCategoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int categoryId = categoryRecyclerView.getChildAdapterPosition(v);
            initBook(getContext(), categoryId);
        }
    };

    View.OnClickListener onBookClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if (getFragmentManager() != null) {
            fragment = new EditBookFragment();

            // Get data
            BookModel book = DataStorage.bookList.get(bookRecyclerView.getChildAdapterPosition(v));
            ((EditBookFragment) fragment).getData(book);

            // Prepare fragment
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment, fragment, AbstractCustomFragment.EDIT_BOOK);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        }
    };

    ValueEventListener bookValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DataStorage.bookList.clear();

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data.getValue() != null) {
                    BookModel book = data.getValue(BookModel.class);
                    if (book != null) {
                        book.setId(data.getKey());
                        DataStorage.bookList.add(book);
                    }
                }
            }

            bookAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void notifyDataLoaded() {
        isLoaded = true;
    }
}
