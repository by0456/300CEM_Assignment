package com.assignment.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MyPlanFragment extends Fragment {
    private static final String TAG_L = "Testing log ";
    private TextView tw;
    private ListView listView;


    private ArrayList<Place> placeList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plan, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        listView = (ListView) view.findViewById(R.id.listViewSimple);
        listView.setAdapter(new PlaceAdapter(this.getContext(), R.layout.list_place_item, placeList));
        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity().getBaseContext(), "You clicked " + placeList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                    }
                }
        );
    }



    private void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Place >>() {}.getType();
        placeList = gson.fromJson(json, type);

        if(placeList == null){
            placeList = new ArrayList<>();
        }

    }
}
