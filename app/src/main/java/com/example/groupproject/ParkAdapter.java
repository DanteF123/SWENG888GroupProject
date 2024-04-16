


package com.example.groupproject;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ViewHolder> {

    private List<Park> parks;
    private List<Park> favoriteParks;
    private Context mContext;
    private ParkNavigationListener navigationListener;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mCollectionReference = db.collection("Favorites");

    public ParkAdapter(List<Park> parks, Context context, ParkNavigationListener listener){
        this.parks=parks;
        this.mContext=context;
        this.favoriteParks=new ArrayList<>();
        this.navigationListener = listener;
    }

    @NonNull
    @Override
    public ParkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_list_item, parent, false);
        populateFavorites();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkAdapter.ViewHolder holder, int position) {
        Park park = parks.get(position);

        holder.name.setText(park.getName());

        if(favoriteParks.contains(park)){
            holder.favorite.setImageResource(R.drawable.favorite_filled);
        }else{
            holder.favorite.setImageResource(R.drawable.favorite_empty);
        }


    }

    @Override
    public int getItemCount() {
        return parks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageButton favorite;
        public ImageButton navigation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.parkText);
            favorite=itemView.findViewById(R.id.parkListFavorite);
            navigation=itemView.findViewById(R.id.parkNavigate);



            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Park selectedPark = parks.get(getAdapterPosition());

                    if(favoriteParks.contains(selectedPark)){
                        favorite.setImageResource(R.drawable.favorite_empty);
                        removeFavorite(getAdapterPosition());

                    }else{
                        favorite.setImageResource(R.drawable.favorite_filled);
                        addFavorite(getAdapterPosition());
                    }


                }
            });

            navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (navigationListener != null) {
                        Park selectedPark = parks.get(getAdapterPosition());
                        navigationListener.navigateToPark(selectedPark);
                    }
                }
            });

        }

    }

    public void updateData(List<Park> newData) {
        parks.clear(); // Clear the existing data
        parks.addAll(newData); // Add new data to the dataset
        notifyDataSetChanged(); // Notify RecyclerView that the dataset has changed
    }

    public void populateFavorites(){
        favoriteParks.clear();
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot parks:queryDocumentSnapshots){
                    Park park = parks.toObject(Park.class);
                    favoriteParks.add(park);
                }
                notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext.getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });



    }

    public void removeFavorite(int index){
        String park = parks.get(index).getName();
        db.collection("Favorites").document(park).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                notifyItemChanged(index);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext,"Error deleting Document.",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void addFavorite(int index){
        Map<String,Object> parkHashmap = new HashMap<>();

        Park selectedPark = parks.get(index);

        String parkName = selectedPark.getName();
        double latitude = selectedPark.getLatitude();
        double longitude = selectedPark.getLongitude();

        // Store name & location data
        parkHashmap.put("name",parkName);
        parkHashmap.put("latitude", latitude);
        parkHashmap.put("longitude", longitude);

        DocumentReference documentReference = mCollectionReference.document(selectedPark.getName());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    removeFavorite(index);
                }
                else {
                    documentReference.set(parkHashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(mContext, "Park added to favorites", Toast.LENGTH_SHORT).show();
                            notifyItemChanged(index);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext,"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



    }


}