package com.assignment.travelplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    // This class is reference the tutorial - Android Google Maps Course
    // The link : https://www.youtube.com/watch?v=s_6xxTjoLGY&list=PLgCYzUzKIBE-vInwQhGSdnbyJ62nixHCt&index=7 and https://github.com/mitchtabian/Google-Maps-Google-Places/tree/739015abf10ff8fab1bf9e6c899e4013c6fa051a

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private ImageView mGps;
    private ImageView mEnter;
    private ImageView mSave;
    private AutoCompleteTextView autoCompleteTextView;
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private Marker marker;
    private Intent intent;
    private int action;
    private static final int REQUEST_CODE_SPEECH = 1004;
    private ImageButton ibMapVoice;
    private FusedLocationProviderClient mFusedLocationProviderClient;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();


        action = intent.getIntExtra("action", 1);

        autoCompleteTextView = findViewById(R.id.autoComplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(MapActivity.this, android.R.layout.simple_list_item_1));
        mGps = (ImageView)findViewById(R.id.ic_gps);
        mEnter = (ImageView)findViewById(R.id.ic_enter);
        mSave = (ImageView)findViewById(R.id.ic_mapSave);

        ibMapVoice = (ImageButton)findViewById(R.id.ibMapVoice);



        getLocationPermission();
        init();



    }

    private void init(){
        Log.d(TAG, "init: initializing");

        ibMapVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(marker==null){
                    if(action==(1)){
                        Intent intent2 = new Intent(v.getContext(), AddPlaceActivity.class);
                        intent2.putExtra("Name", "");
                        intent2.putExtra("Address", "");
                        intent2.putExtra("Latitude", "");
                        intent2.putExtra("Longitude", "");
                        setResult(2,intent2);
                        finish();
                    }else if(action==(2)){
                        Intent intent2 = new Intent(v.getContext(), EditPlaceActivity.class);
                        intent2.putExtra("Name", "");
                        intent2.putExtra("Address", "");
                        intent2.putExtra("Latitude", "");
                        intent2.putExtra("Longitude", "");
                        setResult(3,intent2);
                        finish();
                    }

                }else{
                    //Toast.makeText(v.getContext(), "Title = "+autoCompleteTextView.getText().toString()+", "+marker.getTitle()+" latitude = "+marker.getPosition().latitude+" longitude = "+marker.getPosition().longitude, Toast.LENGTH_SHORT).show();
                    if(action==(1)){
                        Intent intent2 = new Intent(v.getContext(), AddPlaceActivity.class);
                        intent2.putExtra("Name", autoCompleteTextView.getText().toString());
                        intent2.putExtra("Address", marker.getTitle());
                        intent2.putExtra("Latitude", String.valueOf(marker.getPosition().latitude));
                        intent2.putExtra("Longitude", String.valueOf(marker.getPosition().longitude));
                        setResult(2,intent2);
                        finish();
                    }else if(action==(2)){
                        Intent intent2 = new Intent(v.getContext(), EditPlaceActivity.class);
                        intent2.putExtra("Name", autoCompleteTextView.getText().toString());
                        intent2.putExtra("Address", marker.getTitle());
                        intent2.putExtra("Latitude", String.valueOf(marker.getPosition().latitude));
                        intent2.putExtra("Longitude", String.valueOf(marker.getPosition().longitude));
                        setResult(3,intent2);
                        finish();
                    }
                }

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                geoLocate();
            }
        });
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();

                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }


    private void geoLocate(){
        String searchString = autoCompleteTextView.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);

        }catch(IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());

        }
        if(list.size() > 0){
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
            hideKeyboard(MapActivity.this);
        }
    }


    private void getDeviceLocation(){

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted){

                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "My Location");

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());

        }
    }

    private void moveCamera(LatLng latlng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions().position(latlng).title(title);
            mMap.clear();
            marker = mMap.addMarker(options);

        }

        hideSoftKeyboard();
    }

    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();

            }else{
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }

        }else{
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 ){
                    for(int i = 0;i < grantResults.length;i++){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

                    autoCompleteTextView.setText(result.get(0));
                }
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        getLocationPermission();
        init();

    }

    @Override
    public void onBackPressed() {
        if(action==(1)){
            Intent intent2 = new Intent(this, ViewPlaceActivity.class);
            intent2.putExtra("Name", "");
            intent2.putExtra("Address", "");
            intent2.putExtra("Latitude", "");
            intent2.putExtra("Longitude", "");
            setResult(2,intent2);
            finish();
        }else if(action==(2)){
            Intent intent2 = new Intent(this, EditPlaceActivity.class);
            intent2.putExtra("Name", "");
            intent2.putExtra("Address", "");
            intent2.putExtra("Latitude", "");
            intent2.putExtra("Longitude", "");
            setResult(3,intent2);
            finish();
        }
    }


}
