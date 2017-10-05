package com.example.hp.atsapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.example.hp.atsapplication.utils.ConstantValues;
import com.example.hp.atsapplication.utils.RequestServer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements RequestServer.RequestResult {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private AtsApplication app;
    private TextView textMessage;
    private TextView textCity;
    private TextView textIdStation;
    private TextView textZone;
    private TextView textPrice;
    private TextView textPaymentResult;
    FragmentManager fm = getFragmentManager();
    Fragment fragmentConfirm;
    Fragment fragmentResult;

    private RequestServer rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textMessage = (TextView) findViewById(R.id.textMessage);
        textCity = (TextView) findViewById(R.id.textCity);
        textIdStation = (TextView) findViewById(R.id.textIdStation);
        textZone = (TextView) findViewById(R.id.textZone);
        textPrice = (TextView) findViewById(R.id.textPrice);
        textPaymentResult = (TextView) findViewById(R.id.textPaymentResult);

    }

    @Override
    protected void onResume() {
        super.onResume();

        fragmentConfirm = fm.findFragmentById(R.id.fragmentConfirm);
        fragmentResult = fm.findFragmentById(R.id.fragmentResult);

        hideConfirmFragment();
        hideResultFragment();

        app = (AtsApplication) getApplication();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Bluetooth is turned off");
        } else if (!app.isBeaconNotificationEnabled()) {
            app.enableBeaconNotifications(textMessage, this);
        }

//
//        Intent  service = new Intent(getBaseContext(), BeaconMonitorService.class);
//        startService(service);
    }

    public void clickToGetStationInformation(View view) {
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Bluetooth is turned off");
        } else if (!app.isBeaconNotificationEnabled()) {
            Toast.makeText(this, "Begin enable beacon", Toast.LENGTH_SHORT).show();
            app.enableBeaconNotifications(textMessage, this);
        }

    }

    @Override
    public void processFinish(String result) {
        //Process the string returned from server
        try {
            JSONObject infos = new JSONObject(result);

            String city = infos.getString("nameStation");
            String idStation = infos.getString("id");
            String zone = infos.getString("zoneStation");
            String price = infos.getString("price");

            textCity.setText(city);
            textIdStation.setText(idStation);
            textZone.setText(zone);
            textPrice.setText(price + "  đồng");

            SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
            showConfirmFragment();
            setting.edit().putString("IdStation", idStation).commit();

        } catch (Exception e) {
            Log.e("Home Activity", e.getMessage());
            Toast.makeText(this, "Cannot parse json with result: " + result, Toast.LENGTH_LONG).show();
        }

    }

    public void clickToLogOut(View view) {
        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        editor.putBoolean("hasLoggedIn", false);
        editor.commit();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        HomeActivity.this.finish();

    }

    public void setUpDefaultInfo(View view) {
        textCity.setText("-");
        textIdStation.setText("-");
        textZone.setText("-");
        textPrice.setText("0  đồng");

        hideConfirmFragment();
        hideResultFragment();
    }

    public void showConfirmFragment() {
        if (fragmentConfirm != null) {
            fm.beginTransaction().show(fragmentConfirm).commit();
        }
    }

    public void hideConfirmFragment() {
        if (fragmentConfirm != null) {
            fm.beginTransaction().hide(fragmentConfirm).commit();
        }
    }

    public void showResultFragment(String result) {
        TextView textResult = (TextView) findViewById(R.id.textPaymentResult);
        textResult.setText(result);
        if (fragmentResult != null) {
            fm.beginTransaction().show(fragmentResult).commit();
        }
    }

    public void hideResultFragment() {
        if (fragmentResult != null) {
            fm.beginTransaction().hide(fragmentResult).commit();
        }
    }

    public void clickToMakePayment(View view) {
        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        Log.d("Request Payment", "Send Request Payment");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.d("Receive Payment", "Payment Result Json: " + result);
                    JSONObject infos = new JSONObject(result);

                    String status = infos.getString("status");
                    if (status.equals("Thành công")) {
                        showResultFragment("Thanh toán thành công");
                    } else {
                        String reason =  infos.getString("reason");
                        showResultFragment("Thanh toán thất bại. \nLý do: " + reason);
                    }

                    textMessage.setText("Trạng thái: đã xử lý thanh toán.");

                    String idTrans = infos.getString("id");
                    setting.edit().putString("IdTransaction",idTrans).commit();
                } catch (Exception e) {
                    Log.e("Make Payment", e.getMessage());
                }
            }
        };

        List<String> params = new ArrayList<>();
        params.add(setting.getString("Username", ""));
        params.add(setting.getString("IdStation", ""));

//        Map<String, String> params = new HashMap<>();
//        params.put("Username", setting.getString("Username", ""));
//        params.put("IdStation", setting.getString("IdStation", ""));

        rs.execute(params, "transaction", "makePayment", "GET");
        hideConfirmFragment();
        textMessage.setText("Trạng thái: đang thanh toán phí...");
    }

    public void clickToCancelPayment(View view) {
        hideConfirmFragment();
}

    public void clickToCloseResult(View view) {
        hideResultFragment();
    }

    public String getIdTransaction() {
        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        return setting.getString("IdTransaction", "");
    }
}
