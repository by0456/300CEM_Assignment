package com.assignment.travelplanner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Plan implements Parcelable {
    private String planName;


    private Integer [] planBeginDate = new Integer [3];


    private Integer [] planEndDate = new Integer [3];
    private ArrayList<Place> places;

    public Plan(String planName, int year, int month, int day, int year2, int month2, int day2, ArrayList<Place> places){
        this.planName = planName;
        this.places = places;
        this.planBeginDate[0] = year;
        this.planBeginDate[1] = month;
        this.planBeginDate[2] = day;
        this.planEndDate[0] = year2;
        this.planEndDate[1] = month2;
        this.planEndDate[2] = day2;
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

    public Integer [] getPlanBeginDate() {
        return planBeginDate;
    }

    public void setPlanBeginDate(int year, int month, int day) {
        this.planBeginDate[0] = year;
        this.planBeginDate[1] = month;
        this.planBeginDate[2] = day;
    }

    public Integer[] getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(int year2, int month2, int day2) {
        this.planEndDate[0] = year2;
        this.planEndDate[1] = month2;
        this.planEndDate[2] = day2;
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
