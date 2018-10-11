package com.pagatodo.yaganaste.ui.otp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.WindowManager;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.otp.fragments.OtpViewFragment;

/**
 * @author Juan Guerra on 19/04/2017.
 */

public class OtpCodeActivity extends SupportFragmentActivity {

    public static final String OTP_PASS = "1";

    public static Intent createIntent(Context from, String passOtp) {
        Intent intent = new Intent(from, OtpCodeActivity.class);
        Bundle args = new Bundle();
        args.putString(OTP_PASS, passOtp);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_details_em_adq);

        loadFragment(OtpViewFragment.newInstance(getIntent().getExtras().getString(OTP_PASS)));
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
