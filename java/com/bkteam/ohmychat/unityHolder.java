package com.bkteam.ohmychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.Engineering.draw.UnityPlayerActivity;


public class unityHolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity_holder);
        startActivity(new Intent(unityHolder.this, UnityPlayerActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(unityHolder.this, MainActivity.class));
    }
}