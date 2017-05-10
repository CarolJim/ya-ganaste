package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.interfaces.RecoveryPasswordView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_GO_LOGIN;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_RECOVERY_PASS_BACK;
import static com.pagatodo.yaganaste.utils.Constants.DELAY_MESSAGE_PROGRESS;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class RecoveryFragment extends GenericFragment implements View.OnClickListener, RecoveryPasswordView,ValidationForms{

    private View rootview;

    @BindView(R.id.edtCorreoRegistrado)
    CustomValidationEditText edtCorreoRegistrado;
    @BindView(R.id.btnNextRecuperar)
    StyleButton btnRecuperarContrasenia;
    @BindView(R.id.btnBackRecuperar)
    StyleButton btnBack;
    @BindView(R.id.errorMessage)
    ErrorMessage errorMessageView;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;

    private AccountPresenterNew accountPresenter;

    private String correoRegistrado = "";

    public RecoveryFragment() {

    }

    public static RecoveryFragment newInstance() {
        RecoveryFragment fragmentRegister = new RecoveryFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountPresenter = ((AccountActivity)getActivity()).getPresenter();
        accountPresenter.setIView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_recuperar_contrasenia, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnBack.setOnClickListener(this);
        btnRecuperarContrasenia.setOnClickListener(this);
        setValidationRules();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBackRecuperar:
                backScreen(EVENT_RECOVERY_PASS_BACK,null);
                break;
            case R.id.btnNextRecuperar:
                validateForm();
                break;
            default:
                break;
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
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {
        errorMessageView.setMessageText(error.toString());
        UI.hideKeyBoard(getActivity());
    }

    private void hideErrorMessage(){
        errorMessageView.setVisibilityImageError(false);
    }

    /*Implementación de ValidationForm*/
    @Override
    public void setValidationRules() {

        edtCorreoRegistrado.addCustomTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!edtCorreoRegistrado.isValidText()){
                    hideErrorMessage();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        if(correoRegistrado.isEmpty()){
            showValidationError(getString(R.string.ingresa_correo_registrado_requerido));
            edtCorreoRegistrado.setIsInvalid();
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        errorMessageView.setMessageText(error.toString());
        UI.hideKeyBoard(getActivity());
        setEnableViews(true);
    }

    @Override
    public void onValidationSuccess() {
        setEnableViews(false);
        accountPresenter.recoveryPassword(correoRegistrado);
    }

    @Override
    public void getDataForm() {
        correoRegistrado = edtCorreoRegistrado.getText().trim();
    }


    @Override
    public void recoveryPasswordSuccess(String message) {
        UI.showToastShort(message,getActivity());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                backScreen(EVENT_RECOVERY_PASS_BACK,null);
            }
        }, DELAY_MESSAGE_PROGRESS);
        setEnableViews(true);
    }

    @Override
    public void recoveryPasswordFailed(String message) {
        UI.showToastShort(message,getActivity());
        setEnableViews(true);
    }

    private void setEnableViews(boolean isEnable){
        edtCorreoRegistrado.setEnabled(isEnable);
        btnBack.setEnabled(isEnable);
        btnRecuperarContrasenia.setEnabled(isEnable);

    }
}

