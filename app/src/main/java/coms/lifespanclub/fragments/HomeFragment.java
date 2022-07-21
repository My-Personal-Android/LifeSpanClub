package coms.lifespanclub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import coms.lifespanclub.R;

public class HomeFragment extends Fragment {

    private View view;
    private Button button;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        button = view.findViewById(R.id.entry);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Create new fragment and transaction
                Fragment newFragment = new HomeEntryFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.viewPager, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });
        return view;
    }
}