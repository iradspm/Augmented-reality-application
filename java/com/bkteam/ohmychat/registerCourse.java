package com.bkteam.ohmychat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;

public class registerCourse extends Fragment {
    Button register;
    TextInputEditText cid,cname;
    courseDb courseDb;
    String email_exists,reg_exists,unit_exists,course_exists;

  //  private FirebaseAuth mAuth;

    DatabaseReference courseDbRef,studentDbRef;
    public registerCourse()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.register_course, container, false);
        //mAuth=FirebaseAuth.getInstance();

        courseDbRef= FirebaseDatabase.getInstance().getReference().child("Courses");
        studentDbRef=FirebaseDatabase.getInstance().getReference().child("Students");
        //enrolledStudentsData();


        register=v.findViewById(R.id.btnRegister);
        cid=v.findViewById(R.id.inputCourseId);
        cname=v.findViewById(R.id.inputCourseName);
        courseDb=new courseDb(getContext());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c_id,c_name;
                c_id=cid.getText().toString().trim();
                c_name=cname.getText().toString().trim();
                if(TextUtils.isEmpty(c_id)||TextUtils.isEmpty(c_name))
                {
                    cid.setError("course id required");
                    cname.setError("course name required");
                }
               /* else if(c_name.equals(course_exists))
                {
                    cname.setError("course exists");
                    cname.setText("");
                    cname.setFocusable(true);
                }*/
                else
                {
                    Query query = courseDbRef.orderByChild("courseName").equalTo(c_name);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()) {
                                courses courses = new courses(c_id, c_name);
                                courseDbRef.push().setValue(courses);
                                Toast.makeText(getContext(), "course registered success", Toast.LENGTH_SHORT).show();
                                cid.setText("");
                                cname.setText("");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    courses courses=new courses(c_id,c_name);
//                    courseDbRef.push().setValue(courses);
//                    Toast.makeText(getContext(),"course registered success",Toast.LENGTH_SHORT).show();
//                    cid.setText("");
//                    cname.setText("");

                  // String currentUserId=mAuth.getCurrentUser().getUid();
                    /*HashMap<String,Object> myCourses=new HashMap<>();
                    myCourses.put("course_id",c_id);
                    myCourses.put("course_name",c_name);
                    Toast.makeText(getContext(),"Course Registered",Toast.LENGTH_SHORT).show();
                    cid.setText(" ");
                    cname.setText("");*/

                   // courseRef.child("Courses").child(currentUserId).updateChildren(myCourses).addOnCompleteListener(new OnCompleteListener<Void>() {
                   /* courseRef.child("Courses").updateChildren(myCourses).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),"Course Registered",Toast.LENGTH_SHORT).show();
                                cid.setText(" ");
                                cname.setText("");

                            }


                        }
                    }) ;*/

                   /* Boolean result=courseDb.registerCourse(c_id,c_name);
                    if(result==true)
                    {
                        Toast.makeText(getContext(),"Course Registered",Toast.LENGTH_SHORT).show();
                        cid.setText(" ");
                        cname.setText("");
                    }
                    else
                    {
                        Toast.makeText(getContext(),"course not registered",Toast.LENGTH_SHORT).show();
                        cid.setText(" ");
                        cname.setText("");
                        cid.setFocusable(true);
                    }*/
                }
            }
        });
        return v;
    }
    public void enrolledStudentsData()
    {
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
