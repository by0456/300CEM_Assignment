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

public class EditPlaceActivity extends AppCompatActivity {

    private ArrayList<Plan> plan;
    private ArrayList<Place> placeList;
    private EditText etName_edit;
    private EditText etDescription_edit;
    private TextView tvLatitude_edit;
    private TextView tvLongitude_edit;
    private TextView tvAddress_edit;
    private Button btnSave;
    private Button btnMap;
    private int position;
    private int position_place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);
        Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);
        position_place = intent.getIntExtra("position_place", 0);
        loadData(position);
        placeList = plan.get(position).getPlaces();
        etName_edit = (EditText) findViewById(R.id.etName_edit);
        etDescription_edit = (EditText) findViewById(R.id.etDescription_edit);
        tvLatitude_edit = (TextView) findViewById(R.id.tvLatitude_edit);
        tvLongitude_edit = (TextView) findViewById(R.id.tvLongitude_edit);
        tvAddress_edit = (TextView)findViewById(R.id.tvAddress_edit);

        etName_edit.setText(placeList.get(position_place).getName());
        etDescription_edit.setText(placeList.get(position_place).getDescription());
        tvLatitude_edit.setText(placeList.get(position_place).getLatitude());
        tvLongitude_edit.setText(placeList.get(position_place).getLongitude());
        tvAddress_edit.setText(placeList.get(position_place).getAddress());

        if(tvAddress_edit.getText().toString().equals("")){
            tvAddress_edit.setVisibility(View.GONE);
        }
        if(tvLatitude_edit.getText().toString().equals("")){
            tvLatitude_edit.setVisibility(View.GONE);
        }
        if(tvLongitude_edit.getText().toString().equals("")){
            tvLongitude_edit.setVisibility(View.GONE);
        }

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
                Intent intent3 = new Intent(this, MapActivity.class);
                intent3.putExtra("position", position);
                intent3.putExtra("action", "edit");
                intent3.putExtra("name", etName_edit.getText().toString());
                startActivityForResult(intent3, 3);
                break;
            case R.id.action_save:
                placeList.get(position_place).setName(etName_edit.getText().toString());
                placeList.get(position_place).setAddress(tvAddress_edit.getText().toString());
                placeList.get(position_place).setDescription(etDescription_edit.getText().toString());
                placeList.get(position_place).setLatitude(tvLatitude_edit.getText().toString());
                placeList.get(position_place).setLongitude(tvLongitude_edit.getText().toString());
                plan.get(position).setPlaces(placeList);
                saveData();
                Intent intent4 = new Intent(this, EditPlanActivity.class);
                startActivity(intent4);
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
        if(requestCode==3)
        {
            String name = data.getStringExtra("Name");
            String address = data.getStringExtra("Address");
            String latitude = data.getStringExtra("Latitude");
            String longitude = data.getStringExtra("Longitude");

            if(!address.equals("")){
                tvAddress_edit.setText(address);
                tvAddress_edit.setVisibility(View.VISIBLE);
            }else{
                tvAddress_edit.setVisibility(View.GONE);
            }
            if(!latitude.equals("")){
                tvLatitude_edit.setText(latitude);
                tvLatitude_edit.setVisibility(View.VISIBLE);
            }else{
                tvLatitude_edit.setVisibility(View.GONE);

            }
            if(!longitude.equals("")){
                tvLongitude_edit.setText(longitude);
                tvLongitude_edit.setVisibility(View.VISIBLE);
            }else{
                tvLongitude_edit.setVisibility(View.GONE);

            }

            if(!name.equals("")){
                etName_edit.setText(name);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, EditPlanActivity.class);

        startActivity(intent2);
        finish();
    }

}
