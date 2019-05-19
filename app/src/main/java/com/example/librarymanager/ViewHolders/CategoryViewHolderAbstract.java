package com.example.librarymanager.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanager.R;
import com.example.librarymanager.models.CategoryModel;

public class CategoryViewHolderAbstract extends AbstractCustomViewHolder {
    private TextView categoryName;

    public CategoryViewHolderAbstract(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.txtCategory);
    }

    @Override
    public void setData(Object data) {
        CategoryModel category = (CategoryModel) data;
        this.categoryName.setText(category.getName());
    }
}
