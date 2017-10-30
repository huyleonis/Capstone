package fpt.capstone.ats.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.RequestServer;


public class AccountFragment extends Fragment {

    private static final String TAG = "ACCOUNT_FRAGMENT";

    private View rootView;
    private TextView textFullName;
    private TextView textLicensePlate;
    private TextView textVehicleType;
    private TextView textPhone;
    private TextView textEmail;
    private TextView textLicenseId;
    private TextView textNumberId;
    private TextView textBalance;
    private ImageView imgWarning;


    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        textFullName = (TextView) rootView.findViewById(R.id.textFullName);
        textLicensePlate = (TextView) rootView.findViewById(R.id.textLicensePlate);
        textVehicleType = (TextView) rootView.findViewById(R.id.textVehicleType);
        textPhone = (TextView) rootView.findViewById(R.id.textPhone);
        textEmail = (TextView) rootView.findViewById(R.id.textEmail);
        textLicenseId = (TextView) rootView.findViewById(R.id.textLicenseId);
        textNumberId = (TextView) rootView.findViewById(R.id.textNumberId);
        textBalance = (TextView) rootView.findViewById(R.id.textBalance);
        imgWarning = (ImageView) rootView.findViewById(R.id.imgWarning);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        final ProgressDialog pdial = new ProgressDialog(this.getActivity());
        pdial.setMessage("Đang lấy dữ liệu từ hệ thống...");
        pdial.show();

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                pdial.dismiss();
                try {
                    JSONObject jsonAccount = new JSONObject(result);
                    String fullName = jsonAccount.getString("fullname");
                    String licensePlate = jsonAccount.getString("licensePlate");
                    String vehicleType = jsonAccount.getString("vehicleType");
                    String numberId = jsonAccount.getString("numberId");
                    String phone = jsonAccount.getString("phone");
                    String email = jsonAccount.getString("email");
                    String licenseId = jsonAccount.getString("licenseId");
                    double balance = jsonAccount.getDouble("balance");

                    textFullName.setText(fullName);
                    textLicensePlate.setText(licensePlate);
                    textVehicleType.setText(vehicleType);
                    textPhone.setText(phone);
                    textEmail.setText(email);
                    textLicenseId.setText(licenseId);
                    textNumberId.setText(numberId);
                    textBalance.setText(Commons.formatDouble(balance) + " đồng");

                    if (balance < 50000f) {
                        imgWarning.setVisibility(View.VISIBLE);
                    } else {
                        imgWarning.setVisibility(View.INVISIBLE);
                    }


                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                }
            }
        };
        List<String> params = new ArrayList<>();
        params.add(Commons.getUsername(this.getActivity()));

        rs.execute(params, "account", "get", "GET");
    }
}