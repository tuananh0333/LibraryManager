package com.example.librarymanager.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

public abstract class AbstractCustomViewHolder extends ViewHolder {
    public AbstractCustomViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setData(Object data);

    public abstract void setActive(Boolean active);
}
