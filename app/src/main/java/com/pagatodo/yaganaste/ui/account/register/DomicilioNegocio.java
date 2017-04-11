package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.adquirente.AdqPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DATA_BACK;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_DOCUMENTS;
import static com.pagatodo.yaganaste.utils.Recursos.PREGUNTA_DOMICILIO;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioNegocio extends GenericFragment implements ValidationForms, View.OnClickListener,IAdqRegisterView {

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
    @BindView(R.id.radioBtnIsBussinesAddressNo)
    RadioButton radioBtnIsBussinesAddressNo;
    @BindView(R.id.radioBtnIsBussinesAddressYes)
    RadioButton radioBtnIsBussinesAddressYes;
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

    private boolean cpDefault;
    private AdqPresenter adqPresenter;

    public DomicilioNegocio() {
    }

    public static DomicilioNegocio newInstance() {
        DomicilioNegocio fragmentRegister = new DomicilioNegocio();
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

        adqPresenter = new AdqPresenter(this);
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
        rootview = inflater.inflate(R.layout.fragment_domicilio_negocio, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        hideLoader();
        coloniasNombre = new ArrayList<String>();
        adapterColonia = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, R.id.textView_spinner,coloniasNombre);
        spBussinesColonia.setAdapter(adapterColonia);
        btnNextBussinesAddress.setOnClickListener(this);
        btnBackBussinesAddress.setOnClickListener(this);
        editBussinesState.setTextEnabled(false);
        setCurrentData();// Seteamos datos si hay registro en proceso.
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNextBussinesAddress:
                validateForm();
                break;

            case R.id.btnBackBussinesAddress:
                backStepRegister(EVENT_GO_BUSSINES_DATA_BACK,null);
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

        if(!radioBtnIsBussinesAddressYes.isChecked() && !radioBtnIsBussinesAddressNo.isChecked()){
            UI.showToastShort(getString(R.string.datos_negocio_preguntas),getActivity());
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

        //Seteamos los datos del CP
        if(cpDefault) {
            //editZipCode.removeCustomTextWatcher(textWatcherZipCode);
            cpDefault = false;
            setCPDataCurrent();
        }
    }


    private void setCurrentData(){
        RegisterAgent registerAgente = RegisterAgent.getInstance();
        editBussinesStreet.setText(registerAgente.getCalle());
        editBussinesExtNumber.setText(registerAgente.getNumExterior());
        editBussinesIntNumber.setText(registerAgente.getNumInterior());
        if(registerAgente.getCuestionario().size() > 0){
            for(CuestionarioEntity q : registerAgente.getCuestionario()){
                if(q.getPreguntaId() == PREGUNTA_DOMICILIO){
                    radioBtnIsBussinesAddressYes.setChecked(q.isValor());
                    radioBtnIsBussinesAddressNo.setChecked(!q.isValor());
                }
            }
        }

        if (!registerAgente.getCodigoPostal().isEmpty())
            cpDefault = true;

        setValidationRules();
        editBussinesZipCode.setText(registerAgente.getCodigoPostal());

    }

    private void setCPDataCurrent(){
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        //editZipCode.setText(registerUser.getCodigoPostal());
        this.estadoDomicilio = registerAgent.getEstadoDomicilio();
        editBussinesState.setText(this.estadoDomicilio);
        for (int position = 0 ; position<coloniasNombre.size(); position++){
            if(coloniasNombre.get(position).equals(registerAgent.getColonia())){
                spBussinesColonia.setSelection(position);
                break;
            }
        }

        setValidationRules();
    }


    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void onValidationSuccess() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();

        //Almacenamos la información para el registro.
        RegisterUser registerUser = RegisterUser.getInstance();
        registerAgent.setCalle(calle);
        registerAgent.setNumExterior(numExt);
        registerAgent.setNumInterior(numInt);
        registerAgent.setCodigoPostal(codigoPostal);
        registerAgent.setEstadoDomicilio(estado);
        registerAgent.setColonia(colonia);
        registerAgent.setIdColonia(Idcolonia);
        editBussinesZipCode.removeCustomTextWatcher(textWatcherZipCode);
        registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_DOMICILIO, respuestaDomicilio));
        if(registerAgent.getCuestionario().size() > 0)
            registerAgent.getCuestionario().get(1).setValor(respuestaDomicilio);
        else
            registerAgent.getCuestionario().add(new CuestionarioEntity(PREGUNTA_DOMICILIO, respuestaDomicilio));

        adqPresenter.createAdq();
    }

    @Override
    public void getDataForm() {

        calle = editBussinesStreet.getText().toString().trim();
        numExt = editBussinesExtNumber.getText().toString().trim();
        numInt = editBussinesIntNumber.getText().toString().trim();
        codigoPostal = editBussinesZipCode.getText().toString().trim();
        estado = editBussinesZipCode.getText().toString().trim();
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
        this.listaColonias = listaColonias;
        this.estadoDomicilio = listaColonias.get(0).getEstado();
        fillAdapter();
    }

    @Override
    public void agentCreated(String message) {

        nextStepRegister(EVENT_GO_BUSSINES_DOCUMENTS,null);

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
        UI.showToastShort(error.toString(),getActivity());
    }

    TextWatcher textWatcherZipCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ( s.toString().length() > DomicilioActualFragment.MIN_LENGHT_VALIDATION_CP) {
                adqPresenter.getNeighborhoods(s.toString().trim());//Buscamos por CP
            } else {
                if (listaColonias != null) {
                    listaColonias.clear();
                    estadoDomicilio = "";
                    fillAdapter();
                }
            }
        }
    };
}

