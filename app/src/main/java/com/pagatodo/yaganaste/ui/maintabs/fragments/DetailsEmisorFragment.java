package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE;
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.CreateDatailBuilder;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.REEMBOLSO_ADQUIRIENTE;
import static com.pagatodo.yaganaste.ui._controllers.DetailsActivity.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.MY_PERMISSIONS_REQUEST_STORAGE;


public class DetailsEmisorFragment extends GenericFragment {

    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.header_mov)
    LinearLayout header;

    private View rootView;
    private MovimientosResponse movimientosResponse;

    public static DetailsEmisorFragment newInstance(@NonNull MovimientosResponse movimientosResponse) {
        DetailsEmisorFragment detailsEmisorFragment = new DetailsEmisorFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailsActivity.DATA, movimientosResponse);
        detailsEmisorFragment.setArguments(args);
        return detailsEmisorFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        setHasOptionsMenu(true);
        if (args != null) {
            movimientosResponse = (MovimientosResponse) args.getSerializable(DetailsActivity.DATA);
        } else {
            throw new IllegalCallException(DetailsEmisorFragment.class.getSimpleName() + "must be called by newInstance factory method");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_movements_emisor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        String[] date = movimientosResponse.getFechaMovimiento().split(" ");
        TipoTransaccionPCODE tipoTransaccion = TipoTransaccionPCODE.getTipoTransaccionById(movimientosResponse.getIdTipoTransaccion());
        ItemMovements item;
        if (tipoTransaccion != REEMBOLSO_ADQUIRIENTE) {
            item = new ItemMovements<>(movimientosResponse.getDescripcion(), movimientosResponse.getDetalle(),
                    movimientosResponse.getTotal(), date[0], date[1],
                    MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                    movimientosResponse);
        } else {
            item = new ItemMovements<>(movimientosResponse.getDetalle(), movimientosResponse.getConcepto(),
                    movimientosResponse.getTotal(), date[0], date[1],
                    MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                    movimientosResponse);
        }

        CreateDatailBuilder.creatHeaderMovDetail(getContext(), header, item);
        CreateDatailBuilder.createByType(getContext(), container, movimientosResponse);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                onBackPressed();
                return true;*/
            case R.id.action_share:
                openSharedData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSharedData() {
        boolean isValid = true;
        int permissionSMS = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.SEND_SMS);

        int permissionStorage = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionSMS == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
            isValid = false;
        }

        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionStorage == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
            isValid = false;
        }

        if (isValid) {
            takeScreenshot();
        }
    }

    private void takeScreenshot() {
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
        MovimientosResponse emisorData = movimientosResponse;
        /**
         * Armamos la cadena de datos dependiendo el tipo de servicios que mostramos
         */
        String toShare = "";
        /*switch (emisorData.getIdTipoTransaccion()) {
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
        }*/
        TipoTransaccionPCODE tipoTransaccion = TipoTransaccionPCODE.getTipoTransaccionById(emisorData.getIdTipoTransaccion());
        toShare = "¡Hola!\n" + "Estos son los datos del movimiento de tu cuenta ya ganaste\n\n" +
                "Concepto: " + tipoTransaccion.getName() + "\n" +
                "Fecha: " + emisorData.getFechaMovimiento() + "\n" +
                "Hora:" + emisorData.getHoraMovimiento() + "\n" +
                "Autorización: " + emisorData.getNumAutorizacion();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        Uri uri = Uri.fromFile(imageFile);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, toShare);
        getActivity().startActivity(Intent.createChooser(intent, "Compartir Con..."));
    }
}