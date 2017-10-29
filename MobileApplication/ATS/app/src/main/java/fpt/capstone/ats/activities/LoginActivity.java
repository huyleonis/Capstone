package fpt.capstone.ats.activities;

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


import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;


import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.services.BeaconService;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;


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

    public void login(View view) {

        //Check account for login



        //After login successfully
        //Here is the code for saving user's information
        EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        String username = edtUsername.getText().toString();

        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        editor.putString("Username", username);
        editor.putBoolean("hasLoggedIn", true);

        editor.commit();

        checkBluetooth(username);
    }

    private void startServiceAndMainActivity(String username) {

        //Start the service to monitor the Beacon
        Intent serviceIntent = new Intent(this, BeaconService.class);

        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.USERNAME_PARAM, username);
        serviceIntent.putExtras(bundle);

        startService(serviceIntent);

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

                startServiceAndMainActivity(username);
            }
        }
    }

}

