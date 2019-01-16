package com.pagatodo.yaganaste.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import static com.pagatodo.yaganaste.ui._controllers.OnlineTxActivity.DATA;

/**
 * Created by flima on 12/04/2017.
 */

public class NotificationBuilder {

    public static void createTransactionNotification(Context context, Intent toLaunch, String title, String body) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, toLaunch,
                PendingIntent.FLAG_ONE_SHOT);
        sendNotification(context, pendingIntent, 0, title, body);
    }


    public static void createCloseSessionNotification(Context context, Intent toLaunch, String title, String body) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, toLaunch,
                PendingIntent.FLAG_ONE_SHOT);
        sendNotification(context, pendingIntent, 0, title, body);
    }


    private static void sendNotification(Context context, PendingIntent pendingIntent, int idNotification, String title, String message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(idNotification, notificationBuilder.build());
    }
}
