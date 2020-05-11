package com.assignment.travelplanner;

import android.content.Intent;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditPlanActivityTest {


    @Rule
    public ActivityTestRule<EditPlanActivity> EditPlanActivityTest = new ActivityTestRule<>(EditPlanActivity.class);

    private EditPlanActivity EditPlanActivity = null;
    private int position = 0;
    private ArrayList<Integer> viewId;



    @Before
    public void setUp() throws Exception {
        EditPlanActivity = EditPlanActivityTest.getActivity();

    }

    @Test
    public void testPosition(){
        Intent intent = EditPlanActivity.getIntent();
        assertNotNull(intent);
    }

    @Test
    public void testEtPlanName_edit(){
        View view = EditPlanActivity.findViewById(R.id.etPlanName_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvBeginDate_edit(){
        View view = EditPlanActivity.findViewById(R.id.tvBeginDate_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvEndDate_edit(){
        View view = EditPlanActivity.findViewById(R.id.tvEndDate_edit);
        assertNotNull(view);
    }

    @Test
    public void testDpPlanStartDate_edit(){
        View view = EditPlanActivity.findViewById(R.id.dpPlanStartDate_edit);
        assertNotNull(view);
    }

    @Test
    public void testDpPlanEndDate_edit(){
        View view = EditPlanActivity.findViewById(R.id.dpPlanEndDate_edit);
        assertNotNull(view);
    }

    @Test
    public void testPlanVoice_edit(){
        View view = EditPlanActivity.findViewById(R.id.ibPlanVoice_edit);
        assertNotNull(view);
    }



    @Test
    public void testAppbar(){
        View view = EditPlanActivity.findViewById(R.id.appbar);
        assertNotNull(view);
    }

    @Test
    public void testToolbar(){
        View view = EditPlanActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }



    @After
    public void tearDown() throws Exception {
        EditPlanActivity = null;
    }
}