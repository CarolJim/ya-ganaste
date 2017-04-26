package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;


public class SplashActivity extends AppCompatActivity {
    private Preferencias pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity_layout);

        pref = App.getInstance().getPrefs();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Class clazz = !RequestHeaders.getTokenauth().isEmpty() ? SessionActivity.class : MainActivity.class;
                Class clazz = MainActivity.class;
                Intent intent = new Intent(SplashActivity.this,clazz);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 2000);
    }
}
