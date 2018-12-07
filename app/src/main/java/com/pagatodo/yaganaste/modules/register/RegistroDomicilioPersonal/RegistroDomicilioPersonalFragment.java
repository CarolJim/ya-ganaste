package com.pagatodo.yaganaste.modules.register.RegistroDomicilioPersonal;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.account.register.adapters.ColoniasArrayAdapter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**btnNextSelectZip
 * A simple {@link Fragment} subclass.
 */
public class RegistroDomicilioPersonalFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms<Object>, IAccountRegisterView<Object>, IOnSpinnerClick {
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

    @BindView(R.id.text_num_exterior)
    TextInputLayout text_num_exterior;

    @BindView(R.id.text_num_interior)
    TextInputLayout text_num_interior;

    @BindView(R.id.text_cp)
    TextInputLayout text_cp;
    private AccountPresenterNew accountPresenter;

    View rootView;



    private String calle = "";
    private String numExt = "";
    private String numInt = "";
    private String codigoPostal = "";



    private static RegActivity activityf;

    public RegistroDomicilioPersonalFragment() {
        // Required empty public constructor
    }

    public static RegistroDomicilioPersonalFragment newInstance (RegActivity activity) {
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
        ButterKnife.bind(this,rootView);
        btnNextSelectZip.setOnClickListener(this::onClick);

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
                        hideValidationError(editZipCode.getId());
                        // editZipCode.setIsValid();
                        text_cp.setBackgroundResource(R.drawable.inputtext_normal);
                     //   showLoader(getString(R.string.search_zipcode));
                       // accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP
                    }
                }
            }
        });

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

    }

    @Override
    public void onSubSpinnerClick() {

    }

    @Override
    public void hideKeyBoard() {

    }

    @Override
    public void setValidationRules() {
        //  editZipCode.addCustomTextWatcher(textWatcherZipCode);

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
                }
            }
        });

        /*

        editStreet.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editStreet.getId());
                //       editStreet.imageViewIsGone(true);
            }
        });

        */


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

                    }
                }
            }
        });

        /*
        editExtNumber.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editExtNumber.getId());
                editExtNumber.imageViewIsGone(true);
            }
        });
        */

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
            text_cp.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (isValid)
        {
            if (editZipCode.getText().toString().length() > MIN_LENGHT_VALIDATION_CP) {
            hideValidationError(editZipCode.getId());
            // editZipCode.setIsValid();
            text_cp.setBackgroundResource(R.drawable.inputtext_normal);
            showLoader(getString(R.string.search_zipcode));
            accountPresenter.getNeighborhoods(editZipCode.getText().toString().toString().trim());//Buscamos por CP
        }else {
                UI.showErrorSnackBar(getActivity(), getString(R.string.datos_domicilio_cp), Snackbar.LENGTH_SHORT);
                text_cp.setBackgroundResource(R.drawable.inputtext_error);
                isValid = false;
            }
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
/*        registerUser.setEstadoDomicilio(estado);
        registerUser.setColonia(colonia);
        registerUser.setIdColonia(Idcolonia);*/
        activityf.showFragmentDomicilioSelectCP();
    }

    @Override
    public void getDataForm() {
        calle = editStreet.getText().toString().trim();
        numExt = editExtNumber.getText().toString().trim();
        numInt = editIntNumber.getText().toString().trim();
        codigoPostal = editZipCode.getText().toString().trim();

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
        onValidationSuccess();
    }

    @Override
    public void setCurrentAddress(DataObtenerDomicilio domicilio) {

    }
}
