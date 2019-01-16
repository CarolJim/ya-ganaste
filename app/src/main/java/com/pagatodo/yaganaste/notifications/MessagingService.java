package com.pagatodo.yaganaste.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;

import static com.pagatodo.yaganaste.utils.Recursos.NOTIF_COUNT;

/**
 * @author Juan Guerra on 10/04/2017.
 * @update Frank Manzo 13/12/2017
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";
    private static final String CHANNEL_PROMOS = "CHANNEL_PROMOS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Varificamos si el mensaje remoto contiene datos. debemos verificar que concuerden los
        // nombres de variables
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            // sendNotification(remoteMessage.getData().get("myBody"), remoteMessage.getData().get("id"));
            int notifPendents = App.getInstance().getPrefs().loadDataInt(NOTIF_COUNT) + 1;
            //App.setBadge(notifPendents);
            App.getInstance().getPrefs().saveDataInt(NOTIF_COUNT, notifPendents);
            /*String idType = getIntent().getExtras().get("id").toString();
            String urlData = getIntent().getExtras().get("urlData").toString();
            String nameData = getIntent().getExtras().get("nameData").toString();
            String typeData = getIntent().getExtras().get("typeData").toString();*/

            String idType = remoteMessage.getData().get("id").toString();
            String urlData = remoteMessage.getData().get("urlData").toString();
            String nameData = remoteMessage.getData().get("nameData").toString();
            String typeData = remoteMessage.getData().get("typeData").toString();

            // Dependiendo del tipo de idType mandamos a una URL o al proceso de descargar archivos
            if (idType != null) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "idType: " + idType);
                switch (idType) {
                    case "1":
                        //String url = "https://play.google.com/store/apps/details?id=com.pagatodo.yaganaste";
                        String url = urlData;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                    case "2":
                        App.getInstance().downloadFile(urlData,
                                nameData, typeData, null);
                        break;
                    case "3":
                        //  startActivity(new Intent(this, TestActivity.class));
                        // this.finish();
                        break;
                    case "4":
                        break;
                }
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_PROMOS)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.card_sbux)).bigLargeIcon(null))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
    }

    /**
     * Metodo que crea la notificacion.
     *
     * @param messageBody
     * @param id
     */
    private void sendNotification(String messageBody, String id) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("id", id);
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