package com.example.groupproject;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;
import java.util.List;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Intent;
import android.net.Uri;


public class Favorites extends BaseActivity implements ParkNavigationListener {
    private ParkRecyclerAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mCollectionReference = db.collection("Favorites");

    private ArrayList<Park> parkList = new ArrayList<>();
    //1- adapter view
    RecyclerView recyclerView;
    private List<Park> mParkList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_favorites);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDrawer(toolbar);

        // Setup RecyclerView
        setFirebaseRecycler();
    }

    @Override
    public void onParksFound(List<Park> parkList) {

    }
    //Method to attach data to recyclerview using firebase.
    private void setFirebaseRecycler(){

        recyclerView = findViewById(R.id.recyclerView);
        TextView emptyView = findViewById(R.id.empty_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ParkRecyclerAdapter(parkList, this);
        recyclerView.setAdapter(adapter);

        mParkList = new ArrayList<>();


        // Obtain data from collection
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot parks:queryDocumentSnapshots){
                    Park park = parks.toObject(Park.class);
                    mParkList.add(park);

                }

                if (mParkList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);

                    adapter = new ParkRecyclerAdapter(parkList, Favorites.this);
                    recyclerView.setAdapter(adapter);
                }


                adapter= new ParkRecyclerAdapter((ArrayList<Park>) mParkList, Favorites.this);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
                /** Notify user if there is an error. */
                Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void navigateToPark(Park park) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + park.getLatitude() + "," + park.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps is not installed.", Toast.LENGTH_LONG).show();
        }
    }

}