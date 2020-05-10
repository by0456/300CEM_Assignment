package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddPlaceActivity extends AppCompatActivity {

    private ArrayList<Plan> plan;
    private ArrayList<Place> placeList;
    private EditText etName;
    private EditText etDescription;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private TextView tvAddress;

    private TextView tvPlaceDate;
    private TextView tvPlaceTime;
    private int position;
    private int placeYear, placeMonth, placeDay, placeHour, placeMinute;
    private DatePicker dpPlaceDate;
    private TimePicker tpPlaceTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Intent intent = getIntent();
        final Calendar c = Calendar.getInstance();

        position = intent.getIntExtra("position", 0);
        loadData(position);
        placeList = plan.get(position).getPlaces();
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvAddress = (TextView)findViewById(R.id.tvAddress);

        tvPlaceDate = (TextView)findViewById(R.id.tvPlaceDate);
        tvPlaceTime = (TextView)findViewById(R.id.tvPlaceTime);
        dpPlaceDate = (DatePicker)findViewById(R.id.dpPlaceDate);
        tpPlaceTime = (TimePicker)findViewById(R.id.tpPlaceTime);
        tpPlaceTime.setIs24HourView(true);
        placeYear = c.get(Calendar.YEAR);
        placeMonth = c.get(Calendar.MONTH);
        placeDay = c.get(Calendar.DAY_OF_MONTH);
        placeHour = 00;
        placeMinute = 00;



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
                placeYear = dpPlaceDate.getYear();
                placeMonth = dpPlaceDate.getMonth() + 1;
                placeDay = dpPlaceDate.getDayOfMonth();
                placeHour = tpPlaceTime.getHour();
                placeMinute = tpPlaceTime.getMinute();

                placeList.add(new Place(etName.getText().toString(), placeYear, placeMonth, placeDay, placeHour, placeMinute, tvAddress.getText().toString(), etDescription.getText().toString(), tvLatitude.getText().toString(), tvLongitude.getText().toString()));
                plan.get(position).setPlaces(placeList);
                saveData();
                Intent intent3 = new Intent(this, ViewPlaceActivity.class);
                intent3.putExtra("position", position);
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
