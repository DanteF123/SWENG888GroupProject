package com.example.groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ParkRecyclerAdapter extends RecyclerView.Adapter<ParkRecyclerAdapter.ViewHolder> {
    private ArrayList<Park> parks;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mCollectionReference = db.collection("Favorites");

    public ParkRecyclerAdapter(ArrayList<Park> parks,Context context){
        this.parks=parks;
        this.context=context;
    }
    public ParkRecyclerAdapter(ArrayList<Park> parks){
        this.parks=parks;

    }

    @NonNull
    @Override
    public ParkRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ParkRecyclerAdapter.ViewHolder holder, int position) {
        Park park = parks.get(position);
        holder.title.setText(park.getName());
        //holder.address.setText(park.getAddress());
        holder.favorite.setImageResource(R.drawable.favorite_filled);



    }

    @Override
    public int getItemCount() {
       return parks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,address;
        ImageButton favorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.textViewParkName);
            address = itemView.findViewById(R.id.textViewParkLoc);
            favorite=itemView.findViewById(R.id.filledFavorite);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //if you click the favorite button, remove the item from the list.
                    changeFavorite(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }

    }
    public void changeFavorite(int index){
        String park = parks.get(index).getName();
        db.collection("Favorites").document(park).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                parks.remove(index);
                notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error deleting Document.",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
