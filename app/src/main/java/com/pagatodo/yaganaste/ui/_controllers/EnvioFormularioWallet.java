package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.SendWalletFragment;

import static com.pagatodo.yaganaste.ui._controllers.TabActivity.MONEY_SEND;
import static com.pagatodo.yaganaste.ui._controllers.TabActivity.RESUL_FAVORITES;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_BACK_PRESS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_OK_CLOSE;

public class EnvioFormularioWallet extends LoaderActivity implements OnEventListener {

    final public static String EVENT_GO_ENVIOS = "EVENT_GO_ENVIOS";

    private Preferencias pref;
    private Envios payment;
    private Favoritos favoritos;
    ImageView imgOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_fragment_container);
        payment = (Envios) getIntent().getExtras().get("pagoItem");
        favoritos = (Favoritos) getIntent().getExtras().get("favoritoItem");
        showToolbarOk(false);
        imgOk = (ImageView) findViewById(R.id.btn_ok);
        pref = App.getInstance().getPrefs();
        onEvent(EVENT_GO_ENVIOS, null);
        imgOk.setOnClickListener(view -> ((SendWalletFragment) getCurrentFragment()).continueSendPayment());
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_ENVIOS:
                loadFragment(SendWalletFragment.newInstance(payment, favoritos), Direction.FORDWARD, false);
                // loadFragment(PaymentAuthorizeFragmentWallwt.newInstance(), Direction.FORDWARD, false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_OK_CLOSE || resultCode == RESULT_CODE_BACK_PRESS) {
            //finish();
        } else {
            setResult(MONEY_SEND);
            finish();
        }
    }
}
