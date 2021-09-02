package com.bkteam.ohmychat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {
    public TextView email,reg,course,unit,year;
    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        email=itemView.findViewById(R.id.email);
        reg=itemView.findViewById(R.id.reg_number);
        course=itemView.findViewById(R.id.course);
        unit=itemView.findViewById(R.id.unit);
        year=itemView.findViewById(R.id.year);
    }
}
