package fpt.capstone.ats.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;


public class CardInputFragment extends Fragment {
    private static final String CARD_PARAM = "card";

    private String card;
    private String cardType;
    View rootView;
    TextView textCard;
    TextView textResult;



    public CardInputFragment() {
        // Required empty public constructor
    }


    public static CardInputFragment newInstance(String card) {
        CardInputFragment fragment = new CardInputFragment();
        Bundle args = new Bundle();
        args.putString(CARD_PARAM, card);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            card = getArguments().getString(CARD_PARAM);
            switch(card) {
                case "Mobiphone":
                    cardType = "VMS";
                    break;
                case "Vinaphone":
                    cardType = "VNP";
                    break;
                case "Viettel":
                    cardType = "VIETTEL";
                    break;
                case "Vcoin":
                    cardType = "VCOIN";
                    break;
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_input, container, false);

        textCard = (TextView) rootView.findViewById(R.id.textCardType);
        textCard.setText(card);

        textResult = (TextView) rootView.findViewById(R.id.textPaymentResult);
        textResult.setVisibility(View.INVISIBLE);

        return rootView;
    }

    public void requestTopup() {
        final EditText textCardNumber = (EditText) rootView.findViewById(R.id.textCardNumber);
        final EditText textCardSeri = (EditText) rootView.findViewById(R.id.textCardSeri);

        String cardNumber = textCardNumber.getText().toString();
        String cardSeri = textCardSeri.getText().toString();

        if (cardNumber.isEmpty() || cardSeri.isEmpty()) {
            textResult.setText("Không được để trong mã số thẻ và số seri");
            textResult.setVisibility(View.VISIBLE);
            textResult.setTextColor(Color.parseColor(ConstantValues.COLOR_RED));

            return;
        }

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                String message;
                try {
                    JSONObject json = new JSONObject(result);

                    boolean topupResult = json.getBoolean("result");
                    message = json.getString("message");

                    if (topupResult) {
                        textResult.setTextColor(Color.parseColor(ConstantValues.COLOR_GREEN));
                        textCardNumber.setText("");
                        textCardSeri.setText("");
                    } else {
                        textResult.setTextColor(Color.parseColor(ConstantValues.COLOR_RED));
                    }


                } catch(Exception e) {
                    Log.e("REQUEST_TOPUP", e.getMessage() + " - " + result);
                    message = e.getMessage();
                    textResult.setTextColor(Color.parseColor(ConstantValues.COLOR_RED));
                }

                textResult.setText(message);
                textResult.setVisibility(View.VISIBLE);
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("username", Commons.getUsername(getActivity()));
        params.put("card", cardType);
        params.put("cardNumber", cardNumber);
        params.put("cardSeri", cardSeri);

        rs.execute(params, "topup", "mobicard", "POST");
        textResult.setText("");
    }

}
