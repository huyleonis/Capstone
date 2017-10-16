package fpt.capstone.ats.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class TransactionDetailActivity extends AppCompatActivity {

    private TextView textStationName;
    private TextView textStationId;
    private TextView textZone;
    private TextView textDateTime;
    private TextView textPrice;
    private TextView textStatus;
    private TextView textVehicleType;
    private TextView textType;
    String transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        textStationName = (TextView) findViewById(R.id.textStationName);
        textStationId = (TextView) findViewById(R.id.textStationId);
        textZone = (TextView) findViewById(R.id.textZone);
        textDateTime = (TextView) findViewById(R.id.textDateTime);
        textPrice = (TextView) findViewById(R.id.textPrice);
        textStatus = (TextView) findViewById(R.id.textStatus);
        textVehicleType = (TextView) findViewById(R.id.textVehicleType);
        textType = (TextView) findViewById(R.id.textType);

        Bundle bundle = getIntent().getExtras();
        transactionId = bundle.getString(ConstantValues.TRANSACTION_ID_PARAM);

        getTransactionDetail();
    }


    public void getTransactionDetail() {
        Log.d("Request TransDetail", "Send Request TransDetail");
        RequestServer rs = new RequestServer();
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
                    String type = infos.getString("type");
                    String vehicleType = infos.getString("vehicleType");
                    Date datetime = new Date(infos.getLong("dateTime"));

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    DecimalFormat formatter = new DecimalFormat("###,###,###.##");

                    textStationName.setText(stationName);
                    textStationId.setText(stationId);
                    textZone.setText(zone);
                    textPrice.setText(formatter.format(price) + " đồng");
                    textStatus.setText(status);
                    textDateTime.setText(sdf.format(datetime));
                    textType.setText("Thu phí " + type);
                    textVehicleType.setText(vehicleType);
                } catch (Exception e) {
                    Log.e("Transaction Detail", e.getMessage());
                    new AlertDialog.Builder(TransactionDetailActivity.this)
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
        params.add(transactionId);


        rs.execute(params, "transaction", "getDetail", "GET");

    }

}
