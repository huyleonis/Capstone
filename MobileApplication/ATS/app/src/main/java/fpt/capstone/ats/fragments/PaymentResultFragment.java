package fpt.capstone.ats.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fpt.capstone.ats.R;


public class PaymentResultFragment extends Fragment {
    private static final String STATUS_ARG = "STATUS";
    private static final String COLOR_ARG = "COLOR";

    private String status;
    private String color;
    private View rootView;
    private TextView textResult;

    public PaymentResultFragment() {
        // Required empty public constructor
    }

    public static PaymentResultFragment newInstance(String status, String color) {
        PaymentResultFragment fragment = new PaymentResultFragment();
        Bundle args = new Bundle();
        args.putString(STATUS_ARG, status);
        args.putString(COLOR_ARG, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.status = (String) args.get(STATUS_ARG);
            this.color = (String) args.get(COLOR_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_payment_result, container, false);
        textResult = (TextView) rootView.findViewById(R.id.textPaymentResult);
        textResult.setText(status);
        textResult.setTextColor(Color.parseColor(color));
        return rootView;
    }

}
