package com.pagatodo.yaganaste.modules.payReloadsServices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public class PayReloadsServicesActivity extends LoaderActivity {

    private PayReloadsServicesRouter router;
    private static final String TAG_TYPE = "TAG_TYPE";
    private static final String TAG_LIST = "TAG_LIST";

    public static Intent intentRecharges(Activity activity, Type type, ArrayList<IconButtonDataHolder> recList){
        Intent intent = new Intent(activity,PayReloadsServicesActivity.class);
        intent.putExtra(TAG_TYPE,type);
        intent.putExtra(TAG_LIST,recList);
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
            switch (((Type) getIntent().getSerializableExtra(TAG_TYPE))){
                case ALL_RECHARGES:

                    this.router.onShowAllRecharges((ArrayList<IconButtonDataHolder>) getIntent().getSerializableExtra(TAG_LIST));
                    break;
            }
        }
    }

    public enum Type implements Serializable {
        ALL_RECHARGES
    }
}
