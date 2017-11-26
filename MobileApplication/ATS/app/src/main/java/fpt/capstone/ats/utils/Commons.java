package fpt.capstone.ats.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

import fpt.capstone.ats.R;

/**
 * Created by hp on 10/11/2017.
 */

public class Commons {
    public static String formatDouble(double number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        String result = formatter.format(number);
        return result;
    }

    public static String getUsername(Context context) {
        SharedPreferences setting = context.getSharedPreferences(ConstantValues.PREF_NAME, Context.MODE_PRIVATE);
        String username = setting.getString("Username", "");
        return username;
    }

    public static String getVehicleId(Context context) {
        SharedPreferences setting = context.getSharedPreferences(ConstantValues.PREF_NAME, Context.MODE_PRIVATE);
        String vehicleId = setting.getString("VehicleId", "1");
        return vehicleId;
    }

    public static void displayStatusTrans(String status, TextView textView) {
        String statusText = "-";
        Log.w("status: ", status);
        if (status.equals("Success")) {
            textView.setTextColor(Color.parseColor("#7bc043"));
            statusText = "Thanh toán Thành công";
        } else if (status.equals("Finish")) {
            textView.setTextColor(Color.parseColor("#0392cf"));
            statusText = "Hoàn thành";
        } else if (status.equals("Failed") || status.equals("Failed Passed")) {
            textView.setTextColor(Color.parseColor("#ee4035"));
            statusText = "Thanh toán Thất bại";
        } else if (status.equals("Initial") || status.equals("Not pay")) {
            textView.setTextColor(Color.parseColor("#ee4035"));
            statusText = "Chưa thanh toán";
        }

        textView.setText(statusText);
    }

    public static String getCodeError(Context context, String errorCode) {
        String descriptionCode = "";
        if (errorCode.equalsIgnoreCase("01")) {
            descriptionCode = context.getString(R.string.error_01);
        } else if (errorCode.equalsIgnoreCase("02")) {
            descriptionCode = context.getString(R.string.error_02);
        } else if (errorCode.equalsIgnoreCase("04")) {
            descriptionCode = context.getString(R.string.error_04);
        } else if (errorCode.equalsIgnoreCase("05")) {
            descriptionCode = context.getString(R.string.error_05);
        } else if (errorCode.equalsIgnoreCase("06")) {
            descriptionCode = context.getString(R.string.error_06);
        } else if (errorCode.equalsIgnoreCase("07")) {
            descriptionCode = context.getString(R.string.error_07);
        } else if (errorCode.equalsIgnoreCase("09")) {
            descriptionCode = context.getString(R.string.error_09);
        } else if (errorCode.equalsIgnoreCase("11")) {
            descriptionCode = context.getString(R.string.error_11);
        } else if (errorCode.equalsIgnoreCase("20")) {
            descriptionCode = context.getString(R.string.error_20);
        } else if (errorCode.equalsIgnoreCase("21")) {
            descriptionCode = context.getString(R.string.error_21);
        } else if (errorCode.equalsIgnoreCase("22")) {
            descriptionCode = context.getString(R.string.error_22);
        } else if (errorCode.equalsIgnoreCase("23")) {
            descriptionCode = context.getString(R.string.error_23);
        } else if (errorCode.equalsIgnoreCase("24")) {
            descriptionCode = context.getString(R.string.error_24);
        } else if (errorCode.equalsIgnoreCase("25")) {
            descriptionCode = context.getString(R.string.error_25);
        } else if (errorCode.equalsIgnoreCase("26")) {
            descriptionCode = context.getString(R.string.error_26);
        } else if (errorCode.equalsIgnoreCase("27")) {
            descriptionCode = context.getString(R.string.error_27);
        } else if (errorCode.equalsIgnoreCase("28")) {
            descriptionCode = context.getString(R.string.error_28);
        } else if (errorCode.equalsIgnoreCase("29")) {
            descriptionCode = context.getString(R.string.error_29);
        } else if (errorCode.equalsIgnoreCase("30")) {
            descriptionCode = context.getString(R.string.error_30);
        } else if (errorCode.equalsIgnoreCase("31")) {
            descriptionCode = context.getString(R.string.error_31);
        } else if (errorCode.equalsIgnoreCase("32")) {
            descriptionCode = context.getString(R.string.error_32);
        } else if (errorCode.equalsIgnoreCase("33")) {
            descriptionCode = context.getString(R.string.error_33);
        }

        return descriptionCode;
    }
}
