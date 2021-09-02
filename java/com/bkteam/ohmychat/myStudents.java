package com.bkteam.ohmychat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class myStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students);
        String email=getIntent().getStringExtra("email");
        String reg=getIntent().getStringExtra("reg");
        String course=getIntent().getStringExtra("course");
        String unit=getIntent().getStringExtra("unit");
        String year=getIntent().getStringExtra("year");
        Log.i("Email:",email);
        Log.i("Reg:",reg);
        Log.i("Course:",course);
        Log.i("Unit:",unit);
        Log.i("Year:",year);
        Toast.makeText(getApplicationContext(),""+email,Toast.LENGTH_SHORT).show();
    }
}