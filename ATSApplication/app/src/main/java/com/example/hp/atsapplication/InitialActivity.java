package com.example.hp.atsapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.atsapplication.utils.ConstantValues;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Log.d("Initial Activity", "Create initial activity");

        final ImageView image = (ImageView)findViewById(R.id.imgLogo1);
        final TextView text = (TextView) findViewById(R.id.textSystemName);
        final Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);

        //Create animation for logo and text
        image.setAlpha(0f);
        text.setAlpha(0f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Initial Activity", "Begin Animation");
                image.startAnimation(animFadeIn);
                text.startAnimation(animFadeIn);
            }
        }, 500);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Initial Activity", "Begin Check login");
                checkLogin();
            }
        }, 1500);
    }

    private void checkLogin() {
        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = setting.getBoolean("hasLoggedIn", false);

        if(hasLoggedIn) {
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


}
