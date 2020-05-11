package com.assignment.travelplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class EditPlanActivity extends AppCompatActivity {
    private ArrayList<Plan> plan;
    private EditText etPlanName_edit;
    private int planYear, planYear2;
    private int planMonth, planMonth2;
    private int planDay, planDay2;
    private int position;
    private TextView tvBeginDate_edit, tvEndDate_edit;
    private DatePicker dpPlanStartDate_edit, dpPlanEndDate_edit;
    private static final int REQUEST_CODE_SPEECH = 1001;
    private ImageButton ibPlanVoice_edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "");
        setLocale(language);
        setContentView(R.layout.activity_edit_plan);
        loadData();


        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        etPlanName_edit = (EditText)findViewById(R.id.etPlanName_edit);
        tvBeginDate_edit = (TextView)findViewById(R.id.tvBeginDate_edit);
        tvEndDate_edit = (TextView)findViewById(R.id.tvEndDate_edit);

        etPlanName_edit.setText(plan.get(position).getPlanName());

        dpPlanStartDate_edit = (DatePicker)findViewById(R.id.dpPlanStartDate_edit);
        dpPlanEndDate_edit = (DatePicker)findViewById(R.id.dpPlanEndDate_edit);

        ibPlanVoice_edit = (ImageButton)findViewById(R.id.ibPlanVoice_edit);
        ibPlanVoice_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(R.string.editPlanTitle);
        }

        if(plan.get(position).getPlanBeginDate()[0]!=null&&plan.get(position).getPlanBeginDate()[1]!=null&&plan.get(position).getPlanBeginDate()[2]!=null){
            planYear = plan.get(position).getPlanBeginDate()[0];
            planMonth = plan.get(position).getPlanBeginDate()[1];
            planDay = plan.get(position).getPlanBeginDate()[2];
        }

        if(plan.get(position).getPlanEndDate()[0]!=null&&plan.get(position).getPlanEndDate()[1]!=null&&plan.get(position).getPlanEndDate()[2]!=null){
            planYear2 = plan.get(position).getPlanEndDate()[0];
            planMonth2 = plan.get(position).getPlanEndDate()[1];
            planDay2 = plan.get(position).getPlanEndDate()[2];
        }

        dpPlanStartDate_edit.updateDate(planYear, planMonth-1, planDay);
        dpPlanEndDate_edit.updateDate(planYear2, planMonth2-1, planDay2);


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
                planYear = dpPlanStartDate_edit.getYear();
                planMonth = dpPlanStartDate_edit.getMonth()+1;
                planDay = dpPlanStartDate_edit.getDayOfMonth();

                planYear2 = dpPlanEndDate_edit.getYear();
                planMonth2 = dpPlanEndDate_edit.getMonth()+1;
                planDay2 = dpPlanEndDate_edit.getDayOfMonth();

                plan.get(position).setPlanName(etPlanName_edit.getText().toString());
                plan.get(position).setPlanBeginDate(planYear, planMonth, planDay);
                plan.get(position).setPlanEndDate(planYear2, planMonth2, planDay2);
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

    private void speak(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH);

        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH:{
                if(resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    etPlanName_edit.setText(result.get(0));
                }
            }
        }
    }



    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity.class);

        startActivity(intent2);
        finish();
    }

    public void setLocale(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}
