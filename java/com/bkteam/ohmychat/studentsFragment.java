package com.bkteam.ohmychat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class studentsFragment extends Fragment {
    private RecyclerView students ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference studentRef;
    String key;
    private FirebaseRecyclerAdapter<RegisteredStudents, RegisteredStudentsViewHolder> adapter;
    public studentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.activity_check_registered_students, container, false);
        studentRef = FirebaseDatabase.getInstance().getReference().child("Students");
        students =v.findViewById(R.id.recycler_students_menu);
        students.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getContext());
        students.setLayoutManager(layoutManager);
       return v;
    }

    @Override
    public void onStart() {
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
                //holder.gender.setText( model.getGender());
                holder.year.setText( model.getYear());
                //holder.course.setText("Course: " + model.getCourse());
                holder.unit.setText(model.getUnit());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence[] options =new CharSequence[]
                                {
                                        "Delete",
                                        "Cancel"

                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Are you sure to Delete?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0) {
                                    try {
                                        studentRef.orderByChild("email").equalTo(model.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    key = dataSnapshot1.getKey();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        studentRef.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if(i==1)
                                {
                                    try {
                                        startActivity(new Intent(getContext(),studentsFragment.class));
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                                else
                                {
                                    startActivity(new Intent(getContext(),studentsFragment.class));
                                }
                            }
                        });
                        builder.show();
                    }

                    });
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

    @Override
    public void onStop() {
        super.onStop();
    }
}
