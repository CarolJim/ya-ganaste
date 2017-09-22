package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.PageResult;
import com.pagatodo.yaganaste.interfaces.Command;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.utils.UI;

public class SIMActivity extends SupportFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    public void onClick(View v) {
        finish();
    }
}
