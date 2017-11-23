package fpt.capstone.ats.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.services.BeaconService;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class InitialActivity extends AppCompatActivity {

    Button btnContinue;
    EditText edtIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Log.w("Initial Activity", "Create initial activity");

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

    private void checkLogin() {
        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        final boolean hasLoggedIn = setting.getBoolean("hasLoggedIn", false);
        final String username = setting.getString("Username", "");
        String token = setting.getString("token", "");

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result){
                Log.w("INITIAL_ACTIVITY", "check token result = " + result );
                if(result.equals("true")){
                    //Go directly to main activity.
                    Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                    startActivity(intent);

                    Intent serviceIntent = new Intent(InitialActivity.this, BeaconService.class);

                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantValues.USERNAME_PARAM, username);
                    serviceIntent.putExtras(bundle);

                    startService(serviceIntent);
                } else{
                    //Go to Login activity.
                    Intent intent = new Intent(InitialActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                InitialActivity.this.finish();

            }

        };


        if(hasLoggedIn && !username.isEmpty()) {
            Log.w("INITIAL_ACTIVITY", "check token = " + token );
            HashMap<String,String> params = new HashMap();
            params.put("username", username);
            params.put("token", token);
            rs.execute(params, "account", "checkToken", "POST");
        } else {
            //Go to Login activity.
            Intent intent = new Intent(InitialActivity.this, LoginActivity.class);
            startActivity(intent);
            InitialActivity.this.finish();
        }

    }


    public void clickToContinue(View view) {
        checkLogin();

        //Go directly to main activity.
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        InitialActivity.this.finish();
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
