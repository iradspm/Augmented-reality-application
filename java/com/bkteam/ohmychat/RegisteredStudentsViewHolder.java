package com.bkteam.ohmychat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RegisteredStudentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView email, reg_number,year,unit;
    public CardView cardView;
    public ItemClickListener listner;
    public RegisteredStudentsViewHolder(@NonNull View itemView) {
        super(itemView);
        email=itemView.findViewById(R.id.email);
       //cardView=itemView.findViewById(R.id.reg_students);
        reg_number= itemView.findViewById(R.id.reg_number);
        year=itemView.findViewById(R.id.year);
       // gender=itemView.findViewById(R.id.gender);
       // course=itemView.findViewById(R.id.course);
        unit=itemView.findViewById(R.id.unit);
    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}
