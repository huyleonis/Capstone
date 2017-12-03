package fpt.capstone.ats.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.RequestServer;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {

        EditText edtUsername = (EditText) findViewById(R.id.txtUsername);
        String username = edtUsername.getText().toString();

//        EditText edtPassword = (EditText) findViewById(R.id.txtPassword);
//        String password = edtPassword.getText().toString();
//
//        EditText edtCheckPassword = (EditText) findViewById(R.id.txtCheckPassword);
//        String checkPassword = edtCheckPassword.getText().toString();

        EditText edtEmail = (EditText) findViewById(R.id.txtEmail);
        String email = edtEmail.getText().toString();

        EditText edtPhone = (EditText) findViewById(R.id.txtPhone);
        String phone = edtPhone.getText().toString();

        EditText edtNumberId = (EditText) findViewById(R.id.txtNumberId);
        String numberId = edtNumberId.getText().toString();

        EditText edtLicensePlate = (EditText) findViewById(R.id.txtLicensePlate);
        String licensePlate = edtLicensePlate.getText().toString();

        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setCancelable(false);
        pdial.show();

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {

                pdial.dismiss();
                if (result.equals("Username is existed")) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Tên đăng nhập không hợp lệ")
                            .setMessage("Tài khoản đã tồn tại")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                } else if (result.equals("License plate is existed")) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Biển số xe không hợp lệ")
                            .setMessage("Biển số xe không tồn tại")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                } else if (result.equals("Success")) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                    RegisterActivity.this.finish();
                }
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
//        params.put("password", password);
        params.put("email", email);
        params.put("phone", phone);
        params.put("numberId", numberId);
        params.put("licensePlate", licensePlate);

        rs.execute(params, "account", "insertAccount", "POST");
    }

    public void checkValid(){
        
    }
}
