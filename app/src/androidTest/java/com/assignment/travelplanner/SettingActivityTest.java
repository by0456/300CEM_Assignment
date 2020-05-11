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
public class SettingActivityTest {


    @Rule
    public ActivityTestRule<SettingActivity> SettingActivityTest = new ActivityTestRule<>(SettingActivity.class);

    private SettingActivity SettingActivity = null;



    @Before
    public void setUp() throws Exception {
        SettingActivity = SettingActivityTest.getActivity();

    }

    @Test
    public void testTvSetting(){
        View view = SettingActivity.findViewById(R.id.tvSetting);
        assertNotNull(view);
    }

    @Test
    public void testTvEnglish(){
        View view = SettingActivity.findViewById(R.id.tvEnglish);
        assertNotNull(view);
    }

    @Test
    public void testTcChinese(){
        View view = SettingActivity.findViewById(R.id.tvChinese);
        assertNotNull(view);
    }

    @Test
    public void testAppbar(){
        View view = SettingActivity.findViewById(R.id.appbar);
        assertNotNull(view);
    }

    @Test
    public void testToolbar(){
        View view = SettingActivity.findViewById(R.id.toolbar);
        assertNotNull(view);
    }



    @After
    public void tearDown() throws Exception {
        SettingActivity = null;
    }
}