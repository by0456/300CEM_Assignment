package com.assignment.travelplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditPlaceActivity extends AppCompatActivity {

    private ArrayList<Plan> plan;
    private ArrayList<Place> placeList;
    private EditText etName_edit;
    private EditText etDescription_edit;
    private TextView tvLatitude_edit;
    private TextView tvLongitude_edit;
    private TextView tvAddress_edit;
    private int position;
    private int position_place;
    private int placeYear, placeMonth, placeDay, placeHour, placeMinute;
    private DatePicker dpPlaceDate_edit;
    private TimePicker tpPlaceTime_edit;
    private static final int REQUEST_CODE_SPEECH = 1003;
    private ImageButton ibPlaceVoice_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);
        final Calendar c = Calendar.getInstance();
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

        dpPlaceDate_edit = (DatePicker)findViewById(R.id.dpPlaceDate_edit);
        tpPlaceTime_edit = (TimePicker)findViewById(R.id.tpPlaceTime_edit);
        tpPlaceTime_edit.setIs24HourView(true);

        placeYear = placeList.get(position_place).getPlaceDate()[0];
        placeMonth = placeList.get(position_place).getPlaceDate()[1];
        placeDay = placeList.get(position_place).getPlaceDate()[2];
        placeHour = placeList.get(position_place).getPlaceTime()[0];
        placeMinute = placeList.get(position_place).getPlaceTime()[1];;

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

        ibPlaceVoice_edit = (ImageButton)findViewById(R.id.ibPlaceVoice_edit);
        ibPlaceVoice_edit.setOnClickListener(new View.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_edit_place, menu);
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
                intent3.putExtra("action", 2);
                intent3.putExtra("name", etName_edit.getText().toString());
                startActivityForResult(intent3, 3);
                break;
            case R.id.action_delete:
                AlertDialog diaBox = AskOption();
                diaBox.show();
                break;
            case R.id.action_save:

                placeYear = dpPlaceDate_edit.getYear();
                placeMonth = dpPlaceDate_edit.getMonth() + 1;
                placeDay = dpPlaceDate_edit.getDayOfMonth();
                placeHour = tpPlaceTime_edit.getHour();
                placeMinute = tpPlaceTime_edit.getMinute();

                placeList.get(position_place).setName(etName_edit.getText().toString());
                placeList.get(position_place).setAddress(tvAddress_edit.getText().toString());
                placeList.get(position_place).setDescription(etDescription_edit.getText().toString());
                placeList.get(position_place).setLatitude(tvLatitude_edit.getText().toString());
                placeList.get(position_place).setLongitude(tvLongitude_edit.getText().toString());
                placeList.get(position_place).setPlaceDate(placeYear, placeMonth, placeDay);
                placeList.get(position_place).setPlaceTime(placeHour, placeMinute);

                plan.get(position).setPlaces(placeList);
                saveData();
                Intent intent4 = new Intent(this, ViewPlaceActivity.class);
                intent4.putExtra("position", position);
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
        } else if(requestCode==REQUEST_CODE_SPEECH){
            if(resultCode == RESULT_OK && null!=data){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                etName_edit.setText(result.get(0));
            }
        }
    }

    private void speak(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, please speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH);

        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, ViewPlaceActivity.class);

        intent2.putExtra("position", position);
        startActivity(intent2);

        finish();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete this place")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        plan.get(position).getPlaces().remove(position_place);
                        saveData();
                        Intent intent = new Intent(EditPlaceActivity.this, ViewPlaceActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                        finish();

                        dialog.dismiss();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

}
