package com.example.hp.atsapplication.estimote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.hp.atsapplication.HomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hp on 9/24/2017.
 */

public class BeaconInfoManager {

    private static final String TAG = "BeaconNotification";

    private BeaconManager beaconManager;

    private List<BeaconRegion> regionToMonitor = new ArrayList<>();
    private HashMap<String, String> enterMessage = new HashMap<>();
    private HashMap<String, String> exitMessage = new HashMap<>();

    private Context context;

    private int notificationId = 0;

    public BeaconInfoManager(Context context, final TextView UUID, final TextView major,
                             final TextView minor, final TextView message) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.d(TAG, "Enter Beacon Region: " + beaconRegion.getIdentifier());
                String mess = enterMessage.get(beaconRegion.getIdentifier());
                if (mess != null) {
                    UUID.setText("UUID: " + beaconRegion.getProximityUUID().toString());
                    major.setText("Major: " + beaconRegion.getMajor());
                    minor.setText("Minor: " + beaconRegion.getMinor());
                    message.setText("Message: " + mess);
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.d(TAG, "Exit Beacon Region: " + beaconRegion.getIdentifier());
                String mess = exitMessage.get(beaconRegion.getIdentifier());
                if (mess != null) {
                    UUID.setText("UUID: ");
                    major.setText("Major: ");
                    minor.setText("Minor: ");
                    message.setText("Message: " + mess);
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

    public void addNotification(com.example.hp.atsapplication.estimote.Beacon beacon, String enterMess,
                                String exitMess) {
        BeaconRegion region = beacon.toBeaconRegion();
        enterMessage.put(region.getIdentifier(), enterMess);
        exitMessage.put(region.getIdentifier(), exitMess);
        regionToMonitor.add(region);
    }

}
