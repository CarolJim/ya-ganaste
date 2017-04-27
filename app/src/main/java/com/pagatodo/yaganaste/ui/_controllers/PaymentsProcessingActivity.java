package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.payments.fragments.SendPaymentFragment;

/**
 * Created by Jordan on 25/04/2017.
 */

public class PaymentsProcessingActivity extends SupportFragmentActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_generic_fragment_container);

        loadFragment(SendPaymentFragment.newInstance(), Direction.NONE, false);
    }
}
