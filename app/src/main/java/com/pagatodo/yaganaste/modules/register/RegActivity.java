package com.pagatodo.yaganaste.modules.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

public class RegActivity extends LoaderActivity implements RegContracts.Presenter{

    private RegRouter router;

    public static Intent createIntent(Activity activity){
        return new Intent(activity,RegActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        router = new RegRouter(this);
        initViews();
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void initViews() {

    }
}
