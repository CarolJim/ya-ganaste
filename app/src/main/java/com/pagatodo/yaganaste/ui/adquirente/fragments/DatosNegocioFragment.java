package com.pagatodo.yaganaste.ui.adquirente.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;

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
public class DatosNegocioFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms, INavigationView<Object, ErrorObject>, IDatosNegView<ErrorObject>,
        DialogDoubleActions, IOnSpinnerClick {

    private static final String GIROS = "1";
    @BindView(R.id.editBussinesName)
    CustomValidationEditText editBussinesName;
    @BindView(R.id.spinnerBussineLine)
    Spinner spinnerBussineLine;
    @BindView(R.id.editBussinesPhone)
    CustomValidationEditText editBussinesPhone;
    @BindView(R.id.btnBackBussinesInfo)
    Button btnBackBussinesInfo;
    @BindView(R.id.btnNextBussinesInfo)
    Button btnNextBussinesInfo;
    @BindView(R.id.errorBussinesNameMessage)
    ErrorMessage errorName;
    @BindView(R.id.errorBussineLineMessage)
    ErrorMessage errorBussineLine;
    @BindView(R.id.errorBussinesPhoneMessage)
    ErrorMessage errorPhone;
    private View rootview;
    private String nombre = "";
    private String telefono = "";

    private List<SubGiro> girosComercio;


    private BussinesLineSpinnerAdapter giroArrayAdapter;
    private DatosNegocioPresenter datosNegocioPresenter;


    public static DatosNegocioFragment newInstance(List<SubGiro> girosComercio) {
        DatosNegocioFragment fragmentRegister = new DatosNegocioFragment();
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
            giroHint.setSubgiro(getString(R.string.giro_commerce_spinner));
            giroHint.setIdSubgiro(-1);
            giroHint.setIdSubgiro(-1);
            girosComercio.add(giroHint);
        }


        btnBackBussinesInfo.setOnClickListener(this);
        btnNextBussinesInfo.setOnClickListener(this);
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(),
                R.layout.spinner_layout, girosComercio, this);
        spinnerBussineLine.setAdapter(giroArrayAdapter);

        spinnerBussineLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }
        });

        setValidationRules();
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
        editBussinesName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editBussinesName.getId());
                    editBussinesName.imageViewIsGone(true);
                } else {
                    if (editBussinesName.getText().isEmpty()) {
                        showValidationError(editBussinesName.getId(), getString(R.string.datos_negocio_nombre));
                        editBussinesName.setIsInvalid();
                    } else {
                        hideValidationError(editBussinesName.getId());
                        editBussinesName.setIsValid();
                    }
                }
            }
        });

        editBussinesName.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editBussinesName.getId());
                editBussinesName.imageViewIsGone(true);
            }
        });

        editBussinesPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editBussinesPhone.getId());
                    editBussinesPhone.imageViewIsGone(true);
                } else {
                    if (editBussinesPhone.getText().isEmpty()) {
                        showValidationError(editBussinesPhone.getId(), getString(R.string.datos_negocio_telefono));
                        editBussinesPhone.setIsInvalid();
                    } else if (!editBussinesPhone.isValidText()) {
                        showValidationError(editBussinesPhone.getId(), getString(R.string.datos_telefono_incorrecto));
                        editBussinesPhone.setIsInvalid();
                    } else if (editBussinesPhone.isValidText()) {
                        hideValidationError(editBussinesPhone.getId());
                        editBussinesPhone.setIsValid();
                    }
                }
            }
        });

        editBussinesPhone.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editBussinesPhone.getId());
                editBussinesPhone.imageViewIsGone(true);
            }
        });
    }

    @Override
    public void validateForm() {

        getDataForm();

        boolean isValid = true;

        if (nombre.isEmpty()) {
            showValidationError(editBussinesName.getId(), getString(R.string.datos_negocio_nombre));
            editBussinesName.setIsInvalid();
            isValid = false;
        }

        if (giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()).getIdSubgiro() == -1) {
            showValidationError(spinnerBussineLine.getId(), getString(R.string.datos_negocio_giro));
            isValid = false;
        }

        if (!editBussinesPhone.isValidText()) {
            showValidationError(editBussinesPhone.getId(), getString(R.string.datos_telefono_incorrecto));
            editBussinesPhone.setIsInvalid();
            isValid = false;
        }

        if (telefono.isEmpty()) {
            showValidationError(editBussinesPhone.getId(), getString(R.string.datos_negocio_telefono));
            editBussinesPhone.setIsInvalid();
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }
    }


    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.editBussinesName:
                errorName.setMessageText(error.toString());
                break;
            case R.id.spinnerBussineLine:
                errorBussineLine.setMessageText(error.toString());
                break;
            case R.id.editBussinesPhone:
                errorPhone.setMessageText(error.toString());
                break;
        }

        /*ErrorObject errorObject = new ErrorObject(error.toString(), null);
        errorObject.setHasConfirm(true);
        onEventListener.onEvent(EVENT_SHOW_ERROR, errorObject);*/
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.editBussinesName:
                errorName.setVisibilityImageError(false);
                break;
            case R.id.spinnerBussineLine:
                errorBussineLine.setVisibilityImageError(false);
                break;
            case R.id.editBussinesPhone:
                errorPhone.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {
        /*Guardamos datos en Singleton de registro.*/
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        registerAgent.setNombre(nombre);
        registerAgent.setTelefono(telefono);
        registerAgent.setGiro(giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()));

        onEventListener.onEvent(EVENT_SET_BUSINESS_LIST, girosComercio);

        nextScreen(EVENT_GO_BUSSINES_ADDRESS, null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        nombre = editBussinesName.getText();
        telefono = editBussinesPhone.getText();
    }

    private void setCurrentData() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        if (registerAgent.getNombre().isEmpty()) {
            return;
        }
        //spinnerBussineLine.setOnItemSelectedListener(null);

        editBussinesName.setText(registerAgent.getNombre());
        editBussinesPhone.setText(registerAgent.getTelefono());
        spinnerBussineLine.setSelection(giroArrayAdapter.getItemPosition(registerAgent.getGiro()));

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

    @Override
    public void onSpinnerClick() {
        hideValidationError(spinnerBussineLine.getId());
        editBussinesName.clearFocus();
        editBussinesPhone.clearFocus();
        spinnerBussineLine.requestFocus();
    }
}

