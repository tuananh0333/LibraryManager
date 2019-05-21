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
import android.widget.SearchView;

import com.example.librarymanager.R;
import com.example.librarymanager.ViewHolders.CategoryViewHolder;
import com.example.librarymanager.adapters.RecycleViewAdapter;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.models.BookModel;

public class BookListFragment extends AbstractCustomFragment {
    private RecyclerView categoryRecyclerView, bookRecyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;

    private RecycleViewAdapter bookAdapter, categoryAdapter;

    DataStorage dataStorage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.list_book_layout, container, false);

        addControllers(view);
        addEvents();

        dataStorage = new DataStorage(this);

        initCategory(getContext());
        initBook(getContext());

        return view;
    }

    private void addControllers(View view) {
        bookRecyclerView = view.findViewById(R.id.book_list);
        categoryRecyclerView = view.findViewById(R.id.category_list);

        searchView = view.findViewById(R.id.search_param);

        progressBar = view.findViewById(R.id.process_bar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);

        // TODO Đổi style category đang chọn
    }

    private void addEvents() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dataStorage.getBookDataWithName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
            // TODO active category card
            int categoryId = categoryRecyclerView.getChildAdapterPosition(v);
            dataStorage.getBookDataWithCategoryId(categoryId);
            bookRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener onBookClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookIndex = bookRecyclerView.getChildAdapterPosition(v);
                activity.commitFragment(AbstractCustomFragment.EDIT_BOOK, DataStorage.bookList.get(bookIndex));
        }
    };

    @Override
    public void bookLoaded() {
        bookAdapter.notifyDataSetChanged();
        if (DataStorage.bookList.size() > 0) {
            bookRecyclerView.setVisibility(View.VISIBLE);
        } else {
            // TODO alert no data
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
    public String getFragmentTag() {
        return LIST_BOOK;
    }

    @Override
    public void setData(Object data) {

    }
}
