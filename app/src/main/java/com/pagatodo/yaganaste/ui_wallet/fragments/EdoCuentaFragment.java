package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.EVENT_GO_VISUALIZER_EDO_CUENTA_ERROR;
import static com.pagatodo.yaganaste.utils.ForcedUpdateChecker.SHOW_LOGS;
import static com.pagatodo.yaganaste.utils.ForcedUpdateChecker.URL_ACCOUNTS_STATEMENTS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

public class EdoCuentaFragment extends GenericFragment {

    private static final String YEAR_DOWNLOAD = "YEAR_DOWNLOAD", MONTH_DOWNLOAD = "MONTH_DOWNLOAD",
            OTP_DOWNLOAD = "OTP_DOWNLOAD";

    View rootView;
    @BindView(R.id.wv_edo_cuenta)
    WebView webView;

    private int month, year;
    private String otp, idCuenta;
    private boolean statementsExists = false;

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
        onEventListener.onEvent(EVENT_SHOW_LOADER, "Buscando estado de cuenta");
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        Map<String, String> headers = new HashMap<>();
        headers.put("authToken", otp);
        webView.getSettings().setJavaScriptEnabled(true);
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        String checkUrl = config.getString(URL_ACCOUNTS_STATEMENTS) + idCuenta + "/" + year + "/" + month;
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false))
            Log.e("YG", "Url Estado Cuenta: " + checkUrl);
        webView.loadUrl(checkUrl, headers);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!statementsExists) {
                    statementsExists = true;
                    try {
                        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
                        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                try {
                    statementsExists = true;
                    onEventListener.onEvent(EVENT_HIDE_LOADER, null);
                    onEventListener.onEvent(EVENT_GO_VISUALIZER_EDO_CUENTA_ERROR, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                try {
                    statementsExists = true;
                    onEventListener.onEvent(EVENT_HIDE_LOADER, null);
                    onEventListener.onEvent(EVENT_GO_VISUALIZER_EDO_CUENTA_ERROR, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
