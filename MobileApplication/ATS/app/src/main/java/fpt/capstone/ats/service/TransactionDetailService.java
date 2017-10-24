package fpt.capstone.ats.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpt.capstone.ats.sqlite.DBAdapter;
import fpt.capstone.ats.utils.RequestServer;

/**
 * Created by dovie on 10/20/2017.
 */

public class TransactionDetailService extends IntentService {

    private DBAdapter database;
    private RequestServer rs;

    public TransactionDetailService() {
        super("TransactionDetailService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("TRANSACTION DETAIL", "SYNCHRONIZING...");
        saveAllDetail();
        deleteRecord30DaysOld();

    }

    public void saveAllDetail() {
        Log.d("Request TransDetail", "Send Request TransDetail");
        rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.d("Receive TransDetail", "Transaction Detail Json: " + result);

                    database = new DBAdapter(TransactionDetailService.this);
                    database.open();
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray jsonArray = jsonResponse.getJSONArray("TransactionDetails");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String transactionId = jsonObject.getString("id");

                        Cursor resultSet = database.getInfo(transactionId);
                        if (resultSet.moveToFirst()) {
                            Log.d("DATABASE INSERT", "RECORD DUPLICATED");
                        } else {
                            String stationName = jsonObject.getString("stationName");
                            int stationId = jsonObject.getInt("stationId");
                            String zone = jsonObject.getString("zone");
                            double price = jsonObject.getDouble("price");
                            String status = jsonObject.getString("status");
                            String type = jsonObject.getString("type");
                            String vehicleType = jsonObject.getString("vehicleType");
                            Date datetime = new Date(jsonObject.getLong("dateTime"));

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date lastModifiedDate = new Date();
                            String lastModified = sdf.format(lastModifiedDate);

                            long re = database.insertInfo(transactionId, stationName, stationId, zone,
                                    sdf.format(datetime), price, status, vehicleType, type, lastModified);
                            Log.d("DATABASE INSERT", String.valueOf(re));
                        }
                    }
                    database.close();
                } catch (Exception e) {
                    Log.e("Transaction Detail", e.getMessage());
                }
            }
        };
        List<String> params = new ArrayList<>();
        rs.execute(params, "transaction", "getDetail", "GET");
    }

    public boolean deleteRecord30DaysOld() {
        boolean isSuccessful = false;
        try {
            database = new DBAdapter(TransactionDetailService.this);
            database.open();

            isSuccessful = database.deleteInfoAfter30Days();

            database.close();
        } catch (Exception e) {
            Log.e("TRANS DETAIL ERROR", "DELETE RECORDS 30 DAYS ERROR");
        }

        return isSuccessful;
    }
}
