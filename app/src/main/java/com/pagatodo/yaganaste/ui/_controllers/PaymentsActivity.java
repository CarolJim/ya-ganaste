package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

/**
 * Created by Jordan on 18/04/2017.
 */

public class PaymentsActivity extends SupportFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_fragment_container);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
