package com.example.librarymanager.databases;

import com.example.librarymanager.models.BookModel;
import com.example.librarymanager.models.CategoryModel;

import java.util.ArrayList;

public class DataStorage {
    public static ArrayList<BookModel> bookList;
    public static ArrayList<CategoryModel> categoryList;

    public static void initData()
    {
        bookList = new ArrayList<>();
        categoryList = new ArrayList<>();
    }

    public static ArrayList<String> getCategoryListName()
    {
        ArrayList<String> data = new ArrayList<>();
        for (CategoryModel category: categoryList) {
            data.add(category.getName());
        }

        return data;
    }
}
