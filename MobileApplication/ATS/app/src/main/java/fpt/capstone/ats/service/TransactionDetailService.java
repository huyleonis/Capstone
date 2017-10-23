package fpt.capstone.ats.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import fpt.capstone.ats.sqlite.DBAdapter;
import fpt.capstone.ats.utils.RequestServer;

/**
 * Created by dovie on 10/20/2017.
 */

public class TransactionDetailService extends Service {

    private DBAdapter database;
    private RequestServer rs;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "SERVICE STARTED", Toast.LENGTH_LONG).show();
        getAllDetail();
        deleteRecord30DaysOld();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "SERVICE DESTROYED", Toast.LENGTH_LONG).show();
    }

    public void getAllDetail() {
        Log.d("Request TransDetail", "Send Request TransDetail");
        rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.d("Receive TransDetail", "Transaction Detail Json: " + result);

                    database = new DBAdapter(TransactionDetailService.this);
                    database.open();
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String transactionId = jsonObject.getString("transactionId");
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
                    database.close();
                } catch (Exception e) {
                    Log.e("Transaction Detail", e.getMessage());
                }
            }
        };
        rs.execute(null, "transaction", "getDetail", "GET");
    }

    public boolean deleteRecord30DaysOld() {

        database = new DBAdapter(TransactionDetailService.this);
        database.open();

        boolean isSuccessful = database.deleteInfoAfter30Days();

        database.close();

        return isSuccessful;
    }
}
