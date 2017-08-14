package com.pagatodo.yaganaste.net;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
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
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            handleData(notification.getTitle(), notification.getBody());
        }
    }

    @Override
    public void handleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String title = extras.getString("gcm.notification.title");
        String body = extras.getString("gcm.notification.body");
        handleData(title, body);
    }

    private void handleData(String title, String body) {
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancelAll();
        String[] values = body.split("-");
        NotificationBuilder.createTransactionNotification(this, OnlineTxActivity.createIntent(this, values[1].trim()),
                title, values[0]);
    }
}