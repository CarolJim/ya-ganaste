package com.pagatodo.yaganaste.modules.register.CorreoUsuario;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomPassSixDigits;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroCorreoFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IUserDataRegisterView,
        View.OnFocusChangeListener, CustomPassSixDigits.OnCodeChangedListener {

    private static RegActivity activityf;
    @BindView(R.id.text_email)
    TextInputLayout text_email;
    @BindView(R.id.customPassSixDigits)
    CustomPassSixDigits customPassSixDigits;
    @BindView(R.id.edit_email)
    EditText editMail;
    @BindView(R.id.btnNextDatosUsuario)
    StyleButton btnNextDatosUsuario;
    @BindView(R.id.titulo_datos_usuario)
    TextView titulo_datos_usuario;
    @BindView(R.id.edit_text_aux)
    EditText edit_text_aux;
    @BindView(R.id.edit_text_aux_confirm)
    EditText edit_text_aux_confirm;
    @BindView(R.id.password)
    LinearLayout password;
    @BindView(R.id.text_password)
    TextInputLayout edt_psw;
    @BindView(R.id.edit_password)
    EditText edit_psw;
    @BindView(R.id.text_passwordconfirm)
    TextInputLayout edt_psw_confirm;
    @BindView(R.id.edit_passwordconfirm)
    EditText edit_psw_confirm;
    @BindView(R.id.activity_lets_start)
    LinearLayout linear_pass;
    @BindView(R.id.password_confirm)
    LinearLayout password_confirm;
    @BindView(R.id.customPassSixDigitsConfirm)
    CustomPassSixDigits customPassSixDigitsConfirm;
    boolean emailvalid=true;
    private View rootview;
    private Preferencias preferencias;
    private AccountPresenterNew accountPresenter;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.


    private String email = "";
    private String psd = "";
    private String psd_con = "";

    public static RegistroCorreoFragment newInstance() {
        return new RegistroCorreoFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityf = (RegActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferencias = App.getInstance().getPrefs();
        accountPresenter = new AccountPresenterNew(getActivity());
        accountPresenter.setIView(this);

    }

    public RegistroCorreoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_registro_correo, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        btnNextDatosUsuario.setOnClickListener(this);
        customPassSixDigits.setOnClickListener(this);
        customPassSixDigits.setListener(this);

        customPassSixDigitsConfirm.setOnClickListener(this);
        customPassSixDigitsConfirm.setListener(this);

        edit_psw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    password.setVisibility(View.VISIBLE);
                    edt_psw.setVisibility(View.GONE);
                    customPassSixDigits.getCode1().requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(customPassSixDigits.getCode1(), InputMethodManager.SHOW_IMPLICIT);
                } else {

                }
                isChecked();
            }
        });

        edit_psw_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    //password.setVisibility(View.VISIBLE);
                    password_confirm.setVisibility(View.VISIBLE);
                    edt_psw_confirm.setVisibility(View.GONE);
                    customPassSixDigitsConfirm.getCode1().requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(customPassSixDigitsConfirm.getCode1(), InputMethodManager.SHOW_IMPLICIT);
                } else {

                }
                isChecked();
            }
        });

        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextDatosUsuario) {
            validateForm();
            isChecked();

        }
        /*switch (view.getId()) {
            case R.id.btnNextDatosUsuario:
                validateForm();
                isChecked();
                break;
            case R.id.customPassSixDigits:
                customPassSixDigitsConfirm.clearCode();
                break;
        }*/
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void isEmailAvaliable() {
        hideLoader();
        emailValidatedByWS = true;
        userExist = false;
        text_email.setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void isEmailRegistered() {
        hideLoader();
        emailValidatedByWS = false;
        userExist = true;
        //editMail.setIsInvalid();
        text_email.setBackgroundResource(R.drawable.inputtext_error);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo_existe), Snackbar.LENGTH_LONG);
    }

    @Override
    public void validationPasswordSucces() {

    }

    @Override
    public void validationPasswordFailed(String message) {

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
    public void setValidationRules() {

        editMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //  hideValidationError(editMail.getId());
                    //editMail.imageViewIsGone(true);
                    emailValidatedByWS = false;
                    text_email.setBackgroundResource(R.drawable.inputtext_active);

                } else {
                    if (!UtilsNet.isOnline(getActivity())) {
                        //   editMail.setIsInvalid();
                        showValidationError(editMail.getId(), getString(R.string.no_internet_access));
                    } else if (editMail.getText().toString().isEmpty()) {
                        //    editMail.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (!ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && !emailValidatedByWS) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && !emailValidatedByWS) {
                        accountPresenter.validateEmail(editMail.getText().toString());
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim().toLowerCase()) && emailValidatedByWS) {
                        text_email.setBackgroundResource(R.drawable.inputtext_normal);
                        // hideValidationError(editMail.getId());
                        //editMail.setIsValid();
                    }

                }
                isChecked();
            }
        });

    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (email.isEmpty()) {
            //showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
            //  editNames.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.ingress_your_mail), Snackbar.LENGTH_SHORT);
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (userExist) {
            //showValidationError(editNames.getId(), getString(R.string.datos_personal_nombre));
            //  editNames.setIsInvalid();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            UI.showErrorSnackBar(getActivity(), getString(R.string.datos_usuario_correo_existe), Snackbar.LENGTH_SHORT);
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (psd.isEmpty()) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.psd), Snackbar.LENGTH_SHORT);
            edt_psw.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (psd_con.isEmpty()) {
            UI.showErrorSnackBar(getActivity(), getString(R.string.psd_con), Snackbar.LENGTH_SHORT);
            edt_psw_confirm.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }
        if (isValid) {
            setLogData();
        }
    }

    public void setLogData() {
        RegisterUserNew registerUser = RegisterUserNew.getInstance();
        registerUser.setEmail(email);
        registerUser.setContrasenia(psd);
        if (BuildConfig.DEBUG) {
            onValidationSuccess();
            //  accountPresenter.validatePersonDatanew();
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
        activityf.showFragmentDatosPersonales();
    }

    @Override
    public void getDataForm() {
        email = editMail.getText().toString();
        psd = customPassSixDigits.getText().toString();
        psd_con = customPassSixDigitsConfirm.getText().toString();
    }

    @Override
    public void onCodeChanged() {
        if (customPassSixDigitsConfirm.getText().toString().length() == 6) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(rootview.getWindowToken(), 0);
            if (TextUtils.equals(customPassSixDigits.getText(), customPassSixDigitsConfirm.getText())) {
                isChecked();
            } else {
                customPassSixDigitsConfirm.clearCode();
                customPassSixDigitsConfirm.getCode1().requestFocus();
                imm.showSoftInput(customPassSixDigitsConfirm.getCode1(), InputMethodManager.SHOW_IMPLICIT);
                UI.showErrorSnackBar(getActivity(), getString(R.string.password_invalid), Snackbar.LENGTH_LONG);
            }
        }
        //UI.showErrorSnackBar(getActivity(), getString(R.string.password_invalid), Snackbar.LENGTH_LONG);
        //edit_email
    }

    @Override
    public void setVisivility() {
        //customPassSixDigits.setVisibility(View.GONE);
        //titulo_datos_usuario.setVisibility(View.GONE);
        edt_psw_confirm.setVisibility(View.GONE);
        password_confirm.setVisibility(View.VISIBLE);
        customPassSixDigitsConfirm.getCode1().requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(customPassSixDigitsConfirm.getCode1(), InputMethodManager.SHOW_IMPLICIT);

    }

    public void isChecked() {
        getDataForm();
        boolean isValid = true;
        if (email == null || email.equals("") || userExist) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (psd == null || psd.equals("")) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (psd_con == null || psd_con.equals("")) {
            isValid = false;
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_gray);
        }
        if (isValid) {
            btnNextDatosUsuario.setBackgroundResource(R.drawable.button_rounded_blue);
        }
    }


}
