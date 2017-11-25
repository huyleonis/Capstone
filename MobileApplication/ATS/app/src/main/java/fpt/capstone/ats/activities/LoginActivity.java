package fpt.capstone.ats.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


        //Check bluetooth
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            new AlertDialog.Builder(this)
                    .setTitle("Lỗi bluetooth")
                    .setMessage("Thiết bị chưa bật bluetooth. Vui lòng bật bluetooth để sử dụng ứng dụng thu phí tự động ATS.")
                    .setPositiveButton("Bật bluetooth", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            bluetoothAdapter.enable();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
            return;
        } else {
            //check login
            checkLogin(username, password);
        }

    }

    private void checkLogin(final String username, final String password){
        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setCancelable(false);
        pdial.show();


        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult(){

            @Override
            public void processFinish(String result) {
                try {
                    pdial.dismiss();

                    JSONObject json = new JSONObject(result);
                    String loginResult = json.getString("result");
                    Log.w(TAG, "Login Result: " + loginResult );

                    if (loginResult.equals("Success")) {
                        String fullname = json.getString("fullname");

                        Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        bundle.putString("fullname", fullname);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        LoginActivity.this.finish();
                    } else if (loginResult.equals("Account does not exist")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Đăng nhập không hợp lệ")
                                .setMessage("Tài khoản không tồn tại")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create().show();
                    } else if (loginResult.equals("Password is invalid")) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Đăng nhập không hợp lệ")
                                .setMessage("Mật khẩu không chính xác")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create().show();
                    }
                } catch(JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e.getMessage() + " - " + result);
                    Toast.makeText(LoginActivity.this, "JSON Exception: " + result, Toast.LENGTH_LONG).show();
                }

            }

        };
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        rs.execute(params, "account","login", "POST");
    }



}