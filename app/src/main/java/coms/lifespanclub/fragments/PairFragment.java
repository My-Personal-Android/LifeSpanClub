package coms.lifespanclub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import coms.lifespanclub.R;
import coms.lifespanclub.StartActivity;
import coms.lifespanclub.activities.DeviceScanActivity;
import coms.lifespanclub.activities.MainActivity;

public class PairFragment extends Fragment {


    private View view;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pair, container, false);

        button = view.findViewById(R.id.gotopair);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), DeviceScanActivity.class);
                getActivity().startActivity(myIntent);
            }
        });
        return view;
    }

}