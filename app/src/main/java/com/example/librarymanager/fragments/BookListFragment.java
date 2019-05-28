package com.example.librarymanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.librarymanager.R;
import com.example.librarymanager.adapters.RecycleViewAdapter;
import com.example.librarymanager.databases.DataStorage;

public class BookListFragment extends AbstractCustomFragment {
    private RecyclerView categoryRecyclerView, bookRecyclerView;
    private ProgressBar progressBar;

    private RecycleViewAdapter bookAdapter, categoryAdapter;

    DataStorage dataStorage;

    private int currentSelected = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.list_book_layout, container, false);

        addControllers(view);

        dataStorage = new DataStorage(this);

        initCategory(getContext());
        initBook(getContext());

        return view;
    }

    private void addControllers(View view) {
        bookRecyclerView = view.findViewById(R.id.book_list);
        categoryRecyclerView = view.findViewById(R.id.category_list);

        progressBar = view.findViewById(R.id.process_bar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);

        // TODO Đổi style category đang chọn
    }


    private void initCategory(Context context)
    {
        // Set up Recycle View
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryAdapter = new RecycleViewAdapter(R.layout.card_category_layout, DataStorage.categoryList);

        categoryAdapter.setClickListener(onCategoryClick);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setVisibility(View.GONE);
    }

    private void initBook(Context context)
    {
        // Setup Recycle View
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(context);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookRecyclerView.setLayoutManager(verticalLayoutManager);

        // Set up adapter
        bookAdapter = new RecycleViewAdapter(R.layout.card_book_layout, DataStorage.bookList);
        bookAdapter.setClickListener(onBookClick);
        bookRecyclerView.setAdapter(bookAdapter);
    }

    View.OnClickListener onCategoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Get current selected category
            int categoryId = categoryRecyclerView.getChildAdapterPosition(v);
            categoryAdapter.notifyDataSetChanged();

            // Highlight selected category and request data
            if (categoryId == currentSelected) {
                currentSelected = -1;
                categoryAdapter.select(-1);
                dataStorage.getAllBook();
            } else {
                currentSelected = categoryId;
                categoryAdapter.select(categoryId);
                dataStorage.getBookDataWithCategoryId(categoryId);
            }


            // Turn on process bar
            bookRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener onBookClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookIndex = bookRecyclerView.getChildAdapterPosition(v);
                bookAdapter.notifyDataSetChanged();
                activity.commitFragment(AbstractCustomFragment.EDIT_BOOK, DataStorage.bookList.get(bookIndex));
        }
    };

    @Override
    public void finish() {
        // Update data
        currentSelected = -1;

        dataStorage.getAllCategory();
        dataStorage.getAllBook();

        // Update UI
        categoryRecyclerView.setVisibility(View.GONE);
        bookRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void bookLoaded() {
        bookAdapter.notifyDataSetChanged();
        if (DataStorage.bookList.size() > 0) {
            bookRecyclerView.setVisibility(View.VISIBLE);
        } else {
            // TODO alert no data
            bookRecyclerView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void categoryLoaded() {
        categoryAdapter.notifyDataSetChanged();

        if (DataStorage.categoryList.size() > 0) {
            categoryRecyclerView.setVisibility(View.VISIBLE);
        } else {
            // TODO alert no data
        }
    }

    @Override
    public void setData(Object data) {
        if (data != null) {
            bookAdapter.notifyDataSetChanged();
            currentSelected = -1;
            dataStorage.getBookDataWithName((String) data);
            bookRecyclerView.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
