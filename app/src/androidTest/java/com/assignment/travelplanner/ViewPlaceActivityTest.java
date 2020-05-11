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
public class ViewPlaceActivityTest {


        @Rule
        public ActivityTestRule<ViewPlaceActivity> ViewPlaceActivityTestRule = new ActivityTestRule<>(ViewPlaceActivity.class);

        private ViewPlaceActivity ViewPlaceActivity = null;



        @Before
        public void setUp() throws Exception {
            ViewPlaceActivity = ViewPlaceActivityTestRule.getActivity();

        }

        @Test
        public void testPlaceList(){
            View view = ViewPlaceActivity.findViewById(R.id.listViewPlace);
            assertNotNull(view);
        }

        @Test
        public void testAppbar(){
            View view = ViewPlaceActivity.findViewById(R.id.appbar);
            assertNotNull(view);
        }

        @Test
        public void testToolbar(){
            View view = ViewPlaceActivity.findViewById(R.id.toolbar);
            assertNotNull(view);
        }



        @After
        public void tearDown() throws Exception {
            ViewPlaceActivity = null;
        }
}