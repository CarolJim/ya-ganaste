package com.pagatodo.yaganaste.ui.adquirente;


import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.GiroComercio;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS;
import static com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter.TYPE.SUBTITLE;
import static com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter.TYPE.TITLE;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_FAMILIAR;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosNegocio extends GenericFragment implements View.OnClickListener, ValidationForms, INavigationView, AdapterView.OnItemSelectedListener {

    private View rootview;
    @BindView(R.id.editBussinesName)
    CustomValidationEditText editBussinesName;
    @BindView(R.id.spinnerBussineLine)
    Spinner spinnerBussineLine;
    @BindView(R.id.spinnerSubBussineLine)
    Spinner spinnerSubBussineLine;

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
    private long id_giro = 0 ;
    private String telefono = "";
    private boolean respuestaFamiliares;

    private List<GiroComercio> girosComercio;
    private List<GiroComercio> subGiros;
    private SparseArray<List<GiroComercio>> girosSubgiros;

    private BussinesLineSpinnerAdapter giroArrayAdapter;
    private BussinesLineSpinnerAdapter subGiroArrayAdapter;


    public static DatosNegocio newInstance() {
        DatosNegocio fragmentRegister = new DatosNegocio();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
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
        subGiros = new ArrayList<>();
        subGiroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(), R.layout.spinner_layout, subGiros, SUBTITLE);
        spinnerBussineLine.setOnItemSelectedListener(this);
        btnBackBussinesInfo.setOnClickListener(this);
        btnNextBussinesInfo.setOnClickListener(this);
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(), R.layout.spinner_layout, girosComercio, TITLE);
        spinnerBussineLine.setAdapter(giroArrayAdapter);
        spinnerSubBussineLine.setAdapter(subGiroArrayAdapter);
        radioPublicServant.check(R.id.radioBtnPublicServantNo);
    }

    @Override
    public void onStart() {
        super.onStart();
        setCurrentData();// Seteamos datos si hay registro en proceso.
    }
    private void initValues() {
        List<GiroComercio> girosComercioComplete = Utils.getGirosArray(getActivity());
        girosComercio = new ArrayList<>();
        ArrayList<Integer> mGirosIds = new ArrayList<>();
        GiroComercio giroHint = new GiroComercio();
        giroHint.setnGiro("Giro Comercial");
        giroHint.setnSubgiro("Selecciona el SubGiro");
        giroHint.setIdGiro(-1);
        giroHint.setIdSubgiro(-1);
        girosComercio.add(giroHint);
        girosSubgiros = new SparseArray<>();

        List<GiroComercio> subGirosToAdd = new ArrayList<>();
        subGirosToAdd.add(giroHint);
        girosSubgiros.append(giroHint.getIdGiro(), subGirosToAdd);

        for (GiroComercio giroComercio : girosComercioComplete) {
            subGirosToAdd = girosSubgiros.get(giroComercio.getIdGiro());
            if (subGirosToAdd == null) {
                subGirosToAdd = new ArrayList<>();
                girosSubgiros.append(giroComercio.getIdGiro(), subGirosToAdd);
            }
            subGirosToAdd.add(giroComercio);
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

        if (giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()).getIdGiro() == -1) {
            showValidationError(getString(R.string.datos_negocio_giro));
            return;
        }

        if (subGiroArrayAdapter.getItem(spinnerSubBussineLine.getSelectedItemPosition()).getIdGiro() == -1) {
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
        registerAgent.setGiro(subGiroArrayAdapter.getItem(spinnerSubBussineLine.getSelectedItemPosition()));
        registerAgent.setTelefono(telefono);
        if(registerAgent.getCuestionario().size() > 0)
            registerAgent.getCuestionario().get(0).setValor(respuestaFamiliares);
        else
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_FAMILIAR, respuestaFamiliares));


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
        onItemSelected(null, null, giroArrayAdapter.getItemPosition(registerAgent.getGiro()), 0);
        spinnerSubBussineLine.setSelection(subGiroArrayAdapter.getItemPosition(registerAgent.getGiro()));

        if (!registerAgent.getCuestionario().isEmpty()) {
            for (CuestionarioEntity q : registerAgent.getCuestionario()) {
                if (q.getPreguntaId() == PREGUNTA_FAMILIAR) {
                    radioBtnPublicServantYes.setChecked(q.isValor());
                    radioBtnPublicServantNo.setChecked(!q.isValor());
                }
            }
        }
        spinnerBussineLine.setOnItemSelectedListener(this);

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

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(), getActivity());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subGiros.clear();
        subGiros.addAll(girosSubgiros.get(giroArrayAdapter.getGiroId(position)));
        subGiroArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //No-Op
    }
}

