package com.example.groupproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    AutoCompleteTextView mAutoCompleteTextView;
    AutoCompleteAdapter mAutoCompleteAdapter;
    TextView responseView;
    PlacesClient mPlacesClient;
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
        responseView = findViewById(R.id.response);

        String apiKey = getString(R.string.api_key);
        if (apiKey.isEmpty()) {
            responseView.setText(getString(R.string.error));
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        mPlacesClient = Places.createClient(this);
        initAutoCompleteTextView();

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
    private void initAutoCompleteTextView() {

        mAutoCompleteTextView = findViewById(R.id.homeScreenSearchEditText);
        mAutoCompleteTextView.setThreshold(1);
        mAutoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        mAutoCompleteAdapter = new AutoCompleteAdapter(this, mPlacesClient);
        mAutoCompleteTextView.setAdapter(mAutoCompleteAdapter);
    }

    private final AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = mAutoCompleteAdapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    mPlacesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
                            responseView.setText(task.getPlace().getName() + "\n" + task.getPlace().getAddress());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            responseView.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    private void signOut() {
        mAuth.signOut();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        i.putExtra("logout", true);
        startActivity(i);
    }
}
