package com.example.librarymanager.databases;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDatabase {
    private static FirebaseUser currentUser;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public static void createUser(String username, String password) {
        final String email = username + "@lib.tdc.edu.vn";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                        } else {
                            currentUser = null;
                        }
                    }
                });
    }

    public static void signInWithEmailAndPassWord(String username, String password) {
        final String email = username + "@lib.tdc.edu.vn";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                        } else {
                            currentUser = null;
                        }
                    }
                });
    }


    public static void signOut() {
        mAuth.signOut();
    }
}
