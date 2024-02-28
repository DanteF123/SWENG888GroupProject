package com.example.groupproject;


import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;




public class SearchResults extends AppCompatActivity {
    private SearchRecyclerAdapter adapter;
    private ArrayList<Park> parkList;

    private Button favoritesButton;
    //1- adapter view
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        favoritesButton = findViewById(R.id.favoriteButton);

        recyclerView=findViewById(R.id.searchRecyclerView);
        //
        parkList = createFakeParkList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SearchRecyclerAdapter(parkList);
        recyclerView.setAdapter(adapter);

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapter.favoriteParks.size()>0){
                    Intent intent = new Intent(SearchResults.this,Favorites.class);
                    intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) adapter.favoriteParks);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Favorites Selected",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    // Method to create fake park data
    private ArrayList<Park> createFakeParkList() {
        ArrayList<Park> parkList = new ArrayList<>();
        parkList.add(new Park("Yosemite National Park", "California", "Yosemite National Park is in California’s Sierra Nevada mountains. It’s famed for its giant, ancient sequoia trees, and for Tunnel View, the iconic vista of towering Bridalveil Fall and the granite cliffs of El Capitan and Half Dome. In Yosemite Village are shops, restaurants, lodging, the Yosemite Museum and the Ansel Adams Gallery, with prints of the photographer’s renowned black-and-white landscapes of the area."));
        parkList.add(new Park("Central Park", "New York, NY", "Central Park is an urban park between the Upper West Side and Upper East Side neighborhoods of Manhattan in New York City that was the first landscaped park in the United States."));
        parkList.add(new Park("Arches National Park", "Utah", "Arches National Park lies north of Moab in the state of Utah. Bordered by the Colorado River in the southeast, it’s known as the site of more than 2,000 natural sandstone arches, such as the massive, red-hued Delicate Arch in the east. Long, thin Landscape Arch stands in Devils Garden to the north. Other geological formations include Balanced Rock, towering over the desert landscape in the middle of the park."));
        parkList.add(new Park("Banff National Park","Alberta, Canada","Rocky Mountain park offering year-round activities & glacial lakes such as Lake Louise, also a town. "));
        return parkList;
    }

}






