package com.example.groupproject;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the navigation drawer
        initializeDrawer(toolbar);

        TextView emailTextView = findViewById(R.id.profileEmailTextView);
        TextView passwordTextView = findViewById(R.id.profilePasswordTextView);  // Placeholder

        // Get & set the user's name & email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            emailTextView.setText(String.format("Email: %s", user.getEmail()));

            // For password, we don't fetch and show due to security reasons
            passwordTextView.setText("Password: ********");
        }
    }

    @Override
    public void onParksFound(List<Park> parkList) {

    }
}
