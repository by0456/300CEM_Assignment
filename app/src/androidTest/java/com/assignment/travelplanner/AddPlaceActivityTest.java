package com.assignment.travelplanner;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddPlaceActivityTest {


    @Rule
    public ActivityTestRule<AddPlaceActivity> AddPlaceActivityTest = new ActivityTestRule<>(AddPlaceActivity.class);

    private AddPlaceActivity AddPlaceActivity = null;



    @Before
    public void setUp() throws Exception {
        AddPlaceActivity = AddPlaceActivityTest.getActivity();

    }

    @Test
    public void testEtName(){
        View view = AddPlaceActivity.findViewById(R.id.etName);
        assertNotNull(view);
    }

    @Test
    public void testEtDescription(){
        View view = AddPlaceActivity.findViewById(R.id.etDescription);
        assertNotNull(view);
    }

    @Test
    public void testTvLatitude(){
        View view = AddPlaceActivity.findViewById(R.id.tvLatitude);
        assertNotNull(view);
    }

    @Test
    public void testTvLongitude(){
        View view = AddPlaceActivity.findViewById(R.id.tvLongitude);
        assertNotNull(view);
    }

    @Test
    public void testTvAddress(){
        View view = AddPlaceActivity.findViewById(R.id.tvAddress);
        assertNotNull(view);
    }

    @Test
    public void testLabelAddress(){
        View view = AddPlaceActivity.findViewById(R.id.labelAddress);
        assertNotNull(view);
    }

    @Test
    public void testLabelLatitude(){
        View view = AddPlaceActivity.findViewById(R.id.labelLatitude);
        assertNotNull(view);
    }

    @Test
    public void testLabelLongiude(){
        View view = AddPlaceActivity.findViewById(R.id.labelLongitude);
        assertNotNull(view);
    }

    @Test
    public void testTvPlaceDate(){
        View view = AddPlaceActivity.findViewById(R.id.tvPlaceDate);
        assertNotNull(view);
    }

    @Test
    public void testTvPlaceTime(){
        View view = AddPlaceActivity.findViewById(R.id.tvPlaceTime);
        assertNotNull(view);
    }

    @Test
    public void testDpPlaceDate(){
        View view = AddPlaceActivity.findViewById(R.id.dpPlaceDate);
        assertNotNull(view);
    }

    @Test
    public void testTpPlaceTime(){
        View view = AddPlaceActivity.findViewById(R.id.tpPlaceTime);
        assertNotNull(view);
    }

    @Test
    public void testIbPlaceVoice(){
        View view = AddPlaceActivity.findViewById(R.id.ibPlaceVoice);
        assertNotNull(view);
    }


    @Test
    public void testAppbar(){
        View view = AddPlaceActivity.findViewById(R.id.appbar);
        assertNotNull(view);
    }

    @Test
    public void testToolbar(){
        View view = AddPlaceActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }



    @After
    public void tearDown() throws Exception {
        AddPlaceActivity = null;
    }
}