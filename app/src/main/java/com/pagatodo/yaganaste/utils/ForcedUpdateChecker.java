package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pagatodo.yaganaste.App;

import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOYALTY;

public class ForcedUpdateChecker {

    private static final String TAG = ForcedUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "forced_update_required";
    public static final String KEY_CURRENT_VERSION = "forced_update_current_version";
    public static final String KEY_UPDATE_URL = "forced_update_store_url";
    public static final String SHOW_LOYALTY_CARDS = "show_loyalty_cards";

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
        App.getInstance().getPrefs().saveDataBool(SHOW_LOYALTY, showLoyalty);
        if (!showLoyalty) {
            App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, false);
        }
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
            String appVersion = getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);

            if (!TextUtils.equals(currentVersion, appVersion)
                    && onUpdateNeededListener != null) {
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
