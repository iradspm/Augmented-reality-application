package com.bkteam.ohmychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    private Button loginButton;
    private EditText userEmail, userPassword;
    private TextView needNewAccountLink;
    private TextView forgotPasswordLink;
    private ProgressDialog loadingBar;
    String currentUserId,myEmail ;


    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            userRef = FirebaseDatabase.getInstance().getReference().child("Users");
            currentUserId = mAuth.getCurrentUser().getUid();

        }catch (Exception e)
        {
            e.printStackTrace();
        }



        InitializeFields();
        //getUserEmail();

        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterActivity();
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassword();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });


    }

    private void ResetPassword() {
        if (userEmail.getText().toString().matches(""))
            Toast.makeText(LoginActivity.this, "Please enter the account's email!", Toast.LENGTH_SHORT).show();
        else{
            mAuth.sendPasswordResetEmail(userEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(LoginActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void AllowUserToLogin() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Email required");
           // Toast.makeText(this, "Please enter email...", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            userPassword.setError("Password required");
            //Toast.makeText(this, "Please enter password...", Toast.LENGTH_LONG).show();
        }else if (!(Patterns.EMAIL_ADDRESS.matcher(email)).matches()) {
            userEmail.setError("Invalid email");
        }
        else{
            loadingBar.setTitle(R.string.logging_in);
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                userRef.child(currentUserId).child("deviceToken")
                                        .setValue(deviceToken)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    SendUserToMainActivity();
                                                    Toast.makeText(LoginActivity.this, "Logged in ", Toast.LENGTH_LONG).show();
                                                    loadingBar.dismiss();
                                                }

                                            }
                                        });


                              /* SendUserToMainActivity();
                                Toast.makeText(LoginActivity.this,
                                        "Logged in Successfull...", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();*/

                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error : " + message, Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }

    }

    private void InitializeFields() {
        loginButton = (Button) findViewById(R.id.login_button);
        userEmail = (EditText) findViewById(R.id.login_email);
        userPassword = (EditText) findViewById(R.id.login_password);
        needNewAccountLink = (TextView) findViewById(R.id.need_new_account_link);
        forgotPasswordLink = (TextView) findViewById(R.id.forgot_password_link);
        loadingBar = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*if (currentUser != null) {
           SendUserToMainActivity();
        }*/
    }


    private void SendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, student_dashboard.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.homepageoptions,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.teacher:
                startActivity(new Intent(LoginActivity.this, teacherLogin.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
