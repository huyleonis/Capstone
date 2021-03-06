package fpt.capstone.ats.fragments;


import android.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fpt.capstone.ats.R;
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
    private TextView textStation;
    private TextView textPrice;
    private ImageView imgTrans;

    private FragmentManager fm;
    private PaymentResultFragment resultFragment;
    private ConfirmPaymentFragment confirmFragment;
    private ExitFragment exitFragment;


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
        textStation = (TextView) rootView.findViewById(R.id.textStation);
        textPrice = (TextView) rootView.findViewById(R.id.textPrice);
        imgTrans = (ImageView) rootView.findViewById(R.id.imgTrans);

        setUpDefaultInfo(null);
        return rootView;
    }

    @Override
    public void onResume() {
        Log.w(TAG, "Resume home fragment");
        super.onResume();

        Bundle bundle = this.getActivity().getIntent().getExtras();

        if (bundle != null) {
            resolveBundle(bundle);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                boolean isCreated = bundle.getBoolean(ConstantValues.IS_CREATED_PARAM);
                String transactionId = bundle.getString(ConstantValues.TRANSACTION_ID_PARAM);

                this.setUpStationInfo(nameStation, idStation, zone, price, isCreated, transactionId);
            }

            boolean isDisplayConfirm = bundle.getBoolean(ConstantValues.DISPLAY_CONFIRM_PARAM, false);
            boolean isDisplayResult = bundle.getBoolean(ConstantValues.DISPLAY_RESULT_PARAM, false);
            String result = bundle.getString(ConstantValues.RESULT_PARAM);
            String resultColor = bundle.getString(ConstantValues.RESULT_COLOR_PARAM);
            String photo = bundle.getString(ConstantValues.PHOTO_PARAM);

            if (photo != null && !photo.isEmpty()) {
                Picasso.with(this.getActivity())
                        .load(RequestServer.DEFAULT_URL + "imgs/plates/" + photo)
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.notfound_img)
                        .into(imgTrans);
            }


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

            boolean exit = bundle.getBoolean(ConstantValues.EXIT_PARAM, false);
            if (exit) {
                setUpDefaultInfo(null);
                exitFragment = ExitFragment.newInstance();
                fm.beginTransaction().replace(R.id.frame_dialog, exitFragment).commit();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (exitFragment != null && exitFragment.isVisible()) {
                            fm.beginTransaction().remove(exitFragment).commit();
                        }
                    }
                }, 3000);
                bundle.putBoolean(ConstantValues.EXIT_PARAM, false);
            }

            this.getActivity().getIntent().putExtras(bundle);
        }
    }

    public void setUpStationInfo(String city, String idStation, String zone, double price, boolean isCreated, String transactionId) {
        textStation.setText("["+ idStation +"] " + city + " - " + zone);
        textPrice.setText(Commons.formatDouble(price) + " đồng");

        SharedPreferences setting = this.getActivity().getSharedPreferences(ConstantValues.PREF_NAME, MODE_PRIVATE);
        setting.edit()
                .putString(ConstantValues.ID_STATION_PARAM, idStation)
                .putBoolean(ConstantValues.IS_CREATED_PARAM, isCreated)
                .putString(ConstantValues.TRANSACTION_ID_PARAM, transactionId)
                .commit();
    }

    public void setUpDefaultInfo(View view) {
        textStation.setText("<Chưa vào trạm>");
        textPrice.setText("0  đồng");
        imgTrans.setImageResource(R.drawable.default_img);
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
