package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADITIONAL_INFORMATION;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DATA_BACK;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_SET_ADDRESS;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_DOMICILIO;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioNegocioFragment extends GenericFragment implements ValidationForms,
        View.OnClickListener, IAdqRegisterView<ErrorObject>, RadioGroup.OnCheckedChangeListener,
        IOnSpinnerClick {

    public static final String _DOMICILIO = "1";
    public static final String COLONIAS = "2";
    public static int MIN_LENGHT_VALIDATION_CP = 4;
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
    @BindView(R.id.btnBackBussinesAddress)
    Button btnBackBussinesAddress;
    @BindView(R.id.btnNextBussinesAddress)
    Button btnNextBussinesAddress;
    @BindView(R.id.errorBussinesStreetMessage)
    ErrorMessage errorStreet;
    @BindView(R.id.errorNumberAddressMessage)
    ErrorMessage errorNumberAddress;
    @BindView(R.id.errorBussinesZipCodeMessage)
    ErrorMessage errorZipCode;
    @BindView(R.id.errorBussinesColoniaMessage)
    ErrorMessage errorColonia;
    private View rootview;
    private ArrayAdapter<String> adapterColonia;
    private List<ColoniasResponse> listaColonias;
    private List<String> coloniasNombre;
    private String estadoDomicilio = "";
    private String calle = "";
    private String numExt = "";
    private String numInt = "";
    private String codigoPostal = "";
    private String estado = "";
    private String colonia = "";
    private String Idcolonia = "";
    private boolean respuestaDomicilio;
    private String idEstado = "";

    private String colonyToLoad;

    private AccountAdqPresenter adqPresenter;

    private ZipWatcher textWatcherZipCode;

    private DataObtenerDomicilio domicilio;

    public static DomicilioNegocioFragment newInstance(DataObtenerDomicilio domicilio,
                                                       List<ColoniasResponse> listaColonias) {
        DomicilioNegocioFragment fragmentRegister = new DomicilioNegocioFragment();
        Bundle args = new Bundle();
        args.putSerializable(_DOMICILIO, domicilio);
        args.putSerializable(COLONIAS, (Serializable) listaColonias);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adqPresenter = new AccountAdqPresenter(this);
        Bundle args = getArguments();
        if (args != null) {
            Serializable dom = args.getSerializable(_DOMICILIO);
            Serializable cols = args.getSerializable(COLONIAS);

            if (dom != null) {
                this.domicilio = (DataObtenerDomicilio) dom;
            }

            if (cols != null) {
                this.listaColonias = (List<ColoniasResponse>) cols;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_domicilio_negocio_cupo, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        textWatcherZipCode = new ZipWatcher();
        hideLoader();
        coloniasNombre = new ArrayList<>();
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, coloniasNombre, this);
        spBussinesColonia.setAdapter(adapterColonia);
        btnNextBussinesAddress.setOnClickListener(this);
        btnBackBussinesAddress.setOnClickListener(this);
        editBussinesState.setTextEnabled(false);


        spBussinesColonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }
        });

        setCurrentData();
        radioIsBussinesAddress.setOnCheckedChangeListener(this);
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextBussinesAddress:
                validateForm();
                break;

            case R.id.btnBackBussinesAddress:
                backScreen(EVENT_GO_BUSSINES_DATA_BACK, null);
                break;
            default:
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
                        adqPresenter.getNeighborhoods(editBussinesZipCode.getText().toString().toString().trim());//Buscamos por CP
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

    private void fillAdapter() {
        coloniasNombre.clear();
        coloniasNombre.add(getString(R.string.colonia));
        for (ColoniasResponse coloniasResponse : this.listaColonias) {
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        editBussinesState.setText(this.estadoDomicilio);
    }


    private void setCurrentData() {
        /*errorStreet.setVisibilityImageError(false);
        errorNumberAddress.setVisibilityImageError(false);
        errorZipCode.setVisibilityImageError(false);
        errorColonia.setVisibilityImageError(false);*/

        clearAllFocus();

        RegisterAgent registerAgente = RegisterAgent.getInstance();


        if (!registerAgente.getCodigoPostal().isEmpty()) {
            for (CuestionarioEntity q : registerAgente.getCuestionario()) {
                if (q.getPreguntaId() == PREGUNTA_DOMICILIO) {
                    radioIsBussinesAddress.check(q.isValor() ? R.id.radioBtnIsBussinesAddressYes : R.id.radioBtnIsBussinesAddressNo);
                }
            }
        }
        if (radioIsBussinesAddress.getCheckedRadioButtonId() == -1) {
            radioIsBussinesAddress.check(R.id.radioBtnIsBussinesAddressYes);
            onCheckedChanged(radioIsBussinesAddress, R.id.radioBtnIsBussinesAddressYes);
        } else {
            editBussinesStreet.setText(registerAgente.getCalle());
            editBussinesExtNumber.setText(registerAgente.getNumExterior());
            editBussinesIntNumber.setText(registerAgente.getNumInterior());
            colonyToLoad = registerAgente.getIdColonia();
            editBussinesZipCode.setText(registerAgente.getCodigoPostal());

            if (listaColonias == null) {
                textWatcherZipCode.afterTextChanged(editBussinesZipCode.getText());
            } else {
                setNeighborhoodsAvaliables(listaColonias);
            }
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
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        //Almacenamos la información para el registro.
        registerAgent.setCalle(calle);
        registerAgent.setNumExterior(numExt);
        registerAgent.setNumInterior(numInt);
        registerAgent.setCodigoPostal(codigoPostal);
        registerAgent.setEstadoDomicilio(estado);
        registerAgent.setIdEstado(idEstado);
        registerAgent.setColonia(colonia);
        registerAgent.setIdColonia(Idcolonia);
        editBussinesZipCode.removeCustomTextWatcher(textWatcherZipCode);
        respuestaDomicilio = radioIsBussinesAddress.getCheckedRadioButtonId() == R.id.radioBtnIsBussinesAddressYes;

        if (registerAgent.getCuestionario().size() > 0) {
            for (CuestionarioEntity cuestionario : registerAgent.getCuestionario()) {
                if (cuestionario.getPreguntaId() == PREGUNTA_DOMICILIO) {
                    registerAgent.getCuestionario().remove(cuestionario);
                }
            }
        }
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_DOMICILIO, respuestaDomicilio));

        nextScreen(EVENT_GO_BUSSINES_ADITIONAL_INFORMATION, null);
    }

    @Override
    public void getDataForm() {
        calle = editBussinesStreet.getText();
        numExt = editBussinesExtNumber.getText();
        numInt = editBussinesIntNumber.getText();
        codigoPostal = editBussinesZipCode.getText();
        estado = estadoDomicilio;//editBussinesZipCode.getText();

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
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {
        if (this.domicilio != null && radioIsBussinesAddress.getCheckedRadioButtonId() == R.id.radioBtnIsBussinesAddressYes) {
            this.domicilio.setColoniasDomicilio(listaColonias);
        }
        this.listaColonias = listaColonias;
        this.estadoDomicilio = listaColonias.get(0).getEstado();
        this.idEstado = listaColonias.get(0).getIdEstado();
        fillAdapter();

        if (colonyToLoad != null && !colonyToLoad.isEmpty()) {
            for (int pos = 0; pos < this.listaColonias.size(); pos++) {
                if (this.listaColonias.get(pos).getColoniaId().equalsIgnoreCase(colonyToLoad)) {
                    spBussinesColonia.setSelection(pos + 1);
                    break;
                }
            }
            colonyToLoad = null;
        }
    }

    @Override
    public void setCurrentAddress(DataObtenerDomicilio domicilio) {
        this.domicilio = domicilio;
        onEventListener.onEvent(EVENT_SET_ADDRESS, this.domicilio);
        fillHomeAddress();
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

    @Override
    public void agentCreated(String message) {
        /*App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        onEventListener.onEvent(EVENT_SET_COLONIES_LIST, listaColonias);
        nextScreen(EVENT_GO_BUSSINES_DOCUMENTS, null);*/
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
        // Reiniciamos el editTExt para no teenr nada de texto
        editBussinesZipCode.setText("");
        if (error.getWebService() == OBTENER_COLONIAS_CP) {
            // Se comenta esta linea para evitar el salto hacia atras
            //  onClick(btnBackBussinesAddress);
        } else if (error.getWebService() == OBTENER_DOMICILIO || error.getWebService() == OBTENER_DOMICILIO_PRINCIPAL) {
            cleanFields();
            radioIsBussinesAddress.check(R.id.radioBtnIsBussinesAddressNo);
        }
        error.setHasConfirm(true);
        error.setHasCancel(false);
        onEventListener.onEvent(EVENT_SHOW_ERROR, error);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radioBtnIsBussinesAddressNo:
                cleanFields();
                break;

            case R.id.radioBtnIsBussinesAddressYes:
            default:
                loadHomeAddress();
                break;
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

    private void loadHomeAddress() {
        if (domicilio == null) {
            adqPresenter.getClientAddress();
        } else {
            fillHomeAddress();
        }
    }

    @Override
    public void onSpinnerClick() {
        hideValidationError(spBussinesColonia.getId());
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

    private void clearAllFocus() {
        editBussinesStreet.clearFocus();
        editBussinesExtNumber.clearFocus();
        editBussinesIntNumber.clearFocus();
        editBussinesZipCode.clearFocus();
        editBussinesState.clearFocus();
    }

    private class ZipWatcher extends AbstractTextWatcher {

        @Override
        public void afterTextChanged(String s) {
            if (editBussinesZipCode.isValidText()) {
                adqPresenter.getNeighborhoods(s.trim());//Buscamos por CP
                UI.hideKeyBoard(getActivity());
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