package com.pagatodo.yaganaste.ui.account.login;

/**
 * Created by Armando Sandoval on 22/11/2017.
 */


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

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
        errorIntent=0;
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
        //Toast.makeText(context, "Error de Autenticación\n" + errString, Toast.LENGTH_LONG).show();
        String mensaje=" Demaciados intentos fallidos  Intentalo Más Tarde";
        generateCode.generatecode(mensaje,errorIntent);

    }

    @Override
    public void onAuthenticationFailed() {
       // Toast.makeText(context, "Autenticación Fallida", Toast.LENGTH_LONG).show();
            String mensaje="Huella no Reconocida Intentalo Otra Vez";
        generateCode.generatecode(mensaje);

                errorIntent++;
                generateCode.generatecode(mensaje);
                if (errorIntent==4){
                    mensaje="";
                    generateCode.generatecode(mensaje,errorIntent);
                }

    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(context,
                "Ayuda de autenticación\n" + helpString,
                Toast.LENGTH_LONG).show();
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
