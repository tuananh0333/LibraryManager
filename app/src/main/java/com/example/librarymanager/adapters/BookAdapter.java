package com.example.librarymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanager.R;

import java.util.ArrayList;
import java.util.List;

import com.example.librarymanager.models.BookModel;

public class BookAdapter extends ArrayAdapter<BookModel> {
    private Context context;
    private int resource;
    private List<BookModel> bookLists;

    public BookAdapter(Context context, int resource, List<BookModel> bookLists) {
        super(context, resource, bookLists);
        this.context = context;
        this.resource = resource;
        this.bookLists = bookLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_view_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtBookName = (TextView) convertView.findViewById(R.id.txtBookName);
//            viewHolder.imgBookImage = (ImageView) convertView.findViewById(R.id.imgBookImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BookModel book = bookLists.get(position);
        viewHolder.txtBookName.setText(book.getName());

        return convertView;
    }

    public List<BookModel> getBookLists() {
        return bookLists;
    }

    public void setBookLists(List<BookModel> bookLists) {
        this.bookLists = bookLists;
    }

    public class ViewHolder {
        TextView txtBookName;
        ImageView imgBookImage;
    }
}
