package com.bkteam.ohmychat;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registration extends AppCompatActivity {
RelativeLayout relativeLayout;
TextInputEditText regNo,emailAd;
RadioGroup radioGroup;
Spinner spinner,unit;
Button register;
RadioButton radioButton;
StudentDb studentDb;

    FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    DatabaseReference studentDbRef,userRef,unitsDbRef,courseDbRef,draw1DbRef,draw2DbRef;
    String currentUserId,userEmail;
    ArrayList<String>courseArrayList=new ArrayList<>();
    ArrayList<String>unitArrayList=new ArrayList<>();
    String coursename,currentCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth= FirebaseAuth.getInstance();
        unitsDbRef=FirebaseDatabase.getInstance().getReference();
        courseDbRef=FirebaseDatabase.getInstance().getReference();
        studentDbRef=FirebaseDatabase.getInstance().getReference().child("Students");
        userRef=FirebaseDatabase.getInstance().getReference().child("User");

        draw1DbRef=FirebaseDatabase.getInstance().getReference().child("Drawing 1");
        draw2DbRef=FirebaseDatabase.getInstance().getReference().child("Drawing 2");

        currentUserId=mAuth.getCurrentUser().getUid();
        currentCourseId=unitsDbRef.getKey();
        displayCurrentUserInfo(); //getting email
        loadingbar=new ProgressDialog(this);

        regNo=findViewById(R.id.inputReg);
        emailAd=findViewById(R.id.inputEmail);
        //course=findViewById(R.id.inputCourse);
        //getCourse();

        radioGroup=findViewById(R.id.radiogroup);

        //units spinner
        unit=findViewById(R.id.unit);
        //calling method from db to load registered units
        loadSpinnerData();
       // enrolledStudentsData();

        //year of study spinner
        spinner=findViewById(R.id.spinner);
        List<String> yearOfStudy=new ArrayList<>();
        yearOfStudy.add("Select Year");
        yearOfStudy.add("1");
        yearOfStudy.add("2");
        yearOfStudy.add("3");
        yearOfStudy.add("4");
        yearOfStudy.add("5");
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.spinnerlayout,yearOfStudy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        relativeLayout=findViewById(R.id.rlayout);

        studentDb=new StudentDb(this);

        register=findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(regNo.getText().toString().trim()))
                {
                    regNo.setError(" Registration number required");
                    //password.setError(" Password required");
                    //email.setError(" Email required");
                }/*
                else if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
                {
                    email.setError(" Invalid email");
                }
                else if(password.getText().toString().length()<6)
                {
                    password.setError("password length > 6");
                }
                else if(regNo.getText().toString().trim().equals(reg_exists))
                {
                    regNo.setError("User exists");
                    regNo.setText("");
                    regNo.setFocusable(true);
                }*/
                else if(unit.getSelectedItemId()==-1)
                {
                    Toast.makeText(getApplicationContext(),"No unit available",Toast.LENGTH_SHORT).show();
                }
                else if(spinner.getSelectedItemId()==0)
                {
                    Toast.makeText(getApplicationContext(),"select year of study please",Toast.LENGTH_SHORT).show();
                }
                /*else if(userEmail.equals(emailAd.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"user email enrolled",Toast.LENGTH_SHORT).show();
                    emailAd.setFocusable(true);
                }*/
                else if(unit.getSelectedItem().equals("Drawing 1"))
                {
                    //validate units,gender and year then register student
                    String registration_number, year, gender, Courseunit,email;
                    registration_number = regNo.getText().toString().trim();
                    //  pass = password.getText().toString().trim();
                    // Email = email.getText().toString().trim();
                    int selectedGender = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedGender);
                    gender = radioButton.getText().toString().trim();
                    year = spinner.getSelectedItem().toString().trim();
                    Courseunit = unit.getSelectedItem().toString().trim();
                  //  stuCourse=course.getText().toString();
                    // courseN = course.getSelectedItem().toString().trim();
                    email=emailAd.getText().toString();
                    try
                    {
                        Query query1 = studentDbRef.orderByChild("email").equalTo(userEmail);
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists())
                                {
                                    EnrollStudents enrollStudents=new EnrollStudents(email,registration_number,year,gender,Courseunit);
                                    studentDbRef.push().setValue(enrollStudents);
                                    draw1DbRef.push().setValue(enrollStudents);
                                    Toast.makeText(getApplicationContext(),"user enrolled successfully",Toast.LENGTH_SHORT).show();
                                    regNo.setText("");
                                    regNo.setFocusable(true);
                                }
                                if(dataSnapshot.exists())
                                {
                                    Toast.makeText(getApplicationContext(),"user already exists",Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"use a different email address",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(unit.getSelectedItem().equals("Drawing 2"))
                {
                    //validate units,gender and year then register student
                    String registration_number, year, gender, Courseunit,email;
                    registration_number = regNo.getText().toString().trim();
                    //  pass = password.getText().toString().trim();
                    // Email = email.getText().toString().trim();
                    int selectedGender = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedGender);
                    gender = radioButton.getText().toString().trim();
                    year = spinner.getSelectedItem().toString().trim();
                    Courseunit = unit.getSelectedItem().toString().trim();
                   // stuCourse=course.getText().toString();
                    // courseN = course.getSelectedItem().toString().trim();
                    email=emailAd.getText().toString();
                    try
                    {
                        Query query1 = studentDbRef.orderByChild("email").equalTo(userEmail);
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists())
                                {
                                    EnrollStudents enrollStudents=new EnrollStudents(email,registration_number,year,gender,Courseunit);
                                    studentDbRef.push().setValue(enrollStudents);
                                    draw2DbRef.push().setValue(enrollStudents);
                                    Toast.makeText(getApplicationContext(),"user enrolled successfully",Toast.LENGTH_SHORT).show();
                                    regNo.setText("");
                                    regNo.setFocusable(true);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"user already exists",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                else {
                    try {
                        //validate units,gender and year then register student
                        String registration_number, year, gender, Courseunit,email;
                        registration_number = regNo.getText().toString().trim();
                      //  pass = password.getText().toString().trim();
                       // Email = email.getText().toString().trim();
                        int selectedGender = radioGroup.getCheckedRadioButtonId();
                        radioButton = findViewById(selectedGender);
                        gender = radioButton.getText().toString().trim();
                        year = spinner.getSelectedItem().toString().trim();
                        Courseunit = unit.getSelectedItem().toString().trim();
                        //stuCourse=course.getText().toString();
                       // courseN = course.getSelectedItem().toString().trim();
                        email=emailAd.getText().toString();

                       // Query query = studentDbRef.orderByChild("email").equalTo(email);
                        Query query1 = studentDbRef.orderByChild("email").equalTo(userEmail);
                                    query1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(!dataSnapshot.exists())
                                            {
                                                EnrollStudents enrollStudents=new EnrollStudents(email,registration_number,year,gender,Courseunit);
                                                studentDbRef.push().setValue(enrollStudents);
                                                Toast.makeText(getApplicationContext(),"Enrolled successfuly",Toast.LENGTH_SHORT).show();
                                                regNo.setText("");
                                                regNo.setFocusable(true);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(),"user exists",Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "No course/unit available", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }


    private void displayCurrentUserInfo() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    try {
                        userEmail=dataSnapshot.child("email").getValue().toString().trim();
                        emailAd.setText(userEmail);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadSpinnerData() {
       /* unitsDb unitsDb=new unitsDb(getApplicationContext());
        List<String> myUnits=unitsDb.getAllLabels();
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,myUnits);
        unit.setAdapter(dataAdapter);*/
        unitsDbRef.child("units").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unitArrayList.clear();
                for(DataSnapshot item:dataSnapshot.getChildren())
                {
                    unitArrayList.add(item.child("unit").getValue(String.class));
                }
                ArrayAdapter<String>arrayAdapter1=new ArrayAdapter<>(Registration.this,R.layout.spinnerlayout,unitArrayList);
                unit.setAdapter(arrayAdapter1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getCourse()
    {
        unitsDbRef.child("units").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    try {
                        coursename=dataSnapshot.child("course").getValue().toString();
                      //  course.setText(coursename);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
