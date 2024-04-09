package com.example.groupproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ParkFinderCallback{
/*
 * An activity that displays a map showing the place at the device's current location.
 */
public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 1001;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private Context mContext;
    private SearchView mSearchView;

    List<Park> mParkList;
    RecyclerView mRecyclerView;
    ParkAdapter mAdapter;
    public Location enteredLocation;
    private FusedLocationProviderClient fusedLocationClient;

    private final Object lock = new Object(); // Lock object for synchronization

    // custom radius set in meters
    private static final int PROXIMITY_RADIUS = 100000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind items to corresponding layout
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext=getApplicationContext();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        mSearchView = findViewById(R.id.idSearchView);
        /** Include a listener to the searchView */
        createSearchViewListener();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the navigation drawer
        initializeDrawer(toolbar);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // call method to find nearby parks

        findNearbyParks();
        mRecyclerView=findViewById(R.id.recyclerView3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mParkList = new ArrayList<>();
    }
    private void findNearbyParks() {

        //create ParkFinderTask object
        ParkFinderTask task = new ParkFinderTask(mMap,this::onParksFound);

        // Define the place type
        String placeType = "park";

        // Set the search location based on current user location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);

        }
        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng currentLocation = new LatLng(latitude, longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));


                    // Define a query
                    StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                    query.append("location=").append(latitude).append(",").append(longitude);
                    query.append("&radius=").append(PROXIMITY_RADIUS);
                    query.append("&types=").append(placeType);
                    query.append("&sensor=true");
                    query.append("&key=").append("AIzaSyC2vkifl8EXv316cJy5jprIDcYUnYdU1Gk");


                    // Perform the search
                    task.execute(query.toString());


                    // Proceed with using the currentLocation
                } else {
                    // Handle the case when last known location is null (e.g., display a message to the user)
                    Toast.makeText(getApplicationContext(), "Last known location is not available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void searchForParks(Address location) {
        //create ParkFinderTask object
        ParkFinderTask task = new ParkFinderTask(mMap,this::onParksFound);

        // Define the place type
        String placeType = "park";


        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng currentLocation = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));


            // Define a query
            StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            query.append("location=").append(latitude).append(",").append(longitude);
            query.append("&radius=").append(PROXIMITY_RADIUS);
            query.append("&types=").append(placeType);
            query.append("&sensor=true");
            query.append("&key=").append("AIzaSyC2vkifl8EXv316cJy5jprIDcYUnYdU1Gk");


            // Perform the search
            task.execute(query.toString());


            // Proceed with using the currentLocation
        } else {
            // Handle the case when last known location is null (e.g., display a message to the user)
            Toast.makeText(getApplicationContext(), "Last known location is not available", Toast.LENGTH_SHORT).show();
        }
    }
    // Search on the map
    private void createSearchViewListener(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                synchronized (lock){
                    mParkList.clear();
                    mAdapter.notifyDataSetChanged();
                    lock.notifyAll();

                }

                /** Getting the location name from the searchView */
                String locationName = mSearchView.getQuery().toString();
                /** Create list of address where we will store the locations found **/
                List<Address> addressList = null;
                /** Check if the location is null */
                /** Initializing the geocode */
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    addressList = geocoder.getFromLocationName(locationName, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                /** Getting the location in the first position */
                Address address = null;
                if (addressList != null) {
                    address = addressList.get(0);
                    /** Creating the LatLng object to store the address coordinates */
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    searchForParks(address);

                    /** Add a marker */
                    mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));
                    /** Animate the camera */
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /** Since we are not using autocomplete, this method will not
                 * be implemented at this time */
                return false;
            }
        });
    }

    @Override
    public void onParksFound(List<Park> parkList) {
        if (parkList != null && !parkList.isEmpty()) {
            if (mAdapter == null) {
                mAdapter = new ParkAdapter(parkList);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.updateData(parkList); // Create a method in your adapter to update the dataset
            }
        } else {
            mParkList.clear();
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "No parks found nearby", Toast.LENGTH_SHORT).show();
        }

    }
}