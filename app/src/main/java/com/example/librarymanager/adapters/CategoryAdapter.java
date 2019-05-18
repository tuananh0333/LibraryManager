package com.example.librarymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanager.R;

import java.util.List;

import com.example.librarymanager.models.CategoryModel;

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {
    private Context context;
    private int resource;
    private List<CategoryModel> categoryLists;

    public CategoryAdapter(Context context, int resource, List<CategoryModel> categoryLists) {
        super(context, resource, categoryLists);
        this.context = context;
        this.resource = resource;
        this.categoryLists = categoryLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.category_view_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtBookName = (TextView) convertView.findViewById(R.id.txtBookName);
            viewHolder.imgBookImage = (ImageView) convertView.findViewById(R.id.imgBook);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryModel category = categoryLists.get(position);
        viewHolder.txtBookName.setText(category.getName());

        return convertView;
    }

    public List<CategoryModel> getCategoryLists() {
        return categoryLists;
    }

    public void setCategoryLists(List<CategoryModel> categoryLists) {
        this.categoryLists = categoryLists;
    }

    public class ViewHolder {
        TextView txtBookName;
        ImageView imgBookImage;
    }
}
