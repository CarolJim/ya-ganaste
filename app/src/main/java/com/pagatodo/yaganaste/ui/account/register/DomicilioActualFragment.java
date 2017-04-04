package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PERSONAL_DATA_BACK;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioActualFragment extends GenericFragment implements View.OnClickListener, ValidationForms,IAccountRegisterView {

    public static int MIN_LENGHT_VALIDATION_CP = 4;
    private View rootview;
    @BindView(R.id.editStreet)
    CustomValidationEditText editStreet;
    @BindView(R.id.editExtNumber)
    CustomValidationEditText editExtNumber;
    @BindView(R.id.editIntNumber)
    CustomValidationEditText editIntNumber;
    @BindView(R.id.editZipCode)
    CustomValidationEditText editZipCode;
    @BindView(R.id.editState)
    CustomValidationEditText editState;
    @BindView(R.id.spColonia)
    Spinner spColonia;
    @BindView(R.id.radioBtnTerms)
    RadioButton radioBtnTerms;
    @BindView(R.id.btnBackDomicilioActual)
    Button btnBackDomicilioActual;
    @BindView(R.id.btnNextDomicilioActual)
    Button btnNextDomicilioActual;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;


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
    private boolean cpDefault;
    private AccountPresenterNew accountPresenter;

    public DomicilioActualFragment() {
    }

    public static DomicilioActualFragment newInstance() {
        DomicilioActualFragment fragmentRegister = new DomicilioActualFragment();
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
        accountPresenter = new AccountPresenterNew(this);
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
        rootview = inflater.inflate(R.layout.fragment_domicilio_actual, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        hideLoader();
        coloniasNombre = new ArrayList<String>();
        adapterColonia = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, R.id.textView_spinner,coloniasNombre);
        spColonia.setAdapter(adapterColonia);
        btnNextDomicilioActual.setOnClickListener(this);
        btnBackDomicilioActual.setOnClickListener(this);
        editState.setTextEnabled(false);
        setCurrentData();// Seteamos datos si hay registro en proceso.

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextDomicilioActual:
                validateForm();
                break;
            case R.id.btnBackDomicilioActual:
                backStepRegister(EVENT_PERSONAL_DATA_BACK,null);
                break;
            default:
                break;
        }
    }

    /*Implementación ValidateForm*/
    @Override
    public void setValidationRules() {
        editZipCode.addCustomTextWatcher(textWatcherZipCode);
    }

    private void fillAdapter(){
        coloniasNombre.clear();
        for(ColoniasResponse coloniasResponse : this.listaColonias){
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        editState.setText(this.estadoDomicilio);

        //Seteamos los datos del CP
        if(cpDefault) {
            //editZipCode.removeCustomTextWatcher(textWatcherZipCode);
            cpDefault = false;
            setCPDataCurrent();
        }
    }

    @Override
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {
        this.listaColonias = listaColonias;
        this.estadoDomicilio = listaColonias.get(0).getEstado();
        fillAdapter();
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
        if(!radioBtnTerms.isChecked()){
            UI.showToastShort(getString(R.string.datos_domicilio_terminos),getActivity());
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void onValidationSuccess() {
        //Almacenamos la información para el registro.
        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setCalle(calle);
        registerUser.setNumExterior(numExt);
        registerUser.setNumInterior(numInt);
        registerUser.setCodigoPostal(codigoPostal);
        registerUser.setEstadoDomicilio(estado);
        registerUser.setColonia(colonia);
        registerUser.setIdColonia(Idcolonia);
        editZipCode.removeCustomTextWatcher(textWatcherZipCode);
        //TODO Creamos usuario Implementando la secuencia necesaria de métodos.
        accountPresenter.createUser();
    }

    @Override
    public void getDataForm() {
        calle = editStreet.getText().toString().trim();
        numExt = editExtNumber.getText().toString().trim();
        numInt = editIntNumber.getText().toString().trim();
        codigoPostal = editZipCode.getText().toString().trim();
        estado = editState.getText().toString().trim();
        if(spColonia.getSelectedItem() != null) {
            colonia = spColonia.getSelectedItem().toString();
            for(ColoniasResponse coloniaInfo : listaColonias){
                if(coloniaInfo.getColonia().equals(colonia)){
                    Idcolonia = coloniaInfo.getColoniaId();
                }
            }
        }
    }

    private void setCPDataCurrent(){
        RegisterUser registerUser = RegisterUser.getInstance();
        //editZipCode.setText(registerUser.getCodigoPostal());
        this.estadoDomicilio = registerUser.getEstadoDomicilio();
        editState.setText(this.estadoDomicilio);
        for (int position = 0 ; position<coloniasNombre.size(); position++){
            if(coloniasNombre.get(position).equals(registerUser.getColonia())){
            spColonia.setSelection(position);
            break;
            }
        }

        setValidationRules();
    }

    private void setCurrentData(){
        RegisterUser registerUser = RegisterUser.getInstance();
        editStreet.setText(registerUser.getCalle());
        editExtNumber.setText(registerUser.getNumExterior());
        editIntNumber.setText(registerUser.getNumInterior());
        radioBtnTerms.setSelected(false);
        radioBtnTerms.setChecked(false);
        if (!registerUser.getCodigoPostal().isEmpty())
            cpDefault = true;

        setValidationRules();
        editZipCode.setText(registerUser.getCodigoPostal());
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void clientCreatedSuccess(String message) {
        showLoader(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextStepRegister(EVENT_GO_GET_CARD,null); // Mostramos la pantalla para obtener tarjeta.
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void clientCreateFailed(String error) {
        showError(error);
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
                if ( s.toString().length() > MIN_LENGHT_VALIDATION_CP) {
                    accountPresenter.getNeighborhoods(s.toString().trim());//Buscamos por CP
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

