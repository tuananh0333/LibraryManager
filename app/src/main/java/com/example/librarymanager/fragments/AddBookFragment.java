package com.example.librarymanager.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.librarymanager.R;
import com.example.librarymanager.databases.BookDatabase;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.models.BookModel;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.text.TextUtils.isEmpty;

public class AddBookFragment extends AbstractCustomFragment{
    private EditText edtBookName, edtBookAuthor;
    private Spinner spnCategory;
    private ImageButton btnCapture, btnChoose;
    private ImageView imgPicture;

    private Button btnAdd, btnCancel, btnDelete;

    private Bitmap selectedBitMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_book_layout, container, false);

        addControllers(view);
        addEvents();

        return view;
    }

    private void addControllers(View view) {
        edtBookName = view.findViewById(R.id.edtBookName);
        edtBookAuthor = view.findViewById(R.id.edtAuthor);

        spnCategory = view.findViewById(R.id.spnBookCategory);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item,
                DataStorage.getCategoryListName());
        spnCategory.setAdapter(spinnerAdapter);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setVisibility(View.GONE);

        btnCapture = view.findViewById(R.id.btnCapture);
        btnChoose = view.findViewById(R.id.btnChoose);

        imgPicture = view.findViewById(R.id.imgPicture);
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
    }

    private void addBook() {
        if (!validate()) {
            return;
        }

        // Prepare data
        BookModel book = new BookModel();
        book.setName(edtBookName.getText().toString());
        book.setAuthor(edtBookAuthor.getText().toString());
        book.setCategory(DataStorage.categoryList.get((int)spnCategory.getSelectedItemId()).getId());
        book.setImage(convertBitmapToString(selectedBitMap));

        // Add book to database
        BookDatabase bookDatabase = new BookDatabase();
        bookDatabase.writeNew(book);

        finish();
    }

    private boolean validate() {
        if (edtBookName == null || edtBookAuthor == null) {
            return false;
        }

        String bookName = edtBookName.getText().toString().trim();
        String bookAuthor = edtBookAuthor.getText().toString().trim();

        if (isEmpty(bookName)) {
            edtBookName.setError("Vui lòng nhập tên sách");
            edtBookName.requestFocus();
            return false;
        }

        if (isEmpty(bookAuthor)) {
            edtBookAuthor.setError("Vui lòng nhập tên tác giả");
            edtBookAuthor.requestFocus();
            return false;
        }

        if (selectedBitMap == null) {
            Toast.makeText(getActivity(), "Vui lòng chọn hoặc chụp hình ảnh của sách", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA:
                    Bundle extras = data.getExtras();
                    try {
                        selectedBitMap = (Bitmap) extras.get("data");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case GALLERY:
                    Uri imageUri = data.getData();
                    try {
                        selectedBitMap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            if (selectedBitMap != null) {
                imgPicture.setImageBitmap(getScaledVersion(selectedBitMap, imgPicture));
            } else {
                Toast.makeText(getActivity(), "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "Không chọn được ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void bookLoaded() {

    }

    @Override
    public void categoryLoaded() {

    }
}
