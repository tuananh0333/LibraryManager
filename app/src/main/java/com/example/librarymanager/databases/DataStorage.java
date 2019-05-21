package com.example.librarymanager.databases;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.librarymanager.models.BookModel;
import com.example.librarymanager.models.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataStorage {
    public static ArrayList<BookModel> bookList = new ArrayList<>();
    public static ArrayList<CategoryModel> categoryList = new ArrayList<>();

    private BookDatabase bookDatabase;
    private CategoryDatabase categoryDatabase;

    private IDataListener dataListener;

    public DataStorage(IDataListener dataListener) {
        this.dataListener = dataListener;

        initCategoryData();
    }

    public static ArrayList<String> getCategoryListName()
    {
        ArrayList<String> data = new ArrayList<>();
        for (CategoryModel category: categoryList) {
            data.add(category.getName());
        }

        return data;
    }

    public void initCategoryData() {
        categoryDatabase = new CategoryDatabase();
        categoryDatabase.addListenerForSingleValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue() != null) {
                        CategoryModel category = data.getValue(CategoryModel.class);
                        if (category != null) {
                            category.setId(data.getKey());
                            categoryList.add(category);
                        }
                    }
                }

                if (categoryList.size() > 0) {
                    dataListener.categoryLoaded();

                    initBookData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initBookData() {
        bookDatabase = new BookDatabase(0);
        bookDatabase.addListenerForSingleValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue() != null) {
                        BookModel book = data.getValue(BookModel.class);
                        if (book != null) {
                            book.setId(data.getKey());
                            bookList.add(book);
                            Log.d("abc", book.getId() + " : " + book.getName() + " : " + book.getAuthor());
                        }
                    }
                }
                    dataListener.bookLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getBookDataWithCategoryId(int categoryId) {
        bookDatabase = new BookDatabase(categoryId);
        bookDatabase.addListenerForSingleValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue() != null) {
                        BookModel book = data.getValue(BookModel.class);
                        if (book != null) {
                            book.setId(data.getKey());
                            bookList.add(book);
                        }
                    }
                }
                dataListener.bookLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getBookDataWithName(String param) {
        bookDatabase = new BookDatabase();
        bookDatabase.getBooksWithName(param);
        bookDatabase.addListenerForSingleValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue() != null) {
                        BookModel book = data.getValue(BookModel.class);
                        if (book != null) {
                            book.setId(data.getKey());
                            bookList.add(book);
                        }
                    }
                }

                Log.d("abc", bookList.size() + " : " + bookList.get(0).toString());
//                dataListener.bookLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
