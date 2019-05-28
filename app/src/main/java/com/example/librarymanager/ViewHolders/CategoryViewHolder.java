package com.example.librarymanager.viewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.librarymanager.R;
import com.example.librarymanager.models.CategoryModel;

public class CategoryViewHolder extends AbstractCustomViewHolder {
    private TextView categoryName;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.txtCategory);
    }

    @Override
    public void setData(Object data) {
        CategoryModel category = (CategoryModel) data;
        this.categoryName.setText(category.getName());
    }

    @Override
    public void setActive(Boolean active) {
        if (active) {
            if (this.categoryName != null) {
                this.categoryName.setBackgroundResource(R.color.active);
            }
        } else {
            this.categoryName.setBackgroundResource(R.color.white);
        }
    }
}
