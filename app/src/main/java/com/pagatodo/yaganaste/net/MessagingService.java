package com.pagatodo.yaganaste.net;

import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.OnlineTxActivity;
import com.pagatodo.yaganaste.utils.NotificationBuilder;

/**
 * @author Juan Guerra on 10/04/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MessagingService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
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
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            NotificationBuilder.createTransactionNotification(this, OnlineTxActivity.class, notification.getTitle(), notification.getBody());
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void handleIntent(Intent intent) {
        NotificationBuilder.createTransactionNotification(this, OnlineTxActivity.class,
                "Hola :)" + intent.getExtras().getString("gcm.notification.title"),
                intent.getExtras().getString("gcm.notification.body"));
    }
}