package com.example.librarymanager.fragments;

import android.app.Fragment;
import android.view.View;

public abstract class AbstractCustomFragment extends Fragment {
    public abstract void finish();
    abstract void addControllers(View view);
    abstract void addEvents();
}
