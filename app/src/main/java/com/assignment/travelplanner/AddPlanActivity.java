package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddPlanActivity extends AppCompatActivity {
    private ArrayList<Plan> plan;
    private EditText etPlanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        loadData();

        etPlanName = (EditText)findViewById(R.id.etPlanName);
        Button btnCreatePlan = (Button)findViewById(R.id.btnCreatePlan);
        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.add(new Plan(etPlanName.getText().toString(), new ArrayList<Place>()));
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
        String json = gson.toJson(plan);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Plan >>() {}.getType();
        plan = gson.fromJson(json, type);

        if(plan == null){
            plan = new ArrayList<>();
        }

    }


}
