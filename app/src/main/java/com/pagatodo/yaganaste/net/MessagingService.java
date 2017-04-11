package com.pagatodo.yaganaste.net;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * @author Juan Guerra on 10/04/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public MessagingService() {
        super();
        Log.d(TAG, "MServ");
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "DMess");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.d(TAG, "MSent");
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.d(TAG, "SError");
    }

    @Override
    protected Intent zzF(Intent intent) {
        Log.d(TAG, "zzF");
        return super.zzF(intent);
    }

    @Override
    public boolean zzH(Intent intent) {
        Log.d(TAG, "zzH");
        return super.zzH(intent);
    }

    @Override
    public void zzm(Intent intent) {
        Log.d(TAG, "zzM");
        super.zzm(intent);
    }

    /*private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification *//*, notificationBuilder.build());
    }*/
}