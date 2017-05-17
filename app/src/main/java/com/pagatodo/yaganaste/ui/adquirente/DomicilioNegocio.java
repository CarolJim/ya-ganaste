package com.pagatodo.yaganaste.ui.adquirente;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DATA_BACK;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DOCUMENTS;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_SET_ADDRESS;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_SET_COLONIES_LIST;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_DOMICILIO;

/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioNegocio extends GenericFragment implements ValidationForms, View.OnClickListener,IAdqRegisterView<ErrorObject>, RadioGroup.OnCheckedChangeListener {

    public static final String _DOMICILIO = "1";
    public static final String COLONIAS = "2";

    private View rootview;

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

    private ArrayAdapter<String> adapterColonia;
    private List<ColoniasResponse> listaColonias;
    private List<String> coloniasNombre;
    private String estadoDomicilio= "";
    private String calle = "";
    private String numExt = "";
    private String numInt = "";
    private String codigoPostal = "";
    private String estado = "";
    private String colonia = "";
    private String Idcolonia = "";
    private boolean respuestaDomicilio;

    private String colonyToLoad;

    private AccountAdqPresenter adqPresenter;

    private ZipWatcher textWatcherZipCode;

    private DataObtenerDomicilio domicilio;

    public static DomicilioNegocio newInstance(DataObtenerDomicilio domicilio,
                                               List<ColoniasResponse> listaColonias) {
        DomicilioNegocio fragmentRegister = new DomicilioNegocio();
        Bundle args = new Bundle();
        args.putSerializable(_DOMICILIO, domicilio);
        args.putSerializable(COLONIAS, (Serializable) listaColonias);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adqPresenter = new AccountAdqPresenter(this,getContext());
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
        rootview = inflater.inflate(R.layout.fragment_domicilio_negocio, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        textWatcherZipCode = new ZipWatcher();
        hideLoader();
        coloniasNombre = new ArrayList<>();
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout,coloniasNombre);
        spBussinesColonia.setAdapter(adapterColonia);
        btnNextBussinesAddress.setOnClickListener(this);
        btnBackBussinesAddress.setOnClickListener(this);
        editBussinesState.setTextEnabled(false);
        setCurrentData();
        radioIsBussinesAddress.setOnCheckedChangeListener(this);
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNextBussinesAddress:
                validateForm();
                break;

            case R.id.btnBackBussinesAddress:
                backScreen(EVENT_GO_BUSSINES_DATA_BACK,null);
                break;
            default:
                break;
        }
    }

    @Override
    public void setValidationRules() {
        editBussinesZipCode.addCustomTextWatcher(textWatcherZipCode);
    }

    @Override
    public void validateForm() {

        getDataForm();
        if(calle.isEmpty()){
            UI.showToastShort(getString(R.string.datos_domicilio_calle),getActivity());
            return;
        }
        if(numExt.isEmpty()){
            UI.showToastShort(getString(R.string.datos_domicilio_num_ext),getActivity());
            return;
        }
        if(codigoPostal.isEmpty()){
            UI.showToastShort(getString(R.string.datos_domicilio_cp),getActivity());
            return;
        }

        if(colonia.isEmpty()){
            UI.showToastShort(getString(R.string.datos_domicilio_colonia),getActivity());
            return;
        }


        onValidationSuccess();

    }

    private void fillAdapter(){
        coloniasNombre.clear();
        for(ColoniasResponse coloniasResponse : this.listaColonias){
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        editBussinesState.setText(this.estadoDomicilio);
    }


    private void setCurrentData() {
        RegisterAgent registerAgente = RegisterAgent.getInstance();
        if (!registerAgente.getCodigoPostal().isEmpty()){
            List<CuestionarioEntity> cuestionario = registerAgente.getCuestionario();
                for(CuestionarioEntity q : cuestionario){
                    if(q.getPreguntaId() == PREGUNTA_DOMICILIO){
                        if (q.isValor()) {
                            radioIsBussinesAddress.check(q.isValor() ? R.id.radioBtnIsBussinesAddressYes : R.id.radioBtnIsBussinesAddressNo);
                        }
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
            colonyToLoad = registerAgente.getColonia();

            editBussinesZipCode.setText(registerAgente.getCodigoPostal());

            if (listaColonias == null) {
                textWatcherZipCode.afterTextChanged(editBussinesZipCode.getText());
            } else {
                setNeighborhoodsAvaliables(listaColonias);
            }
        }
    }


    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
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
        registerAgent.setColonia(colonia);
        registerAgent.setIdColonia(Idcolonia);
        editBussinesZipCode.removeCustomTextWatcher(textWatcherZipCode);
        respuestaDomicilio = radioIsBussinesAddress.getCheckedRadioButtonId() == R.id.radioBtnIsBussinesAddressYes;
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_DOMICILIO, respuestaDomicilio));
        if(!registerAgent.getCuestionario().isEmpty())
            registerAgent.getCuestionario().get(1).setValor(respuestaDomicilio);
        else
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_DOMICILIO, respuestaDomicilio));

        adqPresenter.createAdq();
    }

    @Override
    public void getDataForm() {

        calle = editBussinesStreet.getText();
        numExt = editBussinesExtNumber.getText();
        numInt = editBussinesIntNumber.getText();
        codigoPostal = editBussinesZipCode.getText();
        estado = editBussinesZipCode.getText();
        if(spBussinesColonia.getSelectedItem() != null) {
            colonia = spBussinesColonia.getSelectedItem().toString();
            for(ColoniasResponse coloniaInfo : listaColonias){
                if(coloniaInfo.getColonia().equals(colonia)){
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
        fillAdapter();

        if (colonyToLoad != null && !colonyToLoad.isEmpty()) {
            for (int pos = 0 ; pos < coloniasNombre.size() ; pos++) {
                if (coloniasNombre.get(pos).equalsIgnoreCase(colonyToLoad)) {
                    spBussinesColonia.setSelection(pos);
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
        colonyToLoad = domicilio.getColonia();
        textWatcherZipCode.setEnabled(true);
        if (domicilio.getColoniasDomicilio() == null) {
            textWatcherZipCode.afterTextChanged(editBussinesZipCode.getText());
        } else {
            setNeighborhoodsAvaliables(domicilio.getColoniasDomicilio());
        }

    }

    @Override
    public void agentCreated(String message) {
        onEventListener.onEvent(EVENT_SET_COLONIES_LIST, listaColonias);
        nextScreen(EVENT_GO_BUSSINES_DOCUMENTS,null);
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
        if (error.getWebService() == OBTENER_COLONIAS_CP) {
            onClick(btnBackBussinesAddress);
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

    private class ZipWatcher extends AbstractTextWatcher {

        @Override
        public void afterTextChanged(String s) {
            if ( editBussinesZipCode.isValidText()) {
                adqPresenter.getNeighborhoods(s.trim());//Buscamos por CP
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