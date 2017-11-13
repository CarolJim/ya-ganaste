package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IChangeNipView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassValidation;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_LISTA;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_PUSH;

/**
 * Encargada de gestionar el cambio de contraseña, los elementos graficos de la vista y enviar al MVP
 */
public class MyPassFragment extends GenericFragment implements View.OnFocusChangeListener, View.OnClickListener,
        ValidationForms, IMyPassValidation, IMyPassView, IChangeNipView, IResetNIPView {
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
    AccountPresenterNew accountPresenter;
    PreferUserPresenter mPreferPresenter;
    View rootview;
    private String password;
    private String passwordOld;
    private String passwordConfirmation;
    private boolean isValidPassword = false;
    private String passErrorMessage;
    private boolean errorIsShowed = false;

    private ChangeNipPresenterImp changeNipPresenterImp;
    private ResetPinPresenter resetPinPresenter;

    public MyPassFragment() {
        // Required empty public constructor
    }

    public static MyPassFragment newInstance() {
        MyPassFragment fragment = new MyPassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferPresenter = ((PreferUserActivity) getActivity()).getPreferPresenter();
        mPreferPresenter.setIView(this);
        accountPresenter = ((PreferUserActivity) getActivity()).getPresenterAccount();
        accountPresenter.setIView(this);
        this.changeNipPresenterImp = new ChangeNipPresenterImp();
        changeNipPresenterImp.setIChangeNipView(this);
        resetPinPresenter = new ResetPinPresenterImp(false);
        resetPinPresenter.setResetNIPView(this);
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
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    validateForm();
                } else {
                    showDialogMesage(getResources().getString(R.string.no_internet_access));
                }
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
                        showValidationError(editOldPassword.getId(), getString(R.string.cambiar_pass_actual));
                    }
                } else {
                    if (editOldPassword.isValidText() && !isValidPassword) {
                    } else if (editOldPassword.getText().isEmpty()) {
                        editOldPassword.setIsInvalid();
                        showValidationError(editOldPassword.getId(), getString(R.string.cambiar_pass_actual));
                    } else if (editOldPassword.isValidText() && isValidPassword) {
                        hideValidationError(editOldPassword.getId());
                        editOldPassword.setIsValid();
                    }
                }
            }
        });

        editOldPassword.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void onTextChanged(String s, int start, int before, int count) {
                isValidPassword = false;
                if (editOldPassword.getText().isEmpty()) {
                    showValidationError(editOldPassword.getId(), getString(R.string.cambiar_pass_actual));
                    editOldPassword.setIsInvalid();
                } else if (!editOldPassword.isValidText()) {
                    showValidationError(editOldPassword.getId(), getString(R.string.datos_usuario_pass_formato));
                    editOldPassword.setIsInvalid();
                } else if (editOldPassword.isValidText()) {
                    hideValidationError(editOldPassword.getId());
                    editOldPassword.setIsValid();
                }
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    if (editPassword.getText().isEmpty()) {
                        editPassword.setIsInvalid();
                        showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_new));
                    }
                } else {
                    if (editPassword.isValidText() && !isValidPassword) {
                        accountPresenter.validatePasswordFormat(
                                editPassword.getText().trim());
                    } else if (editPassword.getText().isEmpty()) {
                        editPassword.setIsInvalid();
                        showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_new));
                    } else if (editPassword.isValidText() && isValidPassword) {
                        hideValidationError(editPassword.getId());
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
                    showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_new));
                    editPassword.setIsInvalid();
                } else if (!editPassword.isValidText()) {
                    showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_formato));
                    editPassword.setIsInvalid();
                } else if (editPassword.isValidText()) {
                    hideValidationError(editPassword.getId());
                    editPassword.setIsValid();
                }

                if (!editPasswordConfirm.getText().isEmpty() && !editPasswordConfirm.getText().equals(editPassword.getText())) {
                    showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                    editPasswordConfirm.setIsInvalid();
                } else if (editPasswordConfirm.getText().isEmpty()) {
                    hideValidationError(editPasswordConfirm.getId());
                    editPasswordConfirm.imageViewIsGone(true);
                } else if (!editPasswordConfirm.getText().isEmpty() && editPasswordConfirm.getText().equals(editPassword.getText())) {
                    hideValidationError(editPasswordConfirm.getId());
                    editPasswordConfirm.setIsValid();
                }
            }
        });

        editPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editPasswordConfirm.getText().isEmpty()) {
                        hideValidationError(editPasswordConfirm.getId());
                        editPasswordConfirm.imageViewIsGone(true);
                    } else if (!editPasswordConfirm.getText().equals(editPassword.getText())) {
                        showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                        editPasswordConfirm.setIsInvalid();
                    } else if (editPasswordConfirm.getText().equals(editPassword.getText())) {
                        hideValidationError(editPasswordConfirm.getId());
                        editPasswordConfirm.setIsValid();
                    }
                } else {
                    if (editPasswordConfirm.getText().isEmpty()) {
                        hideValidationError(editPasswordConfirm.getId());
                        editPasswordConfirm.imageViewIsGone(true);
                    } else if (!editPasswordConfirm.getText().isEmpty() && editPasswordConfirm.getText().equals(editPassword.getText())) {
                        hideValidationError(editPasswordConfirm.getId());
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
                    hideValidationError(editPasswordConfirm.getId());
                    editPasswordConfirm.imageViewIsGone(true);
                } else if (!editPasswordConfirm.getText().equals(editPassword.getText())) {
                    showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
                    editPasswordConfirm.setIsInvalid();
                } else {
                    hideValidationError(editPasswordConfirm.getId());
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
            showValidationError(editPassword.getId(), getString(R.string.datos_usuario_pass_new));
            editPassword.setIsInvalid();
            isValid = false;
        }

        //Validate Password Confirmation equals to Password
        if (!passwordConfirmation.equals(password)) {
            showValidationError(editPasswordConfirm.getId(), getString(R.string.datos_usuario_pass_no_coinciden));
            editPasswordConfirm.setIsInvalid();
            isValid = false;
        }

        //Validate new and old password not the same
        if (password.equals(passwordOld)) {
            showDialogMesage(getString(R.string.datos_usuario_pass_equal_new));
            editPassword.setIsInvalid();
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
            showValidationError(editOldPassword.getId(), getString(R.string.cambiar_pass_actual));
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

    @Override
    public void hideValidationError(int id) {
        switch (id) {
            case R.id.editPassword:
                errorPasswordMessage.setVisibilityImageError(false);
                break;
            case R.id.editPasswordConfirmation:
                errorConfirmPasswordMessage.setVisibilityImageError(false);
                break;
            case R.id.editOldPassword:
                errorOldPasswordMessage.setVisibilityImageError(false);
                break;
        }
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    public void onValidationSuccess() {
        // Deshabilitamos el backButton
        //getActivity().onBackPressed();
        onEventListener.onEvent("DISABLE_BACK", true);

        mPreferPresenter.changePassToPresenter(
                Utils.cipherRSA(editOldPassword.getText().trim()),
                Utils.cipherRSA(editPassword.getText().trim())
        );
    }

    @Override
    public void getDataForm() {
        passwordOld = editOldPassword.getText().trim();
        password = editPassword.getText().trim();
        passwordConfirmation = editPasswordConfirm.getText().trim();
    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent("DISABLE_BACK", true);
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    public void hideLoader() {
        // progressLayout.setVisibility(GONE);
        onEventListener.onEvent("DISABLE_BACK", false);
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
    }

    /**
     * Exito en la peticion de servidor y exito en el cambio de Pass
     *
     * @param mensaje
     */
    @Override

    public void sendSuccessPassToView(String mensaje) {
        App.getInstance().getPrefs().saveData(SHA_256_FREJA, Utils.getSHA256(editPassword.getText()));
        if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(Utils.getSHA256(editPassword.getText()));
        } else {
            changeNipPresenterImp.doChangeNip(Utils.getSHA256(editOldPassword.getText()),
                    Utils.getSHA256(editPassword.getText()));
        }
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
        if (mensaje.contains("Contraseña")) {
            showDialogMesage(getString(R.string.error_service_verify_pass));
        } else {
            showDialogMesage(mensaje);
        }
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

    /**
     * Mostramos un mensaje simple con el String que necesitemos, sin acciones, solo aceptar
     *
     * @param mensaje
     */
    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        if (mensaje.equals(Recursos.MESSAGE_OPEN_SESSION)) {
                            onEventListener.onEvent(EVENT_SESSION_EXPIRED, 1);
                        } else if (mensaje.equals(Recursos.MESSAGE_CHANGE_PASS)) {
                            onEventListener.onEvent(PREFER_USER_LISTA, 1);
                        }
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
        //hideValidationError();
    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void validationPasswordFailed(String mMesage) {
        isValidPassword = false;
        passErrorMessage = mMesage;
        showValidationError(editPassword.getId(), mMesage);
        editPassword.setIsInvalid();
    }

    @Override
    public void validationPasswordSucces() {
        isValidPassword = true;
        editPassword.setIsValid();
        passErrorMessage = "";
        hideValidationError(editPassword.getId());
    }

    @Override
    public void onFrejaNipChanged() {
        endAndBack();
    }

    @Override
    public void showErrorNip(ErrorObject error) {
        hideLoader();
        //onEventListener.onEvent(EVENT_SHOW_ERROR, error);

    }

    @Override
    public void showErrorReset(ErrorObject error) {

    }

    @Override
    public void finishReseting() {
        endAndBack();
    }

    @Override
    public void onFrejaNipFailed() {
        SingletonUser.getInstance().setNeedsReset(true);
        resetPinPresenter.doReseting(Utils.getSHA256(editPassword.getText()));
    }

    @Override
    public void onResetingFailed() {
        App.getInstance().getPrefs().saveDataBool(HAS_PUSH, false);
        endAndBack();
    }

    private void endAndBack() {
        editOldPassword.setText("");
        editPassword.setText("");
        editPasswordConfirm.setText("");
        showDialogMesage(Recursos.MESSAGE_CHANGE_PASS);
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
    }

}
