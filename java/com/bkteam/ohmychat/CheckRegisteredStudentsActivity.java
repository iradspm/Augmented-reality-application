package com.bkteam.ohmychat;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//import com.example.e_funeral.Model.Bookings;
//import com.example.e_funeral.ViewHolder.BookingViewHolder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CheckRegisteredStudentsActivity extends AppCompatActivity {
    private RecyclerView students ;
    Spinner spinner;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference studentRef;
    private FirebaseRecyclerAdapter<RegisteredStudents, RegisteredStudentsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_registered_students);
       /* spinner=(Spinner)findViewById(R.id.spinnerCategory);
        List<String> category=new ArrayList<>();
        category.add("All");
        category.add("Drawing 1");
        category.add("Drawing 2");
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,category);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);*/
        studentRef = FirebaseDatabase.getInstance().getReference().child("Students");
        students = findViewById(R.id.recycler_students_menu);
        students.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        students.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<RegisteredStudents> options =
                new FirebaseRecyclerOptions.Builder<RegisteredStudents>()
                        .setQuery(studentRef, RegisteredStudents.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<RegisteredStudents, RegisteredStudentsViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull RegisteredStudentsViewHolder holder, int position, @NonNull RegisteredStudents model) {
                holder.email.setText(model.getEmail() );
                holder.reg_number.setText( model.getRegistration_number() );
               // holder.gender.setText( model.getGender());
                holder.year.setText( model.getYear());
                //holder.course.setText("Course: " +"Engineering");
                holder.unit.setText(model.getUnit());
            }


            @NonNull
            @Override
            public RegisteredStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registered_students_layout, parent, false);
                RegisteredStudentsViewHolder holder = new RegisteredStudentsViewHolder(view);
                return holder;
            }

        };
        students.setAdapter(adapter);
        adapter.startListening();
    }
}
