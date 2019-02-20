package com.pagatodo.yaganaste.modules.payments.payReloadsServices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public class PayReloadsServicesActivity extends LoaderActivity {

    private PayReloadsServicesRouter router;
    private static final String TAG_TYPE = "TAG_TYPE";

    public static Intent intentRecharges(Activity activity, Type type){
        Intent intent = new Intent(activity,PayReloadsServicesActivity.class);
        intent.putExtra(TAG_TYPE,type);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
        this.router = new PayReloadsServicesRouter(this);
        init();
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    public PayReloadsServicesRouter getRouter() {
        return router;
    }

    public void init(){
        ButterKnife.bind(this);
        if (getIntent().getSerializableExtra(TAG_TYPE) !=null){
            this.router.onShowAllRecharges(((Type) getIntent().getSerializableExtra(TAG_TYPE)));
        }
    }

        public enum Type {
        ALL_RECHARGES,ALL_RECHARGES_FAV,ALL_SERVICES,ALL_SERVICES_FAV
    }
}
