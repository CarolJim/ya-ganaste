package com.pagatodo.yaganaste.ui.account.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
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

    private static int MIN_LENGHT_VALIDATION_PASS = 8;
    private static String CHECK_EMAIL_STATUS = "CHECK_EMAIL_STATUS";
    private View rootview;
    @BindView(R.id.edtitEmail)
    CustomValidationEditText editMail;
    @BindView(R.id.edtitConfirmEmail)
    CustomValidationEditText edtitConfirmEmail;
    @BindView(R.id.editPassword)
    CustomValidationEditText editPassword;
    @BindView(R.id.editPasswordConfirmation)
    CustomValidationEditText editPasswordConfirm;
    @BindView(R.id.btnNextDatosUsuario)
    StyleButton btnNextDatosUsuario;
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

    private String email = "";
    private String emailConfirmation = "";
    private String password = "";
    private String passwordConfirmation = "";
    private boolean isValidPassword = false;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.
    private AccountPresenterNew accountPresenter;
    private String passErrorMessage;

    private boolean errorIsShowed = false;

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

        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
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
        }
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

        editMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editMail.getText().isEmpty()) {
                        editMail.setIsInvalid();
                        showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                    }
                } else {
                    if (editMail.isValidText() && !emailValidatedByWS) {
                        accountPresenter.validateEmail(editMail.getText());
                    } else if (editMail.getText().isEmpty()) {
                        editMail.setIsInvalid();
                        showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                    } else if (editMail.isValidText() && emailValidatedByWS) {
                        hideErrorMessage(editMail.getId());
                        editMail.setIsValid();
                    }

                }
            }
        });


        editMail.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                emailValidatedByWS = false;
                if (editMail.getText().isEmpty()) {
                    showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                    editMail.setIsInvalid();
                } else if (!editMail.isValidText()) {
                    showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_formato));
                    editMail.setIsInvalid();
                } else if (editMail.isValidText()) {
                    hideErrorMessage(editMail.getId());
                    editMail.setIsValid();
                }

                if (!edtitConfirmEmail.getText().isEmpty() && !edtitConfirmEmail.getText().equals(editMail.getText())) {
                    showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
                    edtitConfirmEmail.setIsInvalid();
                } else if (edtitConfirmEmail.getText().isEmpty()) {
                    hideErrorMessage(edtitConfirmEmail.getId());
                    edtitConfirmEmail.imageViewIsGone(true);
                } else if (!edtitConfirmEmail.getText().isEmpty() && edtitConfirmEmail.getText().equals(editMail.getText())) {
                    hideErrorMessage(edtitConfirmEmail.getId());
                    edtitConfirmEmail.setIsValid();
                }
            }
        });


        edtitConfirmEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (edtitConfirmEmail.getText().isEmpty()) {
                        hideErrorMessage(edtitConfirmEmail.getId());
                        edtitConfirmEmail.imageViewIsGone(true);
                    } else if (!edtitConfirmEmail.getText().equals(editMail.getText())) {
                        showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
                        edtitConfirmEmail.setIsInvalid();
                    } else if (edtitConfirmEmail.getText().equals(editMail.getText())) {
                        hideErrorMessage(edtitConfirmEmail.getId());
                        edtitConfirmEmail.setIsValid();
                    }
                } else {
                    if (edtitConfirmEmail.getText().isEmpty()) {
                        hideErrorMessage(edtitConfirmEmail.getId());
                        edtitConfirmEmail.imageViewIsGone(true);
                    } else if (!edtitConfirmEmail.getText().isEmpty() && edtitConfirmEmail.getText().equals(editMail.getText())) {
                        hideErrorMessage(edtitConfirmEmail.getId());
                        edtitConfirmEmail.setIsValid();
                    } else if (!edtitConfirmEmail.getText().isEmpty() && !edtitConfirmEmail.getText().equals(editMail.getText())) {
                        showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
                        edtitConfirmEmail.setIsInvalid();
                    }
                }
            }
        });


        edtitConfirmEmail.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {

                if (edtitConfirmEmail.getText().isEmpty()) {
                    hideErrorMessage(edtitConfirmEmail.getId());
                    edtitConfirmEmail.imageViewIsGone(true);
                } else if (!editMail.getText().equals(edtitConfirmEmail.getText())) {
                    showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
                    edtitConfirmEmail.setIsInvalid();
                } else {
                    hideErrorMessage(edtitConfirmEmail.getId());
                    edtitConfirmEmail.setIsValid();
                }
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    if (editPassword.getText().isEmpty()) {
                        editPassword.setIsInvalid();
                        showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                    }
                } else {
                    if (editPassword.isValidText() && !isValidPassword) {
                        accountPresenter.validatePasswordFormat(editPassword.getText());
                    } else if (editPassword.getText().isEmpty()) {
                        editPassword.setIsInvalid();
                        showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                    } else if (editPassword.isValidText() && isValidPassword) {
                        hideErrorMessage(editPassword.getId());
                        editPassword.setIsValid();
                    }
                }
            }
        });


        editPassword.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void onTextChanged(String s, int start, int before, int count) {
                isValidPassword = false;
                if (editPassword.getText().isEmpty()) {
                    showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                    editPassword.setIsInvalid();
                } else if (!editPassword.isValidText()) {
                    showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
                    editPassword.setIsInvalid();
                } else if (editPassword.isValidText()) {
                    hideErrorMessage(editPassword.getId());
                    editPassword.setIsValid();
                }

                if (!editPasswordConfirm.getText().isEmpty() && !editPasswordConfirm.getText().equals(editPassword.getText())) {
                    showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                    editPasswordConfirm.setIsInvalid();
                } else if (editPasswordConfirm.getText().isEmpty()) {
                    hideErrorMessage(editPasswordConfirm.getId());
                    editPasswordConfirm.imageViewIsGone(true);
                } else if (!editPasswordConfirm.getText().isEmpty() && editPasswordConfirm.getText().equals(editPassword.getText())) {
                    hideErrorMessage(editPasswordConfirm.getId());
                    editPasswordConfirm.setIsValid();
                }
            }
        });

        editPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editPasswordConfirm.getText().isEmpty()) {
                        hideErrorMessage(editPasswordConfirm.getId());
                        editPasswordConfirm.imageViewIsGone(true);
                    } else if (!editPasswordConfirm.getText().equals(editPassword.getText())) {
                        showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                        editPasswordConfirm.setIsInvalid();
                    } else if (editPasswordConfirm.getText().equals(editPassword.getText())) {
                        hideErrorMessage(editPasswordConfirm.getId());
                        editPasswordConfirm.setIsValid();
                    }
                } else {
                    if (editPasswordConfirm.getText().isEmpty()) {
                        hideErrorMessage(editPasswordConfirm.getId());
                        editPasswordConfirm.imageViewIsGone(true);
                    } else if (!editPasswordConfirm.getText().isEmpty() && editPasswordConfirm.getText().equals(editPassword.getText())) {
                        hideErrorMessage(editPasswordConfirm.getId());
                        editPasswordConfirm.setIsValid();
                    } else if (!editPasswordConfirm.getText().isEmpty() && !editPasswordConfirm.getText().equals(editPassword.getText())) {
                        showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                        editPasswordConfirm.setIsInvalid();
                    }
                }
            }
        });

        editPasswordConfirm.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {

                if (editPasswordConfirm.getText().isEmpty()) {
                    hideErrorMessage(editPasswordConfirm.getId());
                    editPasswordConfirm.imageViewIsGone(true);
                } else if (!editPasswordConfirm.getText().equals(editPassword.getText())) {
                    showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                    editPasswordConfirm.setIsInvalid();
                } else {
                    hideErrorMessage(editPasswordConfirm.getId());
                    editPasswordConfirm.setIsValid();
                }
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        //boolean isValid = true;

        //Validate format Email
        if (!editMail.isValidText()) {
            showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_formato));
            editMail.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate if Email exist
        if (userExist) {
            showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_existe));
            editPasswordConfirm.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate Email is Empty
        if (email.replaceAll("\\s", "").isEmpty()) {
            showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
            editMail.setIsInvalid();
            //isValid = false;
            return;
        }

        //Valdiate Email confirm equals to Email
        if (!emailConfirmation.equals(email)) {
            showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
            edtitConfirmEmail.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate Email Confirm is empty
        if (emailConfirmation.isEmpty()) {
            showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_confirma));
            edtitConfirmEmail.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate if Password is empty
        if (password.isEmpty()) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
            editPassword.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate If Is a Valid Password and check if exist a Error message from the server
        if (!isValidPassword && passErrorMessage != null && !passErrorMessage.equals("")) {
            showValidationError(editPassword.getId(), passErrorMessage);
            editPassword.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate is Valid Password Format
        if (!editPassword.isValidText()) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
            editPassword.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate is valid Password Format from Web Service
        if (!isValidPassword) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
            //isValid = false;
            return;
        }

        //Validate if Password Confirmation is Empty
        if (passwordConfirmation.isEmpty()) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_confirm));
            editPasswordConfirm.setIsInvalid();
            //isValid = false;
            return;
        }

        //Validate Password Confirmation equals to Password
        if (!passwordConfirmation.equals(password)) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
            editPasswordConfirm.setIsInvalid();
            //isValid = false;
            return;
        }

        onValidationSuccess();
        /*if (isValid) {
            onValidationSuccess();
        }*/
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

    private void hideErrorMessage(int id) {
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
        nextScreen(EVENT_PERSONAL_DATA, null);//Mostramos la siguiente pantalla de registro.
    }

    @Override
    public void getDataForm() {
        email = editMail.getText().trim();
        emailConfirmation = edtitConfirmEmail.getText().trim();
        password = editPassword.getText().trim();
        passwordConfirmation = editPasswordConfirm.getText().trim();
    }

    private void setCurrentData() {
        RegisterUser registerUser = RegisterUser.getInstance();
        editMail.setText(registerUser.getEmail());
        edtitConfirmEmail.setText(registerUser.getEmail());
        editPassword.setText(registerUser.getContrasenia());
        editPasswordConfirm.setText(registerUser.getContrasenia());
        isValidPassword = true;
        emailValidatedByWS = true;
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
        if (!error.toString().isEmpty())
            //UI.showToastShort(error.toString(), getActivity());
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
    public void validationPasswordSucces() {//Mostrar imagen en edtText
        isValidPassword = true;
        editPassword.setIsValid();
        passErrorMessage = "";
        hideErrorMessage(editPassword.getId());
    }

    @Override
    public void validationPasswordFailed(String message) {//Mostrar imagen en edtText
        isValidPassword = false;
        passErrorMessage = message;
        showValidationError(editPassword.getId(), message);
        editPassword.setIsInvalid();
    }

    @Override
    public void isEmailAvaliable() {
        hideLoader();
        emailValidatedByWS = true;
        userExist = false;
        editMail.setIsValid();
        hideErrorMessage(editMail.getId());
    }

    @Override
    public void isEmailRegistered() {
        hideLoader();
        emailValidatedByWS = false;
        userExist = true;
        editMail.setIsInvalid();
        showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_existe));
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
        //hideErrorMessage();
    }

    private void validate(int field) {


    }

}