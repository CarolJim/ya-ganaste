package com.pagatodo.yaganaste.ui.account.register;

import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomClickableSpan;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_GET_CARD;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PERSONAL_DATA_BACK;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.PRIVACIDAD;
import static com.pagatodo.yaganaste.ui.account.register.LegalsDialog.Legales.TERMINOS;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioActualFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms<Object>, IAccountRegisterView<Object>, IOnSpinnerClick {

    public static int MIN_LENGHT_VALIDATION_CP = 4;
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
    //@BindView(R.id.radioBtnTerms)
    //CheckBox radioBtnTerms;
    @BindView(R.id.btnBackDomicilioActual)
    StyleButton btnBackDomicilioActual;
    @BindView(R.id.btnNextDomicilioActual)
    StyleButton btnNextDomicilioActual;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    @BindView(R.id.errorStreetMessage)
    ErrorMessage errorStreetMessage;
    //@BindView(R.id.errorMessage)
    //ErrorMessage errorMessageView;
    //BindView(R.id.radioBtnTerms)
    //CheckBox radioBtnTerms;
    //@BindView(R.id.radioBtnTermsLayOut)
    //RelativeLayout radioBtnTermsLayOut;
    @BindView(R.id.errorNumeroMessage)
    ErrorMessage errorNumeroMessage;
    @BindView(R.id.errorZipCodeMessage)
    ErrorMessage errorZipCodeMessage;
    @BindView(R.id.errorColoniaMessage)
    ErrorMessage errorColoniaMessage;
    private View rootview;
    //@BindView(R.id.errorCheckMessage)
    //ErrorMessage errorCheckMessage;
    private ColoniasArrayAdapter adapterColonia;
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
    private boolean cpDefault;
    private AccountPresenterNew accountPresenter;


    AbstractTextWatcher textWatcherZipCode = new AbstractTextWatcher() {
        @Override
        public void afterTextChanged(String s) {
            if (s.toString().length() > MIN_LENGHT_VALIDATION_CP) {
                showLoader(getString(R.string.search_zipcode));
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

    public DomicilioActualFragment() {
    }

    public static DomicilioActualFragment newInstance() {
        DomicilioActualFragment fragmentRegister = new DomicilioActualFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
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

        errorStreetMessage.setVisibilityImageError(false);
        errorNumeroMessage.setVisibilityImageError(false);
        errorZipCodeMessage.setVisibilityImageError(false);
        errorColoniaMessage.setVisibilityImageError(false);
        //errorCheckMessage.setVisibilityImageError(false);
        //errorCheckMessage.alingCenter();

        //errorMessageView.setVisibilityImageError(false);
        coloniasNombre = new ArrayList<String>();
        coloniasNombre.add(getString(R.string.colonia));
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, coloniasNombre, this);
        spColonia.setAdapter(adapterColonia);
        btnNextDomicilioActual.setOnClickListener(this);
        btnBackDomicilioActual.setOnClickListener(this);
        //radioBtnTerms.setOnClickListener(this);

        spColonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }
        });

        // radioBtnTermsLayOut.setOnClickListener(this);

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
                backScreen(EVENT_PERSONAL_DATA_BACK, null);
                break;
           /* case R.id.radioBtnTermsLayOut:
                if (radioBtnTerms.isChecked()) {
                    radioBtnTerms.setChecked(false);
                    radioBtnTerms.setSelected(false);
                } else {
                    radioBtnTerms.setChecked(true);
                    radioBtnTerms.setSelected(true);
                }
                break;*/
            default:
                break;
        }
    }

    /*Implementación ValidateForm*/
    @Override
    public void setValidationRules() {
        editZipCode.addCustomTextWatcher(textWatcherZipCode);

        editStreet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editStreet.getId());
                    editStreet.imageViewIsGone(true);
                } else {
                    if (editStreet.getText().isEmpty()) {
                        showValidationError(editStreet.getId(), getString(R.string.datos_domicilio_calle));
                        editStreet.setIsInvalid();
                    } else {
                        hideValidationError(editStreet.getId());
                        editStreet.setIsValid();
                    }
                }
            }
        });

        editStreet.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editStreet.getId());
                editStreet.imageViewIsGone(true);
            }
        });


        editExtNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editExtNumber.getId());
                    editExtNumber.imageViewIsGone(true);
                } else {
                    if (editExtNumber.getText().isEmpty()) {
                        showValidationError(editExtNumber.getId(), getString(R.string.datos_domicilio_num_ext));
                        editExtNumber.setIsInvalid();
                    } else {
                        hideValidationError(editExtNumber.getId());
                        editExtNumber.setIsValid();
                    }
                }
            }
        });

        editExtNumber.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editExtNumber.getId());
                editExtNumber.imageViewIsGone(true);
            }
        });

        editIntNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editIntNumber.imageViewIsGone(true);
                } else {
                    if (editIntNumber.getText().isEmpty()) {
                        editIntNumber.imageViewIsGone(true);
                    } else {
                        editIntNumber.setIsValid();
                    }
                }
            }
        });

        editIntNumber.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                editIntNumber.imageViewIsGone(true);
            }
        });

        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editZipCode.getId());
                    editZipCode.imageViewIsGone(true);
                } else {
                     if (editZipCode.getText().isEmpty()) {
                        showValidationError(editZipCode.getId(), getString(R.string.datos_domicilio_cp));
                        editZipCode.setIsInvalid();
                    } else {
                        hideValidationError(editZipCode.getId());
                        editZipCode.imageViewIsGone(true);
                    }
                }
            }
        });


        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editZipCode.getId());
                    editZipCode.imageViewIsGone(true);
                } else {
                    if (editZipCode.getText().isEmpty()) {
                        editZipCode.setIsInvalid();
                        showValidationError(editZipCode.getId(), getString(R.string.datos_domicilio_cp));
                    } else if (!editZipCode.isValidText()) {
                        editZipCode.setIsInvalid();
                        showValidationError(editZipCode.getId(), getString(R.string.datos_domicilio_cp));

                        if (listaColonias != null) {
                            listaColonias.clear();
                            estadoDomicilio = "";
                            fillAdapter();
                        }
                    } else if (editZipCode.isValidText() && editZipCode.getText().toString().length() > MIN_LENGHT_VALIDATION_CP) {
                        hideValidationError(editZipCode.getId());
                        editZipCode.setIsValid();
                        showLoader(getString(R.string.search_zipcode));
                        accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP
                    }
                }
            }
        });


        editZipCode.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editZipCode.getId());
                editZipCode.imageViewIsGone(true);
            }
        });

        /*radioBtnTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideValidationError(radioBtnTerms.getId());
            }
        });*/
    }

    private void fillAdapter() {
        coloniasNombre.clear();
        coloniasNombre.add(getString(R.string.colonia));
        for (ColoniasResponse coloniasResponse : this.listaColonias) {
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        editState.setText(this.estadoDomicilio);

        //Seteamos los datos del CP
        if (cpDefault) {
            //editZipCode.removeCustomTextWatcher(textWatcherZipCode);
            cpDefault = false;
            setCPDataCurrent();
        }
    }

    @Override
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {
        hideLoader();
        UI.hideKeyBoard(getActivity());
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

        boolean isValid = true;

        if (calle.isEmpty()) {
            showValidationError(editStreet.getId(), getString(R.string.datos_domicilio_calle));
            editStreet.setIsInvalid();
            isValid = false;
        }
        if (numExt.isEmpty()) {
            showValidationError(editExtNumber.getId(), getString(R.string.datos_domicilio_num_ext));
            editExtNumber.setIsInvalid();
            isValid = false;
        }
         if (!editZipCode.isValidText()) {
            showValidationError(editZipCode.getId(), getString(R.string.datos_domicilio_cp));
            editZipCode.setIsInvalid();
            isValid = false;
        }

        if (codigoPostal.isEmpty())
        {
            showValidationError(editZipCode.getId(), getString(R.string.datos_domicilio_cp));
            editZipCode.setIsInvalid();
            isValid = false;
        }

        if (spColonia.getSelectedItemPosition() == 0 || colonia.isEmpty())

        {
            showValidationError(spColonia.getId(), getString(R.string.datos_domicilio_colonia));
            isValid = false;
        }
//        if (!radioBtnTerms.isChecked()) {
//            showValidationError(radioBtnTerms.getId(), getString(R.string.datos_domicilio_terminos));
//            return;
//        }
        if (isValid)

        {
            onValidationSuccess();
        }

    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.editStreet:
                errorStreetMessage.setMessageText(error.toString());
                break;
            case R.id.editExtNumber:
                errorNumeroMessage.setMessageText(error.toString());
                break;
            case R.id.editZipCode:
                errorZipCodeMessage.setMessageText(error.toString());
                break;
            case R.id.spColonia:
                errorColoniaMessage.setMessageText(error.toString());
                break;
            /*case R.id.radioBtnTerms:
                //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                errorCheckMessage.setMessageText(error.toString());
                break;*/
        }
        //errorMessageView.setMessageText(error.toString());
        UI.hideKeyBoard(getActivity());
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.editStreet:
                errorStreetMessage.setVisibilityImageError(false);
                break;
            case R.id.editExtNumber:
                errorNumeroMessage.setVisibilityImageError(false);
                break;
            case R.id.editZipCode:
                errorZipCodeMessage.setVisibilityImageError(false);
                break;
            case R.id.spColonia:
                errorColoniaMessage.setVisibilityImageError(false);
                break;
            /*ase R.id.radioBtnTerms:
                errorCheckMessage.setVisibilityImageError(false);
                break;*/
        }

        //errorMessageView.setVisibilityImageError(false);
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
       // editZipCode.removeCustomTextWatcher(textWatcherZipCode);
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
        if (spColonia.getSelectedItem() != null && listaColonias != null) {
            colonia = spColonia.getSelectedItem().toString();
            for (ColoniasResponse coloniaInfo : listaColonias) {
                if (coloniaInfo.getColonia().equals(colonia)) {
                    Idcolonia = coloniaInfo.getColoniaId();
                }
            }
        }
    }

    private void setCPDataCurrent() {
        RegisterUser registerUser = RegisterUser.getInstance();
        //editZipCode.setText(registerUser.getCodigoPostal());
        this.estadoDomicilio = registerUser.getEstadoDomicilio();
        editState.setText(this.estadoDomicilio);
        for (int position = 0; position < coloniasNombre.size(); position++) {
            if (coloniasNombre.get(position).equals(registerUser.getColonia())) {
                spColonia.setSelection(position);
                break;
            }
        }

        setValidationRules();
    }

    private void setCurrentData() {
        RegisterUser registerUser = RegisterUser.getInstance();
        editStreet.setText(registerUser.getCalle());
        editExtNumber.setText(registerUser.getNumExterior());
        editIntNumber.setText(registerUser.getNumInterior());
//        radioBtnTerms.setSelected(false);
//        radioBtnTerms.setChecked(false);
        if (!registerUser.getCodigoPostal().isEmpty())
            cpDefault = true;

        setValidationRules();
        editZipCode.setText(registerUser.getCodigoPostal());
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
    public void showError(Object error) {

        if (!error.toString().isEmpty())
            // UI.showToastShort(error.toString(), getActivity());
            UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
                    new DialogDoubleActions() {
                        @Override
                        public void actionConfirm(Object... params) {

                        }

                        @Override
                        public void actionCancel(Object... params) {

                        }
                    },
                    true, false);
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
    public void clientCreatedSuccess(String message) {
        showLoader("");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                hideLoader();
                nextScreen(EVENT_GO_GET_CARD, null); // Mostramos la pantalla para obtener tarjeta.
            }
        }, DELAY_MESSAGE_PROGRESS);
    }

    @Override
    public void clientCreateFailed(String error) {
        showError(error);
    }

    @Override
    public void zipCodeInvalid(String message) {
        showValidationError(editZipCode.getId(), message);
        editZipCode.setIsInvalid();

    }

    private void setClickLegales() {
        SpannableString ss = new SpannableString(getString(R.string.terms_and_conditions));
        CustomClickableSpan span1 = new CustomClickableSpan() {
            @Override
            public void onClick(View textView) {
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    LegalsDialog legalsDialog = LegalsDialog.newInstance(TERMINOS);
                    legalsDialog.show(getActivity().getFragmentManager(), LegalsDialog.TAG);
                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }


            }
        };

        //"Al Continuar Reconozco Ser Mayor de Edad, Así Como Haber Leído y Aceptado los Términos y Condiciones y el Aviso de Privacidad"

        CustomClickableSpan span2 = new CustomClickableSpan() {
            @Override
            public void onClick(View textView) {
                boolean isOnline2 = Utils.isDeviceOnline();
                if (isOnline2) {
                    //loadFragment(LegalsFragment.newInstance(LegalsFragment.Legales.TERMINOS));
                    LegalsDialog legalsDialog = LegalsDialog.newInstance(PRIVACIDAD);
                    legalsDialog.show(getActivity().getFragmentManager(), LegalsDialog.TAG);
                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }


            }
        };

        ss.setSpan(span1, 78, 100, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), 78, 100, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss.setSpan(span2, 106, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccentTransparent)), 106, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        txtLegales.setText(ss);
        txtLegales.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onSpinnerClick() {
        hideValidationError(spColonia.getId());
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}

