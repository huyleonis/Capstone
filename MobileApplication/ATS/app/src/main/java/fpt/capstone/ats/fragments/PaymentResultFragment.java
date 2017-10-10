package fpt.capstone.ats.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpt.capstone.ats.R;


public class PaymentResultFragment extends Fragment {


    public PaymentResultFragment() {
        // Required empty public constructor
    }

    public static PaymentResultFragment newInstance() {
        PaymentResultFragment fragment = new PaymentResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_result, container, false);
    }


}
