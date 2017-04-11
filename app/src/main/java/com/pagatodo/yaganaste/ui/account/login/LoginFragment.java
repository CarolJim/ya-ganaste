package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener, ILoginView,ValidationForms{

    private View rootview;

    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;
    @BindView(R.id.edtxtUserName)
    CustomValidationEditText edtUserName;
    @BindView(R.id.edtxtUserPass)
    CustomValidationEditText edtUserPass;
    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;
    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;
    @BindView(R.id.progressIndicator)
    ProgressLayout progressLayout;

    private AccountPresenterNew accountPresenter;

    private String username = "";
    private String password = "";

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragmentRegister = new LoginFragment();
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

        rootview = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnLogin.setOnClickListener(this);
        txtLoginExistUserRecoverPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginExistUser:
                actionBtnLogin();
                break;
            case R.id.txtLoginExistUserRecoverPass:
                //startActivity(new Intent(getActivity(), RecoverPasswordActivity.class));
                break;
            default:
                break;
        }
    }

    private void actionBtnLogin(){

        if (UtilsNet.isOnline(getActivity())) {
            validateForm();
        } else {
            UI.showToastShort(getString(R.string.no_internet_access), getActivity());
        }
    }

    @Override
    public void nextStepRegister(String event, Object data) {
        onEventListener.onEvent(event, data);
    }

    @Override
    public void backStepRegister(String event, Object data) {
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
        UI.showToastShort(error.toString(),getActivity());
    }

    /*Implementación de ValidationForm*/
    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        if(password.isEmpty()){
            showError("Contraseña Requerida, Verifique su Información");
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
    }

    @Override
    public void onValidationSuccess() {
        /*TODO Validar si se debe de cerrar sesuón antes de realizar el login*/
        accountPresenter.login(username,password); // Realizamos el  Login
        //loginSucced();
    }

    @Override
    public void getDataForm() {
        username = edtUserName.getText().trim();
        password = edtUserPass.getText().trim();
    }

    @Override
    public void loginSucced() {
        Intent intentLogin = new Intent(getActivity(), TabActivity.class);
        startActivity(intentLogin);
        getActivity().finish();
    }

    @Override
    public void recoveryPasswordSucced() {

    }
}

