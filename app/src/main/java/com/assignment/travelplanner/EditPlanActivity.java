package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditPlanActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Plan> plan = new ArrayList<>();
    private ArrayList<Place> placeList = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        loadData(position);
        listView = (ListView)findViewById(R.id.listViewPlace);
        listView.setAdapter(new PlaceAdapter(this, R.layout.list_place_item, placeList));
        Button btnAddPlace = (Button)findViewById(R.id.btnAddPlace);
        Button btnEditPlanActivityBack = (Button)findViewById(R.id.btnEditPlanActivityBack);
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddPlaceActivity.class);
                intent.putExtra("position", position);

                startActivity(intent);
                finish();
            }
        });

        btnEditPlanActivityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadData(int position){
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Plan >>() {}.getType();
        plan = gson.fromJson(json, type);
        placeList = plan.get(position).getPlaces();

        if(placeList == null){
            placeList = new ArrayList<>();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity.class);

        startActivity(intent2);
        finish();
    }
}
