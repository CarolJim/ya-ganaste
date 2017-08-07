package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoComprobantesFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoCuentanosMasFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoDomicilioPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoInicioFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaFamiliarFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaProveedorFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.maintabs.fragments.RecargasFormFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.ServiciosFormFragment;

import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_COMPLETE;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.ADQ_REVISION;
import static com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment.COMPLETE_MESSAGES.CUPO_REVISION;
import static com.pagatodo.yaganaste.ui.cupo.fragments.CupoComprobantesFragment.REQUEST_TAKE_PHOTO;
import static com.pagatodo.yaganaste.ui.cupo.fragments.CupoComprobantesFragment.SELECT_FILE_PHOTO;
import static com.pagatodo.yaganaste.utils.Constants.BARCODE_READER_REQUEST_CODE;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT_FAMILIAR;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT_PERSONAL;
import static com.pagatodo.yaganaste.utils.Constants.CONTACTS_CONTRACT_PROVEEDOR;

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
    public final static String EVENT_GO_CUPO_COMPLETE = "EVENT_GO_CUPO_COMPLETE";
    public final static String EVENT_GO_CM_DOCUMENTOS = "EVENT_GO_CM_DOCUMENTOS";


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
        Fragment currentFragment = getCurrentFragment();
        if (!(currentFragment instanceof RegisterCompleteFragment) ) {
            super.onBackPressed();
        }
    }

    @Override
    public void onEvent(String event, Object data) {
        switch (event) {
            case EVENT_GO_CM_DOCUMENTOS:
                Log.e("Test", "A la nueva venta");
                break;
            default:
                super.onEvent(event, data);
        }

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

            case EVENT_GO_CUPO_CUENTAME_MAS:
                loadFragment(CupoCuentanosMasFragment.newInstance(), Direction.FORDWARD, true);
                //loadFragment(CupoComprobantesFragment.newInstance(), Direction.FORDWARD, true);
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
            case EVENT_GO_CUPO_COMPROBANTES:
                loadFragment(CupoComprobantesFragment.newInstance(), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_COMPLETE:
                //loadFragment(RegisterCompleteFragment.newInstance(CUPO_REVISION), Direction.FORDWARD, false);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        if (fragmentList != null) {
            if (requestCode == CONTACTS_CONTRACT_FAMILIAR) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof CupoReferenciaFamiliarFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }

            else if (requestCode == CONTACTS_CONTRACT_PERSONAL) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof CupoReferenciaPersonalFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }

            else if (requestCode == CONTACTS_CONTRACT_PROVEEDOR) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof CupoReferenciaProveedorFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }

            else if (requestCode == REQUEST_TAKE_PHOTO  || requestCode == SELECT_FILE_PHOTO  ) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof CupoComprobantesFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }

        }

    }
}