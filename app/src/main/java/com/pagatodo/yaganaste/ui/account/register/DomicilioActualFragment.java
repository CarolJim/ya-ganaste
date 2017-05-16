package com.pagatodo.yaganaste.ui.account.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PERSONAL_DATA_BACK;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioActualFragment extends GenericFragment implements View.OnClickListener, ValidationForms<Object>,IAccountRegisterView<Object> {

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
    @BindView(R.id.txtLegales)
    StyleTextView txtLegales;
    @BindView(R.id.radioBtnTerms)
    CheckBox radioBtnTerms;
    @BindView(R.id.btnBackDomicilioActual)
    StyleButton btnBackDomicilioActual;
    @BindView(R.id.btnNextDomicilioActual)
    StyleButton btnNextDomicilioActual;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    @BindView(R.id.errorMessage)
    ErrorMessage errorMessageView;


    private ColoniasArrayAdapter adapterColonia;
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
        accountPresenter = ((AccountActivity)getActivity()).getPresenter();
        accountPresenter.setIView(this);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
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
        errorMessageView.setVisibilityImageError(false);
        coloniasNombre = new ArrayList<String>();
        coloniasNombre.add(getString(R.string.colonia));
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout,coloniasNombre);
        spColonia.setAdapter(adapterColonia);
        btnNextDomicilioActual.setOnClickListener(this);
        btnBackDomicilioActual.setOnClickListener(this);
        radioBtnTerms.setOnClickListener(this);
        editState.setTextEnabled(false);
        setCurrentData();// Seteamos datos si hay registro en proceso.
        setClickLegales();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextDomicilioActual:
                validateForm();
                break;
            case R.id.btnBackDomicilioActual:
                backScreen(EVENT_PERSONAL_DATA_BACK,null);
                break;
            default:
                break;
        }
    }

    /*Implementación ValidateForm*/
    @Override
    public void setValidationRules() {
        editZipCode.addCustomTextWatcher(textWatcherZipCode);

        editStreet.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!editStreet.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        editExtNumber.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!editExtNumber.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editZipCode.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!editZipCode.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void fillAdapter(){
        coloniasNombre.clear();
        coloniasNombre.add(getString(R.string.colonia));
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
    public void setCurrentAddress(DataObtenerDomicilio domicilio) {
        //No-Op
    }

    @Override
    public void validateForm() {
        getDataForm();
        if(calle.isEmpty()){
           showValidationError(getString(R.string.datos_domicilio_calle));
            editStreet.setIsInvalid();
            return;
        }
        if(numExt.isEmpty()){
            showValidationError(getString(R.string.datos_domicilio_num_ext));
            editExtNumber.setIsInvalid();
            return;
        }
        if(codigoPostal.isEmpty()){
            showValidationError(getString(R.string.datos_domicilio_cp));
            editZipCode.setIsInvalid();
            return;
        }

        if(spColonia.getSelectedItemPosition() == 0 || colonia.isEmpty()){
            showValidationError(getString(R.string.datos_domicilio_colonia));
            return;
        }
        if(!radioBtnTerms.isChecked()){
            showValidationError(getString(R.string.datos_domicilio_terminos));
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        errorMessageView.setMessageText(error.toString());
        UI.hideKeyBoard(getActivity());
    }

    private void hideErrorMessage(){
        errorMessageView.setVisibilityImageError(false);
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
        if(spColonia.getSelectedItem() != null && listaColonias!= null) {
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

        if(!error.toString().isEmpty())
            UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void backScreen(String event, Object data) {
        onEventListener.onEvent(event,data);
    }

    @Override
    public void clientCreatedSuccess(String message) {
        showLoader(message);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextScreen(EVENT_GO_GET_CARD,null); // Mostramos la pantalla para obtener tarjeta.
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

    @Override
    public void zipCodeInvalid(String message) {
        showValidationError(message);
        editZipCode.setIsInvalid();
    }

    private void setClickLegales(){
        SpannableString ss = new SpannableString(getString(R.string.terms_and_conditions));
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                LegalsDialog legalsDialog = LegalsDialog.newInstance(TERMINOS);
                legalsDialog.show(getActivity().getFragmentManager(),LegalsDialog.TAG);
            }
        };

        //"Al Continuar Reconozco Ser Mayor de Edad, Así Como Haber Leído y Aceptado los Términos y Condiciones y el Aviso de Privacidad"

        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                LegalsDialog legalsDialog = LegalsDialog.newInstance(PRIVACIDAD);
                legalsDialog.show(getActivity().getFragmentManager(),LegalsDialog.TAG);
            }
        };

        ss.setSpan(span1, 78, 100, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), 78, 100, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(span2, 106, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), 106, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        txtLegales.setText(ss);
        txtLegales.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

