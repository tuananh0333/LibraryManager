package com.example.librarymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;

import data_modals.BookModal;
import databases.BookDatabase;
import databases.CategoryDatabase;

public class AddBookActivity extends AppCompatActivity {
    private ArrayAdapter<String> bookAdapter;
    private ArrayAdapter<String> categoryAdapter;

    private EditText edtBookName;
    private EditText edtBookAuthor;
    private Spinner spnCategory;

    private Intent intent;

    BookDatabase bookDatabase;
    CategoryDatabase categoryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_layout);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(onBtnAddClick);
        btnCancel.setOnClickListener(onBtnCancleClick);

        bookDatabase = new BookDatabase();
        categoryDatabase = new CategoryDatabase();

        // Init book adapter
        bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        DatabaseReference bookReference = bookDatabase.getDatabase();

        // Init category adapter
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        DatabaseReference categoryReference = categoryDatabase.getDatabase();

        intent = new Intent(AddBookActivity.this, MainActivity.class);
    }

    private View.OnClickListener onBtnAddClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String bookName = edtBookName.getText().toString().trim();
            String authorName = edtBookAuthor.getText().toString().trim();
            String categoryId = spnCategory.getSelectedItem().toString().trim();

            //TODO Get image from file or camera

            BookModal book = new BookModal(bookName, authorName, categoryId, 0);
            bookDatabase.writeNewBook(book);

            startActivity(intent);
        }
    };

    private View.OnClickListener onBtnCancleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edtBookName.setText("");
            edtBookName.requestFocus();
            spnCategory.setSelection(0);
        }
    };
}
