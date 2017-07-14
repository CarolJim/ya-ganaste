package com.pagatodo.yaganaste.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.R;

/**
 * Created by flima on 12/04/2017.
 */

public class NotificationBuilder {


    public static void createTransactionNotification(Context context, Class clazz, RemoteMessage.Notification notificationContent) {
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        sendNotification(context, pendingIntent, 0, notificationContent.getTitle(), notificationContent.getBody());

    }


    private static void sendNotification(Context context, PendingIntent pendingIntent, int idNotification, String title, String message) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.icon_lapiz)
                .setContentTitle("TITULO;" + title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(idNotification, notificationBuilder.build());
    }
}
