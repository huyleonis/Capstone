package fpt.capstone.ats.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.estimote.coresdk.cloud.google.ProximityBeaconCloud;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fpt.capstone.ats.R;
import fpt.capstone.ats.activities.MainActivity;
import fpt.capstone.ats.app.AtsApplication;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

public class BeaconService extends Service {

    private static final String TAG = "BEACON_SERVICE";
    private static final String DEFAULT_BEACON_UUID = "B9407F30-F5F8-466E-AFF9-25556B572896";
    private static final String DEFAULT_BEACON_IDENTIFIER = "ranged beacon";
    BeaconRegion defaultRegion = new BeaconRegion(DEFAULT_BEACON_IDENTIFIER,
            UUID.fromString(DEFAULT_BEACON_UUID), null, null);


    NotificationManager notificationManager;
    private BeaconManager bm;
    private String currentBeacon = null;
    private String currentType = null;
    private String username;


    public BeaconService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "Start Service to monitor beacon");

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            username = bundle.getString(ConstantValues.USERNAME_PARAM);
        } else {
            username = Commons.getUsername(this);
        }


        bm = new BeaconManager(getApplicationContext());

        bm.setBackgroundScanPeriod(500, 0);


        //setUpRangingBeacon();
        setUpMonitorBeacon();

        bm.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.w(TAG, "Start ranging and monitoring beacon");
                bm.startMonitoring(defaultRegion);
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantValues.STIMULATE_BEACON);
        registerReceiver(intentReceiver, intentFilter);

        return START_STICKY;
    }

    private BroadcastReceiver intentReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String uuid = bundle.getString("uuid");
            int major = bundle.getInt("major");
            int minor = bundle.getInt("minor");
            String identifier = uuid + ";" + major + ";" + minor;
            processBeacon(uuid, major, minor, identifier);
        }
    };


    private void setUpMonitorBeacon() {
        bm.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.w(TAG, "Enter Region of " + beacons.size() + " beacon(s)");
                if (beacons.size() == 0) {
                    currentBeacon = null;
                }

                // Lấy ra hết những beacon mà detect được
                for (Beacon beacon: beacons) {
                    String uuid = beacon.getProximityUUID().toString();
                    int major = beacon.getMajor();
                    int minor = beacon.getMinor();
                    String identifier = uuid + ";" + major + ";" + minor;

                    processBeacon(uuid, major, minor, identifier);
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.w(TAG, "Exit beacon region");
                if (currentType.equals("BEACON_RESULT")) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(ConstantValues.INSIDE_PARAM, false);
                    bundle.putString(ConstantValues.STATUS_PARAM, "Ra khỏi khu vực trạm thu phí");
                    bundle.putInt(ConstantValues.STAGE_PARAM, 4);
                    bundle.putBoolean(ConstantValues.EXIT_PARAM, true);

                    if (isForeground()) { //Nếu ứng dụng đang chạy trên cùng
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(ConstantValues.BEACON_EXIT_ACTION);
                        broadcastIntent.putExtras(bundle);
                        sendBroadcast(broadcastIntent);

                    } else { // Nếu ứng dụng đang không được mở
                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        resultIntent.putExtras(bundle);
                    }
                }
            }
        });
    }

    private void processBeacon(String uuid, int major, int minor, String identifier) {
        // Kiểm tra xem có phải beacon mới hay ko?
        if (!identifier.equals(currentBeacon)) {
            Log.w(TAG, "Detect one new beacon");

            currentBeacon = identifier; // Ghi nhận beacon hiện tại

            RequestServer rs = new RequestServer();
            rs.delegate = new RequestServer.RequestResult() {
                @Override
                public void processFinish(String result) {
                    try {
                        JSONObject infos = new JSONObject(result);

                        String type = infos.getString("type");
                        currentType = type;

                        switch (type) {
                            case "BEACON_PAYMENT":
                                JSONObject paymentBeaconInfo = infos.getJSONObject("info");
                                processPaymentBeaconInfo(paymentBeaconInfo);
                                break;
                            case "BEACON_RESULT":
                                int laneId = infos.getInt("laneId");
                                getResultBeacon(laneId);
                                break;
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception: " + e.getMessage() + " - result: " + result);
                    }
                }
            };
            List<String> params = new ArrayList<String>();
            params.add(uuid);
            params.add(major + "");
            params.add(minor + "");
            params.add(username);

            rs.execute(params, "beacon", "getBeacon", "GET");
        }
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "Stop beacon monitoring service");
        super.onDestroy();

        if (bm != null) {
            bm.stopRanging(defaultRegion);
            bm.stopMonitoring(defaultRegion.getIdentifier());
        }

        unregisterReceiver(intentReceiver);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Kiểm tra xem ứng dụng có đang chạy hay không?
     * @return true nếu ứng dụng đang chạy foreground, false ngược lại
     */
    private boolean isForeground() {
//        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
//
//        if (runningApps != null) {
//            for (ActivityManager.RunningAppProcessInfo app : runningApps) {
//                Log.w(TAG, "isForeground: " + app.processName);
//                if (app.processName.equals("fpt.capstone.ats")) {
//                    return true;
//                }
//            }
//        }
//        return false;
        boolean result = AtsApplication.isVisibleApp();
        Log.w(TAG, "isForeground: " + result);
        return result;

    }

    /**
     * Sau khi lấy dc beacon và là beacon payment thì tự trigger beacon payment và hàm này để xứ lý
     * json sau khi trigger beacon payment trả về
     * @param infos
     * @throws JSONException
     */
    private void processPaymentBeaconInfo(JSONObject infos) throws JSONException {
        String nameStation;
        String idStation;
        String zone;
        String photo = "";
        String transactionId = "";
        double price;
        boolean isCreated;


        if (infos.has("photo")) { //transaction dc tạo bởi camera
            nameStation = infos.getString("stationName");
            idStation = infos.getString("stationId");
            zone = infos.getString("zone");
            price = infos.getDouble("price");
            photo = infos.getString("photo");
            transactionId = infos.getString("id");
            isCreated = true;
        } else { //chỉ lấy giá bình thường
            nameStation = infos.getString("nameStation");
            idStation = infos.getString("stationId");
            zone = infos.getString("zoneStation");
            price = infos.getDouble("price");
            isCreated = false;
        }

        processPaymentBeaconInfo(nameStation, idStation, zone, price, isCreated, photo, transactionId);
    }

    /**
     * Sau khi lấy dữ liệu mà Payment Beacon chứa thì xử lý dữ liệu đó tại đây
     * @param nameStation tên trạm xe đang vào
     * @param idStation mã trạm
     * @param zone khu vực hiện tại
     * @param price giá tương ứng với xe
     */
    private void processPaymentBeaconInfo(String nameStation, String idStation, String zone, double price, boolean isCreated, String photo, String transactionID) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.NAME_STATION_PARAM, nameStation);
        bundle.putString(ConstantValues.ID_STATION_PARAM, idStation);
        bundle.putString(ConstantValues.ZONE_PARAM, zone);
        bundle.putDouble(ConstantValues.PRICE_PARAM, price);
        bundle.putString(ConstantValues.STATUS_PARAM, "Đi vào khu vực thu phí");
        bundle.putBoolean(ConstantValues.DISPLAY_CONFIRM_PARAM, true);
        bundle.putBoolean(ConstantValues.DISPLAY_RESULT_PARAM, false);
        bundle.putBoolean(ConstantValues.INSIDE_PARAM, true);
        bundle.putInt(ConstantValues.STAGE_PARAM, 1);
        bundle.putBoolean(ConstantValues.IS_CREATED_PARAM, isCreated);
        bundle.putString(ConstantValues.TRANSACTION_ID_PARAM, transactionID);
        bundle.putString(ConstantValues.PHOTO_PARAM, photo);

        if (isForeground()) { //Nếu ứng dụng đang chạy trên cùng
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(ConstantValues.BEACON_PAYMENT_ACTION);
            broadcastIntent.putExtras(bundle);
            sendBroadcast(broadcastIntent);

        } else { // Nếu ứng dụng đang không được mở
            Intent resultIntent = new Intent(this, MainActivity.class);
            bundle.putInt(ConstantValues.ID_NOTIFICATION_PARAM, ConstantValues.BEACON_PAYMENT_NOTI_ID);
            resultIntent.putExtras(bundle);

            showNotification("Thu phí tự động", "Bạn đã di chuyển vào khu vực thu phí tự động. " +
                    "Xác nhận trả phí cho phương tiện", "Ứng dụng thanh toán phí vận tải",
                    resultIntent, ConstantValues.BEACON_PAYMENT_NOTI_ID);

        }

        SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, Context.MODE_PRIVATE);
        setting.edit().putString("IdStation", idStation).commit();
    }

    /**
     * Method gọi server lấy dữ liệu Beacon Check Result (Beacon II)
     * @param laneId Làn xe mà xe chạy vào do beacon quy định
     */
    private void getResultBeacon(int laneId) {
        Log.w(TAG, "Detected beacon is Result Beacon in lane [id] = [" + laneId + "]");

        final SharedPreferences setting = getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);

        String transId = setting.getString(ConstantValues.TRANSACTION_ID_PARAM, "");

        Log.w(TAG, "Get Result beacon, check transaction id = <"+transId+">");
        if (transId.isEmpty()) {
            Log.w(TAG, "Get Result Beacon not trigger because there is no transaction id");
            return;
        }

        List<String> params = new ArrayList<>();
        params.add(laneId + "");
        params.add(transId);

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                Log.w(TAG, "Request for check result success");
                processResultBeacon();
                setting.edit().remove(ConstantValues.TRANSACTION_ID_PARAM).commit();
            }
        };

        rs.execute(params, "beacon", "result", "GET");
    }

    /**
     * Sau khi request server tương ứng với Result Beacon thì thực hiện update giao diện và báo
     * notification tại đây
     */
    private void processResultBeacon() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ConstantValues.INSIDE_PARAM, false);
        bundle.putString(ConstantValues.STATUS_PARAM, "Đang kiểm tra kết quả giao dịch");
        bundle.putInt(ConstantValues.STAGE_PARAM, 2);

        if (isForeground()) { //Nếu ứng dụng đang chạy trên cùng
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(ConstantValues.BEACON_RESULT_ACTION);
            broadcastIntent.putExtras(bundle);
            sendBroadcast(broadcastIntent);

        } else { // Nếu ứng dụng đang không được mở
            Intent resultIntent = new Intent(this, MainActivity.class);
            bundle.putInt(ConstantValues.ID_NOTIFICATION_PARAM, ConstantValues.BEACON_RESULT_NOTI_ID);
            resultIntent.putExtras(bundle);

            showNotification("Thu phí tự động", "Bạn đã di chuyển vào khu vực kiểm tra giao dịch thanh toán phí.",
                    "Ứng dụng thanh toán phí vận tải", resultIntent, ConstantValues.BEACON_RESULT_NOTI_ID);
        }
    }

    /**
     * Hiển thị thông báo cho người dùng
     * @param title
     * @param message
     * @param display
     * @param resultIntent
     */
    private void showNotification(String title, String message, String display, Intent resultIntent, int idNotification) {

        PendingIntent intent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentInfo(display)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(intent)
                .setVibrate(new long[] {500, 500, 500, 500})
                .setLights(Color.BLUE, 1000, 1000)
                //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();


        notificationManager.notify(idNotification, notification);
    }
}
