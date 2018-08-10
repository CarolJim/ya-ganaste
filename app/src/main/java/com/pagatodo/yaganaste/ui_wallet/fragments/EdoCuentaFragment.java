package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.ForcedUpdateChecker.URL_ACCOUNTS_STATEMENTS;

public class EdoCuentaFragment extends GenericFragment {

    private static final String YEAR_DOWNLOAD = "YEAR_DOWNLOAD", MONTH_DOWNLOAD = "MONTH_DOWNLOAD",
            OTP_DOWNLOAD = "OTP_DOWNLOAD";

    View rootView;
    @BindView(R.id.wv_edo_cuenta)
    WebView webView;

    private int month, year;
    private String otp, idCuenta;

    public static EdoCuentaFragment newInstance(int year, int month, String otp) {
        EdoCuentaFragment fragment = new EdoCuentaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(YEAR_DOWNLOAD, year);
        bundle.putInt(MONTH_DOWNLOAD, month);
        bundle.putString(OTP_DOWNLOAD, otp);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            month = getArguments().getInt(MONTH_DOWNLOAD);
            year = getArguments().getInt(YEAR_DOWNLOAD);
            otp = getArguments().getString(OTP_DOWNLOAD);
        }
        String cuenta[] = SingletonUser.getInstance().getDataUser().getEmisor().getCuentas().get(0).getCuenta().split(" ");
        idCuenta = cuenta[0] + cuenta[1] + cuenta[2];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_edo_cuenta_webview, container, false);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        onEventListener.onEvent(EVENT_SHOW_LOADER, "Descargando estado de cuenta");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        Map<String, String> headers = new HashMap<>();
        headers.put("authToken", otp);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        webView.loadUrl(config.getString(URL_ACCOUNTS_STATEMENTS) + idCuenta + "/" + year + "/" + month, headers);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                onEventListener.onEvent(EVENT_HIDE_LOADER, null);
            }
        });
    }
}
