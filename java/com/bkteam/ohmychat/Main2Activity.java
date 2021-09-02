package com.bkteam.ohmychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    private ViewPager mViewPaper;
    private TabLayout mTabLayout;
    private TeacherTabAccessorAdapter mTabsAccessorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        // setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Augmented Reality");

        mViewPaper = (ViewPager)findViewById(R.id.main_tabs_paper);
        mTabsAccessorAdapter = new TeacherTabAccessorAdapter(getSupportFragmentManager());
        mViewPaper.setAdapter(mTabsAccessorAdapter);

        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPaper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Are you sure to sign out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    //mAuth.signOut();
                    startActivity(new Intent(Main2Activity.this,LoginActivity.class));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
       /* if(item.getItemId()==R.id.units)
        {
            startActivity(new Intent(getApplicationContext(),ManagerViewHearsePaymentReport.class));
        }*/
        return super.onOptionsItemSelected(item);
    }
}