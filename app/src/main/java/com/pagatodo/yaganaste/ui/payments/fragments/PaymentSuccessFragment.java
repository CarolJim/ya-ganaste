package com.pagatodo.yaganaste.ui.payments.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentSuccessManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentSuccessPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsSuccessPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidateForm;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.CustomValidationEditText;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.MY_PERMISSIONS_REQUEST_STORAGE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_OK;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_OK_CLOSE;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;
import static com.pagatodo.yaganaste.utils.Recursos.IDCOMERCIO_YA_GANASTE;
import static com.pagatodo.yaganaste.utils.Recursos.SPACE;

/**
 * Created by Jordan on 27/04/2017.
 */

public class PaymentSuccessFragment extends SupportFragment implements PaymentSuccessManager,
        View.OnClickListener {

    @BindView(R.id.txt_paymentTitle)
    TextView title;
    @BindView(R.id.txt_importe)
    StyleTextView importe;
    @BindView(R.id.titleReferencia)
    TextView titleReferencia;
    @BindView(R.id.txtReferencia)
    TextView txtReferencia;
    @BindView(R.id.txtAutorizacion)
    TextView autorizacion;
    @BindView(R.id.txtClaveRastreo)
    TextView txtClaveratreo;
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
    @BindView(R.id.btn_addFavorite)
    StyleButton btnAddFavoriite;
    @BindView(R.id.layoutFavoritos)
    LinearLayout layoutFavoritos;
    @BindView(R.id.txtCompania)
    TextView txtCompania;
    @BindView(R.id.nombreEnvio)
    TextView nombreEnvio;
    @BindView(R.id.txtConcepto)
    TextView txtConcepto;
    @BindView(R.id.imgAddFavorite)
    ImageView imgAddFavorite;
    @BindView(R.id.txtHintFavorite)
    StyleTextView txtHintFavorite;

    @BindView(R.id.layout_enviado)
    LinearLayout layoutEnviado;

    @BindView(R.id.layout_compania)
    LinearLayout layoutCompania;

    @BindView(R.id.layout_addfavorites)
    LinearLayout addFavorites;

    @BindView(R.id.layout_clave_rastreo)
    LinearLayout layoutClaveRastreo;

    @BindView(R.id.layout_concepto)
    LinearLayout layoutConcepto;

    @BindView(R.id.view_clave_rastreo)
    View viewClaveRastreo;

    @BindView(R.id.imgShare)
    LinearLayout imageshae;
    boolean enviotrue = false;
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
        imageshae.setOnClickListener(this);
        if (pago.isFavorite()) {
            imgAddFavorite.setImageResource(R.drawable.ic_fav);
            //txtHintFavorite.setText(getString(R.string.is_favorite));
            txtHintFavorite.setVisibility(View.INVISIBLE);
            btnAddFavoriite.setVisibility(View.INVISIBLE);
        }

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
                titleReferencia.setText(getString(R.string.hint_tag));
            } else {
                // titleReferencia.setText(R.string.details_telefono);
                titleReferencia.setText("Teléfono");

            }
            //layoutMail.setVisibility(View.VISIBLE);
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
            //layoutMail.setVisibility(View.VISIBLE);
            layoutFavoritos.setVisibility(View.GONE);
            isMailAviable = true;
            txtCompania.setText(pago.getComercio().getNombreComercio());
        } else if (pago instanceof Envios) {
            layoutCompania.setVisibility(View.GONE);
            if (pago.getComercio().getIdComercio() == IDCOMERCIO_YA_GANASTE) {
                title.setText(R.string.title_envio_success);
                enviotrue = true;
            } else {
                title.setText(R.string.title_envio_in_proccess);
                enviotrue = true;
            }
            nombreEnvio.setText(((Envios) pago).getNombreDestinatario());
            layoutFavoritos.setVisibility(View.GONE);
            String fullName = ((Envios) pago).getNombreDestinatario();
            titleMail.setText(
                    getContext().getResources().getString(R.string.envia_comprobante_a)
                            + " " + StringUtils.formatSingleName(fullName)
                            + " " + getContext().getResources().getString(R.string.envia_comprobante_opcional));


            titleReferencia.setText(((Envios) pago).getTipoEnvio().getShortName());
            if (!result.getData().getClaveRastreo().isEmpty()) {
                txtClaveratreo.setText(result.getData().getClaveRastreo());
                txtClaveratreo.setSelected(true);
                layoutClaveRastreo.setVisibility(View.VISIBLE);
                viewClaveRastreo.setVisibility(View.VISIBLE);

            } else {
                layoutClaveRastreo.setVisibility(View.GONE);
                viewClaveRastreo.setVisibility(View.GONE);
            }

        }

        importe.setText(Utils.getCurrencyValue(pago.getMonto()));

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

            // Mostramos el concepto por ser servicio
            layoutConcepto.setVisibility(View.VISIBLE);
            txtConcepto.setText(pago.getConcepto());
        } else if (pago instanceof Envios) {
            if (formatoPago.length() == 16 || formatoPago.length() == 15) {
                formatoPago = StringUtils.maskReference(StringUtils.format(formatoPago, SPACE, 4, 4, 4, 4), '*', formatoPago.length() - 12);
            } else {
                formatoPago = StringUtils.formatoPagoMedios(formatoPago);
            }
        }

        txtReferencia.setText(formatoPago);
        txtReferencia.setSelected(true);
        titleReferencia.setSelected(true);
        autorizacion.setSelected(true);
        titleMail.setSelected(true);
        txtCompania.setSelected(true);

        autorizacion.setText(StringUtils.formatAutorization(result.getData().getNumeroAutorizacion()));
        SimpleDateFormat dateFormatH = new SimpleDateFormat("HH:mm:ss");
        // fecha.setText(DateUtil.getBirthDateCustomString(Calendar.getInstance()));
        fecha.setText(DateUtil.getPaymentDateSpecialCustom(Calendar.getInstance()));
        hora.setText(dateFormatH.format(new Date()) + " hrs");
        btnContinueEnvio.setOnClickListener(this);
        showBack(false);
    }

    @Override
    public void validateMail() {
        String mail = editMail.getText().trim();

        if (!TextUtils.isEmpty(mail)) {
            if (ValidateForm.isValidEmailAddress(mail)) {
                showLoader(getString(R.string.procesando_enviando_email_loader));
                if (pago instanceof Envios) {
                    presenter.sendTicketEnvio(mail, result.getData().getIdTransaccion());
                } else {
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
        //UI.createSimpleCustomDialog(title, text, getFragmentManager(), this.getFragmentTag());


        UI.showAlertDialog(getContext(), title, text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
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
        /*
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

            */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.error_enviando_ticket))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.txt_reintent_lowcase), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        validateMail();
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finalizePayment();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
        getActivity().finish();

        if (enviotrue) {
            getActivity().finish();
        }
        //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnContinueEnvio.getId()) {
            if (isMailAviable) {
                validateMail();
            } else {
                finalizePayment();
            }

        } else if (v.getId() == R.id.imgShare) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == -1) {
                ValidatePermissions.checkPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
            takeScreenshot();
            //shareContent();
        }
    }

    private void shareContent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String toShare = "";
        if (pago instanceof Recarga) {
            toShare = "¡Hola!\nSe Ha Realizado una Recarga en Ya Ganaste \n" + getString(R.string.share_recargas, Utils.getCurrencyValue(pago.getMonto()), txtReferencia.getText().toString(),
                    pago.getComercio().getNombreComercio(), fecha.getText().toString(), hora.getText().toString(), autorizacion.getText().toString());
        } else if (pago instanceof Servicios) {
            toShare = "¡Hola!\nSe Ha Realizado un Pago de Servicio Desde Ya Ganaste \n" + getString(R.string.share_pds, Utils.getCurrencyValue(pago.getMonto()), txtReferencia.getText().toString(),
                    pago.getComercio().getNombreComercio(), fecha.getText().toString(), hora.getText().toString(), autorizacion.getText().toString());
        } else if (pago instanceof Envios) {
            toShare = "¡Hola!\nSe Ha Realizado un Envío de Dinero Desde Ya Ganaste \n" + getString(R.string.share_envios, Utils.getCurrencyValue(pago.getMonto()), nombreEnvio.getText().toString(),
                    ((Envios) pago).getTipoEnvio().getShortName(), txtReferencia.getText().toString(), fecha.getText().toString(), hora.getText().toString(), autorizacion.getText().toString())
                    .concat(pago.getComercio().getIdComercio() == IDCOMERCIO_YA_GANASTE ? "" : getString(R.string.clave_rastreo, result.getData().getClaveRastreo()));
        }
        intent.putExtra(Intent.EXTRA_TEXT, toShare);
        startActivity(Intent.createChooser(intent, "Compartir Con: "));
    }

    public void hideAddFavorites() {
        if (!pago.isFavorite()) {
            btnAddFavoriite.setVisibility(View.INVISIBLE);
            btnAddFavoriite.setEnabled(false);
            txtHintFavorite.setText(getString(R.string.is_favorite));
            UI.showSuccessSnackBar(getActivity(), getString(R.string.is_favorite), Snackbar.LENGTH_SHORT);
        }
    }

    public void takeScreenshot() {
        try {
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File carpeta = new File(Environment.getExternalStorageDirectory() + getString(R.string.path_image));
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            File imageFile = new File(Environment.getExternalStorageDirectory() + getString(R.string.path_image) + "/", System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            sendScreenshot(imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void sendScreenshot(File imageFile) {
        //MovimientosResponse emisorData = ((MovimientosResponse) serializable);
        /**
         * Armamos la cadena de datos dependiendo el tipo de servicios que mostramos
         */
        String toShare = "";
        if (pago instanceof Recarga) {
            toShare = "¡Hola!\nSe Ha Realizado una Recarga en Ya Ganaste \n" + getString(R.string.share_recargas, Utils.getCurrencyValue(pago.getMonto()), txtReferencia.getText().toString(),
                    pago.getComercio().getNombreComercio(), fecha.getText().toString(), hora.getText().toString(), autorizacion.getText().toString());
        } else if (pago instanceof Servicios) {
            toShare = "¡Hola!\nSe Ha Realizado un Pago de Servicio Desde Ya Ganaste \n" + getString(R.string.share_pds, Utils.getCurrencyValue(pago.getMonto()), txtReferencia.getText().toString(),
                    pago.getComercio().getNombreComercio(), fecha.getText().toString(), hora.getText().toString(), autorizacion.getText().toString());
        } else if (pago instanceof Envios) {
            toShare = "¡Hola!\nSe Ha Realizado un Envío de Dinero Desde Ya Ganaste \n" + getString(R.string.share_envios, Utils.getCurrencyValue(pago.getMonto()), nombreEnvio.getText().toString(),
                    titleReferencia.getText().toString(), txtReferencia.getText().toString(), fecha.getText().toString(), hora.getText().toString(), autorizacion.getText().toString())
                    .concat(pago.getComercio().getIdComercio() == IDCOMERCIO_YA_GANASTE ? "" : getString(R.string.clave_rastreo, result.getData().getClaveRastreo()));
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, toShare);
        startActivity(Intent.createChooser(intent, "Compartir Con..."));

      /*  Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(Intent.EXTRA_TEXT, toShare);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Compartir Con..."));*/
    }
}