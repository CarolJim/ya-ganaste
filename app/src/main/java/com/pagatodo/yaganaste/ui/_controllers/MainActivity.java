package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;


public class MainActivity extends AppCompatActivity {
    private Preferencias pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        pref = App.getInstance().getPrefs();

        Toast.makeText(this,"MainActivity",Toast.LENGTH_SHORT).show();

    }
}
