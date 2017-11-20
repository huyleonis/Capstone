package fpt.capstone.ats.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.services.BeaconService;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "LOGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    public void login(View view) throws FileNotFoundException, UnsupportedEncodingException {
        EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        String username = edtUsername.getText().toString();
        EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        String password = edtPassword.getText().toString();


        //Check account for login

        checkLogin(username, password);
        //After login successfully
        //Here is the code for saving user's information

        int vehicleId = 1;

        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        editor.putString("VehicleId", String.valueOf(vehicleId));
        editor.putString("Username", username);
        editor.putBoolean("hasLoggedIn", true);

        editor.commit();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        checkBluetooth(username);
    }

    private void startServiceAndMainActivity(String username) {

        //Start the service to monitor the Beacon
        Intent serviceIntent = new Intent(this, BeaconService.class);

        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.USERNAME_PARAM, username);
        serviceIntent.putExtras(bundle);

        startService(serviceIntent);

        //Start service to get transaction detail periodly
        // luu local cua transaction detail
//        Log.d("\nTRANSACTION DETAIL", "INTERNET CONNECTED - SYNCHRONIZATION BEGIN");
//        startService(new Intent(this, TransactionDetailService.class));


        //Here is the code for disappear LoginActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void checkBluetooth(final String username) {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Lỗi bluetooth")
                    .setMessage("Thiết bị này không hỗ trợ bluetooth. Vui lòng đăng nhập vào thiết bị " +
                            "hỗ trợ bluetooth để sử dụng ứng dụng thu phí tự động ATS. Xin cám ơn")
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                new AlertDialog.Builder(this)
                        .setTitle("Lỗi bluetooth")
                        .setMessage("Thiết bị chưa bật bluetooth. Vui lòng bật bluetooth để sử dụng ứng dụng thu phí tự động ATS.")
                        .setPositiveButton("Bật bluetooth", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bluetoothAdapter.enable();

                                startServiceAndMainActivity(username);
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create().show();
            } else {

//                startServiceAndMainActivity(username);
            }
        }
    }

    private void checkLogin(final String username, final String password){

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult(){

            @Override
            public void processFinish(String result) {
                //Trong result nếu có chứa "No message available" thì trả về vị trí của nó nếu không thì trả về -1
                System.out.println("chuoi can tim:" + result.indexOf("No message available"));
                if (result.indexOf("No message available") < 0  ) {
                    // checkLogin đúng thì gửi sms chứa mã OTP
                    sendOTP(username);
                    Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    intent.putExtras(bundle);
                    startActivity(intent);


                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu sai. Xin nhập lại"
                            , Toast.LENGTH_LONG).show();
                }
            }

        };
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);

        rs.execute(params, "login","checkLogin", "GET");
    }

    private void sendOTP(String username){

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                System.out.println("otp:" + result);
            }
        };
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        rs.execute(params, "otp", "sendOTP", "GET");
    }


}