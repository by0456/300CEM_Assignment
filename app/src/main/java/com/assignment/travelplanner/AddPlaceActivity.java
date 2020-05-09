package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddPlaceActivity extends AppCompatActivity {

    private ArrayList<Plan> plan;
    private ArrayList<Place> placeList;
    private EditText etName;
    private EditText etDescription;
    private EditText etLatitude;
    private EditText etLongitude;
    private Button btnSave;
    private Button btnMap;
    private int position;

    private static final String TAG = "AddPlaceActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);
        loadData(position);
        placeList = plan.get(position).getPlaces();
        btnSave = (Button)findViewById(R.id.button_save2);
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        btnMap = (Button)findViewById(R.id.btnMap);
        init();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeList.add(new Place(etName.getText().toString(), etDescription.getText().toString(), etLatitude.getText().toString(), etLongitude.getText().toString()));
                plan.get(position).setPlaces(placeList);
                saveData();
                Intent intent2 = new Intent(v.getContext(), EditPlanActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        if(isServicesOK()){
            init();
        }

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
            String Latitude = data.getStringExtra("Latitude");
            String Longitude = data.getStringExtra("Longitude");

            etName.setText(name);
            etDescription.setText(address);
            etLatitude.setText(Latitude);
            etLongitude.setText(Longitude);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, EditPlanActivity.class);

        startActivity(intent2);
        finish();
    }

    private void init(){
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), MapActivity.class);
                intent2.putExtra("position", position);
                startActivityForResult(intent2, 2);
            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK() : checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AddPlaceActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error orrcured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AddPlaceActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
