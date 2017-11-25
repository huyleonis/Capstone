package fpt.capstone.ats.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilterReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.services.BeaconService;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class OTPActivity extends AppCompatActivity {

    private final String TAG = "OTP_ACTIVITY";

    private static double counter;
    ProgressBar progressBar;
    String username;
    String fullname;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Bundle bundle = this.getIntent().getExtras();
        username = bundle.getString("username");
        fullname = bundle.getString("fullname");

        TextView textWelcome = (TextView) findViewById(R.id.textWelcome);
        textWelcome.setText(fullname);

        progressBar = (ProgressBar) findViewById(R.id.progressBarOTP);
        startTimer(5);

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

    public void startTimer(int minute){
        final double second = minute * 60;

        timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                counter += 0.5;
                Log.w(TAG, "Progress Bar: " + counter + "/" + second);
                if (counter >= second) {
                    returnToLoginActivity();
                } else {
                    progressBar.setProgress((int) (counter * 100 / second));
                }
            }
        };


        counter = 0;
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void clickToConfirmAccount(View view) {
        EditText edtLicensePlate = (EditText) findViewById(R.id.edtLicensePlate);
        String licensePlate = edtLicensePlate.getText().toString();

        EditText edtOtpNumber = (EditText) findViewById(R.id.edtOtpNumber);
        String otpNumber = edtOtpNumber.getText().toString();

        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setCancelable(false);
        pdial.show();


        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                pdial.dismiss();

                if (result.equals("License Plate is invalid")) {
                    new AlertDialog.Builder(OTPActivity.this)
                            .setTitle("Lỗi")
                            .setMessage("Đây không phải là biển số xe của bạn")
                            .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    returnToLoginActivity();
                                }
                            })
                            .create().show();
                } else if (result.equals("OTP is invalid")) {
                    new AlertDialog.Builder(OTPActivity.this)
                            .setTitle("Lỗi")
                            .setMessage("Nhập sai mã OTP")
                            .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    returnToLoginActivity();
                                }
                            })
                            .create().show();
                } else if (result.startsWith("Success")) {
                    String randomToken = result.substring(9, result.length());
                    startServiceAndMainActivity(username, randomToken);
                } else {
                    Toast.makeText(OTPActivity.this, result, Toast.LENGTH_LONG).show();
                }
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("licensePlate", licensePlate);
        params.put("OTP", otpNumber);

        rs.execute(params, "account", "otp", "POST");
    }


    private void returnToLoginActivity() {
        timer.cancel();

        Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    private void startServiceAndMainActivity(String username, String randomToken) {
        timer.cancel();

        //After login successfully
        //Here is the code for saving user's information
        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(ConstantValues.USERNAME_PARAM, username);
        editor.putBoolean("hasLoggedIn", true);
        editor.putString("token", randomToken);
        editor.commit();

        //Start the service to monitor the Beacon
        Intent serviceIntent = new Intent(this, BeaconService.class);

        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.USERNAME_PARAM, username);
        serviceIntent.putExtras(bundle);

        startService(serviceIntent);

        //Start service to get transaction detail periodly
        // luu local cua transaction detail
        //Log.d("\nTRANSACTION DETAIL", "INTERNET CONNECTED - SYNCHRONIZATION BEGIN");
        //startService(new Intent(this, TransactionDetailService.class));


        //Here is the code for disappear LoginActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
