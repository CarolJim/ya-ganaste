package com.pagatodo.yaganaste.ui_wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaUyUResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EmisorResponse;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IAddRequestPayment;
import com.pagatodo.yaganaste.utils.qrcode.MyQr;
import com.pagatodo.yaganaste.utils.qrcode.QrcodeGenerator;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogQrProfile extends DialogFragment {

    View rootView;
    @BindView(R.id.img_qr_profile)
    ImageView imgQrProfile;
   /* @BindView(R.id.img_logo)
    ImageView imgLogo;*/

    IAddRequestPayment addRequestPaymentListener;

    public static DialogQrProfile newInstance() {
        DialogQrProfile dialogFragment = new DialogQrProfile();
        /*Bundle args = new Bundle();
        args.putSerializable("backUpResponse", null);
        dialogFragment.setArguments(args);*/
        return dialogFragment;
    }

    public void setAddRequestPaymentListener(IAddRequestPayment addRequestPaymentListener) {
        this.addRequestPaymentListener = addRequestPaymentListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_iset);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_qr_profile, null);
        initViews();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
        ClienteResponse userData = SingletonUser.getInstance().getDataUser().getCliente();
        EmisorResponse emisorData = SingletonUser.getInstance().getDataUser().getEmisor();
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
        String name = nombreprimerUser + " " + apellidoMostrarUser;
        String celPhone = "";
        if (emisorData.getCuentas() != null && emisorData.getCuentas().size() >= 1) {
            CuentaUyUResponse cuenta = emisorData.getCuentas().get(0);
            celPhone = emisorData.getCuentas().get(0).getTelefono();
        }
        showQRCode(name, celPhone, emisorData.getCuentas().get(0));
    }

    private void showQRCode(String name, String cellPhone, CuentaUyUResponse usuario) {
        //Find screen size
        /*WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 4 / 5;*/
        MyQr myQr = new MyQr(name, cellPhone, usuario.getTarjetas().get(0).getNumero(), usuario.getCLABE());
        String gson = new Gson().toJson(myQr);
        //String gsonCipher = Utils.cipherAES(gson, true);
        Log.e("Ya Ganaste", "QR JSON: " + /*myQr.toString()*/gson /*+ "\nQR Ciphered: " + gsonCipher*/);
        QrcodeGenerator qrCodeEncoder = new QrcodeGenerator(gson, null, BarcodeFormat.QR_CODE.toString(), /*smallerDimension*/Utils.convertDpToPixels(500));
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imgQrProfile.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        /*imgLogo.getLayoutParams().width = imgQrProfile.getWidth();
        imgLogo.getLayoutParams().height = imgQrProfile.getHeight();*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((TabActivity) getActivity()).isDialogShowned = false;
    }
}
