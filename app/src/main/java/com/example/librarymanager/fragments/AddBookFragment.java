package com.example.librarymanager.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddBookFragment extends AbstractCustomFragment {
    private EditText edtBookName, edtBookAuthor;
    private Spinner spnCategory;
    private ImageButton btnCapture, btnChoose;
    private ImageView imgPicture;

    private Button btnAdd, btnCancel;

    private AbstractCustomFragment fragment;
    private FragmentTransaction fragmentTransaction;

    private Bitmap selectedBitMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.add_book_layout,
                container,
                false);

        addControllers(view);
        initSpinner(view);
        addEvents();

        return view;
    }

    private void initSpinner(View view) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item,
                DataStorage.getCategoryListName());

        spnCategory.setAdapter(spinnerAdapter);
    }

    @Override
    public void finish() {
        if (getFragmentManager().findFragmentByTag("book_list_fragment") == null) {
            fragment = new BookListFragment();
        }
        else {
            fragment = (AbstractCustomFragment)getFragmentManager().findFragmentByTag("book_list_fragment");
        }

        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_fragment, fragment, "book_list_fragment");

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    void addControllers(View view) {
        edtBookName = view.findViewById(R.id.edtBookName);
        edtBookAuthor = view.findViewById(R.id.edtAuthor);

        spnCategory = view.findViewById(R.id.spnBookCategory);
        spnCategory = view.findViewById(R.id.spnBookCategory);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnCapture = view.findViewById(R.id.btnCapture);
        btnChoose = view.findViewById(R.id.btnChoose);

        imgPicture = view.findViewById(R.id.imgPicture);
    }

    @Override
    void addEvents() {
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
        // Prepare data
        BookModel book = new BookModel();
        book.setName(edtBookName.getText().toString());
        book.setAuthor(edtBookAuthor.getText().toString());
        book.setCategory(DataStorage.categoryList.get((int)spnCategory.getSelectedItemId()).getId());

        // Convert image into string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        selectedBitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imgEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        book.setImage(imgEncoded);

        // Add book to database
        BookDatabase bookDatabase = new BookDatabase();
        bookDatabase.writeNew(book);

        finish();
    }

    private void capturePicture() {
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureImage, 100);
    }

    private void choosePicture() {
        Intent pickImage = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, 200);
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
}
