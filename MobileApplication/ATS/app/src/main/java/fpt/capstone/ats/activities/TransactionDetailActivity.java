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
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.sqlite.DBAdapter;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class TransactionDetailActivity extends AppCompatActivity {

    private static final String TAG = "TRANS_DETAIL_ACTIVITY";

    private TextView textStationName;
    private TextView textDateTime;
    private TextView textPrice;
    private TextView textStatus;
    private TextView textType;
    private Button btnPayment;
    private Button btnReport;

    private String transactionId;

    private DBAdapter database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        textStationName = (TextView) findViewById(R.id.textStationName);
        textDateTime = (TextView) findViewById(R.id.textDateTime);
        textPrice = (TextView) findViewById(R.id.textPrice);
        textStatus = (TextView) findViewById(R.id.textStatus);
        textType = (TextView) findViewById(R.id.textType);
        btnPayment = (Button) findViewById(R.id.btnPayLater);
        btnReport = (Button) findViewById(R.id.btnReport);

        Bundle bundle = getIntent().getExtras();
        transactionId = bundle.getString(ConstantValues.TRANSACTION_ID_PARAM);


        Log.d("GET DETAIL TRANSACTION", "VIEW FROM SERVER");
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
        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setMessage("Đang lấy dữ liệu từ hệ thống...");
        pdial.show();

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

                    String station = "[" + stationId + "] " + stationName + " - " + zone;
                    textStationName.setText(station);
                    textPrice.setText(formatter.format(price) + " đồng");
                    textDateTime.setText(sdf.format(datetime));
                    textType.setText("Thu phí " + type);

                    Commons.displayStatusTrans(status, textStatus);

                    if (status.equals("Failed") || status.equals("Failed Passed")
                            || status.equals("Initial") || status.equals("Not pay")) {
                        btnPayment.setVisibility(View.VISIBLE);
                        btnPayment.setEnabled(true);

                        btnReport.setVisibility(View.VISIBLE);
                        btnReport.setEnabled(true);

                    } else {
                        btnPayment.setVisibility(View.INVISIBLE);
                        btnPayment.setEnabled(false);

                        btnReport.setVisibility(View.VISIBLE);
                        btnReport.setEnabled(true);
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


    public void clickToPayLater(View view) {
        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setMessage("Đang xử lý thanh toán phí...");
        pdial.setTitle("Thanh toán");
        pdial.show();

        Log.d(TAG, "Send Request updateTransStatus");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    pdial.dismiss();
                    Log.w(TAG, "Transaction Status Json: " + result);
                    JSONObject infos = new JSONObject(result);
                    String resultPayment = infos.getString("result");

                    if (result.equals("")) {
                        new AlertDialog.Builder(TransactionDetailActivity.this)
                                .setTitle("Kết quả")
                                .setMessage("Giao dịch đã được thực hiện thành công")
                                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getTransactionDetail();
                                    }
                                })
                                .create().show();
                    } else {
                        new AlertDialog.Builder(TransactionDetailActivity.this)
                                .setTitle("Kết quả")
                                .setMessage("Giao dịch thất bại. " + resultPayment)
                                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getTransactionDetail();
                                    }
                                })
                                .create().show();
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
        rs.execute(params, "transaction", "updateProcessingTransaction", "GET");
    }

    public void viewFromLocal(Cursor resultSet) {
        try {
//            Log.d("Receive TransDetail", "Transaction Detail Json: " + resultSet);

//            if (pdial != null) {
//                pdial.dismiss();
//            }

            String stationName = resultSet.getString(1);
            int stationId = resultSet.getInt(2);
            String zone = resultSet.getString(3);
            String dateTime = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            String status = resultSet.getString(6);
            String vehicleType = resultSet.getString(7);
            String type = resultSet.getString(8);

            DecimalFormat formatter = new DecimalFormat("###,###,###.##");

            String station = "[" + stationId + "] " + stationName + " - " + zone;
            textStationName.setText(station);
            textPrice.setText(formatter.format(price) + " đồng");
            textStatus.setText(status);
            textDateTime.setText(dateTime);
            textType.setText("Thu phí " + type);

            String statusText = "-";
            Log.w("status: ", status);
            if (status.equals("Success")) {
                textStatus.setTextColor(Color.parseColor("#7bc043"));
                statusText = "Thanh toán Thành công";
            } else if (status.equals("Finish")) {
                textStatus.setTextColor(Color.parseColor("#0392cf"));
                statusText = "Hoàn thành";
            } else if (status.equals("Failed") || status.equals("Failed Passed")) {
                textStatus.setTextColor(Color.parseColor("#ee4035"));
                statusText = "Thanh toán Thất bại";
            } else if (status.equals("Initial") || status.equals("Not pay")) {
                textStatus.setTextColor(Color.parseColor("#ee4035"));
                statusText = "Chưa thanh toán";
            }

            textStatus.setText(statusText);

            if (status.equals("Failed") || status.equals("Failed Passed")
                    || status.equals("Initial") || status.equals("Not pay")) {
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

    public void clickToReport(View view) {
        final ProgressDialog pdial = new ProgressDialog(this);
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
                        new AlertDialog.Builder(TransactionDetailActivity.this)
                                .setTitle("Kết quả")
                                .setMessage("Giao dịch của bạn đã được gửi về xử lý sai xót.\nCám ơn bạn đã hỗ trợ")
                                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TransactionDetailActivity.this.finish();
                                    }
                                })
                                .create().show();
                    } else if (updateResult.equalsIgnoreCase("fail")) {
                        Log.e(TAG, "Report Transaction Fail");
                        Toast.makeText(TransactionDetailActivity.this, "Báo cáo thất bại. Vui lòng thử lại", Toast.LENGTH_LONG).show();
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
}