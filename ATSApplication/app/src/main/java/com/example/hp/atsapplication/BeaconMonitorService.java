package com.example.hp.atsapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.TextView;

import com.estimote.coresdk.common.requirements.DefaultRequirementsCheckerCallback;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

public class BeaconMonitorService extends Service {

    private TextView textMessage;
    private HomeActivity homeActivity;

    public BeaconMonitorService() {
    }

    public void setTextView(TextView textMessage) {
        this.textMessage = textMessage;
    }

    public void setHomeActivity(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AtsApplication ats = (AtsApplication) getApplication();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(homeActivity)) {
            Log.e("BM_Service", "Bluetooth is turned off");
        }
        if (!ats.isBeaconNotificationEnabled()) {
            ats.enableBeaconNotifications(textMessage, homeActivity);
        }

        return START_STICKY;
    }


}
