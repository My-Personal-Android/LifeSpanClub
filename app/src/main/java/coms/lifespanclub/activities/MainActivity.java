package coms.lifespanclub.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import coms.lifespanclub.R;
import coms.lifespanclub.adapters.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> allFragmentsList = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        initFragmentName();

        toolbar = findViewById(R.id.toolbar);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setUserInputEnabled(false);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setUpTabLayoutTitle();

        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) ->
                        tab.setText(" " + allFragmentsList.get(position) + " ")
        );
        tabLayoutMediator.attach();


    }

    private void setUpTabLayoutTitle() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        toolbar.setTitle("LifeSpan Club");
                        break;
                    case 1:
                        toolbar.setTitle("Pair");
                        break;
                    case 2:
                        toolbar.setTitle("History");
                        break;
                    case 3:
                        toolbar.setTitle("Profile");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragmentName() {
        allFragmentsList.add("Home");
        allFragmentsList.add("Pair");
        allFragmentsList.add("History");
        allFragmentsList.add("Profile");
    }
}