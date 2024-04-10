package com.example.groupproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Park implements Serializable, Parcelable {
    private String name;
    private double latitude;
    private double longitude;

    private boolean favorite;

    public Park(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.favorite = false;
    }

    protected Park(Parcel in) {
        name = in.readString();
        longitude = Double.parseDouble(in.readString());
        latitude = Double.parseDouble(in.readString());
        favorite = in.readByte() != 0;
    }

    public static final Creator<Park> CREATOR = new Creator<Park>() {
        @Override
        public Park createFromParcel(Parcel in) {
            return new Park(in);
        }

        @Override
        public Park[] newArray(int size) {
            return new Park[size];
        }
    };

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(String.valueOf(longitude));
        dest.writeString(String.valueOf(latitude));
        dest.writeByte((byte) (favorite ? 1 : 0));
    }
}
