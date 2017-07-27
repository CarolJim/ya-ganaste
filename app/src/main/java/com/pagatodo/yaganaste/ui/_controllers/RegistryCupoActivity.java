package com.pagatodo.yaganaste.ui._controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoComprobantesFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoCuentanosMasFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoDomicilioPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoInicioFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaFamiliarFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaProveedorFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;

/**
 * Created by Jordan on 25/07/2017.
 */

public class RegistryCupoActivity extends LoaderActivity implements CupoActivityManager {

    public final static String EVENT_GO_CUPO_DOMICILIO_PERSONAL = "EVENT_GO_CUPO_DOMICILIO_PERSONAL";
    public final static String EVENT_GO_CUPO_COMPROBANTES = "EVENT_GO_CUPO_COMPROBANTES";
    public final static String EVENT_GO_CUPO_CUENTAME_MAS = "EVENT_GO_CUPO_CUENTAME_MAS";
    public final static String EVENT_GO_CUPO_REFERENCIA_FAMILIAR = "EVENT_GO_CUPO_REFERENCIA_FAMILIAR";
    public final static String EVENT_GO_CUPO_REFERENCIA_PERSONAL = "EVENT_GO_CUPO_REFERENCIA_PERSONAL";
    public final static String EVENT_GO_CUPO_REFERENCIA_PROVEEDOR = "EVENT_GO_CUPO_REFERENCIA_PROVEEDOR";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_conainer);
        initViews();
    }

    private void initViews() {
        changeToolbarVisibility(false);
        loadFragment(CupoInicioFragment.newInstance(), Direction.FORDWARD, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);

    }

    @Override
    public void onBtnBackPress() {
        onBackPressed();
    }

    @Override
    public void callEvent(String event, Object data) {
        switch (event) {
            case EVENT_GO_CUPO_DOMICILIO_PERSONAL:
                loadFragment(CupoDomicilioPersonalFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_COMPROBANTES:
                loadFragment(CupoComprobantesFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_CUENTAME_MAS:
                loadFragment(CupoCuentanosMasFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_FAMILIAR:
                loadFragment(CupoReferenciaFamiliarFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_PERSONAL:
                loadFragment(CupoReferenciaPersonalFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_PROVEEDOR:
                loadFragment(CupoReferenciaProveedorFragment.newInstance(), Direction.FORDWARD, true);
                break;
            default:
                onEvent(event, data);
                break;
        }
    }

    @Override
    public void hideToolBar() {
        changeToolbarVisibility(false);
    }

    @Override
    public void showToolBar() {
        changeToolbarVisibility(true);
    }

    public CupoActivityManager getCupoActivityManager() {
        return this;
    }
}