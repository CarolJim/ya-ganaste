package com.pagatodo.yaganaste.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;

import static com.pagatodo.yaganaste.utils.Recursos.NOTIF_COUNT;

/**
 * @author Juan Guerra on 10/04/2017.
 * @update Frank Manzo 13/12/2017
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //   Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Varificamos si el mensaje remoto contiene datos. debemos verificar que concuerden los
        // nombres de variables
        if (remoteMessage.getData().size() > 0) {
            //    Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().get("myBody"));
            int notifPendents = App.getInstance().getPrefs().loadDataInt(NOTIF_COUNT) + 1;
            App.setBadge(notifPendents);
            App.getInstance().getPrefs().saveDataInt(NOTIF_COUNT, notifPendents);
        }
    }

    /**
     * Metodo que crea la notificacion.
     *
     * @param messageBody
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}