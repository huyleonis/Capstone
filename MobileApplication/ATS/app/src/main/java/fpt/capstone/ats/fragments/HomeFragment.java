package fpt.capstone.ats.fragments;


import android.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HOME_FRAGMENT";
    private View rootView;
    private TextView textMessage;
    private TextView textNameStation;
    private TextView textIdStation;
    private TextView textZone;
    private TextView textPrice;

    private FragmentManager fm;
    private PaymentResultFragment resultFragment;
    private ConfirmPaymentFragment confirmFragment;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "Create home fragment");
        super.onCreate(savedInstanceState);

        fm = getChildFragmentManager();
        confirmFragment = ConfirmPaymentFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG, "Create view home fragment");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        textMessage = (TextView) rootView.findViewById(R.id.textMessage);
        textNameStation = (TextView) rootView.findViewById(R.id.textNameStation);
        textIdStation = (TextView) rootView.findViewById(R.id.textIdStation);
        textZone = (TextView) rootView.findViewById(R.id.textZone);
        textPrice = (TextView) rootView.findViewById(R.id.textPrice);

        setUpDefaultInfo(null);
        return rootView;
    }

    @Override
    public void onResume() {
        Log.w(TAG, "Resume home fragment");
        super.onResume();

        Bundle bundle = this.getActivity().getIntent().getExtras();

        if (bundle != null) {
            Log.w(TAG, "Resolve bundle of Main Activity");
            resolveBundle(bundle);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w(TAG, "Pause Home fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Destroy Home Fragment");
    }

    public void resolveBundle(Bundle bundle) {
        Log.w(TAG, "Resolve bundle in Home Fragment");

        if (bundle != null) {
            String status = bundle.getString("status", "");
            this.updateStatusOfTransaction(status);

            boolean inside = bundle.getBoolean("inside");

            Log.w(TAG, "Kiểm tra giá trị <inside>: " + inside);
            if (inside) {
                String nameStation = bundle.getString(ConstantValues.NAME_STATION_PARAM);
                String idStation = bundle.getString(ConstantValues.ID_STATION_PARAM);
                String zone = bundle.getString(ConstantValues.ZONE_PARAM);
                double price = bundle.getDouble(ConstantValues.PRICE_PARAM);

                this.setUpStationInfo(nameStation, idStation, zone, price);
            }

            boolean isDisplayConfirm = bundle.getBoolean(ConstantValues.DISPLAY_CONFIRM_PARAM, false);
            boolean isDisplayResult = bundle.getBoolean(ConstantValues.DISPLAY_RESULT_PARAM, false);
            String result = bundle.getString(ConstantValues.RESULT_PARAM);
            String resultColor = bundle.getString(ConstantValues.RESULT_COLOR_PARAM);

            if (isDisplayConfirm) {
                Log.w(TAG, "Display Confirm Dialog");
                showsConfirmFragment();
            } else if (isDisplayResult) {
                Log.w(TAG, "Display Result Dialog");
                showsResultFragment(result, resultColor);
            } else {
                Log.w(TAG, "Display no dialog");
                hideConfirmFragment();
                hideResultFragment();
            }
        }
        Log.w(TAG, "Done resolving bundle in Home Fragment");
    }

    public void setUpStationInfo(String city, String idStation, String zone, double price ) {
        textNameStation.setText(city);
        textIdStation.setText(idStation);
        textZone.setText(zone);
        textPrice.setText(Commons.formatDouble(price) + " đồng");

        SharedPreferences setting = this.getActivity().getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        setting.edit().putString("IdStation", idStation).commit();
    }

    public void setUpDefaultInfo(View view) {
        textNameStation.setText("-");
        textIdStation.setText("-");
        textZone.setText("-");
        textPrice.setText("0  đồng");
    }

    public void showsConfirmFragment() {
        fm.beginTransaction().replace(R.id.frame_dialog, confirmFragment).commit();
    }

    public void showsResultFragment(String result, String color) {
        resultFragment = PaymentResultFragment.newInstance(result, color);
        fm.beginTransaction().replace(R.id.frame_dialog, resultFragment).commit();
    }

    public void hideConfirmFragment() {
        if (confirmFragment != null && confirmFragment.isVisible()) {
            fm.beginTransaction().remove(confirmFragment).commit();
        }
    }

    public void hideResultFragment() {

        if (resultFragment != null && resultFragment.isVisible()) {
            fm.beginTransaction().remove(resultFragment).commit();
        }
    }

    public void updateStatusOfTransaction(String status) {
        textMessage.setText("Trạng thái: " + status);
    }
}
