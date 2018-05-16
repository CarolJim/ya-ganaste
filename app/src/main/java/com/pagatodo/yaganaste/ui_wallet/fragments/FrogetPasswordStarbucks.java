package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iloginstarbucks;
import com.pagatodo.yaganaste.ui_wallet.presenter.LoginPresenterStarbucks;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * Created by asandovals on 16/05/2018.
 */

public class FrogetPasswordStarbucks extends GenericFragment implements View.OnClickListener, ValidationForms, Iloginstarbucks {
    private View rootView;

    private String correo;

    @BindView(R.id.editcorreo)
    EditText editcorreo;

    @BindView(R.id.btnNextStarbucksPassword)
    StyleButton btnNextStarbucksPassword;


    LoginPresenterStarbucks loginPresenterStarbucks;

    public static FrogetPasswordStarbucks newInstance() {
        FrogetPasswordStarbucks fragmentpass = new FrogetPasswordStarbucks();
        Bundle args = new Bundle();
        fragmentpass.setArguments(args);
        return fragmentpass;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_starbucks_forget_password, container, false);
        initViews();
        setValidationRules();
        loginPresenterStarbucks = new LoginPresenterStarbucks(getContext());
        loginPresenterStarbucks.setIView(this);
        return rootView;
    }



    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.btnNextStarbucksPassword){
            getDataForm();
            loginPresenterStarbucks.forgotpassword(correo);
        }

    }

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {

    }

    @Override
    public void getDataForm() {
        correo = editcorreo.getText().toString();

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnNextStarbucksPassword.setOnClickListener(this);


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
    public void loginstarsucced() {

    }

    @Override
    public void loginfail(String mensaje) {
        UI.showErrorSnackBar(getActivity(),mensaje, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void forgetpasswordsucced() {
        UI.showSuccessSnackBar(getActivity(),"Se enviaron las instrucciones a tu correo", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void forgetpasswordfail(String mensaje) {
        UI.showErrorSnackBar(getActivity(),mensaje, Snackbar.LENGTH_SHORT);
    }
}
