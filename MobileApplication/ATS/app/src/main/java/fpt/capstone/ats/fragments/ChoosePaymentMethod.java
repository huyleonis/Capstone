package fpt.capstone.ats.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpt.capstone.ats.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChoosePaymentMethod.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChoosePaymentMethod#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoosePaymentMethod extends Fragment {

    private OnFragmentInteractionListener delegate;

    public ChoosePaymentMethod() {
        // Required empty public constructor
    }


    public static ChoosePaymentMethod newInstance() {
        ChoosePaymentMethod fragment = new ChoosePaymentMethod();
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
        return inflater.inflate(R.layout.fragment_choose_payment_method, container, false);
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
