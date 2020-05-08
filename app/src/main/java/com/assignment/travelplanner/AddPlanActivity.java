package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

public class AddPlanActivity extends AppCompatActivity {
    private String[] candidateNames;
    private String[] candidateDetails;
    private ArrayList<Place> candidates = new ArrayList<>();
    private ArrayList<Place> placeList;
    private EditText etName;
    private EditText etDescription;
    private EditText etLatitude;
    private EditText etLongitude;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        Intent intent = getIntent();
        placeList = intent.getParcelableArrayListExtra("places");
        btnSave = (Button)findViewById(R.id.button_save2);
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLongitude = (EditText) findViewById(R.id.etLongitude);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeList.add(new Place(etName.getText().toString(), etDescription.getText().toString(), etLatitude.getText().toString(), etLongitude.getText().toString()));
                saveData();
                Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    private void saveData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(placeList);
        editor.putString("task list", json);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity.class);

        startActivity(intent2);
        finish();
    }
}
