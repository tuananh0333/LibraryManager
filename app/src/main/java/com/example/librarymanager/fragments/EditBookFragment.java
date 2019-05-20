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

import com.example.librarymanager.R;
import com.example.librarymanager.databases.BookDatabase;
import com.example.librarymanager.databases.DataStorage;
import com.example.librarymanager.models.BookModel;

import java.io.IOException;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static android.text.TextUtils.isEmpty;

public class EditBookFragment extends AbstractCustomFragment {
    private EditText edtBookName, edtBookAuthor;
    private Spinner spnCategory;
    private Button btnCancel, btnUpdate;
    private ImageButton btnCapture, btnChoose;
    private ImageView imgPicture;

    private BookModel currentBook;

    private Bitmap selectedBitMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.add_book_layout, container, false);

        addControllers(view);
        initSpinner(view);
        prepareData();
        addEvents();

        return view;
    }

    private void initSpinner(View view) {
        // Create spinner Adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item,
                DataStorage.getCategoryListName());

        spnCategory.setAdapter(spinnerAdapter);
    }

    private void addControllers(View view) {
        edtBookName = view.findViewById(R.id.edtBookName);
        edtBookAuthor = view.findViewById(R.id.edtAuthor);
        spnCategory = view.findViewById(R.id.spnBookCategory);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnUpdate = view.findViewById(R.id.btnAdd);

        btnCapture = view.findViewById(R.id.btnCapture);
        btnChoose = view.findViewById(R.id.btnChoose);

        imgPicture = view.findViewById(R.id.imgPicture);
    }

    private void prepareData() {
        btnUpdate.setText(R.string.btn_update);

        // Set up default data to view
        if (currentBook != null) {
            edtBookName.setText(currentBook.getName());
            edtBookAuthor.setText(currentBook.getAuthor());
            spnCategory.setSelection(DataStorage.getCategoryListName().indexOf(currentBook.getCategory()));
            imgPicture.setImageBitmap(convertStringToBitmap(currentBook.getImage()));
        }
    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            updateBook();
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

    private void updateBook () {
        if (!validate()) {
            return;
        }

        // Prepare data
        BookModel book = new BookModel();
        book.setName(edtBookName.getText().toString());
        book.setAuthor(edtBookAuthor.getText().toString());
        book.setCategory(DataStorage.categoryList.get((int)spnCategory.getSelectedItemId()).getId());
        book.setId(currentBook.getId());

        book.setImage(convertBitmapToString(selectedBitMap));

        // Add book to database
        BookDatabase bookDatabase = new BookDatabase();
        bookDatabase.update(book);

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

        String pattern = getString(R.string.name_pattern);

        if (!Pattern.matches(pattern, bookName)) {
            edtBookName.setError("Tên sách chỉ từ 3 - 16 kí tự chữ và số");
            edtBookName.requestFocus();
            return false;
        }

        if (!Pattern.matches(pattern, bookAuthor)) {
            edtBookAuthor.setError("Tên tác giả chỉ từ 3 - 16 kí tự chữ và số");
            edtBookAuthor.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectedBitMap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(selectedBitMap);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                selectedBitMap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imgPicture.setImageBitmap(selectedBitMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getData(BookModel book) {
        currentBook = book;
    }

    @Override
    void updateData() {

    }
}
