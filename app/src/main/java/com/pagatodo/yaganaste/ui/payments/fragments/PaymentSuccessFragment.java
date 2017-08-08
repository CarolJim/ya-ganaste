package com.pagatodo.yaganaste.ui.payments.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarTicketTAEPDSRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarTicketTAEPDSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentsSuccessPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsSuccessPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentSuccessFragment extends GenericFragment implements IRequestResult {

    @BindView(R.id.txt_paymentTitle)
    TextView title;
    @BindView(R.id.txt_importe)
    MontoTextView importe;
    @BindView(R.id.layoutComision)
    LinearLayout layoutComision;
    @BindView(R.id.txtComision)
    MontoTextView txtComision;
    @BindView(R.id.comisionReferenciaText)
    TextView comisionReferenciaText;
    @BindView(R.id.titleReferencia)
    TextView titleReferencia;
    @BindView(R.id.txtReferencia)
    TextView txtReferencia;
    @BindView(R.id.imgLogoPago)
    ImageView imgLogoPago;
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
    @BindView(R.id.layoutEditMail)
    RelativeLayout layoutEditMail;
    @BindView(R.id.editMail)
    EditText editMail;
    @BindView(R.id.btn_continueEnvio)
    StyleButton btnContinueEnvio;
    @BindView(R.id.layoutFavoritos)
    LinearLayout layoutFavoritos;
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
        presenter = new PaymentsSuccessPresenter(getContext(), result);
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

        if (pago instanceof Recarga) {
            title.setText(R.string.title_recarga_success);
            Double comision = result.getData().getComision();
            if (comision > 0) {
                layoutComision.setVisibility(View.VISIBLE);
                //String textComision = String.format("%.2f", comision);
                txtComision.setText(Utils.getCurrencyValue(comision));
            } else {
                layoutComision.setVisibility(View.INVISIBLE);
            }

            if (pago.getComercio().getIdComercio() == 7) {
                titleReferencia.setText(getString(R.string.tag_number) + ":");
            } else {
                titleReferencia.setText(R.string.txt_phone);

            }
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            isMailAviable = true;
        } else if (pago instanceof Servicios) {
            title.setText(R.string.title_servicio_success);
            layoutComision.setVisibility(View.VISIBLE);
            titleReferencia.setText(R.string.ferencia_txt);
            Double comision = result.getData().getComision();
            String textComision = String.format("%.2f", comision);
            textComision = textComision.replace(",", ".");
            txtComision.setText(textComision);
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            isMailAviable = true;
        } else if (pago instanceof Envios) {
            title.setText(R.string.title_envio_success);
            txtComision.setVisibility(View.INVISIBLE);
            comisionReferenciaText.setText("A:");
            titleReferencia.setText(((Envios) pago).getNombreDestinatario());
            layoutMail.setVisibility(View.INVISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            titleMail.setText(R.string.envia_comprobante_a + ((Envios) pago).getNombreDestinatario() + " " + R.string.envia_comprobante_opcional);
        }

        String text = String.format("%.2f", pago.getMonto());
        text = text.replace(",", ".");
        importe.setText(text);

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

        } else if (pago instanceof Envios) {
            // Log.d("PaymentSuccessFragment", "Punto de Debug");
            formatoPago = StringUtils.formatoPagoMedios(formatoPago);
        }


        txtReferencia.setText(formatoPago);
        Glide.with(getContext()).load(pago.getComercio().getLogoURL())
                .placeholder(R.mipmap.logo_ya_ganaste)
                .error(R.mipmap.icon_tab_promos)
                .dontAnimate().into(imgLogoPago);

        autorizacion.setText(result.getData().getNumeroAutorizacion());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatH = new SimpleDateFormat("HH:mm:ss");

        fecha.setText(DateUtil.getBirthDateCustomString(Calendar.getInstance()));
        hora.setText(dateFormatH.format(new Date()) + " hrs");

        //fecha.setText(result.getData().getFecha());
        //hora.setText(result.getData().getHora());

        btnContinueEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMailAviable) {
                    validateMail();
                } else {
                    onFinalize();
                }
            }
        });
    }

    private void onFinalize() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void validateMail() {

        String mail = editMail.getText().toString().trim();
        if (mail != null && !mail.equals("")) {
            if (ValidateForm.isValidEmailAddress(mail)) {
                sendTicket(mail);
            } else {
                showSimpleDialog(getString(R.string.datos_usuario_correo_formato));
            }
        } else {
            onFinalize();
        }

        presenter.validaEmail(mail);
    }

    private void sendTicket(String mail) {
        ((PaymentsProcessingActivity) getActivity()).showLoader(getString(R.string.envia_email));
        EnviarTicketTAEPDSRequest request = new EnviarTicketTAEPDSRequest();
        request.setEmail(mail);
        request.setIdTransaction(result.getData().getIdTransaccion());
        try {
            ApiAdtvo.enviarTicketTAEPDS(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            ((PaymentsProcessingActivity) getActivity()).hideLoader();
            showSimpleDialog(getString(R.string.no_internet_access));
        }
    }


    @Override
    public void onSuccess(DataSourceResult result) {
        ((PaymentsProcessingActivity) getActivity()).hideLoader();

        EnviarTicketTAEPDSResponse data = (EnviarTicketTAEPDSResponse) result.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            showDialog(data.getMensaje());
        } else {
            onFailSendTicket(result);
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        ((PaymentsProcessingActivity) getActivity()).hideLoader();
        onFailSendTicket(error);
    }

    private void onFailSendTicket(DataSourceResult error) {
        String errorTxt = null;
        try {
            EjecutarTransaccionResponse response = (EjecutarTransaccionResponse) error.getData();
            if (response.getMensaje() != null)
                errorTxt = response.getMensaje();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        showDialogError(errorTxt);
    }

    private void showDialog(String text) {
        UI.createSimpleCustomDialogNoCancel(getString(R.string.txt_exito), text,
                getFragmentManager(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        onFinalize();
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                });
    }

    private void showSimpleDialog(String text) {
        UI.createSimpleCustomDialog("Error", text,
                getFragmentManager(), getFragmentTag());
    }

    private void showDialogError(String text) {
        UI.createCustomDialog(getString(R.string.error_enviando_ticket),
                text != null ? text : getString(R.string.error_enviando_ticket),
                getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        validateMail();
                    }

                    @Override
                    public void actionCancel(Object... params) {
                        onFinalize();
                    }
                }, getString(R.string.txt_reintent_lowcase),
                getString(R.string.txt_cancelar));
    }


}
