package com.assignment.travelplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Plan implements Parcelable {
    private String planName;
    private ArrayList<Place> places;

    public Plan(String planName, ArrayList<Place> places){
        this.planName = planName;
        this.places = places;
    }

    protected Plan(Parcel in) {
        places = in.createTypedArrayList(Place.CREATOR);
    }



    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

    public ArrayList<Place> getPlaces(){
        return this.places;
    }

    public String getPlanName(){
        return this.planName;
    }

    public void setPlaces(ArrayList<Place> places){
        this.places = places;
    }

    public void setPlanName(String planName){
        this.planName = planName;
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
