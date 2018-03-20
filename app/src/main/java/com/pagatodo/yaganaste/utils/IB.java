package com.pagatodo.yaganaste.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.pagatodo.yaganaste.R;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * Created by icruz on 01/03/2018.
 */

public class IB {

    public static String INTENT_SHARE = "INTENT_SHARE";

    public static void IntentShare(Context context,String message){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(sharingIntent, INTENT_SHARE));
    }

    public static void createCallIntent(Activity context) {
        String number = context.getString(R.string.numero_telefono_contactanos);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + number));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ValidatePermissions.checkPermissions(context, new String[]{
                    Manifest.permission.CALL_PHONE}, PERMISSION_GENERAL);
        } else {
            context.startActivity(callIntent);
        }
    }
}
