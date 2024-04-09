package com.example.groupproject;

import android.os.AsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParkFinderTask extends AsyncTask<String, Void, List<Park>> {

    private GoogleMap mMap;
    private ParkFinderCallback callback;

    public ParkFinderTask(GoogleMap map, ParkFinderCallback callback) {
        this.mMap = map;
        this.callback = callback;
    }

    @Override
    protected List<Park> doInBackground(String... urls) {
        List<Park> parkList = new ArrayList<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // Read the input stream into a String
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            String result = buffer.toString();
            if (result != null) {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray results = jsonObj.getJSONArray("results");

                // Process the results array to extract amusement park data
                for (int i = 0; i < results.length(); i++) {
                    JSONObject parkObj = results.getJSONObject(i);
                    String name = parkObj.getString("name");
                    JSONObject location = parkObj.getJSONObject("geometry").getJSONObject("location");
                    double latitude = location.getDouble("lat");
                    double longitude = location.getDouble("lng");

                    // Create Park object and add it to the list
                    parkList.add(new Park(name, latitude, longitude));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return parkList;
    }

    @Override
    protected void onPostExecute(List<Park> parkList) {
        super.onPostExecute(parkList);
        // Update UI with park data
        if (parkList != null && mMap != null) {
            for (Park park : parkList) {
                LatLng parkLocation = new LatLng(park.getLatitude(), park.getLongitude());
                mMap.addMarker(new MarkerOptions().position(parkLocation).title(park.getName()));
            }
        }
        // Notify callback if available
        if (callback != null) {
            callback.onParksFound(parkList);
        }
    }
}
