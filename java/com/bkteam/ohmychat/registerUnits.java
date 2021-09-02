package com.bkteam.ohmychat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class registerUnits extends Fragment {
    TextInputEditText course,startDate,endDate;

    final Calendar myCalendar = Calendar.getInstance();
    Button register;
    unitsDb unitsDb;

    DatabaseReference unitsDBref,studentDbRef;
   // FirebaseAuth mAuth;
    String userId,unitKey;
    String email_exists,reg_exists,unit_exists,course_exists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.registerunit, container, false);
        unitsDb=new unitsDb(getContext());

       // mAuth=FirebaseAuth.getInstance();
//        userId=mAuth.getCurrentUser().getUid();
        unitsDBref= FirebaseDatabase.getInstance().getReference().child("units");

        studentDbRef=FirebaseDatabase.getInstance().getReference().child("Students");

        //enrolledStudentsData();


        startDate=view.findViewById(R.id.inputstartDate);
        course=view.findViewById(R.id.inputCourseName);
        //date
         final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    startDate.setText(sdf.format(myCalendar.getTime()));


            }

        };



        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate=view.findViewById(R.id.inputendDate);
        final DatePickerDialog.OnDateSetListener end_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                endDate.setText(sdf.format(myCalendar.getTime()));


            }

        };
        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), end_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TextInputEditText unit1=view.findViewById(R.id.inputUnit);
        final Spinner unitId=(Spinner)view.findViewById(R.id.inputUnitID);



        List<String> myUnitsId=new ArrayList<>();
        myUnitsId.add("Select Unit Code");
        myUnitsId.add("EEE 1101");
        myUnitsId.add("EEE 2202");
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,myUnitsId);
        //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitId.setAdapter(adapter1);
        try {
            unitId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String myStr = unitId.getSelectedItem().toString();
                    //unit1.setText(myStr);
                    if (myStr.equals("EEE 1101")) {
                        unit1.setText("Drawing 1");
                        // unit1.setEnabled(false);
                    } else if (myStr.equals("EEE 2202")) {
                        unit1.setText("Drawing 2");
                        // unit1.setEnabled(false);
                    } else
                    {
                        unit1.setText("No Unit Selected");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        register=view.findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursename,sUnitId,sUnit,Ustart,Uend;
               // sUnit=unit.getSelectedItem().toString().trim();
                try {
                    unitKey=unitsDBref.push().getKey();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                coursename=course.getText().toString();
                sUnit=unit1.getText().toString().trim();
                sUnitId=unitId.getSelectedItem().toString();
                Ustart=startDate.getText().toString().trim();
                Uend=endDate.getText().toString().trim();
                if(TextUtils.isEmpty(sUnit)||TextUtils.isEmpty(Ustart)||TextUtils.isEmpty(Uend))
                {
                    unit1.setError("Unit required");
                    startDate.setError(" start date required");
                    endDate.setError(" end date required");
                }
               /* else if(sUnit.equals(unit_exists))
                {
                    unit1.setError("unit already exists");
                    unit1.setText("");
                    unit1.setFocusable(true);
                }*/
               /* else if(unitId.getSelectedItemId()==0)
                {
                    Toast.makeText(getContext(),"select unit code please",Toast.LENGTH_SHORT).show();
                    if(unitId.getSelectedItemId()==1)
                    {
                        unit1.setText("Drawing 1");
                        unit1.setEnabled(false);
                    }
                    else
                    {
                        unit1.setText("Drawing 2");
                        unit1.setEnabled(false);
                    }
                }*/
                else
                {
                   // DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    Query query = unitsDBref.orderByChild("unit").equalTo(sUnit);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists())
                            {
                                try {
                                    units units=new units(unitKey,coursename,sUnitId,sUnit,Ustart,Uend);
                                    unitsDBref.push().setValue(units);
                                    Toast.makeText(getContext(),"Unit registered success",Toast.LENGTH_SHORT).show();
                                    startDate.setText("");
                                    endDate.setText("");
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                            }else
                            {
                                Toast.makeText(getContext(),"Unit exists",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    units units=new units(sUnit,Ustart,Uend);
//                    unitsDBref.push().setValue(units);
//                    Toast.makeText(getContext(),"Unit registered success",Toast.LENGTH_SHORT).show();
//                    startDate.setText("");
//                    endDate.setText("");
                    //startActivity(new Intent(getContext(), teacherMainActivity.class));
                    //registerUnit(sUnit,Ustart,Uend);

                }
            }

          /*  private void registerUnit(String sUnit, String ustart, String uend) {
                DatabaseReference newUser=myUsersDatabase.child(userId);
                HashMap<String,Object> myMap=new HashMap<>();
                myMap.put("unit",sUnit);
                myMap.put("start_date",ustart);
                myMap.put("end_date",uend);
                newUser.updateChildren(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            //pd.dismiss();
                            //startActivity(new Intent(UserDetailsActivity.this,MainActivity.class));
                           // finish();
                            Toast.makeText(getContext(),"Unit registered success",Toast.LENGTH_SHORT).show();
                            startDate.setText("");
                            endDate.setText("");

                        }else {
                            Toast.makeText(getContext(),"Unit registration failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }*/
        });

       return view;
    }

    private void enrolledStudentsData() {
            studentDbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        email_exists=dataSnapshot.child("email").getValue().toString().trim();
                        reg_exists=dataSnapshot.child("registration_number").getValue().toString().trim();
                        unit_exists=dataSnapshot.child("unit").getValue().toString().trim();
                        course_exists=dataSnapshot.child("course").getValue().toString().trim();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }


}
