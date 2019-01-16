package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iregisterstarbucks;
import com.pagatodo.yaganaste.ui_wallet.presenter.RegisterPresenterStarbucks;
import com.pagatodo.yaganaste.utils.TarjetaStarbucksTextWatcher;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_TO_LOGIN_STARBUCKS;
import static com.pagatodo.yaganaste.modules.emisor.WalletMainActivity.EVENT_GO_TO_REGISTER_COMPLETE_STARBUCKS;

/**
 * Created by asandovals on 16/04/2018.
 */

public class RegisterStarbucksFragment  extends GenericFragment implements   View.OnClickListener, ValidationForms,Iregisterstarbucks {
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


    RegisterPresenterStarbucks registerPresenterStarbucks;

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
        registerPresenterStarbucks = new RegisterPresenterStarbucks(getContext());
        registerPresenterStarbucks.setIView(this);

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
        txtbottom.setOnClickListener(this);
        editnumero_tarjeta.addTextChangedListener(new TarjetaStarbucksTextWatcher(editnumero_tarjeta));
        SpannableString ss;
        ss = new SpannableString(getString(R.string.ya_tienes_cuenta_inicia_sesi));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTituloDialog)), 19, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 0, 0, 0);
        txtbottom.setText(ss);

    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.btnNextStarbucks){
            validateForm();
        }
        if (view.getId()==R.id.txtbottom){
            nextScreen(EVENT_GO_TO_LOGIN_STARBUCKS, null);
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
        } else if  (numerotarjeta.length()<16){
            UI.showErrorSnackBar(getActivity(),getString(R.string.tarjeta_valido), Snackbar.LENGTH_SHORT);
            text_numero_tarjeta.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else {
            text_numero_tarjeta.setBackgroundResource(R.drawable.inputtext_normal);
        }
        if (codigo.isEmpty()){
            UI.showErrorSnackBar(getActivity(),getString(R.string.datos_usuario_pin), Snackbar.LENGTH_SHORT);
            text_codigo.setBackgroundResource(R.drawable.inputtext_error);
            isValid = false;
        }else if  (codigo.length()<8){
            UI.showErrorSnackBar(getActivity(),getString(R.string.pin_valido), Snackbar.LENGTH_SHORT);
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
        showLoader("Verificando Datos");
        registerPresenterStarbucks.registerStarbucks(numerotarjeta,codigo);
    }

    @Override
    public void getDataForm() {

        numerotarjeta= editnumero_tarjeta.getText().toString().trim();
        numerotarjeta= numerotarjeta.replaceAll(" ", "");
        codigo= editcodigo.getText().toString().trim();

    }

    @Override
    public void nextScreen(String event, Object data) {
        onEventListener.onEvent(event, data);
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
        hideLoader();
        UI.showErrorSnackBar(getActivity(), error.toString(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void registerstarsucced() {
        hideLoader();
        nextScreen(EVENT_GO_TO_REGISTER_COMPLETE_STARBUCKS, null);
    }

    @Override
    public void registerfail(String mensaje) {
        hideLoader();
        UI.showErrorSnackBar(getActivity(), mensaje, Snackbar.LENGTH_SHORT);
    }
}
