package com.example.hp.atsapplication;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tinpb on 10/12/2017.
 */

public class CustomerAdapter extends ArrayAdapter<Transaction> {

    Context context;
    int layoutResourceId;
    List<Transaction> list;

    public CustomerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Transaction> objects) {
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
            convertView = inflater.inflate(R.layout.item_row, parent,false);

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateText = sdf.format(date);


        holder.txtDate.setText(dateText);
        holder.txtInfo.setText("Mã trạm: "+ dataItem.getStationId() + "- Giá: " + dataItem.getPrice());

        String statusText = dataItem.getStatus();
        Log.d("status: " , statusText);
        if(statusText.equals("Thành công")){
            holder.txtStatus.setTextColor(Color.parseColor("#0095ff"));
        }else {
            holder.txtStatus.setTextColor(Color.parseColor("#e24825"));
        }
        holder.txtStatus.setText(dataItem.getStatus());

        return convertView;
    }
}
