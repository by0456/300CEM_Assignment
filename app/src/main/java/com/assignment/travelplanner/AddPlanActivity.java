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
import java.util.Calendar;
import java.util.Locale;

public class AddPlanActivity extends AppCompatActivity {
    private ArrayList<Plan> plan;
    private EditText etPlanName;
    private int planYear, planYear2;
    private int planMonth, planMonth2;
    private int planDay, planDay2;
    private TextView tvPlanBeginDate, tvPlanEndDate;
    private DatePicker dpPlanStartDate, dpPlanEndDate;
    private static final int REQUEST_CODE_SPEECH = 1000;
    private ImageButton ibPlanVoice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "");
        setLocale(language);
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

        ibPlanVoice = (ImageButton)findViewById(R.id.ibPlanVoice);
        ibPlanVoice.setOnClickListener(new View.OnClickListener() {
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

                    etPlanName.setText(result.get(0));
                }
            }
        }
    }


    public void setLocale(String language) {
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity.class);

        startActivity(intent2);
        finish();
    }


}
