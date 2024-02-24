package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {
    private ListView mListView;

    private ParkAdapter mParkAdapter;
    ArrayList<Park> parkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parkList = new ArrayList<>();
    }
}