package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.GiroComercio;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.interfaces.IAccountView2;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_FAMILIAR;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosNegocio extends GenericFragment implements View.OnClickListener, ValidationForms, IAccountView2 {

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
    private String giro = "";
    private String telefono = "";
    private boolean respuestaFamiliares;

    private List<GiroComercio> girosComercioComplete;
    private List<GiroComercio> girosComercio;
    // private List<GiroComercio> subGirosComercio;
    private BussinesLineSpinnerAdapter giroArrayAdapter;
    // private GiroArrayAdapter subGiroArrayAdapter;
    private int giroSelected;


    public DatosNegocio() {
    }

    public static DatosNegocio newInstance() {
        DatosNegocio fragmentRegister = new DatosNegocio();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_datos_negocio, container, false);
        initValues();
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnBackBussinesInfo.setOnClickListener(this);
        btnNextBussinesInfo.setOnClickListener(this);
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(), R.layout.spinner_layout, girosComercio);
        spinnerBussineLine.setAdapter(giroArrayAdapter);
        setCurrentData();// Seteamos datos si hay registro en proceso.

        /*spnAreaBussinnes.setAdapter(giroArrayAdapter);
        spnAreaBussinnes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                giroSelected = giroArrayAdapter.getGiroId(position);
                */
                /*generateSubgirosArray(giroSelected);
                subGiroArrayAdapter = new GiroArrayAdapter(getActivity(), R.layout.spinner_layout, subGirosComercio, SUBGIRO);
                subGiroArrayAdapter.notifyDataSetChanged();
                spinnerRegisterCommerceSubGiro.setAdapter(subGiroArrayAdapter);*/

            /*}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
    }

    private void initValues() {
        girosComercioComplete = Utils.getGirosArray(getActivity());
        girosComercio = new ArrayList<>();
        ArrayList<Integer> mGirosIds = new ArrayList<>();
        // subGirosComercio = new ArrayList<>();
        GiroComercio giroHint = new GiroComercio();
        giroHint.setnGiro("Giro Comercial");
        girosComercio.add(giroHint);
        for (GiroComercio giroComercio : girosComercioComplete) {
            if (!mGirosIds.contains(giroComercio.getIdGiro())) {
                mGirosIds.add(giroComercio.getIdGiro());
                girosComercio.add(giroComercio);
            }
        }
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

        if (giro.isEmpty()) {
            showValidationError(getString(R.string.datos_negocio_giro));
            return;
        }

        if (telefono.isEmpty()) {
            showValidationError(getString(R.string.datos_negocio_telefono));
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
        registerAgent.setGiro(giroSelected);
        registerAgent.setTelefono(telefono);
        if(registerAgent.getCuestionario().size() > 0)
            registerAgent.getCuestionario().get(0).setValor(respuestaFamiliares);
        else
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_FAMILIAR, respuestaFamiliares));


        nextStepRegister(EVENT_GO_BUSSINES_ADDRESS, null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        nombre = editBussinesName.getText().toString().trim();
        giro = spinnerBussineLine.getSelectedItem().toString();
        telefono = editBussinesPhone.getText().toString().trim();
        respuestaFamiliares = radioBtnPublicServantYes.isChecked();
    }

    private void setCurrentData() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        editBussinesName.setText(registerAgent.getNombre());
        editBussinesPhone.setText(registerAgent.getTelefono());
        spinnerBussineLine.setSelection(0); // TODO restablecer este valor
        if(registerAgent.getCuestionario().size() > 0){
            for(CuestionarioEntity q : registerAgent.getCuestionario()){
                if(q.getPreguntaId() == PREGUNTA_FAMILIAR){
                    radioBtnPublicServantYes.setChecked(q.isValor());
                    radioBtnPublicServantNo.setChecked(!q.isValor());
                }
            }
        }

    }

    @Override
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(), getActivity());
    }
}

