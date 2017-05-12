package com.pagatodo.yaganaste.ui.adquirente;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_SET_BUSINESS_LIST;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_FAMILIAR;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosNegocio extends GenericFragment implements View.OnClickListener,
        ValidationForms, INavigationView<Object, ErrorObject>, IDatosNegView<ErrorObject>,DialogDoubleActions {

    private static final String GIROS = "1";

    private View rootview;
    @BindView(R.id.editBussinesName)
    CustomValidationEditText editBussinesName;
    @BindView(R.id.spinnerBussineLine)
    Spinner spinnerBussineLine;

    @BindView(R.id.editBussinesPhone)
    CustomValidationEditText editBussinesPhone;
    @BindView(R.id.radioPublicServant)
    RadioGroup radioPublicServant;
    @BindView(R.id.radioBtnPublicServantNo)
    RadioButton radioBtnPublicServantNo;
    @BindView(R.id.radioBtnPublicServantYes)
    RadioButton radioBtnPublicServantYes;
    @BindView(R.id.btnBackBussinesInfo)
    Button btnBackBussinesInfo;
    @BindView(R.id.btnNextBussinesInfo)
    Button btnNextBussinesInfo;

    private String nombre = "";
    private String telefono = "";
    private boolean respuestaFamiliares;

    private List<SubGiro> girosComercio;


    private BussinesLineSpinnerAdapter giroArrayAdapter;
    private DatosNegocioPresenter datosNegocioPresenter;


    public static DatosNegocio newInstance(List<SubGiro> girosComercio) {
        DatosNegocio fragmentRegister = new DatosNegocio();
        Bundle args = new Bundle();
        args.putSerializable(GIROS, (Serializable) girosComercio);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.datosNegocioPresenter = new DatosNegocioPresenter(getActivity(), this);
        Bundle args = getArguments();
        if (args != null) {
            Serializable subgiros = args.getSerializable(GIROS);
            if (subgiros != null) {
                this.girosComercio = (List<SubGiro>) subgiros;
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_datos_negocio, container, false);
        initViews();
        initValues();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        if (girosComercio == null) {
            girosComercio = new ArrayList<>();

            SubGiro giroHint = new SubGiro();
            giroHint.setSubgiro("Giro Comercial");
            giroHint.setIdSubgiro(-1);
            giroHint.setIdSubgiro(-1);
            girosComercio.add(giroHint);
        }


        btnBackBussinesInfo.setOnClickListener(this);
        btnNextBussinesInfo.setOnClickListener(this);
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(), R.layout.spinner_layout, girosComercio);
        spinnerBussineLine.setAdapter(giroArrayAdapter);
        radioPublicServant.check(R.id.radioBtnPublicServantNo);
    }

    private void initValues() {

        if (girosComercio.size() == 1) {
            datosNegocioPresenter.getGiros();
        } else {
            setCurrentData();
        }
    }

    @Override
    public void setGiros(List<SubGiro> giros) {
        girosComercio.addAll(giros);
        setCurrentData();// Seteamos datos si hay registro en proceso.
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnBackBussinesInfo:
                RegisterAgent.resetRegisterAgent();
                getActivity().finish();
                break;
            case R.id.btnNextBussinesInfo:
                validateForm();
                break;
            default:
                break;
        }
    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

        getDataForm();

        if (nombre.isEmpty()) {
            showValidationError(getString(R.string.datos_negocio_nombre));
            return;
        }

        if (giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()).getIdSubgiro() == -1) {
            showValidationError(getString(R.string.datos_negocio_giro));
            return;
        }

        if (telefono.isEmpty()) {
            showValidationError(getString(R.string.datos_negocio_telefono));
            return;
        }

        if (!editBussinesPhone.isValidText()) {
            showValidationError(getString(R.string.datos_telefono_incorrecto));
            return;
        }

        if (!radioBtnPublicServantYes.isChecked() && !radioBtnPublicServantNo.isChecked()) {
            showValidationError(getString(R.string.datos_negocio_preguntas));
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(), getActivity());
    }

    @Override
    public void onValidationSuccess() {
        /*Guardamos datos en Singleton de registro.*/
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        registerAgent.setNombre(nombre);
        registerAgent.setTelefono(telefono);
        registerAgent.setGiro(giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()));
        if(registerAgent.getCuestionario().size() > 0)
            registerAgent.getCuestionario().get(0).setValor(respuestaFamiliares);
        else
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_FAMILIAR, respuestaFamiliares));

        onEventListener.onEvent(EVENT_SET_BUSINESS_LIST, girosComercio);

        nextScreen(EVENT_GO_BUSSINES_ADDRESS, null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        nombre = editBussinesName.getText();

        telefono = editBussinesPhone.getText();
        respuestaFamiliares = radioBtnPublicServantYes.isChecked();
    }

    private void setCurrentData() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        if (registerAgent.getNombre().isEmpty()) {
            return;
        }
        spinnerBussineLine.setOnItemSelectedListener(null);

        editBussinesName.setText(registerAgent.getNombre());
        editBussinesPhone.setText(registerAgent.getTelefono());
        spinnerBussineLine.setSelection(giroArrayAdapter.getItemPosition(registerAgent.getGiro()));

        if (!registerAgent.getCuestionario().isEmpty()) {
            for (CuestionarioEntity q : registerAgent.getCuestionario()) {
                if (q.getPreguntaId() == PREGUNTA_FAMILIAR) {
                    radioBtnPublicServantYes.setChecked(q.isValor());
                    radioBtnPublicServantNo.setChecked(!q.isValor());
                }
            }
        }

    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(ErrorObject error) {
        error.setHasConfirm(true);
        error.setHasCancel(false);
        error.setErrorActions(this);
        onEventListener.onEvent(EVENT_SHOW_ERROR, error);
    }

    @Override
    public void actionConfirm(Object... params) {
        getActivity().finish();
    }

    @Override
    public void actionCancel(Object... params) {
        //No-Op
    }
}

