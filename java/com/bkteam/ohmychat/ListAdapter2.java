package com.bkteam.ohmychat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter2 extends ArrayAdapter {
    private Activity mContext;
    List<unitsModel> paymentlist;
    public ListAdapter2(Activity mContext, List<unitsModel> paymentlist) {
        super(mContext,R.layout.listitem2,paymentlist);
        this.mContext=mContext;
        this.paymentlist=paymentlist;
    }
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View listItemView, @NonNull ViewGroup parent) {
        try {
            LayoutInflater inflater = mContext.getLayoutInflater();
            listItemView = inflater.inflate(R.layout.listitem2, null, true);
            TextView tvn = listItemView.findViewById(R.id.id);
            TextView tvd = listItemView.findViewById(R.id.name);
            TextView tva = listItemView.findViewById(R.id.start);
            TextView tvam = listItemView.findViewById(R.id.end);

            unitsModel fp = paymentlist.get(position);
            tvn.setText(fp.getUnitId());
            tvd.setText(fp.getUnit());
            tva.setText(fp.getStartDate());
            tvam.setText(fp.getEndDate());

            return listItemView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItemView;
    }
}
