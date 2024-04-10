package com.example.groupproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ParkAdapter extends RecyclerView.Adapter<ParkAdapter.ViewHolder> {

    private List<Park> parks;

    public ParkAdapter(List<Park> parks){this.parks=parks;}

    @NonNull
    @Override
    public ParkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkAdapter.ViewHolder holder, int position) {
        Park park = parks.get(position);
        holder.name.setText(park.getName());

    }

    @Override
    public int getItemCount() {
        return parks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.parkText);
        }
    }

    public void updateData(List<Park> newData) {
        parks.clear(); // Clear the existing data
        parks.addAll(newData); // Add new data to the dataset
        notifyDataSetChanged(); // Notify RecyclerView that the dataset has changed
    }
}