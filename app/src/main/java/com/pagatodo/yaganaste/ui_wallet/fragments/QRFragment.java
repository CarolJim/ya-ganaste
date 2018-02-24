package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.WINDOW_SERVICE;
import static com.pagatodo.yaganaste.utils.StringUtils.getCreditCardFormat;


public class QRFragment extends SupportFragment {

    @BindView(R.id.imgYaGanasteQR)
    ImageView imgYaGanasteQR;
    public static QRFragment newInstance() {
        return new QRFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void initViews() {
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
        //txtNameTitular.setText(name);

        String celPhone = "";
        String clabe = "";
        //cardNumber = "";
        if (usuario.getCuentas() != null && usuario.getCuentas().size() >= 1) {
            CuentaResponse cuenta = usuario.getCuentas().get(0);
            celPhone = usuario.getCuentas().get(0).getTelefono();
            //cardNumber = getCreditCardFormat(cuenta.getTarjeta());
            clabe = cuenta.getCLABE();
        }
        showQRCode(name, celPhone, usuario.getCuentas().get(0));
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
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null, BarcodeFormat.QR_CODE.toString(), 286);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imgYaGanasteQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
