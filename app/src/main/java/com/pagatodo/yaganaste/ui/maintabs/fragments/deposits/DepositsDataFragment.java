package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaUyUResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EmisorResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui_wallet.patterns.Container;
import com.pagatodo.yaganaste.ui_wallet.patterns.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.WalletMainActivity;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;
import com.pagatodo.yaganaste.utils.UtilsIntents;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.WINDOW_SERVICE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

/**
 * Created by Jordan on 17/05/2017.
 */

public class DepositsDataFragment extends SupportFragment implements View.OnClickListener {
    //DepositsManager depositsManager;
    @BindView(R.id.imgYaGanasteQR)
    ImageView imgYaGanasteQR;
    @BindView(R.id.text_data_list)
    ListView listView;
    private View rootView;
    String mensaje,cardNumber;
    boolean onlineNetWork, onlineGPS;


    public static DepositsDataFragment newInstance() {
        DepositsDataFragment depositsDataFragment = new DepositsDataFragment();
        Bundle args = new Bundle();
        depositsDataFragment.setArguments(args);
        return depositsDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //depositsManager = ((DepositsFragment) getParentFragment()).getDepositManager();
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                onBackPressed();
                return true;*/
            case R.id.action_share:
                UtilsIntents.IntentShare(getContext(),mensaje);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, true);
        return inflater.inflate(R.layout.fragment_deposito_datos, container, false);
    }



    private void getStatusGPS() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        onlineNetWork = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        onlineGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
        ClienteResponse userData = SingletonUser.getInstance().getDataUser().getCliente();

        String nombreprimerUser;
        String apellidoMostrarUser;
        if (userData.getPrimerApellido().isEmpty()) {
            apellidoMostrarUser = userData.getSegundoApellido();
        } else {
            apellidoMostrarUser = userData.getPrimerApellido();
        }
        nombreprimerUser = StringUtils.getFirstName(userData.getNombre());
        if (nombreprimerUser.isEmpty()) {
            nombreprimerUser = userData.getNombre();
        }

        EmisorResponse usuario = SingletonUser.getInstance().getDataUser().getEmisor();
        //String name = usuario.getNombre().concat(SPACE).concat(usuario.getPrimerApellido()).concat(SPACE).concat(usuario.getSegundoApellido());

        //txtNameTitular.setText(name);
        String name = nombreprimerUser + " " + apellidoMostrarUser;
        //txtNameTitular.setText(name);

        String celPhone = "";
        String clabe = "";
        cardNumber = "";
        if (usuario.getCuentas() != null && usuario.getCuentas().size() >= 1) {
            CuentaUyUResponse cuenta = usuario.getCuentas().get(0);
            celPhone = usuario.getCuentas().get(0).getTelefono();
            cardNumber = getCreditCardFormat(cuenta.getTarjetas().get(0).getNumero());
            clabe = cuenta.getCLABE();
        }
        showQRCode(name, celPhone, usuario.getCuentas().get(0));

        mensaje = getString(R.string.string_share_deposits, name, celPhone, clabe, cardNumber);

        Container list = new Container();
        list.addTextDataA(new TextData(R.string.datos_deposito_titular,name));
        list.addTextDataA(new TextData(R.string.datos_depsito_numero_celular, celPhone));
        list.addTextDataA(new TextData(R.string.datos_deposito_clabe, clabe));
        list.addTextDataA(new TextData(R.string.datos_deposito_num_card, cardNumber));
        listView.setAdapter(ContainerBuilder.DEPOSITO(getContext(),list));
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //btnDepositar.setOnClickListener(this);
        //imageshae.setOnClickListener(this);
        WalletMainActivity.showToolBarMenu();
    }

    @Override
    public void onClick(View v) {
        getStatusGPS();
        /*f (v.getId() == R.id.btnDepositar) {
            if (onlineNetWork || onlineGPS) {
                depositsManager.onTapButton();
            } else {
                showDialogMesage(getActivity().getResources().getString(R.string.ask_permission_gps));
            }
        }*/
        if (v.getId() == R.id.deposito_Share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            //intent.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(intent, "Compartir con.."));
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        if (getParentFragment() != null && getParentFragment().isMenuVisible()) {
            //((ToolBarActivity) getActivity()).setVisibilityPrefer(false);
        }

        String statusId = SingletonUser.getInstance().getCardStatusId();
        if (SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero().equals("")) {
            checkState("0");
        } else if (statusId != null && !statusId.isEmpty()) {
            // && statusId.equals(Recursos.ESTATUS_DE_NO_BLOQUEADA)
            checkState(statusId);
        } else {
            checkState(App.getInstance().getStatusId());
        }
    }


    private void checkState(String state) {
        switch (state) {
            case "0":
                //imgYaGanasteQR.setImageResource(R.mipmap.main_card_zoom_gray);
                //txtNumberCard.setText(getString(R.string.transfer_card_unavailable));
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                //printCard(cardNumber);
                //txtNumberCard.setText(cardNumber);
                break;
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                //imgYaGanasteQR.setImageResource(R.mipmap.main_card_zoom_gray);
                //txtNumberCard.setText(cardNumber);
                break;
            default:
                //printCard(cardNumber);
                break;
        }
    }

    private void showQRCode(String name, String cellPhone, CuentaUyUResponse usuario) {
        //Find screen size
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        QrcodeGenerator.MyQr myQr = new QrcodeGenerator.MyQr(name, cellPhone, usuario.getTarjetas().get(0).getNumero(), usuario.getCLABE());
        String gson = new Gson().toJson(myQr);
        //String gsonCipher = Utils.cipherAES(gson, true);
        Log.e("Ya Ganaste", "QR JSON: " + /*myQr.toString()*/gson /*+ "\nQR Ciphered: " + gsonCipher*/);
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null, BarcodeFormat.QR_CODE.toString(), smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imgYaGanasteQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /*private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }*/
/*
    private void printCard(String cardNumber) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.main_card_zoom_blue);
        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        TextPaint textPaint = new TextPaint();
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", getContext());
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(typeface);

        float heigth = canvas.getHeight();
        float width = canvas.getWidth();
        textPaint.setTextSize(heigth * 0.115f);

        canvas.drawText(cardNumber, width * 0.07f, heigth * 0.6f, textPaint);


        imgYaGanasteQR.setImageBitmap(bitmap);
    }
*/


}
