package com.bkteam.ohmychat;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.Engineering.draw.UnityPlayerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPaper;
    private TabLayout mTabLayout;
    private TabsAccessorAdapter mTabsAccessorAdapter;


    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();

        //mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
       // setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Augmented Reality");

        mViewPaper = (ViewPager)findViewById(R.id.main_tabs_paper);
        mTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        mViewPaper.setAdapter(mTabsAccessorAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPaper);

    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            SendUserToLoginActivity();
        }
        else{

            VerifyUserExistance();
            updateUserStatus("online");
        }
    }




    private void VerifyUserExistance() {
        String currentUserID = mAuth.getCurrentUser().getUid();
        rootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("name").exists())){
                    SendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToFindFriendsActivity() {
        Intent findFriendsIntent = new Intent(MainActivity.this, FindFriendActivity.class);
        startActivity(findFriendsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    private void SendUserToSettingsActivity() {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_option){
            updateUserStatus("offline");
            mAuth.signOut();
            SendUserToLoginActivity();
        }

        if(item.getItemId() == R.id.main_settings_option){
            SendUserToSettingsActivity();
        }

        if(item.getItemId() == R.id.main_create_group_option){
            RequestNewGroup();
        }

        if(item.getItemId() == R.id. main_find_friends_option){
            SendUserToFindFriendsActivity();
        }
        return true;
    }

    private void RequestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle(R.string.enter_group_name);

        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("Create Discussion group");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String groupName = groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(MainActivity.this, "Please write Group Name..", Toast.LENGTH_SHORT).show();
                }
                else{
                    CreateNewGroup(groupName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(final String groupName) {
        rootRef.child("Groups").child(groupName).setValue("").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, groupName + " group is Create Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUserStatus(String state){
        String saveCurrentUserTime, saveCurrentUserDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate  = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentUserDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime  = new SimpleDateFormat("hh:mm ss");
        saveCurrentUserTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();

        onlineStateMap.put("time", saveCurrentUserTime);
        onlineStateMap.put("date", saveCurrentUserDate);
        onlineStateMap.put("state", state);

        currentUserId = currentUser.getUid();
        rootRef.child("Users").child(currentUserId).child("userState")
                .updateChildren(onlineStateMap);

    }

}