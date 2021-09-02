package com.bkteam.ohmychat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassActivity extends AppCompatActivity {
    EditText email, oldPass, newPass;
    Button update;
    FirebaseUser user;
    AuthCredential credential;
    DatabaseReference userRef;
    FirebaseAuth mAuth;
    String currentUserId,userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        mAuth= FirebaseAuth.getInstance();
        userRef=FirebaseDatabase.getInstance().getReference().child("User");
        currentUserId=mAuth.getCurrentUser().getUid();

        InitializeField();
        displayCurrentUserInfo();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });


    }

    private void ChangePassword() {
        String userEmail = email.getText().toString();
        String userOldPass = oldPass.getText().toString();
        final String userNewPass = newPass.getText().toString();
        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userOldPass) || TextUtils.isEmpty(userNewPass)) {
            email.setError("Email address required");
            oldPass.setError("old password required");
            newPass.setError("new passowrd required");
        } else {
            try {
                credential = EmailAuthProvider.getCredential(userEmail, userOldPass);

                user = FirebaseAuth.getInstance().getCurrentUser();
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(userNewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangePassActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ChangePassActivity.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ChangePassActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void InitializeField() {
        email = (EditText) findViewById(R.id.change_pass_email);
        oldPass = (EditText) findViewById(R.id.change_pass_password);
        newPass = (EditText) findViewById(R.id.change_pass_new_password);
        update = (Button) findViewById(R.id.change_pass_button);
    }
    private void displayCurrentUserInfo() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    userEmail=dataSnapshot.child("email").getValue().toString().trim();
                    email.setText(userEmail);
                    email.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
