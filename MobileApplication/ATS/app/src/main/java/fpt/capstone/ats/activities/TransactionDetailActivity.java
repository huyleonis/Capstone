package fpt.capstone.ats.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class TransactionDetailActivity extends AppCompatActivity {

    private static final String TAG = "TRANS_DETAIL_ACTIVITY";

    private TextView textStationName;
    private TextView textStationId;
    private TextView textZone;
    private TextView textDateTime;
    private TextView textPrice;
    private TextView textStatus;
    private TextView textVehicleType;
    private TextView textType;
    private Button btnPayment;

    private ProgressDialog pdial;
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
        btnPayment = (Button) findViewById(R.id.btnUpdateTransStatus);

        Bundle bundle = getIntent().getExtras();
        transactionId = bundle.getString(ConstantValues.TRANSACTION_ID_PARAM);

        getTransactionDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtsApplication.onResumeApp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AtsApplication.onPausedApp();
    }

    public void getTransactionDetail() {
        Log.w("Request TransDetail", "Send Request TransDetail");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.w("Receive TransDetail", "Transaction Detail Json: " + result);

                    if (pdial != null) {
                        pdial.dismiss();
                    }
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

                    Log.w("status: " , status);
                    if(status.equals("Thành công")){
                        textStatus.setTextColor(Color.parseColor("#7bc043"));
                    } else if (status.equals("Kết thúc")) {
                        textStatus.setTextColor(Color.parseColor("#0392cf"));
                    } else {
                        textStatus.setTextColor(Color.parseColor("#ee4035"));
                    }

                    if (!(status.contains("Thành công") || status.equals("Kết thúc"))) {
                        btnPayment.setVisibility(View.VISIBLE);
                        btnPayment.setEnabled(true);
                    }
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

    public void updateTransactionStatus(){
        pdial = new ProgressDialog(this);
        pdial.setMessage("Đang xử lý thanh toán phí...");
        pdial.setTitle("Thanh toán");
        pdial.show();

        Log.w("Request updTransStatus", "Send Request updateTransStatus");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.w("Receive TransStatus", "Transaction Status Json: " + result);
                    JSONObject infos = new JSONObject(result);

                    String status = infos.getString("status");

                    Log.w(TAG, "processFinish() returned: " + status);

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


        rs.execute(params, "transaction", "updateProcessingTransaction", "GET");
    }

    public void clickToUpdateTransStatus(View view){
        updateTransactionStatus();
        getTransactionDetail();
    }

}
