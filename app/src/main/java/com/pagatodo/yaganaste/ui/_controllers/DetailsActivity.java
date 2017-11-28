package com.pagatodo.yaganaste.ui._controllers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ShareEvent;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.exceptions.NullObjectExcepcion;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adqtransactioncancel.fragments.DetailTransactionAdqCancel;
import com.pagatodo.yaganaste.ui.adquirente.fragments.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.TransactionResultFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsEmisorFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.CompartirReciboFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static android.R.attr.fragment;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class DetailsActivity extends LoaderActivity implements OnEventListener {

    public static final String DATA = "data";
    public static final String TYPE = "type";
    public final static String EVENT_GO_TO_FINALIZE_SUCCESS = "FINALIZAR_CANCELACION_SUCCESS";
    public final static String EVENT_GO_TO_FINALIZE_ERROR = "FINALIZAR_CANCELACION_ERROR";
    public final static String EVENT_GO_LOAD_SHARE_EMAIL = "EVENT_GO_LOAD_SHARE_EMAIL";
    public final static String EVENT_CLOSE_ACT = "EVENT_CLOSE_ACT";
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 117;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 101;
    CircleImageView imageView;
    ImageView imageshare;
    private DataMovimientoAdq dataMovimentTmp;
    private Serializable serializable;
    private Object types;

    public static Intent createIntent(@NonNull Context from, MovimientosResponse data) {
        return createIntent(from, TYPES.EMISOR, data);
    }

    public static Intent createIntent(@NonNull Context from, DataMovimientoAdq data) {
        return createIntent(from, TYPES.ADQUIRENTE, data);
    }

    public static Intent createIntent(@NonNull Context from, TYPES type, Serializable data) {
        Intent intent = new Intent(from, DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(DATA, data);
        extras.putString(TYPE, type.toString());
        intent.putExtras(extras);
        return intent;
    }

    @Override
    public void onEvent(String event, Object data2) {
        super.onEvent(event, data2);
        switch (event) {
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()), Direction.FORDWARD, true);
                break;

            case EVENT_GO_TO_FINALIZE_SUCCESS:
                setResult(RESULT_CANCEL_OK);
                this.finish();
                break;

            case EVENT_GO_TO_FINALIZE_ERROR:
                setResult(-1);
                this.finish();
                break;

            case EVENT_CLOSE_ACT:
                this.finish();
                break;

            /**
             * Cargamos nuestro Fragmento de CompartirRecibo, pasandole el movimiento que tenemos
             * en pantalla
             */
            case EVENT_GO_LOAD_SHARE_EMAIL:
                //  loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()), Direction.FORDWARD, true);
                loadFragment(CompartirReciboFragment.newInstance(dataMovimentTmp), Direction.FORDWARD, true);
        }

    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_em_adq);
        Bundle extras = getIntent().getExtras();
        imageView = (CircleImageView) findViewById(R.id.imgToRight_prefe);
        imageView.setVisibility(View.GONE);
        imageshare = (ImageView) findViewById(R.id.deposito_Share);
        imageshare.setVisibility(View.VISIBLE);
        if (extras != null && extras.getSerializable(DATA) != null
                && extras.getString(TYPE) != null) {
            serializable = extras.getSerializable(DATA);
            types = TYPES.valueOf(extras.getString(TYPE));
            loadFragment(TYPES.valueOf(extras.getString(TYPE)), serializable);
        } else {
            throw new IllegalCallException("To call " + this.getClass().getSimpleName() +
                    " you should pass as extra's parameters type and DataMovimientoAdq or " +
                    "ResumenMovimientosAdqResponse");
        }

        // Localizamos el tipo de fragmnto que tenemos cargado
        //fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        imageshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof DetailsEmisorFragment) {
                    if(!DEBUG) {
                        Answers.getInstance().logShare(new ShareEvent());
                    }

                    boolean isValid = true;

                    int permissionSMS = ContextCompat.checkSelfPermission(App.getContext(),
                            Manifest.permission.SEND_SMS);

                    int permissionStorage = ContextCompat.checkSelfPermission(App.getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                    // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
                    if (permissionSMS == -1) {
                        ValidatePermissions.checkPermissions(DetailsActivity.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                        isValid = false;
                    }

                    // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
                    if (permissionStorage == -1) {
                        ValidatePermissions.checkPermissions(DetailsActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_STORAGE);
                        isValid = false;
                    }

                    if(isValid){
                        takeScreenshot();
                    }


                } else if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof DetailsAdquirenteFragment) {
                    // TEMP para mostrar el ScreenShoot en vez del Ticket
                     onEvent(EVENT_GO_LOAD_SHARE_EMAIL, "");
                    //takeScreenshot();
                }
            }
        });
    }

    protected void loadFragment(TYPES type, Serializable data) {
        if (type != null) {
            if (type.equals(TYPES.EMISOR)) {
                loadFragment(DetailsEmisorFragment.newInstance((MovimientosResponse) data));
            } else if (type.equals(TYPES.ADQUIRENTE)) {
                /**
                 * Usamos una variable auxiliar dataMovimentTmp que se encarga de guardar en memoria
                 * nuestro objeto DataMovimientoAdq, para enviarlo al fragmento CompartirReciboFragment
                 * PROCESO Detenido hasta liveracion de Ticket
                 */
                dataMovimentTmp = (DataMovimientoAdq) data;
                loadFragment(DetailsAdquirenteFragment.newInstance((DataMovimientoAdq) data));
            }
        } else {
            throw new NullObjectExcepcion("Type is not recognized");
        }
    }

    public void loadInsertDongleFragment(DataMovimientoAdq dataMovimientoAdq) {
        loadFragment(InsertDongleFragment.newInstance(true, dataMovimientoAdq), Direction.FORDWARD, true);
    }

    @Override
    public void onBackPressed() {
        // Hacemos null nuestra variable auxiliar
        if (!isLoaderShow) {
            super.onBackPressed();
        }
    }

    enum TYPES {
        EMISOR,
        ADQUIRENTE
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    /*        View v1 = getWindow().getDecorView().getRootView();
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
          outputStream.close();*/

    private void takeScreenshot() {
        try {
            View v1 = getWindow().getDecorView().getRootView();
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
        MovimientosResponse emisorData = ((MovimientosResponse) serializable);
        /**
         * Armamos la cadena de datos dependiendo el tipo de servicios que mostramos
         */
        String toShare = "";
        if (types != null) {
            if (types.equals(TYPES.EMISOR)) {
                switch (emisorData.getIdTipoTransaccion()) {
                    case 1:
                        toShare = "¡Hola!\nSe Ha Realizado Una Recarga en Ya Ganaste "
                                + "\nTeléfono: " + emisorData.getReferencia()
                                + "\nMonto: " + Utils.getCurrencyValue(emisorData.getTotal())
                                + "\nFecha: " + emisorData.getFechaMovimiento()
                                + "\nHora: " + emisorData.getHoraMovimiento() + " hrs"
                                + "\nAutorización: " + emisorData.getNumAutorizacion();
                        break;
                    case 2:
                        toShare = "¡Hola!\nSe Ha Realizado un Pago de Servicio Desde Ya Ganaste "
                                + "\nCargo Por Servicio: $" + emisorData.getComision()
                                + "\nIVA: $" + emisorData.getIVA()
                                + "\nMonto: " + Utils.getCurrencyValue(emisorData.getTotal())
                                + "\nReferencia: " + emisorData.getReferencia()
                                + "\nFecha: " + emisorData.getFechaMovimiento()
                                + "\nHora: " + emisorData.getHoraMovimiento() + " hrs"
                                + "\nAutorización: " + emisorData.getNumAutorizacion();
                        break;
                    case 3:
                    case 4:
                        toShare = "¡Hola!\nSe Ha Realizado un Envío de Dinero Desde Ya Ganaste"
                                + "\nReferencia: " + emisorData.getReferencia()
                                + "\nConcepto: " + emisorData.getConcepto()
                                + "\nMonto: " + Utils.getCurrencyValue(emisorData.getTotal())
                                + "\nClave de Rastreo: " + emisorData.getClaveRastreo()
                                + "\nFecha: " + emisorData.getFechaMovimiento()
                                + "\nHora: " + emisorData.getHoraMovimiento() + " hrs"
                                + "\nAutorización: " + emisorData.getNumAutorizacion();
                        break;
                }
            }
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, toShare);
        startActivity(Intent.createChooser(intent, "Compartir Con..."));
    }
}