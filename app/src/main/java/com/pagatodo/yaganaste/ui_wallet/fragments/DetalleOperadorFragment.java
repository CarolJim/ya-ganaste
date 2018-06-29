package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IChangeOperador;
import com.pagatodo.yaganaste.interfaces.ValidationForms;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.login.FingerprintAuthenticationDialogFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.presenter.ChangeStatusOperadorPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.CustomErrorDialog;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.EVENT_OPERADOR_DETALLE;
import static com.pagatodo.yaganaste.ui._controllers.AccountActivity.SUCCES_CHANGE_STATUS_OPERADOR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_TO_MOV_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleOperadorFragment extends GenericFragment implements View.OnClickListener, IChangeOperador{

    private View rootView;
    Operadores operadoresResponse;
    @BindView(R.id.correo_operador)
    StyleTextView correo_operador;
    @BindView(R.id.contrasena_operador)
    StyleTextView contrasena_operador;
    @BindView(R.id.status_operador)
    StyleTextView status_operador;
    @BindView(R.id.titulo_negocio)
    StyleTextView titulo_negocio;
    @BindView(R.id.btnbloquaar)
    StyleButton btnbloquaar;
    ChangeStatusOperadorPresenter changeStatusOperadorPresenter;
    public static DetalleOperadorFragment newInstance(Operadores operadoresResponse) {
        DetalleOperadorFragment fragment = new DetalleOperadorFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, operadoresResponse);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        operadoresResponse = (Operadores) args.getSerializable(DetailsActivity.DATA);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detalle_operador, container, false);
        initViews();
        changeStatusOperadorPresenter = new ChangeStatusOperadorPresenter(getContext());
        changeStatusOperadorPresenter.setIView(this);
        return rootView;
    }
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //titulo_negocio.setText(operadoresResponse.getNombreNegocio());
        titulo_negocio.setText("");
        correo_operador.setText(operadoresResponse.getNombreUsuario());
        contrasena_operador.setText(operadoresResponse.getPetroNumero().substring(0,6));
        if (operadoresResponse.getIdEstatusUsuario() ==1){
            status_operador.setText("Operador activo");
        }else {
            status_operador.setText("Operador bloqueado");
            status_operador.setTextColor(Color.parseColor("#D0021B"));
        }
        btnbloquaar.setText(operadoresResponse.getIdEstatusUsuario() ==1? getString(R.string.boton_bloqueo):getString(R.string.boton_desbloque));
        btnbloquaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UI.showAlertDialog(getActivity(), getResources().getString(R.string.app_name), operadoresResponse.getIdEstatusUsuario() ==1? getString(R.string.relizar_bloqueo):getString(R.string.relizar_desbloque),
                        R.string.title_aceptar, (dialogInterface, i) -> {
                            changeStatusOperadorPresenter.change(operadoresResponse.getNombreUsuario(),operadoresResponse.getIdEstatusUsuario()==1?8:1);
                        });
            }
        });

    }

    @Override
    public void onClick(View view) {

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
    public void succedoperador(String mensaje) {
        onEventListener.onEvent(SUCCES_CHANGE_STATUS_OPERADOR, operadoresResponse.getIdEstatusUsuario());
    }

    @Override
    public void failoperador(String mensaje) {
        UI.showErrorSnackBar(getActivity(), mensaje, Snackbar.LENGTH_SHORT);
    }
}
