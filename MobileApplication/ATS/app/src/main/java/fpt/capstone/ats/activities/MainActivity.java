package fpt.capstone.ats.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fpt.capstone.ats.R;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.fragments.AccountFragment;
import fpt.capstone.ats.fragments.HistoryFragment;
import fpt.capstone.ats.fragments.HomeFragment;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN ACTIVITY";
    private static final String DEFAULT_BEACON_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private static final String DEFAULT_BEACON_IDENTIFIER = "rid";
    private static final BeaconRegion ALL_BEACON_REGION = new BeaconRegion(DEFAULT_BEACON_IDENTIFIER,
            UUID.fromString(DEFAULT_BEACON_UUID), null, null);


    BottomNavigationView navigation;

    private AtsApplication app;

    private BeaconManager bm;
    private List<String> beaconInfo = new ArrayList<>();

    private FragmentManager fm = getFragmentManager();
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private HistoryFragment historyFragment = HistoryFragment.newInstance();
    private AccountFragment accountFragment = AccountFragment.newInstance();

    private TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                beaconInfo.clear();
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
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
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        textMessage = homeFragment.getTextViewMessage();
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

                for (Beacon beacon: beacons) {
                    String uuid = beacon.getProximityUUID().toString();
                    String major = beacon.getMajor() + "";
                    String minor = beacon.getMinor() + "";

                    String info = uuid + ":" + major + ":" + minor;
                    if (beaconInfo.indexOf(info) < 0) {
                        beaconInfo.add(info);

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
                                            processPaymentBeacon(textMessage, stationId, username, homeFragment);
                                            break;
                                        case "BEACON_RESULT":
                                            processResultBeacon(textMessage, laneId, homeFragment);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                                }
                            }
                        };

                        List<String> params = new ArrayList<String>();
                        params.add(uuid);
                        params.add(major);
                        params.add(minor);

                        rs.execute(params, "beacon", "get", "GET");
                    }
                }

            }
        });
    }

    private void processPaymentBeacon(TextView message, int idStation, String username, final HomeFragment homeFragment) {
        message.setText("Trạng thái: Đi vào khu vực thu phí");

        List<String> params = new ArrayList<>();

        params.add(idStation + "");
        params.add(username);

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    JSONObject infos = new JSONObject(result);

                    String city = infos.getString("nameStation");
                    String idStation = infos.getString("idStation");
                    String zone = infos.getString("zoneStation");
                    double price = infos.getDouble("price");

                    SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
                    setting.edit().putString("IdStation", idStation).commit();

                    homeFragment.setUpStationInfo(city, idStation, zone, price);
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

    private void processResultBeacon(final TextView message, int idLane, HomeFragment homeFragment) {
        RequestServer rs = new RequestServer();

        List<String> params = new ArrayList<>();
        params.add(idLane + "");
        params.add(homeFragment.getIdTransaction());
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                Log.d("Exit Beacon","Send Transaction success");

                message.setText("Trạng thái: đang kiểm tra kết quả giao dịch");
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
                        String reason =  infos.getString("failReason");
                        homeFragment.showsResultFragment("Thanh toán thất bại. \nLý do: " + reason, "#ff0015");
                    }


                    textMessage.setText("Trạng thái: đã xử lý thanh toán.");



                    String idTrans = infos.getString("id");
                    setting.edit().putString("IdTransaction",idTrans).commit();
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
        textMessage.setText("Trạng thái: đang thanh toán phí...");
    }

    public void clickToCancelPayment(View view) {

        homeFragment.hideConfirmFragment();
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
}
