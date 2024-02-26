package com.example.groupproject;

import java.io.Serializable;

public class Park implements Serializable {
    private String name;
    private String address;
    private String description;
    private boolean favorite;

    public Park(String name, String city, String description) {
        this.name = name;
        this.address = city;
        this.description = description;
        this.favorite = true;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}
