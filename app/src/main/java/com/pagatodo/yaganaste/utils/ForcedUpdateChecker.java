package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;

import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOYALTY;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADTVO;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_TRANS;

public class ForcedUpdateChecker {

    private static final String TAG = ForcedUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "forced_update_required";
    public static final String KEY_CURRENT_VERSION = "forced_update_current_version";
    public static final String KEY_UPDATE_URL = "forced_update_store_url";
    public static final String SHOW_LOYALTY_CARDS = "show_loyalty_cards";
    public static final String SHOW_LOGS = "show_logs";
    public static final String CONNECTION_TIMEOUT = "connection_timeout";
    public static final String URL_YG_TRANS = "url_yg_trans";
    public static final String URL_YG_ADMIN = "url_yg_admin";
    public static final String URL_YG_ADQ = "url_yg_adq";
    public static final String URL_STARBUCKS = "url_starbucks";
    public static final String PIN_YG_TRANS = "pin_yg_trans";
    public static final String PIN_YG_ADMIN = "pin_yg_admin";
    public static final String PIN_YG_ADQ = "pin_yg_adq";
    public static final String PIN_STARBUCKS = "pin_starbucks";
    public static final String SIZE_APP = "size_app";
    public static final String TRACE_SUCCESS_WS = "trace_success_ws";
    public static final String URL_ACCOUNTS_STATEMENTS = "url_accounts_statements";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl);
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
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        boolean showLoyalty = remoteConfig.getBoolean(SHOW_LOYALTY_CARDS);
        boolean showLogs = remoteConfig.getBoolean(SHOW_LOGS);
        boolean traceSuccessWs = remoteConfig.getBoolean(TRACE_SUCCESS_WS);
        int connectionTimeout = Integer.valueOf(remoteConfig.getString(CONNECTION_TIMEOUT));
        App.getInstance().getPrefs().saveDataBool(SHOW_LOYALTY, showLoyalty);
        App.getInstance().getPrefs().saveDataBool(SHOW_LOGS_PROD, !BuildConfig.DEBUG ? showLogs : true);
        App.getInstance().getPrefs().saveDataBool(TRACE_SUCCESS_WS, traceSuccessWs);
        App.getInstance().getPrefs().saveDataInt(CONNECTION_TIMEOUT, connectionTimeout);
        App.getInstance().getPrefs().saveDataBool(SHOW_LOGS_PROD, !BuildConfig.DEBUG ? showLogs : true);
        App.getInstance().getPrefs().saveDataInt(CONNECTION_TIMEOUT, connectionTimeout);
        if (!showLoyalty) {
            App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, false);
        }
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
            String appVersion = getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);

            if (!TextUtils.equals(currentVersion, appVersion) && onUpdateNeededListener != null) {
                onUpdateNeededListener.onUpdateNeeded(updateUrl);
            }
        }
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
