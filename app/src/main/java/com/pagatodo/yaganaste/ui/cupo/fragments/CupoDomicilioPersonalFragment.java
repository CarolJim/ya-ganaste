package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.ui.cupo.Colonias;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CupoDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewDomicilioPersonal;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity.CUPO_PASO;
import static com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity.CUPO_PASO_REGISTRO_ENVIADO;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.DomicilioActualFragment.MIN_LENGHT_VALIDATION_CP;
import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.CUPO_PROCESS;

/**
 * Created by Jordan on 25/07/2017.
 */

public class CupoDomicilioPersonalFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IViewDomicilioPersonal<ErrorObject> , IOnSpinnerClick, RadioGroup.OnCheckedChangeListener {

    protected View rootview;
    private CupoActivityManager cupoActivityManager;

    private String TAG = getClass().getSimpleName();



    // Campos de Vista
    @BindView(R.id.btnNextBussinesAddress)
    Button btnNextBussinesAddress;
    @BindView(R.id.btnBackBussinesAddress)
    Button btnBackBussinesAddress;
    @BindView(R.id.textIsBussinesAddress)
    StyleTextView textIsBussinesAddress;

    // Datos Obtenidos de los campos
    private List<ColoniasResponse> listaColonias;
    private String estadoDomicilio = "";
    private List<String> coloniasNombre;
    private ArrayAdapter<String> adapterColonia;
    private String calle = "";
    private String numExt = "";
    private String numInt = "";
    private String codigoPostal = "";
    private String estado = "";
    private String colonia = "";
    private String Idcolonia = "";
    private boolean respuestaDomicilio;
    private String idEstado = "";

    // Campos a llenar
    @BindView(R.id.editBussinesStreet)
    CustomValidationEditText editBussinesStreet;
    @BindView(R.id.editBussinesExtNumber)
    CustomValidationEditText editBussinesExtNumber;
    @BindView(R.id.editBussinesIntNumber)
    CustomValidationEditText editBussinesIntNumber;
    @BindView(R.id.editBussinesZipCode)
    CustomValidationEditText editBussinesZipCode;
    @BindView(R.id.editBussinesState)
    CustomValidationEditText editBussinesState;
    @BindView(R.id.spBussinesColonia)
    Spinner spBussinesColonia;
    @BindView(R.id.radioIsBussinesAddress)
    RadioGroup radioIsBussinesAddress;

    @BindView(R.id.radioBtnIsBussinesAddressYes)
    RadioButton radioBtnIsBussinesAddressYes;
    @BindView(R.id.radioBtnIsBussinesAddressNo)
    RadioButton radioBtnIsBussinesAddressNo;

    // Mensajes de Error
    @BindView(R.id.errorBussinesStreetMessage)
    ErrorMessage errorStreet;
    @BindView(R.id.errorNumberAddressMessage)
    ErrorMessage errorNumberAddress;
    @BindView(R.id.errorBussinesZipCodeMessage)
    ErrorMessage errorZipCode;
    @BindView(R.id.errorBussinesColoniaMessage)
    ErrorMessage errorColonia;

    private ZipWatcher textWatcherZipCode;

    private CupoDomicilioPersonalPresenter presenter;

    private DataObtenerDomicilio domicilio;
    private String colonyToLoad;


    public static CupoDomicilioPersonalFragment newInstance() {
        CupoDomicilioPersonalFragment fragment = new CupoDomicilioPersonalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
        presenter = new CupoDomicilioPersonalPresenter(this, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_domicilio_negocio_cupo, container, false);
        initViews();
        return rootview;
    }


    @Override
    public void onResume() {
        String a = RegisterCupo.getInstance().getCalle();
        if (!a.equals("")) {
            cargarDatos();
        }
        super.onResume();
    }

    private void cargarDatos() {

        RegisterCupo registerCupo = RegisterCupo.getInstance();
        editBussinesStreet.setText(registerCupo.getCalle());
        editBussinesExtNumber.setText(registerCupo.getNumExterior());
        editBussinesIntNumber.setText(registerCupo.getNumInterior());
        editBussinesZipCode.setText(registerCupo.getCodigoPostal());
        editBussinesState.setText(registerCupo.getEstadoDomicilio());
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        textWatcherZipCode = new ZipWatcher();
        coloniasNombre = new ArrayList<>();
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, coloniasNombre, this);
        spBussinesColonia.setAdapter(adapterColonia);
        btnNextBussinesAddress.setOnClickListener(this);
        btnBackBussinesAddress.setOnClickListener(this);
        textIsBussinesAddress.setText(getResources().getString(R.string.cupo_domicilio));
        radioIsBussinesAddress.setOnCheckedChangeListener(this);
        radioBtnIsBussinesAddressYes.setChecked(true);

        setValidationRules();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextBussinesAddress:
                //cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_COMPROBANTES, null);
                validateForm();
                break;
            case R.id.btnBackBussinesAddress:
                RegisterCupo registerCupo = RegisterCupo.getInstance();
                registerCupo.setCalle("");
                registerCupo.setNumExterior("");
                registerCupo.setNumInterior("");
                registerCupo.setCodigoPostal("");
                registerCupo.setEstadoDomicilio("");
                registerCupo.setColonia("");
                registerCupo.setIdColonia("");

                cupoActivityManager.onBtnBackPress();
                break;
        }
    }

    @Override
    public void setValidationRules() {
        editBussinesZipCode.addCustomTextWatcher(textWatcherZipCode);

        editBussinesStreet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editBussinesStreet.getId());
                    editBussinesStreet.imageViewIsGone(true);
                } else {
                    if (editBussinesStreet.getText().isEmpty()) {
                        showValidationError(editBussinesStreet.getId(), getString(R.string.datos_domicilio_calle));
                        editBussinesStreet.setIsInvalid();
                    } else {
                        hideValidationError(editBussinesStreet.getId());
                        editBussinesStreet.setIsValid();
                    }
                }
            }
        });

        editBussinesStreet.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editBussinesStreet.getId());
                editBussinesStreet.imageViewIsGone(true);
            }
        });

        editBussinesExtNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editBussinesExtNumber.getId());
                    editBussinesExtNumber.imageViewIsGone(true);
                } else {
                    if (editBussinesExtNumber.getText().isEmpty()) {
                        showValidationError(editBussinesExtNumber.getId(), getString(R.string.datos_domicilio_num_ext));
                        editBussinesExtNumber.setIsInvalid();
                    } else {
                        hideValidationError(editBussinesExtNumber.getId());
                        editBussinesExtNumber.setIsValid();
                    }
                }
            }
        });

        editBussinesExtNumber.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editBussinesExtNumber.getId());
                editBussinesExtNumber.imageViewIsGone(true);
            }
        });

        editBussinesZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editBussinesZipCode.getId());
                    editBussinesZipCode.imageViewIsGone(true);
                } else {
                    if (editBussinesZipCode.getText().isEmpty()) {
                        showValidationError(editBussinesZipCode.getId(), getString(R.string.datos_domicilio_cp));
                        editBussinesZipCode.setIsInvalid();
                    } else {
                        hideValidationError(editBussinesZipCode.getId());
                        editBussinesZipCode.imageViewIsGone(true);
                    }
                }
            }
        });



        editBussinesZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editBussinesZipCode.getId());
                    editBussinesZipCode.imageViewIsGone(true);
                } else {
                    if (editBussinesZipCode.getText().isEmpty()) {
                        editBussinesZipCode.setIsInvalid();
                        showValidationError(editBussinesZipCode.getId(), getString(R.string.datos_domicilio_cp));
                    } else if (!editBussinesZipCode.isValidText()) {
                        editBussinesZipCode.setIsInvalid();
                        showValidationError(editBussinesZipCode.getId(), getString(R.string.datos_domicilio_cp));

                        if (listaColonias != null) {
                            listaColonias.clear();
                            estadoDomicilio = "";
                            fillAdapter();
                        }
                    } else if (editBussinesZipCode.isValidText() && editBussinesZipCode.getText().toString().length() > MIN_LENGHT_VALIDATION_CP) {
                        hideValidationError(editBussinesZipCode.getId());
                        editBussinesZipCode.setIsValid();
                        showLoader(getString(R.string.search_zipcode));
                        presenter.getNeighborhoods(editBussinesZipCode.getText().toString().toString().trim());//Buscamos por CP
                    }
                }
            }
        });


        editBussinesZipCode.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editBussinesZipCode.getId());
                editBussinesZipCode.imageViewIsGone(true);
            }
        });


    }

    private void fillAdapter() {
        coloniasNombre.clear();
        coloniasNombre.add(getString(R.string.colonia));
        for (ColoniasResponse coloniasResponse : this.listaColonias) {
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        editBussinesState.setText(this.estadoDomicilio);

        RegisterCupo registerCupo = RegisterCupo.getInstance();

        // Aqui se carga la colonia si ya se habia guardado antes.
        if (!registerCupo.getCalle().equals("")) {
            for (int position = 0; position < coloniasNombre.size(); position++) {
                if (coloniasNombre.get(position).equals(registerCupo.getColonia())) {
                    spBussinesColonia.setSelection(position);
                    break;
                }
            }
        }



    }

    
    @Override
    public void validateForm() {

        getDataForm();

        boolean isValid = true;

        if (calle.isEmpty()) {
            showValidationError(editBussinesStreet.getId(), getString(R.string.datos_domicilio_calle));
            editBussinesStreet.setIsInvalid();
            isValid = false;
        }

        if (numExt.isEmpty()) {
            showValidationError(editBussinesExtNumber.getId(), getString(R.string.datos_domicilio_num_ext));
            editBussinesExtNumber.setIsInvalid();
            isValid = false;
        }

        if (!editBussinesZipCode.isValidText()) {
            showValidationError(editBussinesZipCode.getId(), getString(R.string.datos_domicilio_cp));
            editBussinesZipCode.isValidText();
            isValid = false;
        }

        if (codigoPostal.isEmpty()) {
            showValidationError(editBussinesZipCode.getId(), getString(R.string.datos_domicilio_cp));
            editBussinesZipCode.setIsInvalid();
            isValid = false;
        }

        if (spBussinesColonia.getSelectedItemPosition() == 0 || colonia.isEmpty()) {
            showValidationError(spBussinesColonia.getId(), getString(R.string.datos_domicilio_colonia));
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }

    }

    @Override
    public void showValidationError(int id, Object error) {
        switch (id) {
            case R.id.editBussinesStreet:
                errorStreet.setMessageText(error.toString());
                break;
            case R.id.editBussinesExtNumber:
                errorNumberAddress.setMessageText(error.toString());
                break;
            case R.id.editBussinesZipCode:
                errorZipCode.setMessageText(error.toString());
                break;
            case R.id.spBussinesColonia:
                errorColonia.setMessageText(error.toString());
                break;
        }
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.editBussinesStreet:
                errorStreet.setVisibilityImageError(false);
                break;
            case R.id.editBussinesExtNumber:
                errorNumberAddress.setVisibilityImageError(false);
                break;
            case R.id.editBussinesZipCode:
                errorZipCode.setVisibilityImageError(false);
                break;
            case R.id.spBussinesColonia:
                errorColonia.setVisibilityImageError(false);
                break;
        }
    }

    @Override
    public void onValidationSuccess() {

        //Almacenamos la información para el registro.
        RegisterCupo registerCupo = RegisterCupo.getInstance();
        registerCupo.setCalle(calle);
        registerCupo.setNumExterior(numExt);
        registerCupo.setNumInterior(numInt);
        registerCupo.setCodigoPostal(codigoPostal);
        registerCupo.setEstadoDomicilio(estado);
        registerCupo.setColonia(colonia);
        registerCupo.setIdColonia(Idcolonia);
        registerCupo.setIdEstadoNacimineto(idEstado);
        editBussinesZipCode.removeCustomTextWatcher(textWatcherZipCode);

        Log.e("Calle",        registerCupo.getCalle() );
        Log.e("numExt", ""  + registerCupo.getNumExterior());
        Log.e("numInt", ""  + registerCupo.getNumInterior());
        Log.e("Codigo Postal", "" + registerCupo.getCodigoPostal());
        Log.e("Estado", "" + registerCupo.getEstadoDomicilio());
        Log.e("Colonia",""+ registerCupo.getColonia());
        Log.e("Id Colonia",""+ registerCupo.getIdColonia());
        //cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_COMPROBANTES, null);
        presenter.createCupoSolicitud();


    }

    @Override
    public void getDataForm() {
        calle = editBussinesStreet.getText();
        numExt = editBussinesExtNumber.getText();
        numInt = editBussinesIntNumber.getText();
        codigoPostal = editBussinesZipCode.getText();
        estado = editBussinesState.getText();
        Idcolonia = "-1";
        if (spBussinesColonia.getSelectedItem() != null) {
            colonia = spBussinesColonia.getSelectedItem().toString();
            for (ColoniasResponse coloniaInfo : listaColonias) {
                if (coloniaInfo.getColonia().equals(colonia)) {
                    Idcolonia = coloniaInfo.getColoniaId();
                }
            }
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioBtnIsBussinesAddressNo:
                cleanFields();
                break;
            case R.id.radioBtnIsBussinesAddressYes:
            default:
                if (RegisterCupo.getInstance().getCalle().equals("")) {
                    loadHomeAddress();
                } else {
                    loadDataFromSigleton();
                }
                //loadHomeAddress();
                break;
        }
    }

    private void loadDataFromSigleton () {
        onResume();
    }


    private void loadHomeAddress() {
        if (domicilio == null) {
            presenter.getClientAddress();
        } else {
            fillHomeAddress();
        }
    }

    private void fillHomeAddress() {
        textWatcherZipCode.setEnabled(false);
        editBussinesZipCode.setText(domicilio.getCp());
        editBussinesStreet.setText(domicilio.getCalle());
        editBussinesExtNumber.setText(domicilio.getNumeroExterior());
        editBussinesIntNumber.setText(domicilio.getNumeroInterior());
        colonyToLoad = domicilio.getIdColonia();
        textWatcherZipCode.setEnabled(true);
        if (domicilio.getColoniasDomicilio() == null) {
            textWatcherZipCode.afterTextChanged(editBussinesZipCode.getText());
        } else {
            setNeighborhoodsAvaliables(domicilio.getColoniasDomicilio());
        }

    }

    private void cleanFields() {
        errorStreet.setVisibilityImageError(false);
        errorNumberAddress.setVisibilityImageError(false);
        errorZipCode.setVisibilityImageError(false);
        errorColonia.setVisibilityImageError(false);

        clearAllFocus();

        editBussinesStreet.setText("");
        editBussinesExtNumber.setText("");
        editBussinesIntNumber.setText("");
        editBussinesZipCode.setText("");
        editBussinesState.setText("");
    }

    private void clearAllFocus() {
        editBussinesStreet.clearFocus();
        editBussinesExtNumber.clearFocus();
        editBussinesIntNumber.clearFocus();
        editBussinesZipCode.clearFocus();
        editBussinesState.clearFocus();
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

        if (error.getWebService() == OBTENER_COLONIAS_CP) {
            // Se comenta esta linea para evitar el salto hacia atras
            //  onClick(btnBackBussinesAddress);
        } else if (error.getWebService() == OBTENER_DOMICILIO || error.getWebService() == OBTENER_DOMICILIO_PRINCIPAL) {
            cleanFields();
            radioIsBussinesAddress.check(R.id.radioBtnIsBussinesAddressNo);
        }

        editBussinesZipCode.setText("");
        error.setHasConfirm(true);
        error.setHasCancel(false);
        onEventListener.onEvent(EVENT_SHOW_ERROR, error);
    }



    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {
        if (this.domicilio != null && radioIsBussinesAddress.getCheckedRadioButtonId() == R.id.radioBtnIsBussinesAddressYes) {
            this.domicilio.setColoniasDomicilio(listaColonias);
        }
        this.listaColonias = listaColonias;
        this.estadoDomicilio = listaColonias.get(0).getEstado();
        this.idEstado = listaColonias.get(0).getIdEstado();
        fillAdapter();

        if (colonyToLoad != null && !colonyToLoad.isEmpty()) {
            for (int i = 0; i < listaColonias.size(); i ++ ) {
                ColoniasResponse actual = listaColonias.get(i);
                if (actual.getColoniaId().equals(colonyToLoad) ){
                    int spinnerPosition = adapterColonia.getPosition(actual.getColonia());
                    spBussinesColonia.setSelection(spinnerPosition);
                    break;
                }
            }


            colonyToLoad = null;
        }
    }

    @Override
    public void setResponseCreaSolicitudCupo() {
        App.getInstance().getPrefs().saveData( CUPO_PASO , CUPO_PASO_REGISTRO_ENVIADO);
        cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_COMPROBANTES, null);
    }

    @Override
    public void setDomicilio(DataObtenerDomicilio domicilio) {
        Log.e("Test", "Entre a obtener el domicilio actual");
        this.domicilio = domicilio;
        loadHomeAddress();
    }

    @Override
    public void onSpinnerClick() {
        hideValidationError(spBussinesColonia.getId());
    }


    private class ZipWatcher extends AbstractTextWatcher {

        @Override
        public void afterTextChanged(String s) {
            if (editBussinesZipCode.isValidText()) {
                presenter.getNeighborhoods(s.trim());//Buscamos por CP
            } else {
                if (listaColonias != null) {
                    listaColonias.clear();
                    estadoDomicilio = "";
                    fillAdapter();
                }
            }
        }
    }

}
