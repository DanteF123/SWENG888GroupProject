package com.example.groupproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Park implements Serializable, Parcelable {
    private String name;
    private String address;
    private String description;
    private boolean favorite;

    public Park(String name, String city, String description) {
        this.name = name;
        this.address = city;
        this.description = description;
        this.favorite = false;
    }

    protected Park(Parcel in) {
        name = in.readString();
        address = in.readString();
        description = in.readString();
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

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
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
        dest.writeString(address);
        dest.writeString(description);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }
}
