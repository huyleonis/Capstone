package fpt.capstone.ats.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import fpt.capstone.ats.R;
import fpt.capstone.ats.fragments.AccountFragment;
import fpt.capstone.ats.fragments.HistoryFragment;
import fpt.capstone.ats.fragments.HomeFragment;
import fpt.capstone.ats.service.TransactionDetailService;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class MainActivity extends AppCompatActivity {

    //Constant Values
    private static final String TAG = "MAIN ACTIVITY";
    private static final String DEFAULT_BEACON_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final String DEFAULT_BEACON_IDENTIFIER = "rid";
    private static final BeaconRegion ALL_BEACON_REGION = new BeaconRegion(DEFAULT_BEACON_IDENTIFIER,
            UUID.fromString(DEFAULT_BEACON_UUID), null, null);


    //Fields for controlling bottom navigation
    BottomNavigationView navigation;
    private FragmentManager fm = getFragmentManager();
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private HistoryFragment historyFragment = HistoryFragment.newInstance();
    private AccountFragment accountFragment = AccountFragment.newInstance();

    //Field for controlling beacon
    private BeaconManager bm;
    private List<BeaconRegion> beaconReagions = new ArrayList<>();

    //Fields for notification
    private int idNotification = 0;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        notificationManager.cancel(idNotification);
                        selectedFragment = homeFragment;
                        break;
                    case R.id.navigation_history:
                        selectedFragment = historyFragment;
                        break;
                    case R.id.navigation_account:
                        selectedFragment = accountFragment;
                        break;
                }
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        fm.beginTransaction().replace(R.id.content, homeFragment).commit();


        setUpBeaconRanging(Commons.getUsername(this));

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Bluetooth is turned off");
        } else {
            //Start scanning to detect beacon device
            if (bm != null) {
                bm.connect(new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        bm.startRanging(ALL_BEACON_REGION);
                    }
                });
            }
        }

        // luu local cua transaction detail
        if (isOnline()) {
            Log.d("\nTRANSACTION DETAIL", "INTERNET CONNECTED - SYNCHRONIZATION BEGIN");
            startService(new Intent(this, TransactionDetailService.class));
        } else {
            Log.d("\nTRANSACTION DETAIL", "INTERNET CONNECTING...");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void stopBeaconRanging() {
        if (bm != null) {
            bm.stopRanging(ALL_BEACON_REGION);
            bm.disconnect();
        }
    }

    private void setUpBeaconRanging(final String username) {
        bm = new BeaconManager(this);
        bm.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {

                for (Beacon beacon : beacons) {

                    String uuid = beacon.getProximityUUID().toString();
                    int major = beacon.getMajor();
                    int minor = beacon.getMinor();

                    String key = beacon.getUniqueKey();

                    BeaconRegion region = new BeaconRegion(key, UUID.fromString(uuid), major, minor);

                    if (!beaconReagions.contains(region)) {
                        Log.e(TAG, "Detect one beacon");
                        beaconReagions.add(region);
                        bm.startMonitoring(region);

                        RequestServer rs = new RequestServer();
                        rs.delegate = new RequestServer.RequestResult() {
                            @Override
                            public void processFinish(String result) {
                                try {
                                    JSONObject jsonBeacon = new JSONObject(result);

                                    String type = jsonBeacon.getString("type");
                                    int stationId = jsonBeacon.getInt("stationId");
                                    int laneId = jsonBeacon.getInt("laneId");
                                    switch (type) {
                                        case "BEACON_PAYMENT":
                                            processPaymentBeacon(stationId, username);
                                            break;
                                        case "BEACON_RESULT":
                                            processResultBeacon(laneId, homeFragment);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                                }
                            }
                        };

                        List<String> params = new ArrayList<String>();
                        params.add(uuid);
                        params.add(major + "");
                        params.add(minor + "");

                        rs.execute(params, "beacon", "get", "GET");
                    }
                }
            }
        });

        bm.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.e(TAG, "Thoát khỏi 1 beacon rồi");
                beaconReagions.remove(beaconRegion);
                bm.stopMonitoring(beaconRegion.getIdentifier());

                Bundle bundle = getIntent().getExtras();
                bundle.putBoolean("inside", false);
                getIntent().putExtras(bundle);
            }
        });
    }

    private void showNotification(String title, String message, String display, Bundle bundle) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        Intent resultIntent = new Intent(this.getApplication(), MainActivity.class);

        if (bundle != null) {
            resultIntent.putExtras(bundle);
        }

        PendingIntent intent = PendingIntent.getActivity(this.getApplication(), 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this.getApplication())
                .setSmallIcon(R.drawable.logo)
                .setContentInfo(display)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(intent)
                .build();

        notificationManager.notify(++idNotification, notification);
    }

    private void processPaymentBeacon(int idStation, String username) {
        Log.e(TAG, "Detected beacon is Payment Beacon");
        List<String> params = new ArrayList<>();

        params.add(idStation + "");
        params.add(username);

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                Log.e(TAG, "Get info of Payment Beacon");
                try {
                    JSONObject infos = new JSONObject(result);

                    String nameStation = infos.getString("nameStation");
                    String idStation = infos.getString("idStation");
                    String zone = infos.getString("zoneStation");
                    double price = infos.getDouble("price");


                    if (homeFragment.getView() != null && homeFragment.isVisible()) {
                        homeFragment.setUpStationInfo(nameStation, idStation, zone, price);
                    } else {
                        Log.e(TAG, "Tạo bundle cho dzui cái nè");
                        Bundle bundle = new Bundle();
                        bundle.putString("nameStation", nameStation);
                        bundle.putString("idStation", idStation);
                        bundle.putString("zoneStation", zone);
                        bundle.putDouble("price", price);
                        bundle.putString("status", "Đi vào khu vực thu phí");
                        bundle.putBoolean("inside", true);

                        showNotification("Thu phí tự động",
                                "Xe đã di chuyển vào khu vực thu phí tự động", "Đi vào khu vực thu phí", bundle);
                    }

                    //SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
                    //setting.edit().putString("IdStation", idStation).commit();


                } catch (JSONException e) {
                    Log.e("Home Activity", e.getMessage());
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Exception")
                            .setMessage("Cannot parse json with result: " + result)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }
            }
        };
        rs.execute(params, "price", "findPriceDriver", "GET");
    }

    private void processResultBeacon(int idLane, final HomeFragment homeFragment) {
        Log.e(TAG, "Detected beacon is Result Beacon");
        RequestServer rs = new RequestServer();

        List<String> params = new ArrayList<>();
        params.add(idLane + "");
        params.add(homeFragment.getIdTransaction());
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                Log.e("Second Beacon", "Send Transaction success");

                if (homeFragment.getView() != null && homeFragment.isVisible()) {
                    homeFragment.updateStatusOfTransaction("Đang kiểm tra kết quả giao dịch");
                } else {
                    Bundle bundle = getIntent().getExtras();
                    bundle.putString("status", "Đang kiểm tra kết quả giao dịch");
                    getIntent().putExtras(bundle);
                }

            }
        };
        rs.execute(params, "transaction", "checkResult", "GET");
    }

    public void clickToMakePayment(View view) {
        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        Log.d("Request Payment", "Send Request Payment");
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    Log.d("Receive Payment", "Payment Result Json: " + result);
                    JSONObject infos = new JSONObject(result);

                    String status = infos.getString("status");
                    homeFragment.hideConfirmFragment();
                    if (status.equals("Thành công")) {
                        homeFragment.showsResultFragment("Thanh toán thành công", "#46cc2b");
                    } else {
                        String reason = infos.getString("failReason");
                        homeFragment.showsResultFragment("Thanh toán thất bại. \nLý do: " + reason, "#ff0015");
                    }
                    homeFragment.updateStatusOfTransaction("Đã xử lý thanh toán.");
                    String idTrans = infos.getString("id");
                    setting.edit().putString("IdTransaction", idTrans).commit();
                } catch (JSONException e) {
                    Log.e("Make Payment", e.getMessage());
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Exception")
                            .setMessage("Cannot parse json with result: " + result)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create().show();
                }
            }
        };

        List<String> params = new ArrayList<>();
        params.add(Commons.getUsername(this));
        params.add(setting.getString("IdStation", ""));

        rs.execute(params, "transaction", "makePayment", "GET");
        homeFragment.updateStatusOfTransaction("đang thanh toán phí...");
    }

    public void clickToCancelPayment(View view) {

        homeFragment.hideConfirmFragment();
        homeFragment.updateStatusOfTransaction("Không thực hiện thanh toán.");
    }

    public void clickToCloseResult(View view) {
        homeFragment.hideResultFragment();
    }

    public void setBadgeNoti(int pos, int count) {
        BottomNavigationItemView item = (BottomNavigationItemView) navigation.getChildAt(pos);

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, navigation, false);

        item.addView(badge);
    }

    public void clickToLogOut(final View view) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn chắc chắn muốn đăng xuất khỏi tài khoản này? ")
                .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopBeaconRanging();
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

        Log.d(TAG, "day month year: " + day + " - " + month + " - " + year);

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

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof HomeFragment) {
            HomeFragment home = (HomeFragment) fragment;
            home.displayStationInfo();
        }
    }

    // check internet connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
