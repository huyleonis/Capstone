package com.example.hp.atsapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hp.atsapplication.utils.ConstantValues;
import com.example.hp.atsapplication.utils.RequestServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    public static String DEFAULT_URL = "";
//    final ProgressDialog dialog = new ProgressDialog(this);

    private TextView fromDate;
    private TextView toDate;
    private int year, month, day, hours, minus, second;

    String date_time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fromDate = (TextView) findViewById(R.id.txtFromDate);
        toDate = (TextView) findViewById(R.id.txtToDate);


    }

    public void clickToFromDate(View view){
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hours = c.get(Calendar.HOUR_OF_DAY);
        minus = c.get(Calendar.HOUR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfDay, int dayOfMonth   ) {
                date_time = year+ "-" + (monthOfDay+1) + "-" + dayOfMonth;
                timePickerFromDate();
            }
        }, day, month, year);
        datePickerDialog.show();

    }

    private void timePickerFromDate(){
        final Calendar c = Calendar.getInstance();
        hours = c.get(Calendar.HOUR_OF_DAY);
        minus = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                hours = hourOfDay;
                minus = minute;

                fromDate.setText(date_time + " " + hourOfDay + ":" + minute + ":" + second);
            }
        }, hours,minus, true);
        timePickerDialog.show();
    }


    public void clickToToDate(View view) {
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hours = c.get(Calendar.HOUR_OF_DAY);
        minus = c.get(Calendar.HOUR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfDay, int dayOfMonth   ) {
                date_time = year+ "-" + (monthOfDay+1) + "-" + dayOfMonth;
                timePickerToDate();
            }
        }, day, month, year);
        datePickerDialog.show();
    }

    private void timePickerToDate(){
        final Calendar c = Calendar.getInstance();
        hours = c.get(Calendar.HOUR_OF_DAY);
        minus = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                hours = hourOfDay;
                minus = minute;

                toDate.setText(date_time + " " + hourOfDay + ":" + minute + ":" + second);
            }
        }, hours,minus, true);
        timePickerDialog.show();
    }

    public void clickToShowHistory(View view) {

        fromDate = (TextView) findViewById(R.id.txtFromDate);
        String fromDateString = fromDate.getText().toString();
        Log.d("fromDate", fromDateString);

        toDate = (TextView) findViewById(R.id.txtToDate);
        String toDateString = toDate.getText().toString();

        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        RequestServer rs = new RequestServer();
        rs.delegate  = new RequestServer.RequestResult() {

            @Override
            public void processFinish(String result) {

                try {
                    JSONArray list =  new JSONArray(result);
                    Log.d("abc", result);
                    Log.d("Result", String.valueOf(list.get(0)));
                    for(int i=0; i<list.length(); i++){
                        JSONObject trans =  list.getJSONObject(i);
                        if(trans != null){
                            String id = (String) trans.get("id");
                            int stationId = (int) trans.get("stationId");
                            String stationName = (String) trans.get("stationName");
//                            Date dateTime = (Date) trans.get("dateTime");
                            Long dateTime = trans.getLong("dateTime");
                            Date date = new Date(trans.getLong("dateTime"));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateText = simpleDateFormat.format(date);
                            dateText = trans.getString("dateTime");
                            Log.d("dateText" , dateText);
                            Double price = (Double) trans.get("price_id");
                            String username = (String) trans.get("username");


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ArrayList<String> params = new ArrayList<>();
        params.add(setting.getString("Username", ""));
        params.add(fromDateString);
        params.add(toDateString);


        rs.execute(params, "history", "getByDate", "GET");
    }
}
