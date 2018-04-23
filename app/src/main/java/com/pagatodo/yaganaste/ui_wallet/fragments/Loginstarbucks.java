package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iloginstarbucks;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementStatus;
import com.pagatodo.yaganaste.ui_wallet.presenter.LoginPresenterStarbucks;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asandovals on 16/04/2018.
 */

public class Loginstarbucks extends GenericFragment implements   View.OnClickListener, ValidationForms, Iloginstarbucks {
    private View rootView;

    @BindView(R.id.txt_subtitul)
    StyleTextView txt_subtitul;

    @BindView(R.id.text_correo)
    TextInputLayout text_correo;
    @BindView(R.id.text_password)
    TextInputLayout text_password;

    @BindView(R.id.editcorreo)
    EditText editcorreo;
    @BindView(R.id.editpassword)
    EditText editpassword;


    @BindView(R.id.txtbottom)
    StyleTextView txtbottom;
    @BindView(R.id.btnNextStarbucks)
    StyleButton btnNextStarbucks;

    @BindView(R.id.block_iniciar_sesion)
    LinearLayout block_iniciar_sesion;

    private String correo, contrasena;

    LoginPresenterStarbucks loginPresenterStarbucks;

    public static Loginstarbucks newInstance() {
        Loginstarbucks fragmentRegister = new Loginstarbucks();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_starbucks_login, container, false);
        initViews();
        setValidationRules();
        loginPresenterStarbucks = new LoginPresenterStarbucks(getContext());
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        btnNextStarbucks.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.btnNextStarbucks){
            validateForm();
        }

    }



    @Override
    public void setValidationRules() {
        editcorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    text_correo.setBackgroundResource(R.drawable.inputtext_active);
                }else {
                    text_correo.setBackgroundResource(R.drawable.inputtext_normal);
                }
            }
        });
        editpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    text_password.setBackgroundResource(R.drawable.inputtext_active);
                }else {
                    text_password.setBackgroundResource(R.drawable.inputtext_normal);
                }
            }
        });
    }
    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (correo.isEmpty()){
            UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo), Snackbar.LENGTH_SHORT);
            text_correo.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else if(!ValidateForm.isValidEmailAddress(correo)){
            UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_correo_formato), Snackbar.LENGTH_SHORT);
            text_correo.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else {
            text_correo.setBackgroundResource(R.drawable.inputtext_normal);
        }
        if (contrasena.isEmpty()){
            UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pass), Snackbar.LENGTH_SHORT);
            text_password.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else {
            text_password.setBackgroundResource(R.drawable.inputtext_normal);
        }
        if (isValid) {
            onValidationSuccess();
        }
    }

    @Override
    public void showValidationError(int id, Object o) {

    }

    @Override
    public void hideValidationError(int id) {

    }

    @Override
    public void onValidationSuccess() {
        loginPresenterStarbucks.login(correo, contrasena);
    }

    @Override
    public void getDataForm() {
        correo= editcorreo.getText().toString().trim();
        contrasena= editpassword.getText().toString().trim();
    }

    @Override
    public void loginstarsucced() {

    }
}
