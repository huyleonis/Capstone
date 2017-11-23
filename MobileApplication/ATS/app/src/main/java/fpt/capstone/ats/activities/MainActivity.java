package fpt.capstone.ats.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.fragments.AccountFragment;
import fpt.capstone.ats.fragments.HistoryFragment;
import fpt.capstone.ats.fragments.HomeFragment;
import fpt.capstone.ats.services.TransactionDetailService;
import fpt.capstone.ats.services.BeaconService;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class MainActivity extends AppCompatActivity {

    //Constant Values
    private static final String TAG = "MAIN ACTIVITY";

    //Fields of some managers
    private Vibrator vibrator;
    private  Ringtone ringtone;
    private static final long[] VIBRATE_PATTERN = new long[] {0, 1000, 500, 1000};

    //Fields for controlling bottom navigation
    BottomNavigationView navigation;
    private FragmentManager fm = getFragmentManager();
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private HistoryFragment historyFragment = HistoryFragment.newInstance();
    private AccountFragment accountFragment = AccountFragment.newInstance();
    private Fragment selectedFragment;


    //Fields for notification
    private int idNotification = 0;
    private NotificationManager notificationManager;

    //Field for broacast receiver
    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            getIntent().putExtras(bundle);
            ringtone.play();
            vibrator.vibrate(VIBRATE_PATTERN, -1);
            if (homeFragment != null && homeFragment.isVisible()) {
                Log.w(TAG, "Home Fragment is displaying when receive broadcasr");
                homeFragment.resolveBundle(bundle);
            } else {
                Log.w(TAG, "Home Fragment is not displayed when receive broadcasr");
                setBadgeNoti(0);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add bundle for this activity and other fragments
        homeFragment.setArguments(new Bundle());
        accountFragment.setArguments(new Bundle());
        getIntent().putExtras(new Bundle());

        //Declare managers
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Declare the navigation
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        notificationManager.cancel(idNotification);
                        removeBadgeNoti(0);
                        selectedFragment = homeFragment;
                        break;
                    case R.id.navigation_history:
                        removeBadgeNoti(1);
                        selectedFragment = historyFragment;
                        break;
                    case R.id.navigation_account:
                        removeBadgeNoti(2);
                        selectedFragment = accountFragment;
                        break;
                }

                Log.w(TAG, "Navigation Item selected, title = " + item.getTitle() +
                        "Navigate to fragment " + selectedFragment);
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        fm.beginTransaction().replace(R.id.content, homeFragment).commit();
    }

    @Override
    protected void onResume() {
        Log.w(TAG, "Resume Activity");
        super.onResume();
        AtsApplication.onResumeApp();

        intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantValues.BEACON_RESULT_ACTION);
        intentFilter.addAction(ConstantValues.BEACON_PAYMENT_ACTION);
        intentFilter.addAction(ConstantValues.BEACON_EXIT_ACTION);

        registerReceiver(intentReceiver, intentFilter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            int idNotification = bundle.getInt(ConstantValues.ID_NOTIFICATION_PARAM, -1);
            if (idNotification != -1) {
                notificationManager.cancel(idNotification);
                if (homeFragment.getView() == null || homeFragment.isVisible()) {
                    setBadgeNoti(0);
                }
            }
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
        AtsApplication.onPausedApp();
    }

    public void removeBadgeNoti(int pos) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        BottomNavigationItemView item  = (BottomNavigationItemView) menuView.getChildAt(pos);

        if (item.findViewById(R.id.textNotiCount) != null) {
            item.removeViewAt(item.getChildCount() - 1);
        }
    }

    public void setBadgeNoti(int pos) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        BottomNavigationItemView item  = (BottomNavigationItemView) menuView.getChildAt(pos);

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, navigation, false);

        item.addView(badge);
    }

    //Events on click
    public void clickToMakePayment(View view) {
        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);

        final Bundle bundle = getIntent().getExtras();

        Log.w("Request Payment", "Send Request Payment");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.w("Receive Payment", "Payment Result Json: " + result);
                    JSONObject infos = new JSONObject(result);

                    String status = infos.getString("status");

                    if (homeFragment.getView() != null && homeFragment.isVisible()) {
                        homeFragment.hideConfirmFragment();
                        if (status.equals("Success")) {
                            homeFragment.showsResultFragment("Thanh toán thành công", "#46cc2b");
                        } else {
                            String reason =  infos.getString("failReason");
                            homeFragment.showsResultFragment("Thanh toán thất bại. \nLý do: " + reason, "#ff0015");
                        }
                        homeFragment.updateStatusOfTransaction("Đã xử lý thanh toán.");
                    } else {
                        setBadgeNoti(0);
                    }

                    //Cập nhật bundle
                    {
                        bundle.putBoolean(ConstantValues.DISPLAY_CONFIRM_PARAM, false);
                        bundle.putBoolean("isDisplayedConfirm", false);
                        bundle.putBoolean("isDisplayResult", true);
                        bundle.putString("status", "Đã xử lý thanh toán.");

                        if (status.equals("Success")) {
                            bundle.putString(ConstantValues.RESULT_PARAM,"Thanh toán thành công");
                            bundle.putString(ConstantValues.RESULT_COLOR_PARAM, ConstantValues.COLOR_GREEN);
                        } else {
                            String reason =  infos.getString("failReason");
                            bundle.putString(ConstantValues.RESULT_PARAM,"Thanh toán thất bại. \nLý do: " + reason);
                            bundle.putString(ConstantValues.RESULT_COLOR_PARAM, ConstantValues.COLOR_RED);
                        }
                        getIntent().putExtras(bundle);
                    }

                    String idTrans = infos.getString("id");
                    setting.edit().putString(ConstantValues.TRANSACTION_ID_PARAM, idTrans).commit();

                    Log.w(TAG, "After make payment, trans id = " + setting.getString(ConstantValues.TRANSACTION_ID_PARAM, ""));

                } catch (JSONException e) {
                    Log.e("Make Payment", e.getMessage());
                }
            }
        };

        boolean isCreated = setting.getBoolean("isCreated", false);

        if (isCreated) {
            List<String> params = new ArrayList<>();
            params.add(setting.getString(ConstantValues.TRANSACTION_ID_PARAM, ""));

            rs.execute(params, "transaction", "makePayment", "GET");
            homeFragment.updateStatusOfTransaction("đang thanh toán phí...");
        } else {
            List<String> params = new ArrayList<>();
            params.add(Commons.getUsername(this));
            params.add(setting.getString("IdStation", ""));

            rs.execute(params, "transaction", "makePayment", "GET");
            homeFragment.updateStatusOfTransaction("đang thanh toán phí...");
        }

        setting.edit().putString("IdStation", "").putBoolean("isCreated", false).commit();

    }

    public void clickToCancelPayment(View view) {
        Bundle bundle = getIntent().getExtras();
        bundle.putBoolean(ConstantValues.DISPLAY_CONFIRM_PARAM, false);
        bundle.putString(ConstantValues.STATUS_PARAM, "Không thực hiện thanh toán.");
        getIntent().putExtras(bundle);
        if (homeFragment.getView() != null && homeFragment.isVisible()) {
            homeFragment.hideConfirmFragment();
            homeFragment.updateStatusOfTransaction("Không thực hiện thanh toán.");
        }
    }

    public void clickToCloseResult(View view) {
        Bundle bundle = getIntent().getExtras();
        bundle.putBoolean(ConstantValues.DISPLAY_RESULT_PARAM, false);
        getIntent().putExtras(bundle);
        if (homeFragment.getView() != null && homeFragment.isVisible()) {
            homeFragment.hideResultFragment();
        }
    }

    public void clickToLogOut(final View view) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn chắc chắn muốn đăng xuất khỏi tài khoản này? ")
                .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //stop service here
                        stopService(new Intent(getBaseContext(), BeaconService.class));

                        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = setting.edit();

                        editor.putBoolean("hasLoggedIn", false);
                        editor.putString("Username", "");
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }

    public void clickToSetFromDate(final View view) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        Log.w(TAG, "day month year: " + day + " - " + month + " - " + year);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfDay, int dayOfMonth) {
                String dateTime = dayOfMonth + "/" + (monthOfDay + 1) + "/" + year;
                ((TextView) view).setText(dateTime);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void clickToSetToDate(final View view) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfDay, int dayOfMonth) {
                String dateTime = dayOfMonth + "/" + (monthOfDay + 1) + "/" + year;
                ((TextView) view).setText(dateTime);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void clickToShowHistory(View view) {
        historyFragment.showHistory();
    }

    public void clickToStimulateBeacon1(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("uuid", "B9407F30-F5F8-466E-AFF9-25556B57FE6D");
        bundle.putInt("major", 36857);
        bundle.putInt("minor", 31381);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ConstantValues.STIMULATE_BEACON);
        broadcastIntent.putExtras(bundle);
        sendBroadcast(broadcastIntent);
    }

    public void clickToStimulateBeacon2(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("uuid", "B9407F30-F5F8-466E-AFF9-25556B57FE6D");
        bundle.putInt("major", 39748);
        bundle.putInt("minor", 38452);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ConstantValues.STIMULATE_BEACON);
        broadcastIntent.putExtras(bundle);
        sendBroadcast(broadcastIntent);
    }

    public void clickToStimulateBeacon3(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("uuid", "B9407F30-F5F8-466E-AFF9-25556B57FE6D");
        bundle.putInt("major", 24100);
        bundle.putInt("minor", 4804);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ConstantValues.STIMULATE_BEACON);
        broadcastIntent.putExtras(bundle);
        sendBroadcast(broadcastIntent);
    }

    public void clickToTopup(View view) {
        Intent intent = new Intent(this, TopupActivity.class);
        startActivity(intent);
    }
}