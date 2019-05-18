package com.example.librarymanager.databases;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.librarymanager.models.CategoryModel;
import com.google.firebase.database.ValueEventListener;


public class CategoryDatabase {
    private final String NODE_NAME = "categories";

    private DatabaseReference reference;

    public CategoryDatabase() {
        reference = FirebaseDatabase.getInstance().getReference(NODE_NAME);
    }

    public void setValueEventListener(ValueEventListener valueEventListener) {
        reference.addValueEventListener(valueEventListener);
    }

    private void writeNewCategory(CategoryModel category) {
        reference.child(category.getId() + "").setValue(category.getId());
        reference.child(category.getId() + "").setValue(category.getName());
    }


//    ValueEventListener categoryListener = new ValueEventListener() {
//        @Override
//        public void notifyDataChange(@NonNull DataSnapshot dataSnapshot) {
//            if (categoryData != null) {
//                categoryData.clear();
//            } else {
//                categoryData = new ArrayList<>();
//            }
//
//            for (DataSnapshot data : dataSnapshot.getChildren()) {
//                if (data.getValue() != null) {
//                    String id = data.getKey();
//                    String name = data.getValue().toString();
//
//                    CategoryModel category = new CategoryModel(id, name);
//                    categoryData.add(category);
//                }
//            }
//            // Set up adapter
//            // categoryAdapter = new H(this, R.layout.category_view_layout, new ArrayList<CategoryModel>());
//            categoryAdapter = new HorizontalRecycleViewAdapter(new int[] {R.layout.book_view_layout, R.layout.category_view_layout}, categoryData);
//            categoryAdapter.setClickListener(onCategoryCLick);
//
//            // Set up custom list
//
//            categoryList.setAdapter(categoryAdapter);
//            categoryAdapter.notifyDataSetChanged();
//            updateUI();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    };
}
