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
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarTicketTAEPDSRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarTicketTAEPDSResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.text.SimpleDateFormat;
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

    private View rootview;
    Payments pago;
    EjecutarTransaccionResponse result;
    private boolean isRecarga = false;

    public static PaymentSuccessFragment newInstance(Payments pago, EjecutarTransaccionResponse result) {
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        Bundle args = new Bundle();
        args.putSerializable("pago", pago);
        args.putSerializable("result", result);
        fragment.setArguments(args);
        return fragment;
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
        pago = (Payments) getArguments().getSerializable("pago");
        result = (EjecutarTransaccionResponse) getArguments().getSerializable("result");
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
            layoutComision.setVisibility(View.GONE);
            titleReferencia.setText(R.string.txt_phone);
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            isRecarga = true;
        } else if (pago instanceof Servicios) {
            title.setText(R.string.title_servicio_success);
            layoutComision.setVisibility(View.VISIBLE);
            titleReferencia.setText("Referencia:");
            Double comision = result.getData().getComision();
            String textComision = String.format("%.2f", comision);
            textComision = textComision.replace(",", ".");
            txtComision.setText(textComision);

        } else if (pago instanceof Envios) {
            title.setText(R.string.title_envio_success);
            txtComision.setVisibility(View.GONE);
            comisionReferenciaText.setText("A:");
            titleReferencia.setText(((Envios) pago).getNombreDestinatario());
            layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            titleMail.setText("Envía Este Comprobante a " + ((Envios) pago).getNombreDestinatario() + " (Opcional)");
        }

        String text = String.format("%.2f", pago.getMonto());
        text = text.replace(",", ".");
        importe.setText(text);

        txtReferencia.setText(pago.getReferencia());
        Glide.with(getContext()).load(pago.getComercio().getLogoURL()).placeholder(R.mipmap.logo_ya_ganaste).error(R.mipmap.icon_tab_promos).dontAnimate().into(imgLogoPago);

        autorizacion.setText(result.getData().getNumeroAutorizacion());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatH = new SimpleDateFormat("HH:mm:ss");

        fecha.setText(dateFormat.format(new Date()));
        hora.setText(dateFormatH.format(new Date()));
        //fecha.setText(result.getData().getFecha());
        //hora.setText(result.getData().getHora());

        btnContinueEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecarga) {
                    validateMail();

                } else {
                   onFinalize();
                }
            }
        });
    }

    private void onFinalize(){
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void validateMail(){
        String mail = editMail.getText().toString().trim();
        if (mail != null && !mail.equals("")) {
            if (ValidateForm.isValidEmailAddress(mail)) {
                sendTicket(mail);
            } else {
                showSimpleDialog(getString(R.string.datos_usuario_correo_formato));
            }
        }
    }

    private void sendTicket(String mail) {
        ((PaymentsProcessingActivity) getActivity()).showLoader("Enviando Email");
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
            //Actualizamos el Saldo del Emisor
            //SingletonUser.getInstance().getDatosSaldo().setSaldoEmisor(String.valueOf(data.getData().getSaldo()));
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

    private void onFailSendTicket(DataSourceResult error){
        String errorTxt = null;
        try {
            EjecutarTransaccionResponse response = (EjecutarTransaccionResponse) error.getData();
            if (response.getMensaje() != null)
                errorTxt = response.getMensaje();
            //Toast.makeText(this, response.getMensaje(), Toast.LENGTH_LONG).show();

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        showDialogError(errorTxt);
    }

    private void showDialog(String text){
        UI.createSimpleCustomDialogNoCancel("Exito", text,
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

    private void showSimpleDialog(String text){
        UI.createSimpleCustomDialog("Error", text,
                getFragmentManager(), getFragmentTag());
    }

    private void showDialogError(String text){
        UI.createCustomDialog("Error Envando Ticket", text,
                getFragmentManager(), getFragmentTag(), new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        validateMail();
                    }

                    @Override
                    public void actionCancel(Object... params) {
                        onFinalize();
                    }
                }, "Reintentar", "Cancelar");
    }
}
