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
public class EditPlaceActivityTest {


    @Rule
    public ActivityTestRule<EditPlaceActivity> EditPlaceActivityTest = new ActivityTestRule<>(EditPlaceActivity.class);

    private EditPlaceActivity EditPlaceActivity = null;



    @Before
    public void setUp() throws Exception {
        EditPlaceActivity = EditPlaceActivityTest.getActivity();

    }

    @Test
    public void testEtName_edit(){
        View view = EditPlaceActivity.findViewById(R.id.etName_edit);
        assertNotNull(view);
    }

    @Test
    public void testEtDescription_edit(){
        View view = EditPlaceActivity.findViewById(R.id.etDescription_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvLatitude_edit(){
        View view = EditPlaceActivity.findViewById(R.id.tvLatitude_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvLongitude_edit(){
        View view = EditPlaceActivity.findViewById(R.id.tvLongitude_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvAddress_edit(){
        View view = EditPlaceActivity.findViewById(R.id.tvAddress_edit);
        assertNotNull(view);
    }


    @Test
    public void testLabelAddress_edit(){
        View view = EditPlaceActivity.findViewById(R.id.labelAddress_edit);
        assertNotNull(view);
    }

    @Test
    public void testLabelLatitude_edit(){
        View view = EditPlaceActivity.findViewById(R.id.labelLatitude_edit);
        assertNotNull(view);
    }

    @Test
    public void testLabelLongitude_edit(){
        View view = EditPlaceActivity.findViewById(R.id.labelLongitude_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvPlaceDate_edit(){
        View view = EditPlaceActivity.findViewById(R.id.tvPlaceDate_edit);
        assertNotNull(view);
    }

    @Test
    public void testTvPlaceTime_edit(){
        View view = EditPlaceActivity.findViewById(R.id.tvPlaceTime_edit);
        assertNotNull(view);
    }

    @Test
    public void testDpPlaceDate_edit(){
        View view = EditPlaceActivity.findViewById(R.id.dpPlaceDate_edit);
        assertNotNull(view);
    }

    @Test
    public void testTpPlaceTime_edit(){
        View view = EditPlaceActivity.findViewById(R.id.tpPlaceTime_edit);
        assertNotNull(view);
    }

    @Test
    public void testIbPlaceVoice_edit(){
        View view = EditPlaceActivity.findViewById(R.id.ibPlaceVoice_edit);
        assertNotNull(view);
    }


    @Test
    public void testAppbar(){
        View view = EditPlaceActivity.findViewById(R.id.appbar);
        assertNotNull(view);
    }

    @Test
    public void testToolbar(){
        View view = EditPlaceActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }



    @After
    public void tearDown() throws Exception {
        EditPlaceActivity = null;
    }
}
