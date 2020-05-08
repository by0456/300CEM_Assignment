package com.assignment.travelplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlaceList implements Parcelable {
    private ArrayList<Place> places;

    public PlaceList(ArrayList<Place> places){
        this.places = places;
    }

    protected PlaceList(Parcel in) {
        places = in.createTypedArrayList(Place.CREATOR);
    }

    public static final Creator<PlaceList> CREATOR = new Creator<PlaceList>() {
        @Override
        public PlaceList createFromParcel(Parcel in) {
            return new PlaceList(in);
        }

        @Override
        public PlaceList[] newArray(int size) {
            return new PlaceList[size];
        }
    };

    public ArrayList<Place> getPlaces(){
        return this.places;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(places);
    }
}
