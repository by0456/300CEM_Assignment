package com.assignment.travelplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PlaceAdapter extends ArrayAdapter<Place> {

    private int resource;
    private ArrayList<Place> place;
    private Context context;

    public PlaceAdapter(Context context, int resource, ArrayList<Place> place) {
        super(context, resource, place);
        this.resource = resource;
        this.place = place;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            if (v == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(resource, parent, false);
            }

            TextView tvPlaceName = (TextView) v.findViewById(R.id.tvPlaceName);
            TextView tvPlaceDescription = (TextView) v.findViewById(R.id.tvPlaceDescription);
            TextView tvPlaceLatitude = (TextView) v.findViewById(R.id.tvPlaceLatitude);
            TextView tvPlaceLongitude = (TextView) v.findViewById(R.id.tvPlaceLongitude);
            TextView tvPlaceAddress = (TextView) v.findViewById(R.id.tvPlaceAddress);
            TextView tvPlaceDateTime = (TextView)v.findViewById(R.id.tvPlaceDateTime);

            String hour="";
            String minute="";
            if(place.get(position).getPlaceTime()[0]<10){
                hour = "0"+place.get(position).getPlaceTime()[0];
            }else{
                hour = ""+place.get(position).getPlaceTime()[0];
            }
            if(place.get(position).getPlaceTime()[1]<10){
                minute = "0"+place.get(position).getPlaceTime()[1];
            }else{
                minute = ""+place.get(position).getPlaceTime()[1];
            }

            tvPlaceDateTime.setText(place.get(position).getPlaceDate()[2]+" / "+place.get(position).getPlaceDate()[1]+" / "+place.get(position).getPlaceDate()[0]+"   "
            +hour+" : "+minute);
            tvPlaceName.setText(place.get(position).getName());
            tvPlaceDescription.setText(place.get(position).getDescription());


            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
            String language = sharedPreferences.getString("language", "");
            String address, latitude, longitude;
            if(language.equals("en")){
                if(!(place.get(position).getAddress().equals(""))){
                    tvPlaceAddress.setText("Address : "+place.get(position).getAddress());
                }
                if(!(place.get(position).getLatitude().equals(""))){
                    tvPlaceLatitude.setText("Latitude : "+place.get(position).getLatitude());
                }
                if(!(place.get(position).getLongitude().equals(""))){
                    tvPlaceLongitude.setText("Longitude : "+place.get(position).getLongitude());
                }
            }else if(language.equals("zh")){
                if(!(place.get(position).getAddress().equals(""))){
                    tvPlaceAddress.setText("地址 : "+place.get(position).getAddress());
                }
                if(!(place.get(position).getLatitude().equals(""))){
                    tvPlaceLatitude.setText("緯度 : "+place.get(position).getLatitude());
                }
                if(!(place.get(position).getLongitude().equals(""))){
                    tvPlaceLongitude.setText("經度 : "+place.get(position).getLongitude());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}
