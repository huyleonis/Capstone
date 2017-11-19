package fpt.capstone.ats.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilterReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.RequestServer;

public class OTPActivity extends AppCompatActivity {

    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    public void startTimer(){
        //set a new Timer
        timer = new Timer();

        //initialize the timer
        initializeTimerTask();

        //schedule the timer, deplay 1 minute
        timer.schedule(timerTask, 1000*60);
    }

    public void initializeTimerTask(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                String username = getIntent().getExtras().getString("username");
                deleteFileAfterSendOTP(username);
                Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                startActivity(intent);
                OTPActivity.this.finish();
            }
        };
    }
    public void checkOTP(View view){
        EditText edtLicensePlate = (EditText) findViewById(R.id.edtLicensePlate);
        String licensePlate = edtLicensePlate.getText().toString();

        EditText edtOtpNumber = (EditText) findViewById(R.id.edtOtpNumber);
        String otpNumber = edtOtpNumber.getText().toString();
        String username = getIntent().getExtras().getString("username");

        checkLicensePlate(username, licensePlate);
        checkOtpNumber(username, otpNumber);
    }

    public void checkLicensePlate(String username, final String licensePlate){
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                if(!result.equals(licensePlate)){

                    new AlertDialog.Builder(OTPActivity.this)
                            .setTitle("Lỗi")
                            .setMessage("Đây không phải là biển số xe của bạn")
                            .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                    return;
                }
            }
        };
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        rs.execute(params, "otp", "getLicensePlate", "GET");
    }

    public void checkOtpNumber(final String username, final String otpNumber){
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                if(result.equals(otpNumber)){
                    deleteFileAfterSendOTP(username);
                    Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                    startActivity(intent);
                    OTPActivity.this.finish();
                }else {
                    new AlertDialog.Builder(OTPActivity.this)
                            .setTitle("Lỗi")
                            .setMessage("Nhập sai mã OTP")
                            .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                    return;
                }
            }
        };
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        rs.execute(params, "otp", "checkOtpNumber", "GET");
    }

    public void deleteFileAfterSendOTP(String username){
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
            }
        };
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        rs.execute(params, "otp", "deleteFile", "GET");
    }
}
