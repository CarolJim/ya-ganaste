package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IUserDataRegisterView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassValidation;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;

/**
 * Encargada de gestionar el cambio de contraseña, los elementos graficos de la vista y enviar al MVP
 */
public class MyPassFragment extends GenericFragment implements IMyPassView, View.OnClickListener,
        ValidationForms, IMyPassValidation,  View.OnFocusChangeListener {
    //  ValidationForms, IUserDataRegisterView,

    @BindView(R.id.fragment_myemail_btn)
    StyleButton sendButton;
    @BindView(R.id.editOldPassword)
    CustomValidationEditText editOldPassword;
    @BindView(R.id.editPassword)
    CustomValidationEditText editPassword;
    @BindView(R.id.editPasswordConfirmation)
    CustomValidationEditText editPasswordConfirm;
    @BindView(R.id.errorOldPasswordMessage)
    ErrorMessage errorOldPasswordMessage;
    @BindView(R.id.errorPasswordMessage)
    ErrorMessage errorPasswordMessage;
    @BindView(R.id.errorConfirmPasswordMessage)
    ErrorMessage errorConfirmPasswordMessage;

    private String email = "";
    private String emailConfirmation = "";
    private String password = "";
    private String passwordOld = "";
    private String passwordConfirmation = "";
    private boolean isValidPassword = false;
    private boolean emailValidatedByWS = false; // Indica que el email ha sido validado por el ws.
    private boolean userExist = false; // Indica que el email ya se encuentra registrado.
    AccountPresenterNew accountPresenter;
    private String passErrorMessage;

    private boolean errorIsShowed = false;
    PreferUserPresenter mPreferPresenter;

    View rootview;

    public MyPassFragment() {
        // Required empty public constructor
    }

    public static MyPassFragment newInstance() {
        MyPassFragment fragment = new MyPassFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        accountPresenter = ((PreferUserActivity) getActivity()).getPresenterAccount();
        accountPresenter.setIView(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_pass, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        errorConfirmPasswordMessage.setVisibilityImageError(false);
        errorPasswordMessage.setVisibilityImageError(false);

        sendButton.setOnClickListener(this);
        editOldPassword.setOnFocusChangeListener(this);
        editPassword.setOnFocusChangeListener(this);
        editPasswordConfirm.setOnFocusChangeListener(this);

        setValidationRules();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_myemail_btn:
                validateForm();
                //onValidationSuccess();
                break;
        }
    }

    @Override
    public void setValidationRules() {
        editOldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    if (editOldPassword.getText().isEmpty()) {
                        editOldPassword.setIsInvalid();
                        showValidationError(editOldPassword.getId(), getString(R.string.datos_usuario_pass));
                    }
                } else {
                    if (editOldPassword.isValidText() && !isValidPassword) {
                        accountPresenter.validatePasswordFormat(editOldPassword.getText());
                    } else if (editOldPassword.getText().isEmpty()) {
                        editOldPassword.setIsInvalid();
                        showValidationError(editOldPassword.getId(), getString(R.string.datos_usuario_pass));
                    } else if (editOldPassword.isValidText() && isValidPassword) {
                        hideErrorMessage(editOldPassword.getId());
                        editOldPassword.setIsValid();
                    }
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
        boolean isValid = true;

        //Validate If Is a Valid Password and check if exist a Error message from the server
        if (!isValidPassword && passErrorMessage != null && !passErrorMessage.equals("")) {
            showValidationError(editPassword.getId(), passErrorMessage);
            editPassword.setIsInvalid();
            isValid = false;
        }

        //Validate is Valid Password Format
        if (!editPassword.isValidText()) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
            editPassword.setIsInvalid();
            isValid = false;
        }

        //Validate is valid Password Format from Web Service
        if (!isValidPassword) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
            isValid = false;
        }

        //Validate if Password is empty
        if (password.isEmpty()) {
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass));
            editPassword.setIsInvalid();
            isValid = false;
        }

        //Validate Password Confirmation equals to Password
        if (!passwordConfirmation.equals(password)) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
            editPasswordConfirm.setIsInvalid();
            isValid = false;
        }

        //Validate if Password Confirmation is Empty
        if (passwordConfirmation.isEmpty()) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_confirm));
            editPasswordConfirm.setIsInvalid();
            isValid = false;
        }

        //Validate if OldPassword Confirmation is Empty
        if (passwordOld.isEmpty()) {
            showValidationError(editOldPassword.getId(), getString(R.string.datos_usuario_pass));
            editOldPassword.setIsInvalid();
            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object error) {

        switch (id) {
            case R.id.editPassword:
                errorPasswordMessage.setMessageText(error.toString());
                break;
            case R.id.editPasswordConfirmation:
                errorConfirmPasswordMessage.setMessageText(error.toString());
                break;
            case R.id.editOldPassword:
                errorOldPasswordMessage.setMessageText(error.toString());
                break;
        }

        //errorMessageView.setMessageText(error.toString());
        errorIsShowed = true;
    }

    private void hideErrorMessage(int id) {
        switch (id) {
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

    // TODO Pendiente todo el proceso de validaciones de la Pass
    public void onValidationSuccess() {
        // Deshabilitamos el backButton
        //getActivity().onBackPressed();
        onEventListener.onEvent("DISABLE_BACK", true);

        //   mPreferPresenter.sendChangePassToPresenter();
//        mPreferPresenter.changePassToPresenter(
//                editPassword.getText().trim(),
//                editPasswordConfirm.getText().trim()
//        );
        Log.d("ListaOpcionesIteractor", "mPassActual " + editPassword.getText().trim());
        Log.d("ListaOpcionesIteractor", "mPassNueva " + editPasswordConfirm.getText().trim());
        mPreferPresenter.changePassToPresenter(
                Utils.cipherRSA(editPassword.getText().trim()),
                Utils.cipherRSA(editPasswordConfirm.getText().trim())
        );
    }

    @Override
    public void getDataForm() {
        password = editPassword.getText().trim();
        passwordConfirmation = editPasswordConfirm.getText().trim();
    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    /**
     * Exito en la peticion de servidor y exito en el cambio de Pass
     *
     * @param mensaje
     */
    @Override

    public void sendSuccessPassToView(String mensaje) {
        showDialogMesage(mensaje);
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
        editPassword.setText("");
        editPasswordConfirm.setText("");
    }

    /**
     * Exito en la peticion de servidor y Fail en el cambio de Pass.
     * Tambien se usa para mostrar un error de conexion al servidor, desde el Presenter para no tener
     * un tercer metodo
     *
     * @param mensaje
     */
    @Override
    public void sendErrorPassToView(String mensaje) {
        showDialogMesage(mensaje);
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    /**
     * Mostramos un mensaje simple con el String que necesitemos, sin acciones, solo aceptar
     *
     * @param mensaje
     */
    private void showDialogMesage(String mensaje) {
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


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
        }
        //hideErrorMessage();
    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }


    @Override
    public void showLoader(String message) {

    }

    @Override
    public void showError(Object error) {

    }
}
