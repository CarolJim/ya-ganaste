package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.net.ApiTrans;

import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TIMEOUT;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOYALTY;

public class ForcedUpdateChecker {

    private static final String TAG = ForcedUpdateChecker.class.getSimpleName();

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public interface OnUpdateNeededListener {
        void onUpdateNeeded();
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public ForcedUpdateChecker(@NonNull Context context,
                               OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    public void check() {
        getUrls();
        getPins();
        // Validar visualización de Loyalty
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/ShowLoyalty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean shown = dataSnapshot.getValue(Boolean.class);
                    App.getInstance().getPrefs().saveDataBool(SHOW_LOYALTY, shown);
                    if (!shown)
                        App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, false);
                } else {
                    App.getInstance().getPrefs().saveDataBool(SHOW_LOYALTY, false);
                    App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Validar visualización de Logs
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/ShowLogs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean showLogs = dataSnapshot.getValue(Boolean.class);
                    App.getInstance().getPrefs().saveDataBool(SHOW_LOGS_PROD, !BuildConfig.DEBUG ? showLogs : true);
                } else {
                    App.getInstance().getPrefs().saveDataBool(SHOW_LOGS_PROD, true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Validar tiempo de conexión para peticiones a servicios
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/ConnectionTimeout").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int connectionTimeout = dataSnapshot.getValue(Integer.class);
                    App.getInstance().getPrefs().saveDataInt(CONNECTION_TIMEOUT, connectionTimeout);
                } else {
                    App.getInstance().getPrefs().saveDataInt(CONNECTION_TIMEOUT, 25000);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Validar version para actualización forzosa
        App.getDatabaseReference().child("Version/Forcing_ADT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String currentVersion = dataSnapshot.getValue(String.class);
                    String appVersion = getAppVersion(context);
                    if (!TextUtils.equals(currentVersion, appVersion) && onUpdateNeededListener != null) {
                        onUpdateNeededListener.onUpdateNeeded();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUrls(){
        // Obtener url para servidor Emisor Administrativo
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_EMISOR/BASE_URL_ADTVO").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiAdtvo.setUrlServerAdtvo(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtener url para servidor Emisor Transaccional
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_EMISOR/BASE_URL_TRANS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiTrans.setUrlServerTrans(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtener url para servidor Adquiriente
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_ADQ/BASE_URL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiAdq.setUrlServerAdq(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtener url para servidor Starbucks
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Loyalty/Starbucks/BASE_URL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiStarbucks.setUrlStarbucks(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPins(){
        // Obtener pin ssl para servidor Emisor Administrativo
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_EMISOR/BASE_PIN_SSL_ADTVO").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiAdtvo.setPinAdtvo(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtener pin ssl para servidor Emisor Transaccional
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_EMISOR/BASE_PIN_SSL_TRANS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiTrans.setPinTrans(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtener pin ssl para servidor Adquiriente
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_ADQ/BASE_PIN_SSL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiAdq.setPinAdq(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Obtener url para servidor Starbucks
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Loyalty/Starbucks/BASE_PIN_SSL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ApiStarbucks.setPinStarbucks(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getAppVersion(Context context) {
        String result = "";
        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForcedUpdateChecker build() {
            return new ForcedUpdateChecker(context, onUpdateNeededListener);
        }

        public ForcedUpdateChecker check() {
            ForcedUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();
            return forceUpdateChecker;
        }
    }
}
