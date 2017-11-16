package fpt.capstone.ats.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fpt.capstone.ats.services.TransactionDetailService;


/**
 * Created by dovie on 11/1/2017.
 */

public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //context.startService(new Intent(context, TransactionDetailService.class));
    }
}
