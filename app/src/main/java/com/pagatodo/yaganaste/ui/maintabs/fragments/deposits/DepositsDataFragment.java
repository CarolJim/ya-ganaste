package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositsManager;
import com.pagatodo.yaganaste.utils.FontCache;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.WINDOW_SERVICE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;

/**
 * Created by Jordan on 17/05/2017.
 */

public class DepositsDataFragment extends SupportFragment implements View.OnClickListener {
    DepositsManager depositsManager;
    @BindView(R.id.imgYaGanasteQR)
    ImageView imgYaGanasteQR;
    @BindView(R.id.txtNameTitular)
    TextView txtNameTitular;
    @BindView(R.id.txtNumberCard)
    TextView txtNumberCard;
    @BindView(R.id.txtCellPhone)
    TextView txtCellPhone;
    @BindView(R.id.txtCableNumber)
    TextView txtCableNumber;
    @BindView(R.id.btnDepositar)
    Button btnDepositar;

    private View rootView;
    String mensaje, cardNumber;
    ImageView imageshae;
    boolean onlineNetWork, onlineGPS;
    ImageView imageView;

    public static DepositsDataFragment newInstance() {
        DepositsDataFragment depositsDataFragment = new DepositsDataFragment();
        Bundle args = new Bundle();
        depositsDataFragment.setArguments(args);
        return depositsDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositsManager = ((DepositsFragment) getParentFragment()).getDepositManager();
        imageshae = (ImageView) getActivity().findViewById(R.id.deposito_Share);
        imageView = (ImageView) getActivity().findViewById(R.id.imgNotifications);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        UsuarioClienteResponse userData = SingletonUser.getInstance().getDataUser().getUsuario();

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

        UsuarioClienteResponse usuario = SingletonUser.getInstance().getDataUser().getUsuario();
        //String name = usuario.getNombre().concat(SPACE).concat(usuario.getPrimerApellido()).concat(SPACE).concat(usuario.getSegundoApellido());

        //txtNameTitular.setText(name);
        String name = nombreprimerUser + " " + apellidoMostrarUser;
        txtNameTitular.setText(name);

        String celPhone = "";
        String clabe = "";
        cardNumber = "";
        if (usuario.getCuentas() != null && usuario.getCuentas().size() >= 1) {
            CuentaResponse cuenta = usuario.getCuentas().get(0);
            celPhone = usuario.getCuentas().get(0).getTelefono();
            cardNumber = getCreditCardFormat(cuenta.getTarjeta());
            clabe = cuenta.getCLABE();
        }
        showQRCode(name, celPhone, usuario.getCuentas().get(0));
        txtCableNumber.setText(clabe);
        txtCellPhone.setText(celPhone);
        txtNumberCard.setText(cardNumber);

        mensaje = getString(R.string.string_share_deposits, name, celPhone, clabe, cardNumber);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnDepositar.setOnClickListener(this);
        imageshae.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getStatusGPS();
        if (v.getId() == R.id.btnDepositar) {
            if (onlineNetWork || onlineGPS) {
                depositsManager.onTapButton();
            } else {
                showDialogMesage(getActivity().getResources().getString(R.string.ask_permission_gps));
            }
        }
        if (v.getId() == R.id.deposito_Share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            intent.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(intent, "Compartir con.."));
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        if (getParentFragment() != null && getParentFragment().isMenuVisible()) {
            //((ToolBarActivity) getActivity()).setVisibilityPrefer(false);
        }

        String statusId = SingletonUser.getInstance().getCardStatusId();
        if (SingletonUser.getInstance().getDataUser().getUsuario().getCuentas().get(0).getTarjeta().equals("")) {
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
                txtNumberCard.setText(getString(R.string.transfer_card_unavailable));
                break;
            case Recursos.ESTATUS_CUENTA_DESBLOQUEADA:
                //printCard(cardNumber);
                txtNumberCard.setText(cardNumber);
                break;
            case Recursos.ESTATUS_CUENTA_BLOQUEADA:
                //imgYaGanasteQR.setImageResource(R.mipmap.main_card_zoom_gray);
                txtNumberCard.setText(cardNumber);
                break;
            default:
                //printCard(cardNumber);
                break;
        }
    }

    private void showQRCode(String name, String cellPhone, CuentaResponse usuario) {
        //Find screen size
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        QrcodeGenerator.MyQr myQr = new QrcodeGenerator.MyQr(name, cellPhone, usuario.getTarjeta(), usuario.getCLABE());
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

    private void showDialogMesage(final String mensaje) {
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
    }

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
}
