package com.pagatodo.yaganaste.ui.account.register;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PERSONAL_DATA;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DatosUsuarioFragment extends GenericFragment implements View.OnClickListener, ValidationForms, IUserDataRegisterView,
        View.OnFocusChangeListener {
    private Preferencias prefs = App.getInstance().getPrefs();
    private static int MIN_LENGHT_VALIDATION_PASS = 8;
    private static String CHECK_EMAIL_STATUS = "CHECK_EMAIL_STATUS";

/*
    @BindView(R.id.edtitEmail)
    CustomValidationEditText editMailold;

    @BindView(R.id.edtitConfirmEmail)
    CustomValidationEditText edtitConfirmEmaildos;
    @BindView(R.id.editPassword)
    CustomValidationEditText editPassworddos;
    @BindView(R.id.editPasswordConfirmation)
    CustomValidationEditText editPasswordConfirmdos;
*/

    @BindView(R.id.edit_email)
    EditText editMail;
    @BindView(R.id.edit_emailconfirm)
    EditText edtitConfirmEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_passwordconfirm)
    EditText editPasswordConfirm;

    @BindView(R.id.text_email)
    TextInputLayout text_email;

    @BindView(R.id.text_emailconfirm)
    TextInputLayout text_emailconfirm;

    @BindView(R.id.text_password)
    TextInputLayout text_password;

    @BindView(R.id.text_passwordconfirm)
    TextInputLayout text_passwordconfirm;

    @BindView(R.id.btnNextDatosUsuario)
    StyleButton btnNextDatosUsuario;
    @BindView(R.id.btnBackDatosUsuario)
    StyleButton btnBackDatosUsuario;
    @BindView(R.id.errorMailMessage)
    ErrorMessage errorMailMessage;
    @BindView(R.id.errorConfirmaMailMessage)
    ErrorMessage errorConfirmaMailMessage;
    @BindView(R.id.errorPasswordMessage)
    ErrorMessage errorPasswordMessage;
    @BindView(R.id.errorConfirmPasswordMessage)
    ErrorMessage errorConfirmPasswordMessage;
    //ErrorMessage errorMessageView;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    private View rootview;
    private String email = "";
    private String emailConfirmation = "";
    private String password = "";
    private String passwordConfirmation = "";
    private boolean isValidPassword = true;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.
    private AccountPresenterNew accountPresenter;
    private String passErrorMessage;
    private AppCompatImageView btnBack;
    private boolean errorIsShowed = false;
    private Preferencias preferencias;

    public DatosUsuarioFragment() {
    }

    public static DatosUsuarioFragment newInstance() {
        DatosUsuarioFragment fragmentRegister = new DatosUsuarioFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferencias = App.getInstance().getPrefs();
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
        btnBack = (AppCompatImageView) getActivity().findViewById(R.id.btn_back);
        preferencias.saveDataBool(Recursos.HUELLA_FAIL,true);
        //accountPresenter = new AccountPresenterNew(getActivity(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_datos_usuario, container, false);

        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        //errorMessageView.setVisibilityImageError(false);
        errorConfirmPasswordMessage.setVisibilityImageError(false);
        errorPasswordMessage.setVisibilityImageError(false);
        errorConfirmaMailMessage.setVisibilityImageError(false);
        errorMailMessage.setVisibilityImageError(false);

        btnNextDatosUsuario.setOnClickListener(this);
        btnBackDatosUsuario.setOnClickListener(this);
        edtitConfirmEmail.setOnFocusChangeListener(this);
        editPassword.setOnFocusChangeListener(this);
        editPasswordConfirm.setOnFocusChangeListener(this);

        setCurrentData();// Seteamos datos si hay registro en proceso.
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextDatosUsuario) {
            validateForm();
        } else if (view.getId() == R.id.btnBackDatosUsuario) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibilityBack(true);
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {
        editMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //  hideValidationError(editMail.getId());
                    //editMail.imageViewIsGone(true);
                    text_email.setBackgroundResource(R.drawable.inputtext_active);

                } else {
                    if (!UtilsNet.isOnline(getActivity())) {
                        //   editMail.setIsInvalid();
                        showValidationError(editMail.getId(), getString(R.string.no_internet_access));
                    }else if (editMail.getText().toString().isEmpty()) {
                        //    editMail.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (!ValidateForm.isValidEmailAddress(editMail.getText().toString()) && !emailValidatedByWS) {

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        text_email.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString().trim())  && !emailValidatedByWS) {
                        accountPresenter.validateEmail(editMail.getText().toString());
                    } else if (ValidateForm.isValidEmailAddress(editMail.getText().toString()) && emailValidatedByWS) {
                        text_email.setBackgroundResource(R.drawable.inputtext_normal);
                        // hideValidationError(editMail.getId());
                        //editMail.setIsValid();
                    }
                }
            }
        });

        /*
        editMail.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editMail.getId());
                editMail.imageViewIsGone(true);
                emailValidatedByWS = false;
            }
        });
        */

        edtitConfirmEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //hideValidationError(edtitConfirmEmail.getId());
                    // edtitConfirmEmail.imageViewIsGone(true);
                    text_emailconfirm.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (edtitConfirmEmail.getText().toString().isEmpty()) {
                        //   showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_confirma));
                        //   edtitConfirmEmail.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo_confirma), Snackbar.LENGTH_SHORT);
                        text_emailconfirm.setBackgroundResource(R.drawable.inputtext_error);
                    } else if (!edtitConfirmEmail.getText().toString().isEmpty() && !edtitConfirmEmail.getText().toString().equals(editMail.getText().toString())) {
                        //showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        text_emailconfirm.setBackgroundResource(R.drawable.inputtext_error);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo_no_coinciden), Snackbar.LENGTH_SHORT);
                        //edtitConfirmEmail.setIsInvalid();
                    } else if (edtitConfirmEmail.getText().toString().equals(editMail.getText().toString())) {
                        /// hideValidationError(edtitConfirmEmail.getId());
                        //edtitConfirmEmail.setIsValid();
                        text_emailconfirm.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        /*
        edtitConfirmEmail.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(edtitConfirmEmail.getId());
                edtitConfirmEmail.imageViewIsGone(true);
            }
        });
        */


        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // hideValidationError(editPassword.getId());
                    // editPassword.imageViewIsGone(true);
                    text_password.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (!UtilsNet.isOnline(getActivity())) {
                        //  editPassword.setIsInvalid();
                        //  showValidationError(editPassword.getId(), getString(R.string.no_internet_access));
                    }else if (editPassword.getText().toString().isEmpty()) {
                        // editPassword.setIsInvalid();
                        // showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pass), Snackbar.LENGTH_SHORT);
                        text_password.setBackgroundResource(R.drawable.inputtext_error);
                    }  else if (isValidPassword) {
                        // hideValidationError(editPassword.getId());
                        // editPassword.setIsValid();
                        text_password.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                }
            }
        });

        /*
        editPassword.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editPassword.getId());
                editPassword.imageViewIsGone(true);
                isValidPassword = false;
            }
        });
        */

        editPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //hideValidationError(editPasswordConfirm.getId());
                    //editPasswordConfirm.imageViewIsGone(true);
                    text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_active);
                } else {
                    if (editPasswordConfirm.getText().toString().isEmpty()) {
                        // showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_confirm));
                        // editPasswordConfirm.setIsInvalid();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pass_confirm), Snackbar.LENGTH_SHORT);
                        text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_error);
                    } else if (!editPasswordConfirm.getText().toString().isEmpty() && !editPasswordConfirm.getText().toString().equals(editPassword.getText().toString())) {
                        //showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                        //editPasswordConfirm.setIsInvalid();

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pass_no_coinciden), Snackbar.LENGTH_SHORT);
                        text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_error);

                    } else if (editPasswordConfirm.getText().toString().equals(editPassword.getText().toString())) {
                        //  hideValidationError(editPasswordConfirm.getId());
                        //  editPasswordConfirm.setIsValid();
                        text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_normal);

                    }
                }
            }
        });


        /*
        editPasswordConfirm.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editPasswordConfirm.getId());
                editPasswordConfirm.imageViewIsGone(true);
            }
        });
        */

    }
    public void setVisibilityBack(boolean mBoolean){
        if (mBoolean) {
            btnBack.setVisibility(View.VISIBLE);
        }else {
            btnBack.setVisibility(View.GONE);
        }
    }
    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;

        //Validate format Email
        if (!ValidateForm.isValidEmailAddress(editMail.getText().toString())) {
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
            isValid = false;
            //return;
        }

        //Validate if Email exist
        if (userExist) {
            //  showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_existe));
            // editPasswordConfirm.setIsInvalid();
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }


        //Validate Email is Empty
        if (email.replaceAll("\\s", "").isEmpty()) {
            //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
            //editMail.setIsInvalid();
            text_email.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }

        //Valdiate Email confirm equals to Email
        if (!emailConfirmation.equals(email)) {
            // showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
            //edtitConfirmEmail.setIsInvalid();
            text_emailconfirm.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }

        //Validate Email Confirm is empty
        if (emailConfirmation.isEmpty()) {
            //   showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_confirma));
            //edtitConfirmEmail.setIsInvalid();
            text_emailconfirm.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }


        //Validate If Is a Valid Password and check if exist a Error message from the server
        if (!isValidPassword && passErrorMessage != null && !passErrorMessage.equals("")) {
            //  showValidationError(editPassword.getId(), passErrorMessage);
            // editPassword.setIsInvalid();
            text_password.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }



        //Validate if Password is empty
        if (password.isEmpty()) {
            // showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
            // editPassword.setIsInvalid();
            text_password.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }

        //Validate is valid Password Format from Web Service
        if (!isValidPassword) {
            //  showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
            isValid = false;
            //return;
        }


        //Validate Password Confirmation equals to Password
        if (!passwordConfirmation.equals(password)) {
            // showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
            //editPasswordConfirm.setIsInvalid();
            text_passwordconfirm.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }

        //Validate if Password Confirmation is Empty
        if (passwordConfirmation.isEmpty()) {
            //showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_confirm));
            //editPasswordConfirm.setIsInvalid();
            text_password.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
            //return;
        }

        //onValidationSuccess();
        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.edtitEmail:
                errorMailMessage.setMessageText(error.toString());
                break;
            case R.id.edtitConfirmEmail:
                errorConfirmaMailMessage.setMessageText(error.toString());
                break;
            case R.id.editPassword:
                errorPasswordMessage.setMessageText(error.toString());
                break;
            case R.id.editPasswordConfirmation:
                errorConfirmPasswordMessage.setMessageText(error.toString());
                break;
        }

        //errorMessageView.setMessageText(error.toString());
        errorIsShowed = true;
    }

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.edtitEmail:
                errorMailMessage.setVisibilityImageError(false);
                break;
            case R.id.edtitConfirmEmail:
                errorConfirmaMailMessage.setVisibilityImageError(false);
                break;
            case R.id.editPassword:
                errorPasswordMessage.setVisibilityImageError(false);
                break;
            case R.id.editPasswordConfirmation:
                errorConfirmPasswordMessage.setVisibilityImageError(false);
                break;
        }
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    @Override
    public void onValidationSuccess() {
        /*Guardamos datos en Singleton de registro.*/
        errorConfirmPasswordMessage.setVisibilityImageError(false);
        errorPasswordMessage.setVisibilityImageError(false);
        errorConfirmaMailMessage.setVisibilityImageError(false);
        errorMailMessage.setVisibilityImageError(false);

        RegisterUser registerUser = RegisterUser.getInstance();
        registerUser.setEmail(email);
        registerUser.setContrasenia(password);
        App.getInstance().setCadenaHuella(password);

        nextScreen(EVENT_PERSONAL_DATA, null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        email = editMail.getText().toString().trim();
        emailConfirmation = edtitConfirmEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        passwordConfirmation = editPasswordConfirm.getText().toString().trim();
    }

    private void setCurrentData() {
        RegisterUser registerUser = RegisterUser.getInstance();
        editMail.setText(registerUser.getEmail());
        edtitConfirmEmail.setText(registerUser.getEmail());
        editPassword.setText(registerUser.getContrasenia());
        editPasswordConfirm.setText(registerUser.getContrasenia());
        if (!registerUser.getEmail().isEmpty()) {
            isValidPassword = true;
            emailValidatedByWS = true;
        }
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
    public void showError(Object error) {
        if (!error.toString().isEmpty()) {
//            UI.createSimpleCustomDialog("", error.toString(), getFragmentManager(),
//                    new DialogDoubleActions() {
//                        @Override
//                        public void actionConfirm(Object... params) {
//
//                        }
//z
//                        @Override
//                        public void actionCancel(Object... params) {
//
//                        }
//                    },
//                    true, false);
        }
    }

    @Override
    public void validationPasswordSucces() {//Mostrar imagen en edtText
        isValidPassword = true;
        // editPassword.setIsValid();
        passErrorMessage = "";
        hideValidationError(editPassword.getId());
    }

    @Override
    public void validationPasswordFailed(String message) {//Mostrar imagen en edtText
        isValidPassword = false;
        passErrorMessage = message;
        //  showValidationError(editPassword.getId(), message);
        // editPassword.setIsInvalid();
    }

    @Override
    public void isEmailAvaliable() {
        hideLoader();
        emailValidatedByWS = true;
        userExist = false;
        //   editMail.setIsValid();
        //   hideValidationError(editMail.getId());
        text_email.setBackgroundResource(R.drawable.inputtext_normal);
    }

    @Override
    public void isEmailRegistered() {
        hideLoader();
        emailValidatedByWS = false;
        userExist = true;
        //editMail.setIsInvalid();
        text_email.setBackgroundResource(R.drawable.inputtext_error);
        UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo_existe), Snackbar.LENGTH_SHORT);
        //showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_existe));
    }

    private CustomErrorDialog createCustomDialog(String title, String message) {
        CustomErrorDialog customErrorDialog = CustomErrorDialog.getInstance(R.layout.dialog_custom_error_message, title, message, true, false);
        customErrorDialog.setDialogActions(new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {

            }

            @Override
            public void actionCancel(Object... params) {

            }
        });
        return customErrorDialog;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
        }
        //hideValidationError();
    }

    private void validate(int field) {

    }

}