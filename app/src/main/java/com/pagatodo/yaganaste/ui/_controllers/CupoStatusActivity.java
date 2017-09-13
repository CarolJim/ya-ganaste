package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.cupo.fragments.StatusRegisterCupoFragment;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;


/**
 * Created by Dell on 25/07/2017.
 */

public class CupoStatusActivity extends LoaderActivity {

    public final static String EVENT_GO_STATUS_REGISTER_CUPO        = "EVENT_GO_STATUS_REGISTER_CUPO";
    public final static String EVENT_GO_STATUS_REGISTER_CUPO_BACK   = "EVENT_GO_STATUS_REGISTER_CUPO_BACK";


    private String action = "";
    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment_conainer);

        if(getIntent() != null && getIntent().getExtras() != null){
            action = getIntent().getExtras().getString(SELECTION, EVENT_GO_STATUS_REGISTER_CUPO);
            onEvent(action, null);
        }
        //todo poner flujo adecuado
        else{
            onEvent(EVENT_GO_STATUS_REGISTER_CUPO, null);
        }



    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_STATUS_REGISTER_CUPO:
                loadFragment(StatusRegisterCupoFragment.newInstance(), Direction.FORDWARD, false);
                break;

        }
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }
}
