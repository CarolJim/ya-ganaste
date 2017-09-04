package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoSolicitud;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.account.register.RegisterCompleteFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoComprobantesFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoCuentanosMasFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoDomicilioPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoInicioFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReenviarReferenciasFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaFamiliarFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoReferenciaProveedorFragment;
import com.pagatodo.yaganaste.ui.cupo.fragments.StatusRegisterCupoFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CupoDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.maintabs.fragments.RecargasFormFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.ServiciosFormFragment;

import java.util.ArrayList;
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
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.CUPO_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.INCORRECT_FORMAT;

/**
 * Created by Jordan on 25/07/2017.
 */

public class RegistryCupoActivity extends LoaderActivity implements CupoActivityManager {

    public final static String EVENT_GO_CUPO_DOMICILIO_PERSONAL = "EVENT_GO_CUPO_DOMICILIO_PERSONAL";
    public final static String EVENT_GO_CUPO_COMPROBANTES = "EVENT_GO_CUPO_COMPROBANTES";
    public final static String EVENT_GO_CUPO_REENVIAR_COMPROBANTES = "EVENT_GO_CUPO_REENVIAR_COMPROBANTES";
    public final static String EVENT_GO_CUPO_REENVIAR_REFERENCIAS = "EVENT_GO_CUPO_REENVIAR_REFERENCIAS";
    public final static String EVENT_GO_CUPO_CUENTAME_MAS = "EVENT_GO_CUPO_CUENTAME_MAS";
    public final static String EVENT_GO_CUPO_REFERENCIA_FAMILIAR = "EVENT_GO_CUPO_REFERENCIA_FAMILIAR";
    public final static String EVENT_GO_CUPO_REFERENCIA_FAMILIAR_REENVIAR = "EVENT_GO_CUPO_REFERENCIA_FAMILIAR_REENVIAR";
    public final static String EVENT_GO_CUPO_REFERENCIA_PERSONAL = "EVENT_GO_CUPO_REFERENCIA_PERSONAL";
    public final static String EVENT_GO_CUPO_REFERENCIA_PERSONAL_REENVIAR = "EVENT_GO_CUPO_REFERENCIA_PERSONAL_REENVIAR";
    public final static String EVENT_GO_CUPO_REFERENCIA_PROVEEDOR = "EVENT_GO_CUPO_REFERENCIA_PROVEEDOR";
    public final static String EVENT_GO_CUPO_REFERENCIA_PROVEEDOR_REENVIAR = "EVENT_GO_CUPO_REFERENCIA_PROVEEDOR_REENVIAR";
    public final static String EVENT_GO_CUPO_COMPLETE = "EVENT_GO_CUPO_COMPLETE";
    public final static String EVENT_GO_CM_DOCUMENTOS = "EVENT_GO_CM_DOCUMENTOS";
    public final static String EVENT_GO_CUPO_INICIO = "EVENT_GO_CM_DOCUMENTOS";
    public final static String EVENT_GO_CUPO_ESTATUS_REGISTRO= "EVENT_GO_CUPO_ESTATUS_REGISTRO";

    public final static String CUPO_PASO  = "CUPO_PASO";
    public final static String CUPO_PASO_REGISTRO_ENVIADO     = "CUPO_PASO_REGISTRO_ENVIADO";
    public final static String CUPO_PASO_DOCUMENTOS_ENVIADOS  = "CUPO_PASO_DOCUMENTOS_ENVIADOS";

    public final static int ESTADO_ENVIO_DOCUMENTOS    = 0;
    public final static int ESTADO_REENVIAR_DOCUMENTOS = 1;

    public final static int VALOR_DEFAULT_ID_RELACION_PROVEEDOR = 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_conainer);
        initViews();
    }


    @Override
    protected void onPause() {
        clearSingletonCupo();
        super.onPause();
    }

    public static void clearSingletonCupo () {
        RegisterCupo registerCupo = RegisterCupo.getInstance();
        registerCupo.setEstadoCivil("");
        registerCupo.setIdEstadoCivil(0);
        registerCupo.setHijos("");
        registerCupo.setIdHijos(0);
        registerCupo.setCreditoBancario(false);
        registerCupo.setCreditoAutomotriz(false);
        registerCupo.setCreditoBancario(false);
        registerCupo.setNumeroTarjeta("");

        registerCupo.setFamiliarNombre("");
        registerCupo.setFamiliarApellidoPaterno("");
        registerCupo.setFamiliarApellidoMaterno("");
        registerCupo.setFamiliarTelefono("");
        registerCupo.setFamiliarRelacion("");
        registerCupo.setFamiliarIdRelacion(0);

        registerCupo.setPersonalNombre("");
        registerCupo.setPersonalApellidoPaterno("");
        registerCupo.setPersonalApellidoMaterno("");
        registerCupo.setPersonalTelefono("");
        registerCupo.setPersonalRelacion("");
        registerCupo.setPersonalIdRelacion(0);

        registerCupo.setProveedorNombre("");
        registerCupo.setProveedorApellidoPaterno("");
        registerCupo.setProveedorApellidoMaterno("");
        registerCupo.setProveedorTelefono("");
        registerCupo.setProveedorProductoServicio("");

        registerCupo.setCalle("");
        registerCupo.setNumExterior("");
        registerCupo.setNumInterior("");
        registerCupo.setCodigoPostal("");
        registerCupo.setEstadoDomicilio("");
        registerCupo.setColonia("");
        registerCupo.setIdColonia("");
        registerCupo.setNacionalidad("");
        registerCupo.setLugarNacimiento("");
        registerCupo.setIdEstadoNacimineto("");

    }

    private void initViews() {
        changeToolbarVisibility(false);



        if (App.getInstance().getPrefs().loadData(CUPO_PASO).equals(CUPO_PASO_DOCUMENTOS_ENVIADOS) || App.getInstance().getPrefs().loadData(CUPO_PASO).equals(CUPO_PASO_REGISTRO_ENVIADO))  {
            if (App.getInstance().getPrefs().loadData(CUPO_PASO).equals(CUPO_PASO_DOCUMENTOS_ENVIADOS)) {
                loadFragment(StatusRegisterCupoFragment.newInstance(), Direction.FORDWARD, false);
            } else if (App.getInstance().getPrefs().loadData(CUPO_PASO).equals(CUPO_PASO_REGISTRO_ENVIADO)) {
                loadFragment(CupoComprobantesFragment.newInstance(ESTADO_ENVIO_DOCUMENTOS), Direction.FORDWARD, false);
            }
        } else {
            int sesionStatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
            if (sesionStatus == IdEstatus.I14.getId()) {
                App.getInstance().getPrefs().saveData( CUPO_PASO , CUPO_PASO_REGISTRO_ENVIADO);
                loadFragment(CupoComprobantesFragment.newInstance(ESTADO_ENVIO_DOCUMENTOS), Direction.FORDWARD, false);
            } else if (sesionStatus == IdEstatus.I15.getId()|| sesionStatus == IdEstatus.I16.getId() ||  sesionStatus == IdEstatus.I17.getId()) {
                App.getInstance().getPrefs().saveData( CUPO_PASO , CUPO_PASO_DOCUMENTOS_ENVIADOS);
                loadFragment(StatusRegisterCupoFragment.newInstance(), Direction.FORDWARD, false);
            } else {
                loadFragment(CupoInicioFragment.newInstance(), Direction.FORDWARD, false);
            }
        }


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
                finish();
                Intent intent = new Intent(this, TabActivity.class);
                startActivity(intent);

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
                if (App.getInstance().getPrefs().loadData(CUPO_PASO).equals("")) {
                    loadFragment(CupoCuentanosMasFragment.newInstance(), Direction.FORDWARD, true);
                } else if ( App.getInstance().getPrefs().loadData(CUPO_PASO).equals(CUPO_PASO_REGISTRO_ENVIADO) ) {
                    loadFragment(CupoComprobantesFragment.newInstance(ESTADO_ENVIO_DOCUMENTOS), Direction.FORDWARD, true);
                } else if ( App.getInstance().getPrefs().loadData(CUPO_PASO).equals(CUPO_PASO_DOCUMENTOS_ENVIADOS) ) {
                    loadFragment(StatusRegisterCupoFragment.newInstance(), Direction.FORDWARD, true);
                }
                break;
            case EVENT_GO_CUPO_REFERENCIA_FAMILIAR:
                loadFragment(CupoReferenciaFamiliarFragment.newInstance(false), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_PERSONAL:
                loadFragment(CupoReferenciaPersonalFragment.newInstance(false), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_PROVEEDOR:
                loadFragment(CupoReferenciaProveedorFragment.newInstance(false), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_COMPROBANTES:
                loadFragment(CupoComprobantesFragment.newInstance(ESTADO_ENVIO_DOCUMENTOS), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CUPO_REENVIAR_COMPROBANTES:
                loadFragment(CupoComprobantesFragment.newInstance(ESTADO_REENVIAR_DOCUMENTOS), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CUPO_REENVIAR_REFERENCIAS:
                loadFragment(CupoReenviarReferenciasFragment.newInstance((DataEstadoSolicitud) data), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_COMPLETE:
                loadFragment(RegisterCompleteFragment.newInstance(CUPO_REVISION), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CUPO_INICIO:
                loadFragment(CupoInicioFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CUPO_ESTATUS_REGISTRO:
                loadFragment(StatusRegisterCupoFragment.newInstance(), Direction.FORDWARD, false);
                break;
            case EVENT_GO_CUPO_REFERENCIA_FAMILIAR_REENVIAR:
                loadFragment(CupoReferenciaFamiliarFragment.newInstance(true), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_PERSONAL_REENVIAR:
                loadFragment(CupoReferenciaPersonalFragment.newInstance(true), Direction.FORDWARD, true);
                break;
            case EVENT_GO_CUPO_REFERENCIA_PROVEEDOR_REENVIAR:
                loadFragment(CupoReferenciaProveedorFragment.newInstance(true), Direction.FORDWARD, true);
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