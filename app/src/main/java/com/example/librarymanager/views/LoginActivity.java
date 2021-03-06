package com.example.librarymanager.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.librarymanager.R;
import com.example.librarymanager.databases.UserDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Change action bar title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            String TITLE = "Đăng nhập";
            actionBar.setTitle(TITLE);
        }

        mAuth = FirebaseAuth.getInstance();

        addControllers();
        addEvents();
    }

    private void addControllers() {
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    final String email = username + "@lib.tdc.edu.vn";
                    login(email, password);
                } else {
                    if (username.isEmpty()) {
                        edtUsername.setText("");
                        edtUsername.requestFocus();
                        edtUsername.setError("Vui lòng nhập ID");
                    }
                }
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user;

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            updateUI(user);

                            // TODO Lấy thông tin người dùng

                            // The user's ID, unique to the Firebase project. Do NOT use this value to
                            // authenticate with your backend server, if you have one. Use
                            // FirebaseUser.getIdToken() instead.
                            /*
                            String uid = user.getUid();
                            // Name, email address, and profile photo Url
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            Log.d("User email: ", email);
                            Log.d("User uid: ", uid);
                            */
                        }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login: ", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.quit_confirm);
        builder.setMessage("Bạn muốn thoát!");
        builder.setCancelable(false);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserDatabase.signOut();
                finishAffinity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
