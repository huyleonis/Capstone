package fpt.capstone.ats.fragments;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import fpt.capstone.ats.R;


public class ChooseCardFragment extends Fragment {


    View rootView;
    String[] cards = new String[]{"mobiphone", "viettel", "vinaphone", "vcoin"};

    public ChooseCardFragment() {
        // Required empty public constructor
    }


    public static ChooseCardFragment newInstance() {
        ChooseCardFragment fragment = new ChooseCardFragment();
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
        rootView =  inflater.inflate(R.layout.fragment_choose_card, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
