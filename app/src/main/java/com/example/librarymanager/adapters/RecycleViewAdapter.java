package com.example.librarymanager.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.librarymanager.R;
import com.example.librarymanager.ViewHolders.BookViewHolderAbstract;
import com.example.librarymanager.ViewHolders.CategoryViewHolderAbstract;
import com.example.librarymanager.ViewHolders.AbstractCustomViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<AbstractCustomViewHolder> {
    private int layoutId;
    private ArrayList dataSource;

    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RecycleViewAdapter(int layoutId, ArrayList dataSource) {
        this.layoutId = layoutId;
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public AbstractCustomViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CardView cardView = (CardView) inflater.inflate(viewType, parent, false);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        });

        if (viewType == R.layout.book_view_layout) {
            return new BookViewHolderAbstract(cardView);
        }
        else {
            return new CategoryViewHolderAbstract(cardView);
        }
    }

    @Override
    public void onBindViewHolder(@NotNull AbstractCustomViewHolder viewHolder, int i) {
        viewHolder.setData(dataSource.get(i));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }
}
