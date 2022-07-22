package coms.lifespanclub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import coms.lifespanclub.R;

public class HomeFragment extends Fragment {

    private View view;
    private TextView rpmTxtView;
    private TextView speedTxtView;
    private TextView distanceTxtView;
    private TextView calorieTxtView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rpmTxtView = view.findViewById(R.id.rpm);
        speedTxtView = view.findViewById(R.id.speed);
        distanceTxtView = view.findViewById(R.id.distance);
        calorieTxtView = view.findViewById(R.id.calorie);

        return view;
    }

    public Double getRpm(Double readValue) { // rpm / min
        return readValue / 60;
    }

    public Double getSpeedFromRpm(Double rpm) { // km / h
        return 60 * rpm * (590 * 3 * 3014159) / 1000000;
    }

    public Double getDistanceFromSpeed(Double speed) { // km
        return speed / 60;// (min)
    }

    public Double getCalorieFromDistance(Double distance) { // calorie
        return 4.35 / 60; // (min)
    }
}