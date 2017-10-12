package fpt.capstone.ats.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpt.capstone.ats.R;


public class ConfirmPaymentFragment extends Fragment {


    public ConfirmPaymentFragment() {
        // Required empty public constructor
    }

    public static ConfirmPaymentFragment newInstance() {
        ConfirmPaymentFragment fragment = new ConfirmPaymentFragment();
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
        return inflater.inflate(R.layout.fragment_confirm_payment, container, false);
    }

}
