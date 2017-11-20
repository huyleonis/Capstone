package fpt.capstone.ats.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.dto.Report;
import fpt.capstone.ats.sqlite.DBAdapter;
import fpt.capstone.ats.utils.Commons;
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
    private String transactionId;

    private DBAdapter database;

    private DatabaseReference mDatabase;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        database = new DBAdapter(TransactionDetailActivity.this);
        database.open();
        Cursor resultSet = database.getInfo(transactionId);
        if (resultSet.moveToFirst()) {
            Log.d("GET DETAIL TRANSACTION", "VIEW FROM LOCAL");
            viewFromLocal(resultSet);
        } else {
            Log.d("GET DETAIL TRANSACTION", "VIEW FROM SERVER");
            getTransactionDetail();
        }
        database.close();

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
                    int stationId = infos.getInt("stationId");
                    String zone = infos.getString("zone");
                    double price = infos.getDouble("price");
                    String status = infos.getString("status");
                    String type = infos.getString("type");
                    String vehicleType = infos.getString("vehicleType");
                    Date datetime = new Date(infos.getLong("dateTime"));

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    DecimalFormat formatter = new DecimalFormat("###,###,###.##");

                    textStationName.setText(stationName);
                    textStationId.setText(String.valueOf(stationId));
                    textZone.setText(zone);
                    textPrice.setText(formatter.format(price) + " đồng");
                    textDateTime.setText(sdf.format(datetime));
                    textType.setText("Thu phí " + type);
                    textVehicleType.setText(vehicleType);

                    database = new DBAdapter(TransactionDetailActivity.this);
                    database.open();

                    Date lastModifiedDate = new Date();
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String lastModified = sdf.format(lastModifiedDate);

                    long re = database.insertInfo(transactionId, stationName, stationId, zone,
                            sdf.format(datetime), price, status, vehicleType, type, lastModified);
                    Log.d("DATABASE INSERT", String.valueOf(re));

                    database.close();

                    String statusText = "-";
                    Log.w("status: ", status);
                    if (status.equals("Success")) {
                        textStatus.setTextColor(Color.parseColor("#7bc043"));
                        statusText = "Thanh toán Thành công";
                    } else if (status.equals("Finish")) {
                        textStatus.setTextColor(Color.parseColor("#0392cf"));
                        statusText = "Hoàn thành";
                    } else if (status.equals("Failed")) {
                        textStatus.setTextColor(Color.parseColor("#ee4035"));
                        statusText = "Thanh toán Thất bại";
                    } else if (status.equals("Initial") || status.equals("Not pay")) {
                        textStatus.setTextColor(Color.parseColor("#ee4035"));
                        statusText = "Chưa thanh toán";
                    }

                    textStatus.setText(statusText);

                    if (status.equals("Failed") || status.equals("Initial") || status.equals("Not pay")) {
                        btnPayment.setVisibility(View.VISIBLE);
                        btnPayment.setEnabled(true);
                    } else {
                        btnPayment.setEnabled(false);
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

    public void updateTransactionStatus() {
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
                    pdial.dismiss();
                    Log.w("Receive TransStatus", "Transaction Status Json: " + result);
                    JSONObject infos = new JSONObject(result);
                    String newStatus = infos.getString("status");

                    database = new DBAdapter(TransactionDetailActivity.this);
                    database.open();

                    Log.w(TAG, "processFinish() returned: " + newStatus);
                    Cursor resultSet = database.getInfo(transactionId);
                    if (resultSet.moveToFirst()) {
                        String stationName = resultSet.getString(1);
                        int stationId = resultSet.getInt(2);
                        String zone = resultSet.getString(3);
                        String dateTime = resultSet.getString(4);
                        double price = resultSet.getDouble(5);
//                        String status = resultSet.getString(6);
                        String vehicleType = resultSet.getString(7);
                        String type = resultSet.getString(8);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date lastModifiedDate = new Date();
                        String lastModified = sdf.format(lastModifiedDate);

                        boolean isSuccessful = database.updateInfo(transactionId, stationName, stationId,
                                zone, dateTime, price, newStatus, vehicleType, type, lastModified);

                        Log.d("DATABASE UPDATE", String.valueOf(isSuccessful));

                        resultSet = database.getInfo(transactionId);
                        if (resultSet.moveToFirst()) {
                            Log.d("GET DETAIL TRANSACTION", "VIEW FROM LOCAL");
                            viewFromLocal(resultSet);
                        } else {
                            Log.d("GET DETAIL TRANSACTION", "VIEW FROM SERVER");
                            getTransactionDetail();
                        }
                    }
                    database.close();
                    Log.d(TAG, "processFinish() returned: " + newStatus);

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

    public void viewFromLocal(Cursor resultSet) {
        try {
//            Log.d("Receive TransDetail", "Transaction Detail Json: " + resultSet);

            if (pdial != null) {
                pdial.dismiss();
            }

            String stationName = resultSet.getString(1);
            int stationId = resultSet.getInt(2);
            String zone = resultSet.getString(3);
            String dateTime = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            String status = resultSet.getString(6);
            String vehicleType = resultSet.getString(7);
            String type = resultSet.getString(8);

            DecimalFormat formatter = new DecimalFormat("###,###,###.##");

            textStationName.setText(stationName);
            textStationId.setText(String.valueOf(stationId));
            textZone.setText(zone);
            textPrice.setText(formatter.format(price) + " đồng");
            textStatus.setText(status);
            textDateTime.setText(dateTime);
            textType.setText("Thu phí " + type);
            textVehicleType.setText(vehicleType);

            String statusText = "-";
            Log.w("status: ", status);
            if (status.equals("Success")) {
                textStatus.setTextColor(Color.parseColor("#7bc043"));
                statusText = "Thanh toán Thành công";
            } else if (status.equals("Finish")) {
                textStatus.setTextColor(Color.parseColor("#0392cf"));
                statusText = "Hoàn thành";
            } else if (status.equals("Failed")) {
                textStatus.setTextColor(Color.parseColor("#ee4035"));
                statusText = "Thanh toán Thất bại";
            } else if (status.equals("Initial") || status.equals("Not pay")) {
                textStatus.setTextColor(Color.parseColor("#ee4035"));
                statusText = "Chưa thanh toán";
            }

            textStatus.setText(statusText);

            if (status.equals("Failed") || status.equals("Initial") || status.equals("Not pay")) {
                btnPayment.setVisibility(View.VISIBLE);
                btnPayment.setEnabled(true);
            } else {
                btnPayment.setEnabled(false);
            }
        } catch (Exception e) {
            Log.e("Transaction Detail", e.getMessage());
            new AlertDialog.Builder(TransactionDetailActivity.this)
                    .setTitle("Exception")
                    .setMessage("Cannot parse json with result: " + resultSet)
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

    public void clickToUpdateTransStatus(View view) {
        updateTransactionStatus();
    }

    public void writeNewReport() {
        Report report = new Report(transactionId, Commons.getUsername(this), 0, 1);

        Map<String, Object> reportValues = report.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        String key = mDatabase.child("Reports").push().getKey();

        childUpdates.put("/Reports/" + key, reportValues);
        mDatabase.updateChildren(childUpdates);
    }

    public void reportTransaction() {
        pdial = new ProgressDialog(this);
        pdial.setMessage("Đang thông báo lỗi...");
        pdial.setTitle("Báo Lỗi");
        pdial.show();

        Log.w("Request Report Trans", "Send Request Report Transaction");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    pdial.dismiss();
                    Log.w("Receive TransStatus", "Transaction Status Json: " + result);
                    JSONObject infos = new JSONObject(result);
                    String newStatus = infos.getString("status");
                    String updateResult = infos.getString("result");

                    if (updateResult.equalsIgnoreCase("success")) {
                        Log.d("Report Transaction", "Report Transaction Success");

                        database = new DBAdapter(TransactionDetailActivity.this);
                        database.open();

                        Log.w(TAG, "processFinish() returned: " + newStatus);
                        Cursor resultSet = database.getInfo(transactionId);
                        if (resultSet.moveToFirst()) {
                            String stationName = resultSet.getString(1);
                            int stationId = resultSet.getInt(2);
                            String zone = resultSet.getString(3);
                            String dateTime = resultSet.getString(4);
                            double price = resultSet.getDouble(5);
//                        String status = resultSet.getString(6);
                            String vehicleType = resultSet.getString(7);
                            String type = resultSet.getString(8);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String lastModified = sdf.format(new Date());

                            boolean isSuccessful = database.updateInfo(transactionId, stationName, stationId,
                                    zone, dateTime, price, newStatus, vehicleType, type, lastModified);

                            Log.d("DATABASE UPDATE", String.valueOf(isSuccessful));

                            resultSet = database.getInfo(transactionId);
                            if (resultSet.moveToFirst()) {
                                Log.d("GET DETAIL TRANSACTION", "VIEW FROM LOCAL");
                                viewFromLocal(resultSet);
                            } else {
                                Log.d("GET DETAIL TRANSACTION", "VIEW FROM SERVER");
                                getTransactionDetail();
                            }
                        }
                        database.close();
                        Log.d(TAG, "processFinish() returned: " + newStatus);
                    } else if (updateResult.equalsIgnoreCase("fail")) {
                        Log.e("Report Transaction", "Report Transaction Fail");
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
        rs.execute(params, "transaction", "reportTransaction", "GET");
    }

    public void clickToReportTransaction(View view) {
        reportTransaction();
        writeNewReport();
        finish();
    }
}