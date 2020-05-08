package com.assignment.travelplanner;

import android.content.Context;
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

            tvPlaceName.setText(place.get(position).getName());
            tvPlaceDescription.setText(place.get(position).getDescription());
            tvPlaceLatitude.setText("The Latitude : "+place.get(position).getLatitude());
            tvPlaceLongitude.setText("The Longitude : "+place.get(position).getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}