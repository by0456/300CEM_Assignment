package com.assignment.travelplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class ViewPlaceActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Plan> plan = new ArrayList<>();
    private ArrayList<Place> placeList = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "");
        setLocale(language);
        setContentView(R.layout.activity_view_place);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        loadData(position);
        listView = (ListView)findViewById(R.id.listViewPlace);
        listView.setAdapter(new PlaceAdapter(this, R.layout.list_place_item, placeList));
        listView.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                        Intent intent2 = new Intent(view.getContext(), EditPlaceActivity.class);
                        intent2.putExtra("position", position);
                        intent2.putExtra("position_place", p);
                        startActivity(intent2);
                        finish();
                    }
                }
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(R.string.placeListTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(ViewPlaceActivity.this, EditPlanActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
                break;
            case R.id.action_delete:
                AlertDialog diaBox = AskOption();
                diaBox.show();
                break;
            case R.id.action_add:
                Intent intent2 = new Intent(this, AddPlaceActivity.class);
                intent2.putExtra("position", position);

                startActivity(intent2);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
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
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity.class);

        startActivity(intent2);
        finish();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle(R.string.deleteAlertDialog_delete)
                .setMessage(R.string.deleteAlertDialog)
                .setIcon(R.drawable.ic_d)
                .setPositiveButton(R.string.deleteAlertDialog_delete, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        plan.remove(position);
                        saveData();
                        Intent intent = new Intent(ViewPlaceActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        dialog.dismiss();

                    }

                })
                .setNegativeButton(R.string.deleteAlertDialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    private void saveData(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(plan);
        editor.putString("task list", json);
        editor.apply();
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
