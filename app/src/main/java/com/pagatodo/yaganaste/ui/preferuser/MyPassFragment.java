package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.interfaces.IChangeNipView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassValidation;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.ui_wallet.builder.Container;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_PUSH;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;

/**
 * Encargada de gestionar el cambio de contraseña, los elementos graficos de la vista y enviar al MVP
 */
public class MyPassFragment extends GenericFragment implements View.OnClickListener,
        ValidationForms, IMyPassValidation, IMyPassView, IChangeNipView, IResetNIPView {
    //  ValidationForms, IUserDataRegisterView,

    public static final String TAG = MyPassFragment.class.getSimpleName();
    private Preferencias prefs = App.getInstance().getPrefs();
    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.btn_confirmation)
    StyleButton btnConfirmation;
    private InputText.ViewHolderInputText editActual;
    private InputText.ViewHolderInputText editNueva;
    private InputText.ViewHolderInputText editConfir;

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
        btnConfirmation.setOnClickListener(this);
        Container s = new Container(getContext());
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(6);
        editActual = s.addLayoutPass(mLinearLayout, new InputText(R.string.asignar_nueva_contraseña));
        editActual.editText.setFilters(fArray);
        editNueva = s.addLayoutPass(mLinearLayout, new InputText(R.string.confirma_nueva_contrasena));
        editNueva.editText.setFilters(fArray);
        editConfir = s.addLayoutPass(mLinearLayout, new InputText(R.string.confirma_nueva_contraseña));
        editConfir.editText.setFilters(fArray);
        setValidationRules();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmation:
                boolean isOnline = Utils.isDeviceOnline();
                if (isOnline) {
                    validateForm();
                } else {
                    showSnakBar(getResources().getString(R.string.no_internet_access));
                }
                break;
        }
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void showSnakBar(String mensje) {
        hideKeyBoard();
        UI.showErrorSnackBar(getActivity(), mensje, Snackbar.LENGTH_LONG);
    }

    @Override
    public void setValidationRules() {
        editActual.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editActual.editText.getText().toString().isEmpty()) {
                        showSnakBar(getResources().getString(R.string.cambiar_pass_actual));
                        editActual.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        editActual.inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                } else {
                    editActual.inputLayout.setBackgroundResource(R.drawable.inputtext_active);
                }
            }
        });

        editNueva.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editNueva.editText.getText().toString().isEmpty()) {
                        showSnakBar(getResources().getString(R.string.datos_usuario_pass_new));
                        editNueva.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        editNueva.inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
                    }
                } else {
                    editNueva.inputLayout.setBackgroundResource(R.drawable.inputtext_active);
                }
            }
        });

        editConfir.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editConfir.editText.getText().toString().isEmpty()) {
                        showSnakBar(getResources().getString(R.string.datos_usuario_pass_c));
                        editConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                    } else {
                        if (editConfir.editText.getText().toString().equalsIgnoreCase(editNueva.editText.getText().toString())) {
                            editConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
                        } else {
                            editConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
                        }
                    }
                } else {
                    editConfir.inputLayout.setBackgroundResource(R.drawable.inputtext_active);
                }
            }
        });
    }


    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;

        //Validate if Password is empty
        if (passwordOld.isEmpty()) {
            showSnakBar(getString(R.string.cambiar_pass_actual));
            isValid = false;
        }

        if (password.isEmpty()) {
            showSnakBar(getString(R.string.datos_usuario_pass_new));
            isValid = false;
        }

        if (passwordConfirmation.isEmpty()) {
            showSnakBar(getString(R.string.datos_usuario_pass_confirm));
            isValid = false;
        }

        //Validacion de tamaño
        if (passwordOld.trim().length() < 6) {
            showSnakBar(getString(R.string.datos_usuario_pass_formato));
            isValid = false;
        }
        if (password.trim().length() < 6) {
            showSnakBar(getString(R.string.datos_usuario_pass_formato));
            isValid = false;
        }
        if (passwordConfirmation.trim().length() < 6) {
            showSnakBar(getString(R.string.datos_usuario_pass_formato));
            isValid = false;
        }

        //Validate Password Confirmation equals to Password
        if (!passwordConfirmation.equals(password)) {
            showSnakBar(getString(R.string.datos_usuario_pass_no_coinciden));
            isValid = false;
        }

        //Validate new and old password not the same
        if (password.equals(passwordOld)) {
            showSnakBar(getString(R.string.datos_usuario_pass_equal_new));

            isValid = false;
        }

        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object error) {
        switch (id) {
            /*case R.id.editPassword:
                //errorPasswordMessage.setMessageText(error.toString());
                break;
            case R.id.editPasswordConfirmation:
                //errorConfirmPasswordMessage.setMessageText(error.toString());
                break;
            case R.id.editOldPassword:
                //errorOldPasswordMessage.setMessageText(error.toString());
                break;
                */
        }

        //errorMessageView.setMessageText(error.toString());
        errorIsShowed = true;
    }

    @Override
    public void hideValidationError(int id) {
        /*switch (id) {
            case R.id.editPassword:
                //errorPasswordMessage.setVisibilityImageError(false);
                break;
            case R.id.editPasswordConfirmation:
                //errorConfirmPasswordMessage.setVisibilityImageError(false);
                break;
            case R.id.editOldPassword:
                //errorOldPasswordMessage.setVisibilityImageError(false);
                break;
        }*/
        //errorMessageView.setVisibilityImageError(false);
        errorIsShowed = false;
    }

    public void onValidationSuccess() {
        // Deshabilitamos el backButton
        //getActivity().onBackPressed();
        onEventListener.onEvent("DISABLE_BACK", true);

        mPreferPresenter.changePassToPresenter(
                Utils.cipherRSA(editActual.editText.getText().toString().trim(), PUBLIC_KEY_RSA),
                Utils.cipherRSA(editNueva.editText.getText().toString().trim(), PUBLIC_KEY_RSA)
        );
    }

    @Override
    public void getDataForm() {
        passwordOld = editActual.editText.getText().toString().trim();
        password = editNueva.editText.getText().toString().trim();
        passwordConfirmation = editConfir.editText.getText().toString().trim();
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
        App.getInstance().getPrefs().saveData(SHA_256_FREJA, Utils.getSHA256(editNueva.editText.getText().toString()));
        if (SingletonUser.getInstance().needsReset()) {
            resetPinPresenter.doReseting(Utils.getSHA256(editNueva.editText.getText().toString()));
        } else {
            changeNipPresenterImp.doChangeNip(Utils.getSHA256(editActual.editText.getText().toString()),
                    Utils.getSHA256(editNueva.editText.getText().toString()));
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
            editActual.inputLayout.setBackgroundResource(R.drawable.inputtext_error);
            showSnakBar(getString(R.string.error_service_verify_pass));
        } else {
            showSnakBar(mensaje);
        }
        hideLoader();
        onEventListener.onEvent("DISABLE_BACK", false);
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
        showSnakBar(mMesage);
        //editPassword.setIsInvalid();
    }

    @Override
    public void validationPasswordSucces() {
        isValidPassword = true;
        passErrorMessage = "";
        //hideValidationError(editPassword.getId());
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
        resetPinPresenter.doReseting(Utils.getSHA256(editNueva.editText.getText().toString()));
    }

    @Override
    public void onResetingFailed() {
        App.getInstance().getPrefs().saveDataBool(HAS_PUSH, false);
        endAndBack();
    }

    private void endAndBack() {
        /*editActual.editText.setText("");
        editNueva.editText.setText("");
        editConfir.editText.setText("");*/
        //showSnakBar(Recursos.MESSAGE_CHANGE_PASS);
        hideLoader();
        onEventListener.onEvent("PREFER_SECURITY_SUCCESS_PASS", false);
    }

}
