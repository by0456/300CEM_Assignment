package com.assignment.travelplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Locale;

public class PlanAdapter extends ArrayAdapter<Plan> {

    private int resource;
    private ArrayList<Plan> plan;
    private Context context;

    public PlanAdapter(Context context, int resource, ArrayList<Plan> plan) {
        super(context, resource, plan);
        this.resource = resource;
        this.plan = plan;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        try {
            if (v == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = layoutInflater.inflate(resource, parent, false);
            }


            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
            String language = sharedPreferences.getString("language", "");
            String to = "  to  ";
            if(language.equals("en")){
                to = "  to  ";
            }else if(language.equals("zh")||language.equals("zh-rHK")){
                to = "  至  ";
            }
            TextView tvPlanName = (TextView) v.findViewById(R.id.tvPlanName);
            TextView tvPlanDate = (TextView) v.findViewById(R.id.tvPlanDate);


                tvPlanName.setText(plan.get(position).getPlanName());
                tvPlanDate.setText(plan.get(position).getPlanBeginDate()[2]+" / "+plan.get(position).getPlanBeginDate()[1]+" / "+plan.get(position).getPlanBeginDate()[0]
                        +to+plan.get(position).getPlanEndDate()[2]+" / "+plan.get(position).getPlanEndDate()[1]+" / "+plan.get(position).getPlanEndDate()[0]);




        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }
}
