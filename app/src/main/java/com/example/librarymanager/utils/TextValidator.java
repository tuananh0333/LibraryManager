package com.example.librarymanager.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    protected TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView);

    @Override
    public void afterTextChanged(Editable s) {
        validate(textView);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
