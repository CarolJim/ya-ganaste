package com.pagatodo.yaganaste.modules.register.RegistroDomicilioPersonal;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAccountRegisterView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.IOnSpinnerClick;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.Custom_postal_code;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * btnNextSelectZip
 * A simple {@link Fragment} subclass.
 */
public class RegistroDomicilioPersonalFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms<Object>, IAccountRegisterView<Object>, IOnSpinnerClick, Custom_postal_code.OnCodeChangedListenerCP {
    public static int MIN_LENGHT_VALIDATION_CP = 4;
    @BindView(R.id.btnNextSelectZip)
    StyleButton btnNextSelectZip;
    @BindView(R.id.editZipCode)
    EditText editZipCode;
    @BindView(R.id.editIntNumber)
    EditText editIntNumber;
    @BindView(R.id.editExtNumber)
    EditText editExtNumber;
    @BindView(R.id.editStreet)
    EditText editStreet;

    @BindView(R.id.text_calle)
    TextInputLayout text_calle;

    @BindView(R.id.txtcoloria)
    LinearLayout txtcoloria;

    @BindView(R.id.text_num_exterior)
    TextInputLayout text_num_exterior;

    @BindView(R.id.text_num_interior)
    TextInputLayout text_num_interior;

    @BindView(R.id.text_cp)
    TextInputLayout text_cp;

    @BindView(R.id.spColonia)
    AppCompatSpinner spColonia;
    int positioncol;
    @BindView(R.id.imgcp)
    ImageView imgcp;

    @BindView(R.id.component_postal_code)
    Custom_postal_code component_postal_code;

    @BindView(R.id.aux_codepostal)
    EditText aux_codepostal;

    private AccountPresenterNew accountPresenter;

    private List<ColoniasResponse> listaColonias;
    private String estadoDomicilio = "";

    private ColoniasArrayAdapter adapterColonia;
    private List<String> coloniasNombre;

    View rootView;

    private String calle = "";
    private String numExt = "";
    private String numInt = "";
    private String codigoPostal = "";
    private String colonia = "";
    private String Idcolonia = "";


    private static RegActivity activityf;

    public RegistroDomicilioPersonalFragment() {
        // Required empty public constructor
    }

    public static RegistroDomicilioPersonalFragment newInstance(RegActivity activity) {
        activityf = activity;
        return new RegistroDomicilioPersonalFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = new AccountPresenterNew(getActivity());
        accountPresenter.setIView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_registro_domicilio_personal, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        activityf.nextStep();
        imgcp.setOnClickListener(view -> spColonia.performClick());
        btnNextSelectZip.setOnClickListener(this::onClick);
        coloniasNombre = new ArrayList<String>();
        coloniasNombre.add(getString(R.string.colonia));
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, coloniasNombre, this);
        spColonia.setAdapter(adapterColonia);

        component_postal_code.setListener(this);
        editExtNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editExtNumber.getId());
                    //  editExtNumber.imageViewIsGone(true);
                    text_num_exterior.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (editExtNumber.getText().toString().isEmpty()) {
                        //   showValidationError(editExtNumber.getId(), getString(R.string.datos_domicilio_num_ext));
                        //    editExtNumber.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_num_ext), Snackbar.LENGTH_SHORT);
                        text_num_exterior.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        //hideValidationError(editExtNumber.getId());
                        //  editExtNumber.setIsValid();
                        text_num_exterior.setBackgroundResource(R.drawable.inputtext_normal);
                        colorBoton();
                    }
                }
            }
        });


        editIntNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //    editIntNumber.imageViewIsGone(true);
                    text_num_interior.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    /*if (editIntNumber.getText().toString().isEmpty()) {
                        text_num_interior.setBackgroundResource(R.drawable.inputtext_error);
                    } else {*/
                    text_num_interior.setBackgroundResource(R.drawable.inputtext_normal);
                    //}
                }
            }
        });

        editStreet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editStreet.getId());
                    // editStreet.imageViewIsGone(true);
                    text_calle.setBackgroundResource(R.drawable.inputtext_active);

                } else {
                    if (editStreet.getText().toString().isEmpty()) {
                        //showValidationError(editStreet.getId(), getString(R.string.datos_domicilio_calle));

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_calle), Snackbar.LENGTH_SHORT);
                        text_calle.setBackgroundResource(R.drawable.inputtext_error);

                        //   editStreet.setIsInvalid();
                    } else {
                        hideValidationError(editStreet.getId());
                        // editStreet.setIsValid();
                        text_calle.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                    colorBoton();
                }
            }
        });

        spColonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorBoton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onSpinnerClick();
            }


        });


        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    component_postal_code.setVisibility(View.VISIBLE);
                    component_postal_code.setBackgroundResource(R.drawable.inputtext_active);
                    text_cp.setVisibility(View.GONE);
                    component_postal_code.getCode1().requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                }
            }
        });

        /*
        editZipCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                    text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                    showLoader(getString(R.string.search_zipcode));
                    imgcp.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                    accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

                    return true;
                } else {
                    colorBoton();
                    return false;
                }
            }
        });
        */

        editZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editZipCode.getText().length() == 5) {
                    text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                    showLoader(getString(R.string.search_zipcode));
                    imgcp.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                    accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*

        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editZipCode.getId());
                    text_cp.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (editZipCode.getText().toString().isEmpty()) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_cp), Snackbar.LENGTH_SHORT);
                        text_cp.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        hideValidationError(editZipCode.getId());
                        text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editZipCode.getId());
                    // editZipCode.imageViewIsGone(true);
                    text_cp.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (editZipCode.getText().toString().isEmpty()) {
                        //editZipCode.setIsInvalid();
                        showValidationError(editZipCode.getId(), getString(R.string.datos_domicilio_cp));

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_cp), Snackbar.LENGTH_SHORT);
                        text_cp.setBackgroundResource(R.drawable.inputtext_error);
                    } else if (editZipCode.getText().toString().length() > MIN_LENGHT_VALIDATION_CP) {
                        //   hideValidationError(editZipCode.getId());
                        // editZipCode.setIsValid();
                        text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                        //    showLoader(getString(R.string.search_zipcode));
                        //    accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP
                    }
                }
            }
        });
*/
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNextSelectZip:
                validateForm();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSpinnerClick() {
        colorBoton();
        txtcoloria.setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

    @Override
    public void setValidationRules() {

        /*
        editZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideValidationError(editZipCode.getId());
                    text_cp.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (editZipCode.getText().toString().isEmpty()) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_cp), Snackbar.LENGTH_SHORT);
                        text_cp.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        hideValidationError(editZipCode.getId());
                        text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                        colorBoton();
                    }
                }
            }
        });

        */

        editZipCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /* Write your logic here that will be executed when user taps next button */

                    text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                    showLoader(getString(R.string.search_zipcode));
                    accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP

                    handled = true;
                }

                return handled;
            }
        });

    }

    public void colorBoton() {
        getDataForm();
        boolean isValid = true;
        if (calle.isEmpty()) {
            isValid = false;
            btnNextSelectZip.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (numExt.isEmpty()) {
            isValid = false;
            btnNextSelectZip.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (codigoPostal.isEmpty()) {
            isValid = false;
            btnNextSelectZip.setBackgroundResource(R.drawable.button_rounded_gray);
        }

        if (spColonia.getSelectedItemPosition() == 0 || colonia.isEmpty()) {
            isValid = false;
            btnNextSelectZip.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (isValid) {
            btnNextSelectZip.setBackgroundResource(R.drawable.button_rounded_blue);
        }


    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (calle.isEmpty()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_calle), Snackbar.LENGTH_SHORT);
            text_calle.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (numExt.isEmpty()) {
            // showValidationError(editExtNumber.getId(), getString(R.string.datos_domicilio_num_ext));
            // editExtNumber.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_num_ext), Snackbar.LENGTH_SHORT);
            text_num_exterior.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (codigoPostal.isEmpty()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_cp), Snackbar.LENGTH_SHORT);
            component_postal_code.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }

        if (spColonia.getSelectedItemPosition() == 0 || colonia.isEmpty()) {
            // showValidationError(spColonia.getId(), getString(R.string.datos_domicilio_colonia));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_colonia), Snackbar.LENGTH_SHORT);
            txtcoloria.setBackgroundResource(R.drawable.inputtext_error);

            isValid = false;
        }
        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        RegisterUserNew registerUser = RegisterUserNew.getInstance();
        registerUser.setCalle(calle);
        registerUser.setNumExterior(numExt);
        registerUser.setNumInterior(numInt);
        registerUser.setCodigoPostal(codigoPostal);
        registerUser.setEstadoDomicilio(estadoDomicilio);
        registerUser.setColonia(colonia);
        registerUser.setIdColonia(Idcolonia);
        activityf.getRouter().showBusinessData(Direction.FORDWARD);
    }

    @Override
    public void getDataForm() {
        calle = editStreet.getText().toString().trim();
        numExt = editExtNumber.getText().toString().trim();
        numInt = editIntNumber.getText().toString().trim();
        codigoPostal = component_postal_code.getText().trim();
        if (spColonia.getSelectedItem() != null && listaColonias != null) {
            colonia = spColonia.getSelectedItem().toString();
            for (ColoniasResponse coloniaInfo : listaColonias) {
                if (coloniaInfo.getColonia().equals(colonia)) {
                    Idcolonia = coloniaInfo.getColoniaId();
                }
            }
        }

    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

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
        imgcp.setColorFilter(ContextCompat.getColor(getContext(), R.color.texthint), android.graphics.PorterDuff.Mode.SRC_IN);
        coloniasNombre = new ArrayList<String>();
        coloniasNombre.add(getString(R.string.colonia));
        adapterColonia = new ColoniasArrayAdapter(getContext(), R.layout.spinner_layout, coloniasNombre, this);
        spColonia.setAdapter(adapterColonia);


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
    public void clientCreatedSuccess(String message) {

    }

    @Override
    public void clientCreateFailed(String error) {

    }

    @Override
    public void zipCodeInvalid(String message) {
        showValidationError(editZipCode.getId(), message);
    }


    @Override
    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias) {
        hideLoader();
        UI.hideKeyBoard(getActivity());
        this.listaColonias = listaColonias;
        this.estadoDomicilio = listaColonias.get(0).getEstado();
        fillAdapter();
    }

    private void fillAdapter() {
        coloniasNombre.clear();
        coloniasNombre.add(getString(R.string.colonia));
        for (ColoniasResponse coloniasResponse : this.listaColonias) {
            coloniasNombre.add(coloniasResponse.getColonia());
        }
        adapterColonia.notifyDataSetChanged();
        String estado = this.estadoDomicilio;


    }

    @Override
    public void setCurrentAddress(DataObtenerDomicilio domicilio) {

    }

    @Override
    public void onCodeChanged() {
        if (component_postal_code.getText().length() == 5) {
            accountPresenter.getNeighborhoods(component_postal_code.getText().trim());//Buscamos por CP
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
            if (!component_postal_code.getText().isEmpty()) {
                imgcp.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            } else {
                imgcp.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_space), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }
    }
}
