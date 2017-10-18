package com.example.hp.atsapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.atsapplication.utils.ConstantValues;
import com.example.hp.atsapplication.utils.RequestServer;

import java.util.ArrayList;

public class InitialActivity extends AppCompatActivity {

    Button btnContinue;
    EditText edtIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Log.d("Initial Activity", "Create initial activity");

        final ImageView image = (ImageView)findViewById(R.id.imgLogo1);
        final TextView text = (TextView) findViewById(R.id.textSystemName);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setStartOffset(500);
        alphaAnimation.setFillAfter(true);

        image.startAnimation(alphaAnimation);
        text.startAnimation(alphaAnimation);

        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setEnabled(false);
        edtIp = (EditText) findViewById(R.id.edtIpConfig);
        edtIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnContinue.setEnabled(false);
            }
        });

    }

    private void checkLogin() {
        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = setting.getBoolean("hasLoggedIn", false);
        String username = setting.getString("Username", "");

        if(hasLoggedIn && !username.isEmpty()) {
            //Go directly to main activity.
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            //Go directly to main activity.
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        InitialActivity.this.finish();
    }


    public void clickToContinue(View view) {
        checkLogin();
    }

    public void clickToCheckConnection(View view) {
        EditText edtIpConfig = (EditText) findViewById(R.id.edtIpConfig);
        String ip = edtIpConfig.getText().toString();

        RequestServer.DEFAULT_URL = "http://" + ip + "/";


        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setMessage("Đang kiểm tra kết nối dữ liệu...");
        pdial.setTitle("Kiểm tra kết nối");
        pdial.setCancelable(false);
        pdial.show();

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                pdial.dismiss();
                if (result.equals("true")) {
                    btnContinue.setEnabled(true);
                    Toast.makeText(InitialActivity.this, "Kết nối thành công", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InitialActivity.this, "Kết nối thất bại, xin thử lại: \n" + result
                            , Toast.LENGTH_LONG).show();
                }
            }
        };
        rs.execute(new ArrayList<String>(), "ats", "checkConnection", "GET");
    }
}
