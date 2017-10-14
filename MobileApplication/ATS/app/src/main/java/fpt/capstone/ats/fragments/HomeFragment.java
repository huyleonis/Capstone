package fpt.capstone.ats.fragments;


import android.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.activities.LoginActivity;
import fpt.capstone.ats.activities.MainActivity;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;

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
    private TextView textCity;
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
        super.onCreate(savedInstanceState);

        fm = getChildFragmentManager();
        confirmFragment = ConfirmPaymentFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        textMessage = (TextView) rootView.findViewById(R.id.textMessage);
        textCity = (TextView) rootView.findViewById(R.id.textCity);
        textIdStation = (TextView) rootView.findViewById(R.id.textIdStation);
        textZone = (TextView) rootView.findViewById(R.id.textZone);
        textPrice = (TextView) rootView.findViewById(R.id.textPrice);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setUpStationInfo(String city, String idStation, String zone, double price ) {
        textCity.setText(city);
        textIdStation.setText(idStation);
        textZone.setText(zone);
        textPrice.setText(Commons.formatDouble(price) + " đồng");


        showsConfirmFragment();
    }

    public void setUpDefaultInfo(View view) {
        textCity.setText("-");
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
        fm.beginTransaction().remove(confirmFragment).commit();
    }

    public void hideResultFragment() {
        fm.beginTransaction().remove(resultFragment).commit();
    }

    public void updateStatusOfTransaction(String status) {
        textMessage.setText("Trạng thái: " + status);
    }

    public String getIdTransaction() {
        final SharedPreferences setting = this.getActivity().getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        return setting.getString("IdTransaction", "");
    }

}
