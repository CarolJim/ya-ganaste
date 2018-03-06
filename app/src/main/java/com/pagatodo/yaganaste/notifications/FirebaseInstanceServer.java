package com.pagatodo.yaganaste.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.pagatodo.yaganaste.App;


/**
 * @author Juan Guerra on 10/04/2017.
 */

public class FirebaseInstanceServer extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseInstanceServer";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // Implement this method to send token to your app server.
        //App.getInstance().getPrefs().saveData(FIREBASE_KEY, token);
    }
}