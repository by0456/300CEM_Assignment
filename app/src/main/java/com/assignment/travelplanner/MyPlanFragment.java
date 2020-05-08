package com.assignment.travelplanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyPlanFragment extends Fragment {
    private static final String TAG_L = "Testing log ";
    private TextView tw;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plan, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tw = view.findViewById(R.id.textview_myplan);


        view.findViewById(R.id.button_myplan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tw.setText("ABC");
                NavHostFragment.findNavController(MyPlanFragment.this).navigate(R.id.action_MyPlanFragment_to_SecondFragment);
                Log.d(TAG_L, "onClick is work");
            }
        });
    }
}
