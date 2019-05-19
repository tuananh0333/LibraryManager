package com.example.librarymanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.librarymanager.R;
import com.example.librarymanager.adapters.RecycleViewAdapter;
import com.example.librarymanager.databases.BookDatabase;
import com.example.librarymanager.databases.CategoryDatabase;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.models.BookModel;
import com.example.librarymanager.models.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class BookListFragment extends AbstractCustomFragment {
    private RecyclerView categoryRecyclerView, bookRecyclerView;

    private RecycleViewAdapter bookAdapter, categoryAdapter;

    private CategoryDatabase categoryDatabase;
    private BookDatabase bookDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.main_layout, container, false);

        addControllers(view);

        initCategory(getActivity());

        return view;
    }

    private void initCategory(Context context)
    {
        // Set up Recycle View
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        // Set up database
        categoryDatabase = new CategoryDatabase();
        categoryDatabase.setValueEventListener(categoryListener);

        // Set up adapter
        categoryAdapter = new RecycleViewAdapter(R.layout.category_view_layout, DataStorage.categoryList);
        categoryAdapter.setClickListener(onCategoryClick);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void initBook(Context context, int categoryId)
    {
        // Setup Recycle View
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(context);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookRecyclerView.setLayoutManager(verticalLayoutManager);

        bookDatabase = new BookDatabase(DataStorage.categoryList.get(categoryId).getId());
        bookDatabase.setValueEventListener(bookListener);

        // Set up adapter
        bookAdapter = new RecycleViewAdapter(R.layout.book_view_layout, DataStorage.bookList);
        bookAdapter.setClickListener(onBookClick);
        bookRecyclerView.setAdapter(bookAdapter);
    }

    View.OnClickListener onCategoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int categoryId = categoryRecyclerView.getChildAdapterPosition(v);

            initBook(getActivity(), categoryId);
            // TODO User click vào category -> load lại adapter
//            fragment.updateUserInteraction(questionID);
        }
    };

    View.OnClickListener onBookClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        BookModel book = DataStorage.bookList.get(bookRecyclerView.getChildAdapterPosition(v));

        fragment = new EditBookFragment();

        ((EditBookFragment) fragment).getData(book);
        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_fragment, fragment, AbstractCustomFragment.EDIT_BOOK);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        }
    };

    ValueEventListener bookListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DataStorage.bookList.clear();

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data.getValue() != null) {
                    BookModel book = data.getValue(BookModel.class);
                    book.setId(data.getKey());
                    DataStorage.bookList.add(book);
                }
            }

            bookAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener categoryListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DataStorage.categoryList.clear();

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (data.getValue() != null) {
                    CategoryModel category = data.getValue(CategoryModel.class);
                    category.setId(data.getKey());
                    DataStorage.categoryList.add(category);
                }
            }

            categoryAdapter.notifyDataSetChanged();

            if (DataStorage.categoryList.size() != 0) {
                initBook(getContext(), 0);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    void addControllers(View view) {
        bookRecyclerView = view.findViewById(R.id.book_list);
        categoryRecyclerView = view.findViewById(R.id.category_list);
    }

    @Override
    void addEvents() {

    }
}
