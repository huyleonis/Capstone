package com.example.hp.atsapplication;

import android.app.Application;
import android.widget.TextView;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.example.hp.atsapplication.estimote.Beacon;
import com.example.hp.atsapplication.estimote.BeaconInfoManager;

import java.util.ArrayList;
import java.util.List;

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
        beaconList.add(new Beacon(DEFAULT_UUID, 24100, 4804));
        EstimoteSDK.initialize(getApplicationContext(), "demo-7at", "2236dfcc5c0f0d3e6dcf3c29ab4eb63c");
    }

    public void enableBeaconNotifications(final TextView UUID, final TextView major,
                                           final TextView minor, final TextView message){
        if (beaconNotificationEnabled) {
            return;
        }

        BeaconInfoManager bnManager = new BeaconInfoManager(this, UUID, major, minor, message);
        for (Beacon beacon: beaconList) {
            bnManager.addNotification(beacon, "Đi vào", "Ra khỏi");
        }
        bnManager.startMonitoring();
        beaconNotificationEnabled = true;
    }

    public boolean isBeaconNotificationEnabled() {
        return beaconNotificationEnabled;
    }
}
