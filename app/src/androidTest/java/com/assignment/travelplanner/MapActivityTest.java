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
public class MapActivityTest {


    @Rule
    public ActivityTestRule<MapActivity> MapActivityTest = new ActivityTestRule<>(MapActivity.class);

    private MapActivity MapActivity = null;



    @Before
    public void setUp() throws Exception {
        MapActivity = MapActivityTest.getActivity();

    }

    @Test
    public void testAutoCompleteTest(){
        View view = MapActivity.findViewById(R.id.autoComplete);
        assertNotNull(view);
    }



    @Test
    public void testMEnter(){
        View view = MapActivity.findViewById(R.id.ic_enter);
        assertNotNull(view);
    }

    @Test
    public void testMSave(){
        View view = MapActivity.findViewById(R.id.ic_mapSave);
        assertNotNull(view);
    }

    @Test
    public void testMapVoice(){
        View view = MapActivity.findViewById(R.id.ibMapVoice);
        assertNotNull(view);
    }



    @After
    public void tearDown() throws Exception {
        MapActivity = null;
    }
}