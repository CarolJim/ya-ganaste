package com.pagatodo.yaganaste.ui.payments.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentSuccessManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentSuccessPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsSuccessPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_OK;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentSuccessFragment extends GenericFragment implements PaymentSuccessManager, View.OnClickListener {


    @BindView(R.id.txt_paymentTitle)
    TextView title;
    @BindView(R.id.txt_importe)
    MontoTextView importe;
    @BindView(R.id.titleReferencia)
    TextView titleReferencia;
    @BindView(R.id.txtReferencia)
    TextView txtReferencia;
    @BindView(R.id.txtAutorizacion)
    TextView autorizacion;
    @BindView(R.id.txtFecha)
    TextView fecha;
    @BindView(R.id.txtHora)
    TextView hora;
    @BindView(R.id.layoutMail)
    LinearLayout layoutMail;
    @BindView(R.id.titleMail)
    TextView titleMail;
    @BindView(R.id.editMail)
    CustomValidationEditText editMail;
    @BindView(R.id.btn_continueEnvio)
    StyleButton btnContinueEnvio;
    @BindView(R.id.layoutFavoritos)
    LinearLayout layoutFavoritos;
    @BindView(R.id.txtCompania)
    TextView txtCompania;
    @BindView(R.id.nombreEnvio)
    TextView nombreEnvio;


    @BindView(R.id.layout_enviado)
    LinearLayout layoutEnviado;

    @BindView(R.id.layout_compania)
    LinearLayout layoutCompania;


    @BindView(R.id.layout_addfavorites)
    LinearLayout addFavorites;

    Payments pago;
    EjecutarTransaccionResponse result;
    /****/
    IPaymentsSuccessPresenter presenter;
    private View rootview;
    private boolean isMailAviable = false;

    public static PaymentSuccessFragment newInstance(Payments pago, EjecutarTransaccionResponse result) {
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        Bundle args = new Bundle();
        args.putSerializable("pago", pago);
        args.putSerializable("result", result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pago = (Payments) getArguments().getSerializable("pago");
        result = (EjecutarTransaccionResponse) getArguments().getSerializable("result");
        presenter = new PaymentSuccessPresenter(this);

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


        if (pago instanceof Recarga) {
            layoutEnviado.setVisibility(View.GONE);
            title.setText(R.string.title_recarga_success);
            Double comision = result.getData().getComision();
            /*if (comision > 0) {
                layoutComision.setVisibility(View.VISIBLE);
                txtComision.setText(Utils.getCurrencyValue(comision));
            } else {
                layoutComision.setVisibility(View.INVISIBLE);
            }*/

            if (pago.getComercio().getIdComercio() == 7) {
                titleReferencia.setText(getString(R.string.tag_number));
            } else {
                titleReferencia.setText(R.string.details_telefono);

            }
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            isMailAviable = true;
            txtCompania.setText(pago.getComercio().getNombreComercio());
        } else if (pago instanceof Servicios) {
            layoutEnviado.setVisibility(View.GONE);
            title.setText(R.string.title_servicio_success);
            //layoutComision.setVisibility(View.VISIBLE);
            titleReferencia.setText(R.string.txt_referencia_servicio);
            Double comision = result.getData().getComision();
            String textComision = String.format("%.2f", comision);
            textComision = textComision.replace(",", ".");
            //txtComision.setText(textComision);
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            isMailAviable = true;
            txtCompania.setText(pago.getComercio().getNombreComercio());
        } else if (pago instanceof Envios) {
            layoutCompania.setVisibility(View.GONE);
            title.setText(R.string.title_envio_success);
            //txtComision.setVisibility(View.GONE);
            //comisionReferenciaText.setText("A:");
            nombreEnvio.setText(((Envios) pago).getNombreDestinatario());
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            String fullName = ((Envios) pago).getNombreDestinatario();
            titleMail.setText(
                    getContext().getResources().getString(R.string.envia_comprobante_a)
                            + " " + StringUtils.formatSingleName(fullName)
                            + " " + getContext().getResources().getString(R.string.envia_comprobante_opcional));
            isMailAviable = true;

            titleReferencia.setText(((Envios)pago).getTipoEnvio().getShortName());
        }

        String text = String.format("%.2f", pago.getMonto());
        text = text.replace(",", ".");
        importe.setText(text);
        //editMail.setDrawableImage(R.drawable.mail_canvas);

        /*
        Bloqur para poner el formato de telefono o otros ejemplos
        5534812289
        0123456789
         */
        String formatoPago = pago.getReferencia();

        if (pago instanceof Recarga) {
            if (pago.getComercio().getIdComercio() != 7) {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
            if (pago.getComercio().getIdComercio() == 7) {
                formatoPago = StringUtils.formatoPagoMediostag(formatoPago);
            }
        } else if (pago instanceof Servicios) {
            formatoPago = StringUtils.genericFormat(formatoPago, SPACE);
        } else if (pago instanceof Envios) {
            if (formatoPago.length() == 16 || formatoPago.length() == 15) {
                formatoPago = StringUtils.maskReference(StringUtils.format(formatoPago, SPACE, 4,4,4,4), '*', formatoPago.length() -12);
            } else {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
        }


        txtReferencia.setText(formatoPago);

        autorizacion.setText(StringUtils.formatAutorization(result.getData().getNumeroAutorizacion()));
        SimpleDateFormat dateFormatH = new SimpleDateFormat("HH:mm:ss");

       // fecha.setText(DateUtil.getBirthDateCustomString(Calendar.getInstance()));
        fecha.setText(DateUtil.getPaymentDateSpecialCustom(Calendar.getInstance()));
        hora.setText(dateFormatH.format(new Date()) + " hrs");

        btnContinueEnvio.setOnClickListener(this);
    }

    @Override
    public void validateMail() {
        String mail = editMail.getText().trim();

        if (!TextUtils.isEmpty(mail)) {
            if (ValidateForm.isValidEmailAddress(mail)) {
                showLoader(getString(R.string.procesando_enviando_email_loader));
                if(pago instanceof Envios){
                    presenter.sendTicketEnvio(mail, result.getData().getIdTransaccion());
                }else {
                    presenter.sendTicket(mail, result.getData().getIdTransaccion());
                }
            } else {
                showSimpleDialog(getString(R.string.title_error), getString(R.string.datos_usuario_correo_formato));
            }
        } else {
            finalizePayment();
        }
    }

    @Override
    public void onSuccessSendMail(String successMessage) {
        showSuccessDialog(getString(R.string.txt_exito), successMessage);
    }

    @Override
    public void onErrorSendMail(String errorMessage) {
        showDialogErrorSendTicket(errorMessage);
    }

    private void showSimpleDialog(String title, String text) {
        UI.createSimpleCustomDialog(title, text, getFragmentManager(), this.getFragmentTag());
    }


    private void showSuccessDialog(String title, String text) {
        UI.createSimpleCustomDialogNoCancel(title, text,
                getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        finalizePayment();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                });
    }


    private void showDialogErrorSendTicket(String text) {
        UI.createCustomDialog(getString(R.string.error_enviando_ticket),
                text != null ? text : getString(R.string.error_enviando_ticket),
                getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        validateMail();
                    }

                    @Override
                    public void actionCancel(Object... params) {
                        finalizePayment();
                    }
                }, getString(R.string.txt_reintent_lowcase),
                getString(R.string.txt_cancelar));
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
    public void finalizePayment() {
        Intent intent = new Intent();
        intent.putExtra(RESULT, Constants.RESULT_SUCCESS);
        getActivity().setResult(RESULT_CODE_OK, intent);
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnContinueEnvio.getId()) {
            if (isMailAviable) {
                validateMail();
            } else {
                finalizePayment();
            }
        }
    }
}
