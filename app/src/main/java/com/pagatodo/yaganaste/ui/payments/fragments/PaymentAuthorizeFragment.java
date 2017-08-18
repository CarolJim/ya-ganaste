package com.pagatodo.yaganaste.ui.payments.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentAuthorizeManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentAuthorizePresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentAuthorizePresenter;
import com.pagatodo.yaganaste.utils.AbstractTextWatcher;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.ErrorMessage;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.EVENT_SEND_PAYMENT;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * Created by Jordan on 02/05/2017.
 */

public class PaymentAuthorizeFragment extends GenericFragment implements View.OnClickListener, PaymentAuthorizeManager {

    IPaymentAuthorizePresenter paymentAuthorizePresenter;

    @BindView(R.id.txt_paymentTitle)
    TextView title;
    @BindView(R.id.txt_importe)
    MontoTextView importe;
    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.comisionReferenciaText)
    TextView comisionReferenciaText;
    @BindView(R.id.titleReferencia)
    TextView titleReferencia;
    @BindView(R.id.txtReferencia)
    TextView txtReferencia;
    @BindView(R.id.imgLogoPago)
    ImageView imgLogoPago;

    @BindView(R.id.layoutMail)
    LinearLayout layoutMail;

    @BindView(R.id.btn_continueEnvio)
    StyleButton btnContinueEnvio;
    @BindView(R.id.layoutFavoritos)
    LinearLayout layoutFavoritos;
    @BindView(R.id.nombreEnvio)
    StyleTextView nombreEnvio;
    @BindView(R.id.layoutAutorizacon)
    LinearLayout layoutAutorizacon;
    @BindView(R.id.layoutFecha)
    LinearLayout layoutFecha;
    @BindView(R.id.layoutHora)
    LinearLayout layoutHora;
    @BindView(R.id.layoutPass)
    LinearLayout layoutPass;
    @BindView(R.id.editPassword)
    CustomValidationEditText editPassword;
    @BindView(R.id.errorPasswordMessage)
    ErrorMessage errorPasswordMessage;

    String password;
    Envios envio;
    private View rootview;

    public static PaymentAuthorizeFragment newInstance(Payments envio) {
        PaymentAuthorizeFragment fragment = new PaymentAuthorizeFragment();
        Bundle args = new Bundle();
        args.putSerializable("envios", envio);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        envio = (Envios) getArguments().getSerializable("envios");
        paymentAuthorizePresenter = new PaymentAuthorizePresenter(this);
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
        rootview = inflater.inflate(R.layout.fragment_payment_success, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        title.setText(getString(R.string.authorize_payment_title));
        layoutComision.setVisibility(GONE);
        titleReferencia.setText("A:");
        nombreEnvio.setVisibility(VISIBLE);
        nombreEnvio.setText(envio.getNombreDestinatario());

        layoutMail.setVisibility(GONE);
        layoutFavoritos.setVisibility(GONE);

        layoutAutorizacon.setVisibility(INVISIBLE);
        layoutFecha.setVisibility(INVISIBLE);
        layoutHora.setVisibility(INVISIBLE);

        layoutPass.setVisibility(VISIBLE);

        importe.setText(StringUtils.getCurrencyValue(envio.getMonto()));
        String ref = envio.getReferencia();
        switch (envio.getTipoEnvio()) {
            case CABLE:
                txtReferencia.setText(ref);
                break;
            case NUMERO_TARJETA:
                txtReferencia.setText("**** **** **** " + ref.substring(13, ref.length()));
                break;
            case NUMERO_TELEFONO:
                txtReferencia.setText(ref);
                break;
            default:
                break;
        }

        Glide.with(getContext()).load(envio.getComercio().getLogoURL()).placeholder(R.mipmap.logo_ya_ganaste).error(R.mipmap.logo_ya_ganaste).dontAnimate().into(imgLogoPago);
        setValidationRules();
        btnContinueEnvio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_continueEnvio) {
            validateForm();
        }
    }

    @Override
    public void setValidationRules() {
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    hideValidationError(editPassword.getId());
                    editPassword.imageViewIsGone(true);
                }
            }
        });

        editPassword.addCustomTextWatcher(new AbstractTextWatcher() {
            @Override
            public void afterTextChanged(String s) {
                hideValidationError(editPassword.getId());
                editPassword.imageViewIsGone(true);
            }
        });
    }

    @Override
    public void validateForm() {
        getDataForm();
        if (TextUtils.isEmpty(password)) {
            showValidationError(0, getString(R.string.datos_usuario_pass));
        } else {
            paymentAuthorizePresenter.validatePasswordFormat(password);
        }
    }

    @Override
    public void showValidationError(int id, Object error) {
        errorPasswordMessage.setMessageText(error.toString());
    }

    @Override
    public void hideValidationError(int id) {
        errorPasswordMessage.setVisibilityImageError(false);
    }

    @Override
    public void onValidationSuccess() {
        onEventListener.onEvent(EVENT_SEND_PAYMENT, envio);
    }

    @Override
    public void getDataForm() {
        password = editPassword.getText().toString();
    }

    @Override
    public void showError(Object error) {
        if (!TextUtils.isEmpty(error.toString())) {
            UI.createSimpleCustomDialog("Error", error.toString(), getActivity().getSupportFragmentManager(), getFragmentTag());
        }
    }

    @Override
    public void validationPasswordSucces() {
        paymentAuthorizePresenter.generateOTP(Utils.getSHA256(password));
    }

    @Override
    public void validationPasswordFailed(String error) {
        hideLoader();
        showValidationError(0, error);
    }

    @Override
    public void showLoader(String title) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, title);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void onOtpGenerated(String otp) {
        onEventListener.onEvent(EVENT_SEND_PAYMENT, envio);
    }

    @Override
    public void showError(final Errors error) {
        hideLoader();

        DialogDoubleActions actions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                if (error.allowsReintent()) {
                    paymentAuthorizePresenter.generateOTP(Utils.getSHA256(password));
                } else {
                    getActivity().onBackPressed();
                }
            }

            @Override
            public void actionCancel(Object... params) {
                getActivity().onBackPressed();
            }
        };

        UI.createSimpleCustomDialog("", error.getMessage(), getActivity().getSupportFragmentManager(),
                actions, true, error.allowsReintent());
    }
}
