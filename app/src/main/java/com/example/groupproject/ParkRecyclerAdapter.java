package com.example.groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParkRecyclerAdapter extends RecyclerView.Adapter<ParkRecyclerAdapter.ViewHolder> {
    private ArrayList<Park> parks;
    private Context context;

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
        holder.description.setText(park.getDescription());

        if (park.isFavorite()){
            holder.favorite.setBackgroundResource(R.drawable.favorite_filled);
        }
        else{
            holder.favorite.setBackgroundResource(R.drawable.favorite_empty);
        }




    }

    @Override
    public int getItemCount() {
       return parks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,description;
        ImageButton favorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.textViewParkName);
            description = itemView.findViewById(R.id.textViewParkDesc);
            favorite=itemView.findViewById(R.id.filledFavorite);
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Park park = parks.get(position);
                    park.setFavorite(false);
                }
            });

        }

    }
}
