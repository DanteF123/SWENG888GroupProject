package com.example.groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    private ArrayList<Park> parks;
    private Context context;

    public ArrayList<Park> favoriteParks= new ArrayList<>();


    public SearchRecyclerAdapter(ArrayList<Park> parks,Context context){
        this.parks=parks;
        this.context=context;
    }
    public SearchRecyclerAdapter(ArrayList<Park> parks){
        this.parks=parks;

    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {
        Park park = parks.get(position);
        holder.title.setText(park.getName());
        //holder.address.setText(park.getAddress());

        if(parks.get(position).isFavorite()){
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
        TextView title,address;
        ImageButton favorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.textViewParkName);
            address = itemView.findViewById(R.id.textViewParkLoc);
            favorite=itemView.findViewById(R.id.parkListFavorite);

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
        if(parks.get(index).isFavorite()){
            parks.get(index).setFavorite(false);
            favoriteParks.remove(parks.get(index));
        }else{
            parks.get(index).setFavorite(true);
            favoriteParks.add((Park) parks.get(index));
        }


    }

}
