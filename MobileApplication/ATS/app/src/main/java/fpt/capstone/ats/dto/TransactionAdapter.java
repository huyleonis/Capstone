package fpt.capstone.ats.dto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fpt.capstone.ats.R;
import fpt.capstone.ats.activities.TransactionDetailActivity;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.ConstantValues;

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
        ImageView btnDetail;
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
            holder.btnDetail = (ImageView) convertView.findViewById(R.id.btnDetail);

            convertView.setTag(holder);
        } else {
            holder = (DataHolder) convertView.getTag();
        }


        final Transaction dataItem = list.get(position);
        Date date = new Date(dataItem.getDateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateText = sdf.format(date);


        holder.txtDate.setText(dateText);
        holder.txtInfo.setText("["+ dataItem.getStationId() + "] " + dataItem.getStationName() + " - "
                + Commons.formatDouble(dataItem.getPrice()) + "đ");

        String status = dataItem.getStatus();
        String statusText = "-";
        Log.w("status: " , status);
        if(status.equals("Success")){
            holder.txtStatus.setTextColor(Color.parseColor("#7bc043"));
            statusText = "Thanh toán Thành công";
        } else if (status.equals("Finish")) {
            holder.txtStatus.setTextColor(Color.parseColor("#0392cf"));
            statusText = "Hoàn thành";
        } else if (status.equals("Failed")) {
            holder.txtStatus.setTextColor(Color.parseColor("#ee4035"));
            statusText = "Thanh toán Thất bại";
        } else if (status.equals("Initial") || status.equals("Not pay")) {
            holder.txtStatus.setTextColor(Color.parseColor("#ee4035"));
            statusText = "Chưa thanh toán";
        }

        holder.txtStatus.setText(statusText);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransactionDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ConstantValues.TRANSACTION_ID_PARAM, dataItem.getId());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
