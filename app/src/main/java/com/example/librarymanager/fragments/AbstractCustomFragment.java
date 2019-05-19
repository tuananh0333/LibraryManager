package com.example.librarymanager.fragments;

import android.app.Fragment;
import android.view.View;

import java.util.ArrayList;

public abstract class AbstractCustomFragment extends Fragment {
    abstract void finish();
    abstract void addControllers(View view);
    abstract void addEvents();
}
