package com.videri.openweathermap.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AIDA on 3/9/16.
 */
public class Coord implements Serializable {
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @SerializedName("lon")
    private double longitude;

    @SerializedName("lat")
    private double latitude;
}
