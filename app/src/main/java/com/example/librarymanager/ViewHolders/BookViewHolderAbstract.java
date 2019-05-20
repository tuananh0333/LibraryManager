package com.example.librarymanager.ViewHolders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarymanager.R;
import com.example.librarymanager.models.BookModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BookViewHolderAbstract extends AbstractCustomViewHolder {
    private final ImageView bookImage;
    private final TextView bookName;
    private TextView bookAuthor;

    public BookViewHolderAbstract(@NonNull View itemView) {
        super(itemView);

        bookImage = itemView.findViewById(R.id.imgBook);
        bookName = itemView.findViewById(R.id.txtBookName);
        //TODO find book state textView from itemView
    }

    @Override
    public void setData(Object data) {
        BookModel book = (BookModel) data;

        if (bookImage != null) {
            try{
                byte [] encodeByte= Base64.decode(book.getImage(),Base64.DEFAULT);

                InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bookImage.setImageBitmap(bitmap);

            } catch (NullPointerException e){
                e.getMessage();
            }
        }
        if (bookName != null) {
            this.bookName.setText(book.getName());
        }

        if (bookAuthor != null) {
            this.bookAuthor.setText(book.getAuthor());
        }
    }
}
