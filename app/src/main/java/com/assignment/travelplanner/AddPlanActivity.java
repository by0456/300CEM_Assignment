package com.assignment.travelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class AddPlanActivity extends AppCompatActivity {
    private ArrayList<Plan> plan;
    private EditText etPlanName;
    private int planYear, planYear2;
    private int planMonth, planMonth2;
    private int planDay, planDay2;
    private TextView tvPlanBeginDate, tvPlanEndDate;
    private DatePicker dpPlanStartDate, dpPlanEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        loadData();

        final Calendar c = Calendar.getInstance();
        planYear = c.get(Calendar.YEAR);
        planMonth = c.get(Calendar.MONTH)+1;
        planDay = c.get(Calendar.DAY_OF_MONTH);
        planYear2 = c.get(Calendar.YEAR);
        planMonth2 = c.get(Calendar.MONTH)+1;
        planDay2 = c.get(Calendar.DAY_OF_MONTH);
        dpPlanStartDate = (DatePicker)findViewById(R.id.dpPlanStartDate);
        dpPlanEndDate = (DatePicker)findViewById(R.id.dpPlanEndDate);

        etPlanName = (EditText)findViewById(R.id.etPlanName);
        tvPlanBeginDate = (TextView)findViewById(R.id.tvBeginDate);
        tvPlanEndDate = (TextView)findViewById(R.id.tvEndDate);

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
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_save:

                planYear = dpPlanStartDate.getYear();
                planMonth = dpPlanStartDate.getMonth()+1;
                planDay = dpPlanStartDate.getDayOfMonth();

                planYear2 = dpPlanEndDate.getYear();
                planMonth2 = dpPlanEndDate.getMonth()+1;
                planDay2 = dpPlanEndDate.getDayOfMonth();

                plan.add(new Plan(etPlanName.getText().toString(), planYear, planMonth, planDay, planYear2, planMonth2, planDay2, new ArrayList<Place>()));
                saveData();
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
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

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity.class);

        startActivity(intent2);
        finish();
    }


}
