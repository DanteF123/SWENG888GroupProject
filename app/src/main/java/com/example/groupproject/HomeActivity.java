package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mUseMyLocationButton,searchButton, favorites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        mUseMyLocationButton = (Button) findViewById(R.id.UseMyLocationButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        favorites = (Button) findViewById(R.id.favoritesButton);
        mAuth = FirebaseAuth.getInstance();
        mUseMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Favorites.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchResults.class);
                startActivity(intent);
            }
        });
    }

    private void signOut(){
        mAuth.signOut();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        i.putExtra("logout", true);
        startActivity(i);
    }
}