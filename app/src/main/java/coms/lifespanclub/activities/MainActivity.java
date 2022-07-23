package coms.lifespanclub.activities;

import static coms.lifespanclub.activities.DeviceControlActivity.makeGattUpdateIntentFilter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import coms.lifespanclub.R;
import coms.lifespanclub.adapters.ViewPagerAdapter;
import coms.lifespanclub.serivce.BluetoothLeService;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();


    private ArrayList<String> allFragmentsList = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;

    public static String DeviceName = "";
    public static String DeviceAddress = "";

    public static boolean shouldConnect = false;

    MyViewModel myViewModel;

    private boolean mConnected = false;
    private BluetoothLeService mBluetoothLeService;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(DeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
            }
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                System.out.println(BluetoothLeService.EXTRA_DATA.getClass().getName() + "");
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG,"OnStart");
        if(shouldConnect) {
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initFragmentName();

        //Make View Holder Object
        myViewModel= new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.init();
        myViewModel.sendData("Hello test");

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

//        if(shouldConnect) {
//            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
//            bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldConnect) {
            registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            if (mBluetoothLeService != null && DeviceAddress != "" && DeviceAddress != null) {
                final boolean result = mBluetoothLeService.connect(DeviceAddress);
                Log.d(TAG, "Connect request result=" + result);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(shouldConnect) {
            unregisterReceiver(mGattUpdateReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(shouldConnect) {
            unbindService(mServiceConnection);
            mBluetoothLeService = null;
        }
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

    private void displayData(String data) {
        if (data != null) {
            String[] parts = data.split("\n");
            System.out.println(parts[0]);
            myViewModel.sendData(parts[0]);
        }
    }
}