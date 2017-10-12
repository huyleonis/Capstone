package com.example.hp.atsapplication;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by tinpb on 10/12/2017.
 */

public class CustomerAdapter extends ArrayAdapter<Transaction> {

    public CustomerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Transaction> objects) {
        super(context, resource, objects);
    }
}
