package com.pagatodo.yaganaste.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui._controllers.manager.FavoritesActivity.FAVORITE_PROCESS;
import static com.pagatodo.yaganaste.utils.Constants.EDIT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * Created by icruz on 01/03/2018.
 */

public class UtilsIntents {

    public static String INTENT_SHARE = "INTENT_SHARE";
    public static int INTENT_FAVORITE = 46325;

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

    public static void favoriteIntents(Activity activity, Favoritos favorito){
        Intent intentEditFav = new Intent(activity, FavoritesActivity.class);
        intentEditFav.putExtra(activity.getString(R.string.favoritos_tag), favorito);
        intentEditFav.putExtra(CURRENT_TAB_ID, Constants.PAYMENT_ENVIOS);
        intentEditFav.putExtra(FAVORITE_PROCESS, EDIT_FAVORITE);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //activity.startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            //activity.startActivity(intentEditFav, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {*/
            //activity.startActivity(intentEditFav);
            activity.startActivityForResult(intentEditFav, INTENT_FAVORITE);
        //}
    }
}
