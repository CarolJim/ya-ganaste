package com.pagatodo.yaganaste.ui.login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.interfaces.ILoginView;
import com.pagatodo.yaganaste.interfaces.UpdateFragment;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class LoginFragment extends GenericFragment implements View.OnClickListener,ValidationForms, ILoginView {

    private View rootview;
    private StyleButton button_login;
    private StyleTextView txtForgotPassword;

    private StyleEdittext edtUser;
    private StyleEdittext edtPassword;

    private String userKey = "";
    private String userPassword = "";

    private static ProgressDialog progressDialog;

    private String TAG_DEBUG = "REGISTER";

    private UpdateFragment accountFormsInterface;

    public LoginFragment() {
        // Required empty public constructor
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

        this.accountFormsInterface = (UpdateFragment) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        init();
        return rootview;
    }

    private void init(){


        /*
        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){

                    validateForm();

                }
                return false;
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            default:
                break;
        }
    }


    /**Implementación de ValidationForms*/

    @Override
    public void setValidationRules() {

    }

    @Override
    public void validateForm() {

        /*Obtenemos datos del formulario*/
        getDataForm();

       /* if(userKey.isEmpty()) {
            showValidationError(getString(R.string.msg_user_valido));
            return;
        }

        if(userKey.length() < Constants.LENGTH_USER) {
            showValidationError(getString(R.string.msg_user_valido));
            return;
        }

        if(userPassword.isEmpty()) {
            showValidationError(getString(R.string.msg_contrasenia_valida));
            return;
        }

        if(userPassword.length() < Constants.LENGTH_PASS) {
            showValidationError(getString(R.string.msg_contrasenia_longitud,Constants.LENGTH_PASS));
            return;
        }*/

        onValidationSuccess();
    }

    @Override
    public void showValidationError(Object error) {
        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onValidationSuccess() {

        /*Realizamos el login a través del presenter*/

    }

    @Override
    public void getDataForm() {

        userKey = edtUser.getText().toString().trim();
        userPassword = edtPassword.getText().toString().trim();
    }

    @Override
    public void showProgress(String message) {


    }

    @Override
    public void hideProgress() {


    }

    @Override
    public void showError(Object error) {

        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();

    }

    /**Implementación de OnLogin*/

    @Override
    public void loginSucced() {

       ;

    }

    @Override
    public void recoveryPasswordSucced() {
        Toast.makeText(getActivity(),"Tu contraseña se ha enviado a tu correo electrónico.",Toast.LENGTH_SHORT).show();

    }
}

