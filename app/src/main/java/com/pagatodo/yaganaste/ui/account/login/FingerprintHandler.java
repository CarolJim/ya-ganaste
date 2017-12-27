package com.pagatodo.yaganaste.ui.account.login;

/**
 * Created by Armando Sandoval on 22/11/2017.
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.utils.Utils;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;


@SuppressLint("NewApi")
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Preferencias prefs = App.getInstance().getPrefs();
    private CancellationSignal cancellationSignal;
    private Context context;
    private generateCode generateCode;
    private boolean mSelfCancelled;
    private int errorIntent=0;
    public FingerprintHandler(Context mContext) {
        context = mContext;

    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        cancellationSignal = new CancellationSignal();

        mSelfCancelled = false;

        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    public void stopListening() {
        if (cancellationSignal != null) {
            mSelfCancelled = true;
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    public void stopListeningcontraseña() {
        if (cancellationSignal != null) {
            mSelfCancelled = true;
            cancellationSignal = null;
        }
    }


    public void setGenerateCode(generateCode generateCodec) {
        generateCode = generateCodec;
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {

        String mensaje = context.getString(R.string.fingerprint_try_later);
        generateCode.generatecode(mensaje,errorIntent);

    }

    @Override
    public void onAuthenticationFailed() {

            String mensaje = context.getString(R.string.fingerprint_unrecognized);
        generateCode.generatecode(mensaje);

                errorIntent++;
                generateCode.generatecode(mensaje);
                if (errorIntent==3){
                    prefs.saveDataBool(HUELLA_FAIL, true);//Huella Fail
                    mensaje="";
                    generateCode.generatecode(mensaje,errorIntent);
                }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(context, "Ayuda de autenticación\n" + helpString, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        generateCode.generatecode();
    }

    public  interface generateCode{
        void generatecode();
        void generatecode(String mensaje);
        void generatecode(String mensaje,int errors);
    }
}