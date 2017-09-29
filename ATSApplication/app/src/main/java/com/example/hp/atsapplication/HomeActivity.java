package com.example.hp.atsapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.example.hp.atsapplication.utils.RequestServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements RequestServer.RequestResult {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private AtsApplication app;
    private TextView textUUID;
    private TextView textMajor;
    private TextView textMinor;
    private TextView textMessage;

    private TextView textCity;
    private TextView textIdStation;
    private TextView textDistance;


    private RequestServer rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textUUID = (TextView) findViewById(R.id.textUUID);
        textMajor = (TextView) findViewById(R.id.textMajor);
        textMinor = (TextView) findViewById(R.id.textMinor);
        textMessage = (TextView) findViewById(R.id.textMessage);

        textCity = (TextView) findViewById(R.id.textCity);
        textIdStation = (TextView) findViewById(R.id.textIdStation);
        textDistance = (TextView) findViewById(R.id.textDistance);
    }

    @Override
    protected void onResume() {
        super.onResume();

        app = (AtsApplication) getApplication();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Bluetooth is turned off");
        } else if (!app.isBeaconNotificationEnabled()) {
            app.enableBeaconNotifications(textUUID, textMajor, textMinor, textMessage);
        }
    }

    public void clickToGetStationInformation(View view) {
        Map<String, String> params = new HashMap<>();
        params.put("UUID", "1234");
        rs = new RequestServer();
        rs.delegate = this;
        rs.execute(params, "Demo.php");

    }

    @Override
    public void processFinish(String result) {
        //Process the string returned from server
        try {
            JSONObject infos = new JSONObject(result);

            String city = infos.getString("City");
            String idStation = infos.getString("Id");
            String distance = infos.getString("Distance");

            textCity.setText(city);
            textIdStation.setText(idStation);
            textDistance.setText(distance);

        } catch (Exception e) {
            Log.e("Home Activity", e.getMessage());
            Toast.makeText(this, "Cannot parse json with result: " + result, Toast.LENGTH_LONG).show();
        }

    }
}
