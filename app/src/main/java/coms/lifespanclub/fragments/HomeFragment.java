package coms.lifespanclub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import coms.lifespanclub.R;
import coms.lifespanclub.activities.MyViewModel;

public class HomeFragment extends Fragment {

    private View view;
    private TextView rpmTxtView;
    private TextView speedTxtView;
    private TextView distanceTxtView;
    private TextView calorieTxtView;

    private MyViewModel mViewModel;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        new ViewModelProvider(getActivity()).get(MyViewModel.class).getRpm().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                rpmTxtView.setText(message);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public String getRpm(Double readValue) { // rpm / min
        Double calculate = readValue;
        String strDouble = String.format("%. 2f", calculate);
        return strDouble;
    }

    public String getSpeedFromRpm(Double rpm) { // km / h
        Double calculate = 60 * rpm * (590 * 3 * 3.14159) / 1000000;
        String strDouble = String.format("%. 2f", calculate);
        return strDouble;
    }

    public String getDistanceFromSpeed(Double speed) { // km
        Double calculate = speed / 60;
        String strDouble = String.format("%. 2f", calculate);
        return strDouble;
    }

    public String getCalorieFromDistance(Double distance) { // calorie
        Double calculate = 4.35 / 60;
        String strDouble = String.format("%. 2f", calculate);
        return strDouble;
    }
}