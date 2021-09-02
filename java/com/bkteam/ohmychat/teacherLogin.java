package com.bkteam.ohmychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class teacherLogin extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView forgotPassword, newUser;
    //TeacherDb teacherDb;
    FirebaseUser currentUser;
    private FirebaseAuth mAUTH;
    private ProgressDialog loadingbar;
    DatabaseReference databaseReference;
    String uEmail,uPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        //teacherDb=new TeacherDb(this);
      //  mAUTH = FirebaseAuth.getInstance();
       // currentUser = mAUTH.getCurrentUser();
        loadingbar = new ProgressDialog(this);

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_password);
        login = findViewById(R.id.signin_button);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uEmail=dataSnapshot.child("email").getValue().toString();
                uPassword=dataSnapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tEmail, tPassword;
                tEmail = email.getText().toString().trim();
                tPassword = password.getText().toString().trim();
                if (TextUtils.isEmpty(tEmail) || TextUtils.isEmpty(tPassword)) {
                    // Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                    email.setError("Email can't be empty");
                    password.setError("Password can't be empty");
                    email.setFocusable(true);
                } else if (!(Patterns.EMAIL_ADDRESS.matcher(tEmail).matches())) {
                    email.setError("Invalid email");
                } else if (tPassword.length() < 6) {
                    password.setError("password length > 6");
                } else {
                    /*String storedPass=teacherDb.loginTeacher(tEmail);
                    if(tPassword.equals(storedPass))
                    {
                        Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),teacherMainActivity.class));
                        email.setText(" ");
                        password.setText( " ");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Wrong details",Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(getApplicationContext(),teacherMainActivity.class));
                        email.setText(" ");
                        password.setText( " ");
                        email.setFocusable(true);
                    }*/

                    if(tEmail.equals("teacher@gmail.com")&& tPassword.equals("teacher"))
                    {
                        loadingbar.setTitle("Signing in");
                        loadingbar.setMessage("Please Wait...");
                        loadingbar.setCanceledOnTouchOutside(true);
                        loadingbar.show();
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                        loadingbar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        email.setText(" ");
                        password.setText( " ");
                        email.setFocusable(true);

                    }

                }
            }

        });
    }

    private boolean isEmailValid(EditText email) {
        email.setError(null);
        String memail = email.getText().toString();
        if (TextUtils.isEmpty(memail)) {
            email.setError(getString(R.string.error_field_required));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(memail).matches()) {
            email.setError(getString(R.string.error_invalid_email));
        } else return true;
        email.requestFocus();
        return false;
    }

}
