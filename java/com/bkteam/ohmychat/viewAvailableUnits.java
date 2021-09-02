package com.bkteam.ohmychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewAvailableUnits extends AppCompatActivity {
    ListView listview;
    List<unitsModel> unit;
    DatabaseReference unitRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_units);
        listview=findViewById(R.id.listview);
        unit=new ArrayList<>();
        unitRef= FirebaseDatabase.getInstance().getReference().child("units");
        unitRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unit.clear();
                try {
                    for (DataSnapshot myUnits : dataSnapshot.getChildren()) {
                        unitsModel fpmts = myUnits.getValue(unitsModel.class);
                        unit.add(fpmts);
                    }
                    unitListAdapter adapter = new unitListAdapter(viewAvailableUnits.this,unit);
                    listview.setAdapter(adapter);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}