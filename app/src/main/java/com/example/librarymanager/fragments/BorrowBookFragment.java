package com.example.librarymanager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.librarymanager.R;
import com.example.librarymanager.databases.IDataListener;

public class BorrowBookFragment extends AbstractCustomFragment implements IDataListener {
    private EditText edtBookID, edtBorrowerId;
    private TextView lblBookName, lblBorrowerName, lblBorrowedDate, lblDueDate;
    private ImageButton btnScanBookId, btnScanUserId; // Not used
    private Button btnCancel, btnConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_book_layout, container, false);

        addControllers(view);
        addEvents(view);

        return view;
    }

    private void addControllers(View view) {
        edtBookID = view.findViewById(R.id.edtBookId);
        edtBorrowerId = view.findViewById(R.id.edtBorrowerId);

        //TODO find other view
    }

    private void addEvents(View view) {
        // TODO add view event
    }

    @Override
    public void bookLoaded() {

    }

    @Override
    public void categoryLoaded() {

    }
}
