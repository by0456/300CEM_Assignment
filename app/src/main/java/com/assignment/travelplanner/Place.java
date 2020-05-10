package com.assignment.travelplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {

    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private Integer [] placeDate = new Integer[3];

    private Integer [] placeTime = new Integer[2];


    private String address;

    public Place(String name, int year, int month, int day, int hour, int minute, String address, String description, String latitude, String longitude){
        this.name = name;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeDate[0] = year;
        this.placeDate[1] = month;
        this.placeDate[2] = day;
        this.placeTime[0] = hour;
        this.placeTime[1] = minute;
    }

    protected Place(Parcel in) {
        name = in.readString();
        description = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public String getLongitude(){
        return this.longitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer[] getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(int year, int month, int day) {
        this.placeDate[0] = year;
        this.placeDate[1] = month;
        this.placeDate[2] = day;
    }

    public Integer[] getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(int hour, int minute) {
        this.placeTime[0] = hour;
        this.placeTime[1] = minute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}