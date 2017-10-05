package com.example.hp.atsapplication.estimote;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.hp.atsapplication.HomeActivity;
import com.example.hp.atsapplication.utils.ConstantValues;
import com.example.hp.atsapplication.utils.RequestServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hp on 9/24/2017.
 */

public class BeaconInfoManager {

    private static final String TAG = "BeaconNotification";

    private BeaconManager beaconManager;

    private List<BeaconRegion> regionToMonitor = new ArrayList<>();
    private HashMap<String, Integer> beaconType = new HashMap<>();

    private Context context;

    private int notificationId = 0;

    public BeaconInfoManager(Context context, final TextView message, final HomeActivity activity, final String username) {
        this.context = context;
        beaconManager = new BeaconManager(context);

        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.d(TAG, "Enter Beacon Region: " + beaconRegion.getIdentifier());
                int type = beaconType.get(beaconRegion.getIdentifier());

                if (type == com.example.hp.atsapplication.estimote.Beacon.BEACON_PAYMENT) {
                    message.setText("Trạng thái: Đi vào khu vực thu phí");


                    List<String> params = new ArrayList<>();

                    params.add(beaconRegion.getProximityUUID() + ":" + beaconRegion.getMajor() +
                            ":" + beaconRegion.getMinor());
                    params.add(username);

                    RequestServer rs = new RequestServer();
                    rs.delegate = activity;
                    rs.execute(params, "price", "findPriceDriver", "GET");
                } else if (type == com.example.hp.atsapplication.estimote.Beacon.BEACON_CHECK_RESULT) {
                    RequestServer rs = new RequestServer();

                    List<String> params = new ArrayList<>();
                    params.add(beaconRegion.getProximityUUID() + ":" + beaconRegion.getMajor() +
                            ":" + beaconRegion.getMinor());
                    params.add(activity.getIdTransaction());
                    rs.delegate = new RequestServer.RequestResult() {
                        @Override
                        public void processFinish(String result) {
                            Log.d("Exit Beacon","Send Transaction success");
                        }
                    };
                    rs.execute(params, "transaction", "checkResult", "GET");
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.d(TAG, "Exit Beacon Region: " + beaconRegion.getIdentifier());
                int type = beaconType.get(beaconRegion.getIdentifier());

                if (type == com.example.hp.atsapplication.estimote.Beacon.BEACON_PAYMENT) {
                    message.setText("Trạng thái: Chuẩn bị ra khỏi khu vực thu phí");

                    activity.setUpDefaultInfo(null);
                }
            }
        });
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                for (BeaconRegion region: regionToMonitor) {
                    beaconManager.startMonitoring(region);
                }
            }
        });
    }

    public void addNotification(com.example.hp.atsapplication.estimote.Beacon beacon) {
        BeaconRegion region = beacon.toBeaconRegion();
        beaconType.put(region.getIdentifier(), beacon.getType());
        regionToMonitor.add(region);
    }

}
