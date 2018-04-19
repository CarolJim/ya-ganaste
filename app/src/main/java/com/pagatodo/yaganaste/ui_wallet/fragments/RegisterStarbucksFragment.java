package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.DatosPersonalesFragment;
import com.pagatodo.yaganaste.utils.PhoneTextWatcher;
import com.pagatodo.yaganaste.utils.TarjetaStarbucksTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asandovals on 16/04/2018.
 */

public class RegisterStarbucksFragment  extends GenericFragment implements   View.OnClickListener, ValidationForms {
    private View rootView;
    @BindView(R.id.txt_subtitul)
    StyleTextView txt_subtitul;

    @BindView(R.id.text_numero_tarjeta)
    TextInputLayout text_numero_tarjeta;
    @BindView(R.id.text_codigo)
    TextInputLayout text_codigo;





    @BindView(R.id.editnumero_tarjeta)
    EditText editnumero_tarjeta;
    @BindView(R.id.editcodigo)
    EditText editcodigo;

    @BindView(R.id.txtbottom)
    StyleTextView txtbottom;
    @BindView(R.id.btnNextStarbucks)
    StyleButton btnNextStarbucks;


    @BindView(R.id.block_register)
    LinearLayout block_register;

    @BindView(R.id.block_iniciar_sesion)
    LinearLayout block_iniciar_sesion;

    private String numerotarjeta, codigo;

    public static RegisterStarbucksFragment newInstance() {
        RegisterStarbucksFragment fragmentRegister = new RegisterStarbucksFragment();
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
        rootView = inflater.inflate(R.layout.fragment_starbucks_register, container, false);
        initViews();
        return rootView;
    }
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        txt_subtitul.setText(R.string.texto_resgister_starbucks);
        block_iniciar_sesion.setVisibility(View.GONE);
        block_register.setVisibility(View.VISIBLE);
        btnNextStarbucks.setOnClickListener(this);
        setValidationRules();
        editnumero_tarjeta.addTextChangedListener(new TarjetaStarbucksTextWatcher(editnumero_tarjeta));

    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.btnNext){
            validateForm();
        }

    }

    @Override
    public void setValidationRules() {
        editnumero_tarjeta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    text_numero_tarjeta.setBackgroundResource(R.drawable.inputtext_active);
                }else {
                    text_numero_tarjeta.setBackgroundResource(R.drawable.inputtext_normal);
                }
            }
        });
        editcodigo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    text_codigo.setBackgroundResource(R.drawable.inputtext_active);
                }else {
                    text_codigo.setBackgroundResource(R.drawable.inputtext_normal);
                }
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        boolean isValid = true;
        if (numerotarjeta.isEmpty()){
            UI.showErrorSnackBar(getActivity(),getString(R.string.numero_tarjeta_necesario), Snackbar.LENGTH_SHORT);
            text_numero_tarjeta.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else {
            text_numero_tarjeta.setBackgroundResource(R.drawable.inputtext_normal);
        }
        if (codigo.isEmpty()){
            UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pass), Snackbar.LENGTH_SHORT);
            text_codigo.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else {
            text_codigo.setBackgroundResource(R.drawable.inputtext_normal);
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

    }

    @Override
    public void getDataForm() {

        numerotarjeta= editnumero_tarjeta.getText().toString().trim();
        codigo= editcodigo.getText().toString().trim();

    }
}