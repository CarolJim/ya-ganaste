package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pagatodo.yaganaste.R;

public class LandingApprovedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_approved);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void openTabActivity(View view){
        Intent intent = new Intent(LandingApprovedActivity.this, TabActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
