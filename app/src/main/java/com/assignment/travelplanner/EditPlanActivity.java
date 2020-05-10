package com.assignment.travelplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class EditPlanActivity extends AppCompatActivity {
    private ArrayList<Plan> plan;
    private EditText etPlanName_edit;
    private int planYear;
    private int planMonth;
    private int planDay;
    private int position;
    private Button btnPlanDate_edit;
    private TextView tvPlanDate_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);


        btnPlanDate_edit = (Button)findViewById(R.id.btnPlanDate_edit);

        loadData();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        etPlanName_edit = (EditText)findViewById(R.id.etPlanName_edit);
        tvPlanDate_edit = (TextView)findViewById(R.id.tvPlanDate_edit);

        etPlanName_edit.setText(plan.get(position).getPlanName());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("");
        }

        if(plan.get(position).getPlanDate()[0]!=null&&plan.get(position).getPlanDate()[1]!=null&&plan.get(position).getPlanDate()[2]!=null){
            planYear = plan.get(position).getPlanDate()[0];
            planMonth = plan.get(position).getPlanDate()[1];
            planDay = plan.get(position).getPlanDate()[2];
            tvPlanDate_edit.setText("The Date of the plan : "+planDay + " - " + planMonth + " - " + planYear);
        }

        btnPlanDate_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvPlanDate_edit.setText("The Date of the plan : "+dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
                                planYear = year;
                                planMonth = monthOfYear + 1;
                                planDay = dayOfMonth;

                            }
                        }, planYear, planMonth, planDay);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_save:
                plan.get(position).setPlanName(tvPlanDate_edit.getText().toString());
                plan.get(position).setPlanDate(planYear, planMonth, planDay);
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
