package com.pagatodo.yaganaste.ui.adquirente.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IDatosNegView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.DatosNegocioPresenter;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.SubBussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.utils.NumberTagPase;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_PRE_ADDRESS;
import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_SET_BUSINESS_LIST;
import static com.pagatodo.yaganaste.ui._controllers.DetailsActivity.EVENT_CLOSE_ACT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui.preferuser.ListaOpcionesFragment.USER_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;

public class DatosNegocioFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms, INavigationView<Object, ErrorObject>, IDatosNegView<ErrorObject>,
        DialogDoubleActions, IOnSpinnerClick {

    private static final String GIROS = "1";

    @BindView(R.id.editBussinesNameold)
    CustomValidationEditText editBussinesNameold;
    @BindView(R.id.spinnerBussineLine)
    Spinner spinnerBussineLine;
    @BindView(R.id.spinnerSubBussineLine)
    Spinner spinnerSubBussineLine;
    @BindView(R.id.editBussinesPhoneold)
    CustomValidationEditText editBussinesPhoneold;

    @BindView(R.id.editBussinesName)
    EditText editBussinesName;

    @BindView(R.id.editBussinesPhone)
    EditText editBussinesPhone;

    @BindView(R.id.text_nombrenegoci)
    TextInputLayout text_nombrenegoci;

    @BindView(R.id.text_telefono)
    TextInputLayout text_telefono;

    @BindView(R.id.text_lada)
    TextInputLayout text_lada;

    @BindView(R.id.editBussinesLada)
    EditText editBussinesLada;

    @BindView(R.id.txtgiro)
    LinearLayout txtgiro;

    @BindView(R.id.txtsubgiro)
    LinearLayout txtsubgiro;

    @BindView(R.id.titulo_datos_usuario)
    StyleTextView titulo_datos_usuario;

    @BindView(R.id.textgiro)
    StyleTextView textgiro;

    @BindView(R.id.textsubgiro)
    StyleTextView textsubgiro;

    @BindView(R.id.spiner1)
    ImageView spiner1;

    @BindView(R.id.spiner12)
    ImageView spiner12;


    /*@BindView(R.id.btnBackBussinesInfo)
    Button btnBackBussinesInfo;*/
    @BindView(R.id.btnNextBussinesInfo)
    Button btnNextBussinesInfo;
    @BindView(R.id.errorBussinesNameMessage)
    ErrorMessage errorName;
    @BindView(R.id.errorBussineLineMessage)
    ErrorMessage errorBussineLine;
    @BindView(R.id.errorSubBussineLineMessage)
    ErrorMessage errorSubBussineLineMessage;
    @BindView(R.id.errorBussinesPhoneMessage)
    ErrorMessage errorPhone;

    private View rootview;
    private String nombre = "";
    private String telefono = "";
    private String lada = "";

    private List<Giros> girosComercio;

    private BussinesLineSpinnerAdapter giroArrayAdapter;
    private SubBussinesLineSpinnerAdapter subgiroArrayAdapter;
    private DatosNegocioPresenter datosNegocioPresenter;

    public static DatosNegocioFragment newInstance(List<Giros> girosComercio) {
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
                this.girosComercio = (List<Giros>) subgiros;
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

    @SuppressLint("StringFormatInvalid")
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        titulo_datos_usuario.setText(String.format(getString(R.string.sub_titulo_datos_personales), App.getInstance().getPrefs().loadData(NAME_USER)));
        if (girosComercio == null) {
            girosComercio = new ArrayList<>();
            Giros giroHint = new Giros();
            giroHint.setGiro(getString(R.string.giro_commerce_spinner));
            giroHint.setIdGiro(-1);
            List<SubGiro> subgiroHint = new ArrayList<SubGiro>();
            subgiroHint.add(new SubGiro(-1, getString(R.string.subgiro_commerce)));
            giroHint.setListaSubgiros(subgiroHint);
            girosComercio.add(giroHint);
        }

        /*btnBackBussinesInfo.setOnClickListener(this);*/
        btnNextBussinesInfo.setOnClickListener(this);
        giroArrayAdapter = new BussinesLineSpinnerAdapter(getActivity(),
                R.layout.spinner_layout, girosComercio, this);
        spinnerBussineLine.setAdapter(giroArrayAdapter);

        spiner1.setOnClickListener(view -> spinnerBussineLine.performClick());

        spiner12.setOnClickListener(view -> spinnerSubBussineLine.performClick());

        spinnerBussineLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<SubGiro> list = girosComercio.get(position).getListaSubgiros();
                if (!list.contains(new SubGiro(-1, getString(R.string.subgiro_commerce)))) {
                    Collections.sort(list, new Comparator<SubGiro>() {
                        @Override
                        public int compare(SubGiro countries, SubGiro t1) {
                            return countries.getSubgiro().compareTo(t1.getSubgiro());
                        }
                    });
                    list.add(0, new SubGiro(-1, getString(R.string.subgiro_commerce)));
                }
                subgiroArrayAdapter = new SubBussinesLineSpinnerAdapter(getActivity(),
                        R.layout.spinner_layout, list, DatosNegocioFragment.this);
                spinnerSubBussineLine.setAdapter(subgiroArrayAdapter);
                if (list.size() == 2) {
                    spinnerSubBussineLine.setSelection(1);
                }
                onSpinnerClick();
                if (position != 0) {
                    textgiro.setVisibility(View.VISIBLE);
                    textgiro.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }


        });

        spinnerSubBussineLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSubSpinnerClick();
                if (position != 0) {
                    textsubgiro.setVisibility(View.VISIBLE);
                    textsubgiro.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSubSpinnerClick();
            }
        });
        //editBussinesPhone.addTextChangedListener(new PhoneTextWatcher(editBussinesPhone));
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
    public void setGiros(List<Giros> giros) {
        Collections.sort(giros, new Comparator<Giros>() {
            @Override
            public int compare(Giros countries, Giros t1) {
                return countries.getGiro().compareTo(t1.getGiro());
            }
        });
        girosComercio.addAll(giros);
        setCurrentData();// Seteamos datos si hay registro en proceso.
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.btnBackBussinesInfo:
                RegisterAgent.resetRegisterAgent();
                getActivity().finish();
                break;*/
            case R.id.btnNextBussinesInfo:
                validateForm();
                break;
            default:
                break;
        }
    }

    @Override
    public void setValidationRules() {
        editBussinesName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideValidationError(editBussinesName.getId());
                //editBussinesName.imageViewIsGone(true);
                text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_active);

            } else {
                if (editBussinesName.getText().toString().isEmpty()) {
                    // showValidationError(editBussinesName.getId(), getString(R.string.datos_negocio_nombre));
                    //  editBussinesName.setIsInvalid();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                    text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_nombre), Snackbar.LENGTH_SHORT);
                } else {
                    //hideValidationError(editBussinesName.getId());
                    text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_normal);
                    //  editBussinesName.setIsValid();
                }
            }
        });

        editBussinesPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                //   hideValidationError(editBussinesPhone.getId());
                //      editBussinesPhone.imageViewIsGone(true);
                text_telefono.setBackgroundResource(R.drawable.inputtext_active);
            } else {

                telefono = editBussinesPhone.getText().toString();
                telefono = telefono.replaceAll(" ", "");

                if (editBussinesPhone.getText().toString().isEmpty()) {
                    //  showValidationError(editBussinesPhone.getId(), getString(R.string.datos_negocio_telefono));
                    //        editBussinesPhone.setIsInvalid();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    text_telefono.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_telefono), Snackbar.LENGTH_SHORT);
                } else if (telefono.length() < 7) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    text_telefono.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_telefono_incorrecto), Snackbar.LENGTH_SHORT);
                    //showValidationError(editBussinesPhone.getId(), getString(R.string.datos_telefono_incorrecto));
                    // editBussinesPhone.setIsInvalid();
                } else if (isPhone(telefono)) {
                    hideValidationError(editBussinesPhone.getId());
                    text_telefono.setBackgroundResource(R.drawable.inputtext_normal);
                    //  editBussinesPhone.setIsValid();
                }
            }
        });

        editBussinesLada.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {

                text_lada.setBackgroundResource(R.drawable.inputtext_active);
            } else {

                lada = editBussinesLada.getText().toString();
                lada = lada.replaceAll(" ", "");


                if (editBussinesLada.getText().toString().isEmpty()) {

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    text_lada.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_lada), Snackbar.LENGTH_SHORT);
                } else if (!isLada(lada)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    text_lada.setBackgroundResource(R.drawable.inputtext_error);
                    UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_lada), Snackbar.LENGTH_SHORT);

                } else if (isLada(lada)) {
                    hideValidationError(editBussinesLada.getId());
                    text_lada.setBackgroundResource(R.drawable.inputtext_normal);

                }
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (nombre.isEmpty()) {
            //   showValidationError(editBussinesName.getId(), getString(R.string.datos_negocio_nombre));
            // editBussinesName.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
            text_nombrenegoci.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_nombre), Snackbar.LENGTH_SHORT);
            isValid = false;
        }
        if (giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()).getIdGiro() == -1) {
            //showValidationError(spinnerBussineLine.getId(), getString(R.string.datos_negocio_giro));
            txtgiro.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (subgiroArrayAdapter.getItem(spinnerSubBussineLine.getSelectedItemPosition()).getIdSubgiro() == -1) {
            //showValidationError(spinnerSubBussineLine.getId(), getString(R.string.datos_negocio_subgiro));
            txtsubgiro.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        telefono = editBussinesPhone.getText().toString();
        telefono = telefono.replaceAll(" ", "");

        if (telefono.length() < 7 || editBussinesPhone.getText().toString().equalsIgnoreCase("00000000")) {
            // showValidationError(editBussinesPhone.getId(), getString(R.string.datos_telefono_incorrecto));
            //    editBussinesPhone.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            text_telefono.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_telefono_incorrecto), Snackbar.LENGTH_SHORT);
            isValid = false;
        }
        if (telefono.isEmpty()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            text_telefono.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_telefono), Snackbar.LENGTH_SHORT);
            isValid = false;
        }

        lada = editBussinesLada.getText().toString();
        lada = lada.replaceAll(" ", "");

        if (lada.length() < 2 || editBussinesLada.getText().toString().equalsIgnoreCase("000")) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            text_lada.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_lada_incorrecto), Snackbar.LENGTH_SHORT);
            isValid = false;
        }

        if (lada.isEmpty()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            text_telefono.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_negocio_lada), Snackbar.LENGTH_SHORT);
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }
    }

    private boolean isPhone(String input) {
        return Pattern.compile("\\d{10}").matcher(input).find();
    }

    private boolean isLada(String input) {
        return Pattern.compile("\\d{3}").matcher(input).find() ||
                Pattern.compile("\\d{2}").matcher(input).find();
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
            case R.id.spinnerSubBussineLine:
                errorSubBussineLineMessage.setMessageText(error.toString());
                break;
            case R.id.editBussinesPhone:
                errorPhone.setMessageText(error.toString());
                break;
            case R.id.editBussinesLada:

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
            case R.id.spinnerSubBussineLine:
                errorSubBussineLineMessage.setVisibilityImageError(false);
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
        registerAgent.setTelefono(lada
                + telefono);
        registerAgent.setLada(lada);
        registerAgent.setGiro(giroArrayAdapter.getItem(spinnerBussineLine.getSelectedItemPosition()));
        registerAgent.setSubGiros(subgiroArrayAdapter.getItemSelected(spinnerSubBussineLine.getSelectedItemPosition()));
        onEventListener.onEvent(EVENT_SET_BUSINESS_LIST, girosComercio);
        nextScreen(EVENT_GO_BUSSINES_PRE_ADDRESS, null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        nombre = editBussinesName.getText().toString();
        telefono = editBussinesPhone.getText().toString();
        telefono = telefono.replaceAll(" ", "");
    }

    private void setCurrentData() {
        RegisterAgent registerAgent = RegisterAgent.getInstance();
        if (registerAgent.getNombre().isEmpty()) {
            return;
        }

        editBussinesName.setText(registerAgent.getNombre());
        editBussinesPhone.setText(registerAgent.getTelefono());
        editBussinesLada.setText(registerAgent.getLada());
        spinnerBussineLine.setSelection(giroArrayAdapter.getItemPosition(registerAgent.getGiro()));
        //spinnerSubBussineLine.setSelection(subgiroArrayAdapter.getItemPosition(registerAgent.getSubGiros()));
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
        if (onEventListener != null) {
            onEventListener.onEvent(EVENT_HIDE_LOADER, null);
        }
    }

    @Override
    public void showError(ErrorObject error) {
        error.setHasConfirm(true);
        error.setHasCancel(false);
        error.setErrorActions(this);
        showDialogMesage(error.getErrorMessage());
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
        editBussinesLada.clearFocus();
        spinnerBussineLine.requestFocus();
        txtgiro.setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void hideKeyBoard() {
        // getActivity().getWindow().setSoftInputMode(
        //       WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        UI.hideKeyBoard(getActivity());
    }

    @Override
    public void onSubSpinnerClick() {
        txtsubgiro.setBackgroundResource(R.drawable.inputtext_normal);
        hideValidationError(spinnerSubBussineLine.getId());
        editBussinesName.clearFocus();
        editBussinesPhone.clearFocus();
        editBussinesLada.clearFocus();
        spinnerSubBussineLine.requestFocus();
        UI.hideKeyBoard(getActivity());
    }

    private void showDialogMesage(final String mensaje) {
        UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name), mensaje,
                R.string.title_aceptar, (dialogInterface, i) -> {

                });
    }
}

