package com.example.hp.atsapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.example.hp.atsapplication.utils.ConstantValues;
import com.example.hp.atsapplication.utils.RequestServer;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dovie on 10/11/2017.
 */

public class DetailTransactionActivity extends AppCompatActivity implements RequestServer.RequestResult {

    private static final String TAG = DetailTransactionActivity.class.getSimpleName();
    private AtsApplication app;
    private TextView textCity;
    private TextView textIdStation;
    private TextView textZone;
    private TextView textDateTime;
    private TextView textPrice;
    private TextView textStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        //textMessage = (TextView) findViewById(R.id.textMessage);
        textCity = (TextView) findViewById(R.id.textCity);
        textIdStation = (TextView) findViewById(R.id.textIdStation);
        textZone = (TextView) findViewById(R.id.textZone);
        textPrice = (TextView) findViewById(R.id.textPrice);
        //textPaymentResult = (TextView) findViewById(R.id.textPaymentResult);

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

    @Override
    public void processFinish(String result) {
        //Process the string returned from server
        try {
            JSONObject infos = new JSONObject(result);

            String city = infos.getString("stationName");
            String idStation = infos.getString("stationId");
            String zone = infos.getString("zone");
            double price = Double.parseDouble(infos.getString("price"));
            String date = infos.getString("dateTime");
            String status = infos.getString("status");

            DecimalFormat formatter = new DecimalFormat("###,###,###.##");

            textCity.setText(city);
            textIdStation.setText(idStation);
            textZone.setText(zone);
            textDateTime.setText(date);
            textPrice.setText(formatter.format(price) + "đồng");
            textStatus.setText(status);

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

//    public void setUpDefaultInfo(View view) {
//        textCity.setText("-");
//        textIdStation.setText("-");
//        textZone.setText("-");
//        textPrice.setText("0  đồng");
//    }

    public void clickTogetDetail(View view) {
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
//                    isDisplayedResultFragment = true;
//                    if (status.equals("Thành công")) {
////                        showResultFragment("Thanh toán thành công");
//                    } else {
//                        String reason = infos.getString("reason");
////                        showResultFragment("Thanh toán thất bại. \nLý do: " + reason);
//                    }
//
////                    textMessage.setText("Trạng thái: đã xử lý thanh toán.");
//
//                    String idTrans = infos.getString("id");
//                    setting.edit().putString("IdTransaction", idTrans).commit();
                } catch (Exception e) {
                    Log.e("Make Payment", e.getMessage());
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

//    public void clickToCancelPayment(View view) {
//        isDisplayedConfirmFragment = false;
//        hideConfirmFragment();
//    }
}
