package com.pagatodo.yaganaste.ui.account.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_PERSONAL_DATA;

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
    private boolean isValidPassword = true;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.
    private AccountPresenterNew accountPresenter;

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
        switch (view.getId()) {
            case R.id.btnNextDatosUsuario:
                validateForm();
                break;
            default:
                break;
        }
    }

    /*Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

        editMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editMail.isValidText() && !emailValidatedByWS) {
                        // Validamos el email con WS.
                        editMail.imageViewIsGone(true);
                        accountPresenter.validateEmail(editMail.getText());
                    } else if (editMail.getText().isEmpty()) {
                        editMail.setIsInvalid();
                        showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
                    } else {
                        showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_formato));
                    }
                } else {
                    if (!editMail.imageViewIsVisible())
                        hideErrorMessage(editMail.getId());
                }
            }
        });


        edtitConfirmEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editMail.getText().isEmpty() || !editMail.isValidText()) {
                        editMail.setIsInvalid();
                        showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo));
                        editMail.requestEditFocus();
                    }
                } else {
                    if (editMail.isValidText() && edtitConfirmEmail.isValidText()) {
                        hideErrorMessage(edtitConfirmEmail.getId());
                        editPassword.imageViewIsGone(true);
                        editPasswordConfirm.imageViewIsGone(true);
                    }
                }
                /*else{
                    if(!errorIsShowed) {
                        if (edtitConfirmEmail.getText().isEmpty()) {
                            showValidationError(getString(R.string.datos_usuario_correo_confirma));
                        } else if (!edtitConfirmEmail.isValidText()) {
                            showValidationError(getString(R.string.datos_usuario_correo_formato));
                        } else if (!edtitConfirmEmail.getText().equals(editMail.getText())) {
                            showValidationError(getString(R.string.datos_usuario_correo_no_coinciden));
                        }

                        edtitConfirmEmail.setIsInvalid();
                    }
                }*/
            }
        });

        editMail.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hideErrorMessage(editMail.getId());
                edtitConfirmEmail.setText("");
                edtitConfirmEmail.imageViewIsGone(true);

                if (editMail.isValidText() && emailValidatedByWS) {
                    emailValidatedByWS = false;//Si esta validado y cambia, volvemos a solicitar la validacion.
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editMail.isValidText()) {
                        if (edtitConfirmEmail.getText().isEmpty()) {
                            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_correo_confirma));
                            edtitConfirmEmail.setIsInvalid();
                        } else if (!edtitConfirmEmail.getText().equals(editMail.getText())) {
                            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
                            edtitConfirmEmail.setIsInvalid();
                        }
                    }

                    if (!edtitConfirmEmail.isValidText())
                        edtitConfirmEmail.requestEditFocus();
                } else {
                    if (editMail.isValidText() && edtitConfirmEmail.isValidText()) {
                        if (editPassword.getText().isEmpty()) {
                            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
                            editPassword.setIsInvalid();
                            editPassword.requestEditFocus();
                        } else if (editPassword.getText().length() >= MIN_LENGHT_VALIDATION_PASS) {
                            accountPresenter.validatePasswordFormat(editPassword.getText());
                        }
                    }

                    if (editPassword.isValidText()) {
                        hideErrorMessage(editPassword.getId());
                    }
                }
            }
        });

        editPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!editPassword.isValidText()) {
                        editPassword.requestEditFocus();
                    }
                }
            }
        });

        editPassword.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editPasswordConfirm.getText().isEmpty()) {
                    editPasswordConfirm.setText("");
                    editPasswordConfirm.imageViewIsGone(true);
                }
                hideErrorMessage(editPasswordConfirm.getId());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();

        if (userExist) {
            showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo_existe));
            editPasswordConfirm.setIsInvalid();
            return;
        }

        if (email.replaceAll("\\s", "").isEmpty()) {
            showValidationError(editMail.getId(), getString(R.string.datos_usuario_correo));
            editMail.setIsInvalid();
            return;
        }

        /*if(!Validations.isMail(email)){
            showValidationError(getString(R.string.datos_usuario_correo_formato));
            return;
        }*/

        if (emailConfirmation.isEmpty()) {
            showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_confirma));
            edtitConfirmEmail.setIsInvalid();
            return;
        }

        if (!emailConfirmation.equals(email)) {
            showValidationError(edtitConfirmEmail.getId(), getString(R.string.datos_usuario_correo_no_coinciden));
            edtitConfirmEmail.setIsInvalid();
            return;
        }

        if (password.isEmpty()) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
            editPassword.setIsInvalid();
            return;
        }

        if (passwordConfirmation.isEmpty()) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_confirm));
            editPasswordConfirm.setIsInvalid();
            return;
        }

        if (!passwordConfirmation.equals(password)) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
            editPasswordConfirm.setIsInvalid();
            return;
        }

        if (!isValidPassword) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
            return;
        }

        onValidationSuccess();
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
        UI.hideKeyBoard(getActivity());
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
        password = editPassword.getText().toString().trim();
        passwordConfirmation = editPasswordConfirm.getText().trim();
    }

    private void setCurrentData() {
        RegisterUser registerUser = RegisterUser.getInstance();
        editMail.setText(registerUser.getEmail());
        edtitConfirmEmail.setText(registerUser.getEmail());
        editPassword.setText(registerUser.getContrasenia());
        editPasswordConfirm.setText(registerUser.getContrasenia());
        isValidPassword = true;
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
        progressLayout.setVisibility(VISIBLE);
        progressLayout.setTextMessage(message);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        if (!error.toString().isEmpty())
            UI.showToastShort(error.toString(), getActivity());
    }

    @Override
    public void validationPasswordSucces() {//Mostrar imagen en edtText
        isValidPassword = true;
        editPassword.setIsValid();
        hideErrorMessage(editPassword.getId());
    }

    @Override
    public void validationPasswordFailed(String message) {//Mostrar imagen en edtText
        isValidPassword = false;
        showValidationError(editPassword.getId(), message);
        editPassword.requestEditFocus();
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
        editMail.requestEditFocus();
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