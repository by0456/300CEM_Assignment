package com.assignment.travelplanner;

public class Place {

    private String name;
    private String description;
    private String latitude;
    private String longitude;

    public Place(String name, String description, String latitude, String longitude){
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = name;
    }

}