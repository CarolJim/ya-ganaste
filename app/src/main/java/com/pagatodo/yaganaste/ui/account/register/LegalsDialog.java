package com.pagatodo.yaganaste.ui.account.register;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_PRIVACIDAD;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_TERMINOS;

/**
 * Created by flima on 24/04/2017.
 */

public class LegalsDialog extends DialogFragment implements IProgressView {

    public static String TAG = "LegalsDialog";
    public static String TYPE_LEGALS = "TYPE_LEGALS";
    private View rootview;
    @BindView(R.id.webViewLegalsContent)
    WebView webViewLegalsContent;

    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    private Legales typeLegal;

    @Override
    public void errorSessionExpired(DataSourceResult response) {

    }

    public enum Legales implements Serializable {
        TERMINOS,
        PRIVACIDAD
    }

    public static LegalsDialog newInstance(Legales typeLegal) {
        LegalsDialog legalsDialog = new LegalsDialog();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_LEGALS, typeLegal);
        legalsDialog.setArguments(args);
        return legalsDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_legals, container, false);
        initViews();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeLegal = (Legales) getArguments().getSerializable(TYPE_LEGALS);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void initViews() {
        ButterKnife.bind(this, rootview);
        showLoader(getString(R.string.cargando));
        WebSettings settings = webViewLegalsContent.getSettings();
        settings.setJavaScriptEnabled(true);
        webViewLegalsContent.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webViewLegalsContent.loadUrl(getUrlLegals());
        webViewLegalsContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoader();
            }
        });
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        return dialog;
    }

    private String getUrlLegals() {

        switch (typeLegal) {

            case TERMINOS:

                return URL_LEGALES_TERMINOS;

            case PRIVACIDAD:

                return URL_LEGALES_PRIVACIDAD;

            default:

                return "";

        }

    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(GONE);
    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
