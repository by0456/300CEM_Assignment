package com.assignment.travelplanner;

import android.app.Activity;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;



    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

    }

    @Test
    public void testAppbar(){
        View view = mActivity.findViewById(R.id.appbar);
        assertNotNull(view);
    }


    @Test

    public void testToolbar(){
        View view = mActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }

    @Test

    public void testFloatingActionButton(){
        View view = mActivity.findViewById(R.id.fab);
        assertNotNull(view);
    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}