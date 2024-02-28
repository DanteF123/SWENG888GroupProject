package com.example.groupproject;


import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;




public class Favorites extends AppCompatActivity {
    private ParkRecyclerAdapter adapter;

    private ArrayList<Park> parkList = new ArrayList<>();
    //1- adapter view
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_favorites);

        recyclerView=findViewById(R.id.recyclerView);


        parkList = getIntent().getParcelableArrayListExtra("data");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ParkRecyclerAdapter(parkList);
        recyclerView.setAdapter(adapter);


    }


}