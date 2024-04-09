package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button createAccount;
    Button logIn;

    EditText emailEt, passEt;
    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen);

        logIn = findViewById(R.id.logInLogInButton);
        createAccount = findViewById(R.id.logInCreateAccountButton);

        //User creating an account
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });

        //Firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.logInEditTextEmail);
        passEt = findViewById(R.id.loginEditTextPassword);

        //log in
        logIn.setOnClickListener(v -> {

            loginEmailPassUser(
                    emailEt.getText().toString().trim(),
                    passEt.getText().toString().trim()
            );
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        boolean loggedOut = getIntent().getBooleanExtra("logout", false);

        if (loggedOut) {
            Toast.makeText(LoginActivity.this,"Logged out successfully", Toast.LENGTH_LONG).show();
        }
    }
    //method to log in
    private void loginEmailPassUser(String email, String pwd) {
        // Checking for empty texts
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
            firebaseAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            // Authentication succeeded
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish(); // Prevents the user from going back to the login screen
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Authentication failed
                            Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // If either field is empty
            Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_LONG).show();
        }
    }

}