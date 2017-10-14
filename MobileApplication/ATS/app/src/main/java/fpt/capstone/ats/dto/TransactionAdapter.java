package fpt.capstone.ats.dto;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.Commons;

/**
 * Created by tinpb on 10/12/2017.
 */

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    Context context;
    int layoutResourceId;
    List<Transaction> list;

    public TransactionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Transaction> objects) {
        super(context, resource, objects);

        this.layoutResourceId = resource;
        this.context = context;
        this.list = objects;
    }

    static class DataHolder{
        TextView txtDate;
        TextView txtInfo;
        TextView txtStatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHolder holder = null;
        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.history_item, parent,false);

            holder = new DataHolder();
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.txtInfo = (TextView) convertView.findViewById(R.id.txtInfo);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);

            convertView.setTag(holder);
        } else {
            holder = (DataHolder) convertView.getTag();
        }


        Transaction dataItem = list.get(position);
        Date date = new Date(dataItem.getDateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateText = sdf.format(date);


        holder.txtDate.setText(dateText);
        holder.txtInfo.setText("Trạm: ["+ dataItem.getStationId() + "] " + dataItem.getStationName() + "- Giá: "
                + Commons.formatDouble(dataItem.getPrice()));

        String statusText = dataItem.getStatus();
        Log.d("status: " , statusText);
        if(statusText.equals("Thành công")){
            holder.txtStatus.setTextColor(Color.parseColor("#7bc043"));
        } else if (statusText.equals("Kết thúc")) {
            holder.txtStatus.setTextColor(Color.parseColor("#0392cf"));
        } else {
            holder.txtStatus.setTextColor(Color.parseColor("#ee4035"));
        }
        holder.txtStatus.setText(dataItem.getStatus());

        return convertView;
    }
}
