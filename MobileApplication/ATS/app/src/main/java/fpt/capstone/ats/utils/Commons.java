package fpt.capstone.ats.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DecimalFormat;

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
}
