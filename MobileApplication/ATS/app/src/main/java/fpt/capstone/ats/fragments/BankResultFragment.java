package fpt.capstone.ats.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fpt.capstone.ats.R;
import fpt.capstone.ats.activities.TopupActivity;
import fpt.capstone.ats.utils.RequestServer;


public class BankResultFragment extends Fragment {
    private static final String TOKEN_CODE_PARAM = "token";

    private String tokenCode;

    TextView textData;
    View rootView;


    public BankResultFragment() {
    }

    public static BankResultFragment newInstance(String tokenCode) {
        BankResultFragment fragment = new BankResultFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN_CODE_PARAM, tokenCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tokenCode = getArguments().getString(TOKEN_CODE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bank_result, container, false);
        textData = (TextView) rootView.findViewById(R.id.textDataBankResult);
        textData.setMovementMethod(new ScrollingMovementMethod());

        checkOrderObject();
        return rootView;
    }

    private void checkOrderObject() {
        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    JSONObject json = new JSONObject(result);

                    String res = json.getString("result");
                    if (res.equals("true")) {
                        String response = json.getString("message");
                        JSONObject objResult = new JSONObject(response);

                        String responseCode = objResult.getString("response_code");
                        if (responseCode.equalsIgnoreCase("00")) {
                            String response_code = objResult.getString("response_code");
                            String receiver_email = objResult.getString("receiver_email");
                            String order_code = objResult.getString("order_code");
                            int total_amount = objResult.getInt("total_amount");
                            String currency = objResult.getString("currency");
                            String language = objResult.getString("language");
                            String return_url = objResult.getString("return_url");
                            String cancel_url = objResult.getString("cancel_url");
                            String notify_url = objResult.getString("notify_url");
                            String buyer_full_name = objResult.getString("buyer_fullname");
                            String buyer_email = objResult.getString("buyer_email");
                            String buyer_mobile = objResult.getString("buyer_mobile");
                            String buyer_address = objResult.getString("buyer_address");
                            int transaction_id = objResult.getInt("transaction_id");
                            int transaction_status = objResult.getInt("transaction_status");
                            int transaction_amount = objResult.getInt("transaction_amount");
                            String transaction_currency = objResult.getString("transaction_currency");
                            int transaction_escrow = objResult.getInt("transaction_escrow");

                            String dataCheckOrder =
                                    "response_code:  " + response_code + "\n\n" +
                                            "receiver_email:  " + receiver_email + "\n\n" +
                                            "order_code:  " + order_code + "\n\n" +
                                            "total_amount:  " + total_amount + "\n\n" +
                                            "currency:  " + currency + "\n\n" +
                                            "language:  " + language + "\n\n" +
                                            "return_url:  " + return_url + "\n\n" +
                                            "cancel_url:  " + cancel_url + "\n\n" +
                                            "notify_url:  " + notify_url + "\n\n" +
                                            "buyer_full_name:  " + buyer_full_name + "\n\n" +
                                            "buyer_email:  " + buyer_email + "\n\n" +
                                            "buyer_mobile:  " + buyer_mobile + "\n\n" +
                                            "buyer_address:  " + buyer_address + "\n\n" +
                                            "transaction_id:  " + transaction_id + "\n\n" +
                                            "transaction_status:  " + transaction_status + "\n\n" +
                                            "transaction_amount:  " + transaction_amount + "\n\n" +
                                            "transaction_currency:  " + transaction_currency + "\n\n" +
                                            "transaction_escrow:  " + transaction_escrow + "\n\n";

                            textData.setText(dataCheckOrder);
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Lỗi server")
                                    .setMessage(responseCode)
                                    .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create().show();
                        }

                    } else {
                        Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException ex) {
                    Toast.makeText(getActivity(), ex.getMessage() + " - " + result, Toast.LENGTH_LONG).show();
                }
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("tokenCode", tokenCode);

        rs.execute(params, "topup", "checkOrder", "POST");
    }

}
