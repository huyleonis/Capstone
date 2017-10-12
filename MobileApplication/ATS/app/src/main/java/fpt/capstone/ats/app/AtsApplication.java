package fpt.capstone.ats.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.estimote.coresdk.common.config.EstimoteSDK;

import java.util.ArrayList;
import java.util.List;

import fpt.capstone.ats.activities.MainActivity;
import fpt.capstone.ats.estimote.Beacon;
import fpt.capstone.ats.estimote.BeaconInfoManager;
import fpt.capstone.ats.fragments.HomeFragment;
import fpt.capstone.ats.utils.ConstantValues;

/**
 * Created by hp on 9/25/2017.
 */

public class AtsApplication extends Application {
    private static final String DEFAULT_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

    private boolean beaconNotificationEnabled = false;
    private List<Beacon> beaconList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
//        beaconList.add(new Beacon(DEFAULT_UUID, 36857, 31381, Beacon.BEACON_PAYMENT));
//        beaconList.add(new Beacon(DEFAULT_UUID, 39748, 38452, Beacon.BEACON_CHECK_RESULT));
//        beaconList.add(new Beacon(DEFAULT_UUID, 24100, 4804, Beacon.BEACON_CHECK_RESULT));
        EstimoteSDK.initialize(getApplicationContext(), "demo-7at", "2236dfcc5c0f0d3e6dcf3c29ab4eb63c");
    }

    public void enableBeaconNotifications(final TextView message, final HomeFragment homeFragment){
        if (beaconNotificationEnabled) {
            return;
        }

        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        String username = setting.getString("Username", "");

        BeaconInfoManager bnManager = new BeaconInfoManager(this, message, homeFragment, username);
        for (Beacon beacon: beaconList) {
            bnManager.addNotification(beacon);
        }
        bnManager.startMonitoring();
        beaconNotificationEnabled = true;
    }

    public boolean isBeaconNotificationEnabled() {
        return beaconNotificationEnabled;
    }
}
