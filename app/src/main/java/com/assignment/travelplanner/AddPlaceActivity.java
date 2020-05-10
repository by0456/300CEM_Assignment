package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddPlaceActivity extends AppCompatActivity {

    private ArrayList<Plan> plan;
    private ArrayList<Place> placeList;
    private EditText etName;
    private EditText etDescription;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView tvAddress;
    private Button btnSave;
    private Button btnMap;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);
        loadData(position);
        placeList = plan.get(position).getPlaces();
        etName = (EditText) findViewById(R.id.etName_edit);
        etDescription = (EditText) findViewById(R.id.etDescription_edit);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude_edit);
        tvAddress = (TextView)findViewById(R.id.tvAddress_edit);
        tvAddress.setVisibility(View.GONE);
        tvLatitude.setVisibility(View.GONE);
        tvLongitude.setVisibility(View.GONE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_map:
                Intent intent2 = new Intent(this, MapActivity.class);
                intent2.putExtra("position", position);
                intent2.putExtra("action", "add");
                startActivityForResult(intent2, 2);
                break;
            case R.id.action_save:
                placeList.add(new Place(etName.getText().toString(), tvAddress.getText().toString(), etDescription.getText().toString(), tvLatitude.getText().toString(), tvLongitude.getText().toString()));
                plan.get(position).setPlaces(placeList);
                saveData();
                Intent intent3 = new Intent(this, ViewPlaceActivity.class);
                startActivity(intent3);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void saveData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(plan);
        editor.putString("task list", json);
        editor.apply();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            String name = data.getStringExtra("Name");
            String address = data.getStringExtra("Address");
            String latitude = data.getStringExtra("Latitude");
            String longitude = data.getStringExtra("Longitude");

            if(!address.equals("")){
                tvAddress.setVisibility(View.VISIBLE);
            }
            if(!latitude.equals("")){
                tvLatitude.setVisibility(View.VISIBLE);
            }
            if(!longitude.equals("")){
                tvLongitude.setVisibility(View.VISIBLE);
            }

            etName.setText(name);
            tvAddress.setText(address);
            tvLatitude.setText(latitude);
            tvLongitude.setText(longitude);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, ViewPlaceActivity.class);

        startActivity(intent2);
        finish();
    }

}
