package fpt.capstone.ats.fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpt.capstone.ats.R;
import fpt.capstone.ats.dto.Transaction;
import fpt.capstone.ats.dto.TransactionAdapter;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;
import fpt.capstone.ats.utils.RequestServer;


public class HistoryFragment extends Fragment {

    private static final String TAG = "HISTORY_FRAGMENT";

    private View rootView;
    private TextView fromDate;
    private TextView toDate;
    ArrayList<Transaction> lstData = new ArrayList<Transaction>();
    private TransactionAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        rootView =  inflater.inflate(R.layout.fragment_history, container, false);

        fromDate = (TextView) rootView.findViewById(R.id.txtFromDate);
        toDate = (TextView) rootView.findViewById(R.id.txtToDate);


        ListView listView = (ListView) rootView.findViewById(R.id.listViewHistory);
        adapter = new TransactionAdapter(this.getContext(), R.layout.history_item, lstData);
        listView.setAdapter(adapter);

        return rootView;
    }

    public void showHistory() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String fromDateString, toDateString;
        Date date1, date2;

        try {
            fromDateString = fromDate.getText().toString();
            date1 = format.parse(fromDateString);

            Log.d(TAG, "fromDate" + fromDateString);

            toDateString = toDate.getText().toString();
            date2 = format.parse(toDateString);

            Log.d(TAG, "toDate" + toDateString);
        } catch (ParseException e) {
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("Lỗi")
                    .setMessage("Ngày nhập vào không đúng cú pháp")
                    .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
            return;
        }


        // check validate ngày bắt đầu lớn hơn ngày kết thúc
        if (date1.compareTo(date2) > 0 ){
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("Lỗi")
                    .setMessage("Ngày kết thúc phải sau ngày bắt đầu")
                    .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create().show();
            return;
        }

        RequestServer rs = new RequestServer();
        rs.delegate  = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {

                try {
                    lstData.clear();
                    JSONArray list = new JSONArray(result);
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject trans = list.getJSONObject(i);
                        Transaction transaction = new Transaction();
                        transaction.setDateTime((Long) trans.get("dateTime"));
                        transaction.setStationId((Integer) trans.get("stationId"));
                        transaction.setPrice((Double) trans.get("price"));
                        transaction.setStatus((String) trans.get("status"));

                        lstData.add(transaction);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception: " + e.getMessage());
                }
            }

        };


        ArrayList<String> params = new ArrayList<>();
        params.add(Commons.getUsername(this.getContext()));
        params.add(fromDateString.replace('/','-'));
        params.add(toDateString.replace('/','-'));

        rs.execute(params, "history", "getByDate", "GET");
    }


}
