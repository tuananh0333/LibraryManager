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

import com.example.librarymanager.data_modals.CategoryModal;

public class CategoryAdapter extends ArrayAdapter<CategoryModal> {
    private Context context;
    private int resource;
    private List<CategoryModal> categoryLists;

    public CategoryAdapter(Context context, int resource, List<CategoryModal> categoryLists) {
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
            viewHolder.imgBookImage = (ImageView) convertView.findViewById(R.id.imgBookImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryModal category = categoryLists.get(position);
        viewHolder.txtBookName.setText(category.getName());

        return convertView;
    }

    public List<CategoryModal> getCategoryLists() {
        return categoryLists;
    }

    public void setCategoryLists(List<CategoryModal> categoryLists) {
        this.categoryLists = categoryLists;
    }

    public class ViewHolder {
        TextView txtBookName;
        ImageView imgBookImage;
    }
}
