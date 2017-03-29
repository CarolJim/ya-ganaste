package com.pagatodo.yaganaste.ui.account.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAccountView;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.interfaces.enums.TypeLogin;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.UtilsNet;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterOld;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener, IAccountView,ValidationForms{

    private View rootview;

    @BindView(R.id.edtxtLoginExistUserWritePass)
    StyleEdittext edtxtPassword;
    @BindView(R.id.btnLoginExistUser)
    StyleButton btnLogin;
    @BindView(R.id.imgLoginExistProfile)
    CircleImageView imgLoginExistProfile;
    @BindView(R.id.imgLoginExistLeft)
    ImageView imgLoginExistLeft;
    @BindView(R.id.imgLoginExistRight)
    ImageView imgLoginExistRight;
    @BindView(R.id.txtLoginExistBalanceUsername)
    StyleTextView txtLoginExistBalanceUsername;
    @BindView(R.id.txtLoginExistUserRecoverPass)
    StyleTextView txtLoginExistUserRecoverPass;
    private AccountPresenterOld accountPresenter;

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

        accountPresenter = new AccountPresenterOld(this);
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

       /* if (!FlowUser.getUsername().equals(""))
            txtLoginExistBalanceUsername.setText(String.format(getString(R.string.hi_user_login), FlowUser.getUsername()));
        else*/
            txtLoginExistBalanceUsername.setText(RequestHeaders.getUsername());

        if (!RequestHeaders.TokenSesion.equals("") || !RequestHeaders.TokenAutenticacion.equals("")) {
            imgLoginExistRight.setVisibility(View.VISIBLE);
        }

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
            edtxtPassword.setEnabled(false);
            validateForm();
        } else {
            UI.showToastShort(getString(R.string.no_internet_access), getActivity());
        }
    }

    @Override
    public void nextStepAccountFlow(String event) {
        onEventListener.onEvent(event,null);

    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {
        UI.showToastShort(error.toString(),getActivity());
        edtxtPassword.setEnabled(true);
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
        edtxtPassword.setText("");
        /*TODO Validar si se debe de cerrar sesuón antes de realizar el login*/
        accountPresenter.login(TypeLogin.LOGIN_NORMAL,RequestHeaders.getUsername(),password); // Realizamos el  Login
    }

    @Override
    public void getDataForm() {
        password = edtxtPassword.getText().toString().trim();
    }
}

