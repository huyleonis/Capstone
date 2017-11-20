package fpt.capstone.ats.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import fpt.capstone.ats.R;

public class ChoosePaymentMethodFragment extends Fragment {

    View rootView;

    public ChoosePaymentMethodFragment() {
        // Required empty public constructor
    }


    public static ChoosePaymentMethodFragment newInstance() {
        ChoosePaymentMethodFragment fragment = new ChoosePaymentMethodFragment();
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
        rootView = inflater.inflate(R.layout.fragment_choose_payment_method, container, false);


        return rootView;
    }


}
