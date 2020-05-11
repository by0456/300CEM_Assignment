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
public class AddPlanActivityTest {


    @Rule
    public ActivityTestRule<AddPlanActivity> AddPlanActivityTest = new ActivityTestRule<>(AddPlanActivity.class);

    private AddPlanActivity AddPlanActivity = null;



    @Before
    public void setUp() throws Exception {
        AddPlanActivity = AddPlanActivityTest.getActivity();

    }
    @Test
    public void testEtPlanName(){
        View view = AddPlanActivity.findViewById(R.id.etPlanName);
        assertNotNull(view);
    }

    @Test
    public void testTvBeginDate(){
        View view = AddPlanActivity.findViewById(R.id.tvBeginDate);
        assertNotNull(view);
    }

    @Test
    public void testTvEndDate(){
        View view = AddPlanActivity.findViewById(R.id.tvEndDate);
        assertNotNull(view);
    }

    @Test
    public void testDpPlanStartDate(){
        View view = AddPlanActivity.findViewById(R.id.dpPlanStartDate);
        assertNotNull(view);
    }

    @Test
    public void testDpPlanEndDate(){
        View view = AddPlanActivity.findViewById(R.id.dpPlanEndDate);
        assertNotNull(view);
    }

    @Test
    public void testPlanVoice(){
        View view = AddPlanActivity.findViewById(R.id.ibPlanVoice);
        assertNotNull(view);
    }



    @Test
    public void testAppbar(){
        View view = AddPlanActivity.findViewById(R.id.appbar);
        assertNotNull(view);
    }

    @Test
    public void testToolbar(){
        View view = AddPlanActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }



    @After
    public void tearDown() throws Exception {
        AddPlanActivity = null;
    }
}