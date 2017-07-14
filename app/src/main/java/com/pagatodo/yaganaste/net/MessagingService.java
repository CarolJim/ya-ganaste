package com.pagatodo.yaganaste.net;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.utils.NotificationBuilder;

/**
 * @author Juan Guerra on 10/04/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    public MessagingService() {
        super();
        Log.d(TAG, "MServ");
    }

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
            NotificationBuilder.createTransactionNotification(this, MainActivity.class, notification);
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
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


}