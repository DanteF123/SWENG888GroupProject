package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button mUseMyLocationButton, logOutButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        mUseMyLocationButton = (Button) findViewById(R.id.UseMyLocationButton);
        mUseMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                startActivity(intent);

            }
        });

        logOutButton = findViewById(R.id.logOutButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the signOut method when you want to sign the user out
                signOut();
            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        i.putExtra("logout", true);
        startActivity(i);
    }
}
