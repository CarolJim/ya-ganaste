package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.content.Intent;

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
}
