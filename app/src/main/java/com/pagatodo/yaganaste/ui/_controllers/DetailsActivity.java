package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static android.R.attr.fragment;
import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;

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
    CircleImageView imageView;
    ImageView imageshare;
    private DataMovimientoAdq dataMovimentTmp;

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
            Serializable serializable = extras.getSerializable(DATA);
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
                    takeScreenshot();
                } else if (getSupportFragmentManager().findFragmentById(R.id.container) instanceof DetailsAdquirenteFragment) {
                    // TEMP para mostrar el ScreenShoot en vez del Ticket
                    // onEvent(EVENT_GO_LOAD_SHARE_EMAIL, "");
                    takeScreenshot();
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

    public void loadCancelFragment(DataMovimientoAdq data) {
        imageshare.setVisibility(View.GONE);
        loadFragment(DetailTransactionAdqCancel.newInstance(data), Direction.FORDWARD, true);
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
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Compartir Con..."));
    }
}
