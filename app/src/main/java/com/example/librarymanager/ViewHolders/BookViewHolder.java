package com.example.librarymanager.viewHolders;

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

public class BookViewHolder extends AbstractCustomViewHolder {
    private final ImageView bookImage;
    private final TextView bookName;
    private final TextView bookStatus;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);

        bookImage = itemView.findViewById(R.id.imgBook);
        bookName = itemView.findViewById(R.id.lblBookName);
        bookStatus = itemView.findViewById(R.id.lblStatus);
    }

    @Override
    public void setData(Object data) {
        BookModel book = (BookModel) data;

        if (bookImage != null && book.getImage() != null) {
            try{
                byte [] encodeByte= Base64.decode(book.getImage(),Base64.DEFAULT);

                InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bookImage.setImageBitmap(bitmap);

            } catch (NullPointerException e){
                e.getMessage();
            }
        }
        if (bookName != null && book.getName() != null) {
            this.bookName.setText(book.getName());
        }

        if (bookStatus != null) {
            bookStatus.setText(book.getBorrower() != null ? R.string.status_unavaiable : R.string.status_available);
        }
    }

    @Override
    public void setActive(Boolean active) {

    }
}
