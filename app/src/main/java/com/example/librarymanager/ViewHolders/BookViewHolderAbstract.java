package com.example.librarymanager.ViewHolders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanager.R;
import com.example.librarymanager.models.BookModel;

public class BookViewHolderAbstract extends AbstractCustomViewHolder {
    private ImageView bookImage;
    private TextView bookName;
    private TextView bookAuthor;

    public BookViewHolderAbstract(@NonNull View itemView) {
        super(itemView);

        bookImage = itemView.findViewById(R.id.imgBook);
        bookName = itemView.findViewById(R.id.txtBookName);
        //TODO find book state textview from itemView
    }

    @Override
    public void setData(Object data) {
        BookModel book = (BookModel) data;

        // TODO get image from firebase
        if (bookName != null) {
            this.bookName.setText(book.getName());
        }

        if (bookAuthor != null) {
            this.bookAuthor.setText(book.getAuthor());
        }
    }
}
