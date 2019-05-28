package com.example.librarymanager.databases;

import android.support.annotation.NonNull;

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

        categoryDatabase = new CategoryDatabase();
        bookDatabase = new BookDatabase();

        getAllCategory();
        getAllBook();
    }

    public static ArrayList<String> getCategoryListName()
    {
        ArrayList<String> data = new ArrayList<>();
        for (CategoryModel category: categoryList) {
            data.add(category.getName());
        }

        return data;
    }

    public static ArrayList<String> getCategoryListId()
    {
        ArrayList<String> data = new ArrayList<>();
        for (CategoryModel category: categoryList) {
            data.add(category.getId());
        }

        return data;
    }

    public void getAllCategory() {
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllBook() {
        bookDatabase.getAllBook();
        bookDatabase.addValueEventListener(new ValueEventListener() {
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

    public void getBookDataWithCategoryId(int categoryId) {
        bookDatabase.getBookWithCategoryId(categoryId);
        bookDatabase.addValueEventListener(new ValueEventListener() {
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
                dataListener.bookLoaded();

//                Log.d("abc", bookList.size() + " : " + bookList.get(0).toString());
//                dataListener.bookLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
