package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Favorites extends AppCompatActivity {

    Button logOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        logOut = findViewById(R.id.logOutButton);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the signOut method when you want to sign the user out
                signOut();
            }
        });


    }


    private void signOut() {
        mAuth.signOut();
        Intent i = new Intent(Favorites.this, LoginActivity.class);
        i.putExtra("logout", true);
        startActivity(i);
    }
}