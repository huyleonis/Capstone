package fpt.capstone.ats.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.ConstantValues;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void login(View view) {

        //Check account for login



        //After login successfully
        //Here is the code for saving user's information
        EditText edtUsername = (EditText) findViewById(R.id.edtUsername);
        String username = edtUsername.getText().toString();
        int vehicleId = 1;

        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        editor.putString("ID","6");
        editor.putString("VehicleId", String.valueOf(vehicleId));
        editor.putString("Username", username);
        editor.putString("Fullname", "Nguyễn Văn A");
        editor.putString("LicensePlaste", "22B-22222");
        editor.putBoolean("hasLoggedIn", true);

        editor.commit();

        //Start the service to monitor the Beacon



        //Here is the code for disappear LoginActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }


}

