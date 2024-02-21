package com.example.groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ParkAdapter extends ArrayAdapter<Park> {

    private Context context;
    private List<Park> parkList;

    public ParkAdapter(Context context, List<Park> parkList) {
        super(context, 0, parkList);
        this.context = context;
        this.parkList = parkList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.park_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewParkName = convertView.findViewById(R.id.textViewParkName);
            viewHolder.textViewParkAddress = convertView.findViewById(R.id.textViewParkAddress);
            viewHolder.imageViewFavorite = convertView.findViewById(R.id.imageViewFavorite);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Park park = parkList.get(position);

        // Set park name
        viewHolder.textViewParkName.setText(park.getName());

        // Set park address
        viewHolder.textViewParkAddress.setText(park.getAddress());

        // Set favorite button click listener
        viewHolder.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle favorite status
                park.setFavorite(!park.isFavorite());
                // Update the heart button image based on the favorite status
                if (park.isFavorite()) {
                    viewHolder.imageViewFavorite.setImageResource(R.drawable.favorite_filled);
                } else {
                    viewHolder.imageViewFavorite.setImageResource(R.drawable.favorite_empty);
                }
            }
        });

        // Set heart button image based on the favorite status
        if (park.isFavorite()) {
            viewHolder.imageViewFavorite.setImageResource(R.drawable.favorite_filled);
        } else {
            viewHolder.imageViewFavorite.setImageResource(R.drawable.favorite_empty);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewParkName;
        TextView textViewParkAddress;
        ImageView imageViewFavorite;
    }
}