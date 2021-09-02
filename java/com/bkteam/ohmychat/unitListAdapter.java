package com.bkteam.ohmychat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class unitListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<unitsModel> unitList;

    public unitListAdapter(Activity mContext, List<unitsModel> unit_list) {
        super(mContext, R.layout.unit_list_item, unit_list);
        this.mContext = mContext;
        this.unitList = unit_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View listItemView, @NonNull ViewGroup parent) {
        try {
            LayoutInflater inflater = mContext.getLayoutInflater();
             listItemView = inflater.inflate(R.layout.unit_list_item, null, true);
            TextView course = listItemView.findViewById(R.id.course);
            TextView unitId = listItemView.findViewById(R.id.unitId);
            TextView unitName = listItemView.findViewById(R.id.name);
            TextView start = listItemView.findViewById(R.id.start);
            TextView end = listItemView.findViewById(R.id.end);

            unitsModel fp = unitList.get(position);
            course.setText(fp.getCourse());
            unitId.setText(fp.getUnitId());
            unitName.setText(fp.getUnit());
            start.setText(fp.getStartDate());
            end.setText(fp.getEndDate());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return listItemView;
    }
}

