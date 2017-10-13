package com.example.hp.atsapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.example.hp.atsapplication.utils.ConstantValues;
import com.example.hp.atsapplication.utils.RequestServer;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dovie on 10/11/2017.
 */

public class DetailTransactionActivity extends AppCompatActivity implements RequestServer.RequestResult {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private AtsApplication app;
    private TextView textStationName;
    private TextView textStationId;
    private TextView textZone;
    private TextView textDateTime;
    private TextView textPrice;
    private TextView textStatus;
    private Button btnUpdateTransStatus;

    private RequestServer rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        textStationName = (TextView) findViewById(R.id.textStationName);
        textStationId = (TextView) findViewById(R.id.textStationId);
        textZone = (TextView) findViewById(R.id.textZone);
        textDateTime = (TextView) findViewById(R.id.textDateTime);
        textPrice = (TextView) findViewById(R.id.textPrice);
        textStatus = (TextView) findViewById(R.id.textStatus);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        fragmentConfirm = fm.findFragmentById(R.id.fragmentConfirm);
//        fragmentResult = fm.findFragmentById(R.id.fragmentResult);
//
//        if (!isDisplayedConfirmFragment) {
//            hideConfirmFragment();
//        }
//        if (!isDisplayedResultFragment) {
//            hideResultFragment();
//        }
//
//        app = (AtsApplication) getApplication();
//
//        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
//            Log.e(TAG, "Bluetooth is turned off");
//        } else if (!app.isBeaconNotificationEnabled()) {
//            app.enableBeaconNotifications(textMessage, this);
//        }
//
//    }

//    public void clickToGetStationInformation(View view) {
//        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
//            Log.e(TAG, "Bluetooth is turned off");
//        } else if (!app.isBeaconNotificationEnabled()) {
//            Toast.makeText(this, "Begin enable beacon", Toast.LENGTH_SHORT).show();
//            app.enableBeaconNotifications(textMessage, this);
//        }
//
//    }

    @Override
    public void processFinish(String result) {
        //Process the string returned from server
        try {
            JSONObject infos = new JSONObject(result);

            String stationName = infos.getString("stationName");
            int stationId = infos.getInt("stationId");
            String zone = infos.getString("zone");
            double price = infos.getDouble("price");
            String status = infos.getString("status");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String dateTime = dateFormat.format(infos.getString("dateTime"));

            String dateTime = infos.getString("dateTime");

            DecimalFormat formatter = new DecimalFormat("###,###,###.##");

            textStationName.setText(stationName);
            textStationId.setText(stationId);
            textZone.setText(zone);
            textPrice.setText(formatter.format(price) + "đồng");
            textStatus.setText(status);
            textDateTime.setText(dateTime);

//            SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
//            showConfirmFragment();
//            isDisplayedConfirmFragment = true;
//            setting.edit().putString("IdStation", idStation).commit();

        } catch (Exception e) {
            Log.e("TransDetail Activity", e.getMessage());
            new AlertDialog.Builder(DetailTransactionActivity.this)
                    .setTitle("Exception")
                    .setMessage("Cannot parse json with result: " + result)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
            //Toast.makeText(this, "Cannot parse json with result: " + result, Toast.LENGTH_LONG).show();
        }

    }

    public void getTransactionDetail() {
//        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        Log.d("Request TransDetail", "Send Request TransDetail");
        rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.d("Receive TransDetail", "Transaction Detail Json: " + result);
                    JSONObject infos = new JSONObject(result);

                    String stationName = infos.getString("stationName");
                    String stationId = infos.getString("stationId");
                    String zone = infos.getString("zone");
                    double price = infos.getDouble("price");
                    String status = infos.getString("status");

//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    String dateTime = dateFormat.format(infos.getString("dateTime"));
                    String datetime = infos.getString("dateTime");

                    DecimalFormat formatter = new DecimalFormat("###,###,###.##");

                    textStationName.setText(stationName);
                    textStationId.setText(stationId);
                    textZone.setText(zone);
                    textPrice.setText(formatter.format(price) + " đồng");
                    textStatus.setText(status);
                    textDateTime.setText(datetime);
                } catch (Exception e) {
                    Log.e("Transaction Detail", e.getMessage());
                    new AlertDialog.Builder(DetailTransactionActivity.this)
                            .setTitle("Exception")
                            .setMessage("Cannot parse json with result: " + result)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }
            }
        };

        List<String> params = new ArrayList<>();
        params.add("1507602350726");
//        params.add(setting.getString("IdStation", ""));

//        Map<String, String> params = new HashMap<>();
//        params.put("Username", setting.getString("Username", ""));
//        params.put("IdStation", setting.getString("IdStation", ""));

        rs.execute(params, "transactiondetail", "getTransactionDetail", "GET");
//        isDisplayedConfirmFragment = false;
//        hideConfirmFragment();
//        textMessage.setText("Trạng thái: đang thanh toán phí...");
    }

//    public String getIdTransaction() {
//        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
//        return setting.getString("IdTransaction", "");
//    }

    public void updateTransactionStatus(){
        Log.d("Request updTransStatus", "Send Request updateTransStatus");
        rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.d("Receive TransStatus", "Transaction Status Json: " + result);
                    JSONObject infos = new JSONObject(result);

                    String status = infos.getString("status");

                    Log.d(TAG, "processFinish() returned: " + status);
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    String dateTime = dateFormat.format(infos.getString("dateTime"));

                    
                } catch (Exception e) {
                    Log.e("Transaction Detail", e.getMessage());
                    new AlertDialog.Builder(DetailTransactionActivity.this)
                            .setTitle("Exception")
                            .setMessage("Cannot parse json with result: " + result)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }
            }
        };

        List<String> params = new ArrayList<>();
        params.add("1507602350726");
//        params.add(setting.getString("IdStation", ""));

//        Map<String, String> params = new HashMap<>();
//        params.put("Username", setting.getString("Username", ""));
//        params.put("IdStation", setting.getString("IdStation", ""));

        rs.execute(params, "transaction", "updateProcessingTransaction", "GET");
//        isDisplayedConfirmFragment = false;
//        hideConfirmFragment();
//        textMessage.setText("Trạng thái: đang thanh toán phí...");
    }

    public void clickToGetDetail(View view){

        getTransactionDetail();

    }

    public void clickToUpdateTransStatus(View view){
        
        updateTransactionStatus();
        getTransactionDetail();
    }
}
