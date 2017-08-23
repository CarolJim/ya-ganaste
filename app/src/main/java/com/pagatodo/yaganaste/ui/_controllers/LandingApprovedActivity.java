package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;

import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_APPROVED;
import static com.pagatodo.yaganaste.utils.StringConstants.NAME_USER;

public class LandingApprovedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_approved);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Guardamos en las preferencias el exitod e ser adquirente
        Preferencias prefs = App.getInstance().getPrefs();
        prefs.saveDataBool(ADQUIRENTE_APPROVED, true);
    }

    public void openTabActivity(View view){
        Intent intent = new Intent(LandingApprovedActivity.this, TabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        /*
        Intent intent = new Intent(App.getContext(), MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        App.getContext().startActivity(intent);
         */
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}