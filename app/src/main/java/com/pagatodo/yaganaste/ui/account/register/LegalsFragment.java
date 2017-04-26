package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_PRIVACIDAD;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_TERMINOS;


/**
 * A simple {@link Fragment} subclass.
 */
public class LegalsFragment extends GenericFragment implements IProgressView {

    private String TAG= "LegalsFragment";
    public static String TYPE_LEGALS= "TYPE_LEGALS";

    private View rootview;
    @BindView(R.id.webViewLegalsContent)
    WebView webViewLegales;

    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;

    private Legales typeLegal;


    public enum Legales implements Serializable {
        TERMINOS,
        PRIVACIDAD
    }

    private WebView webViewLegalsContent;


    public LegalsFragment() {
        // Required empty public constructor
    }

    public static LegalsFragment newInstance(Legales typeLegal) {
        LegalsFragment fragmentRegister = new LegalsFragment();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_LEGALS,typeLegal);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeLegal =(Legales) getArguments().getSerializable(TYPE_LEGALS);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_legals, container, false);
        initViews();
        init();
        return rootview;
    }

    private void init(){
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        WebSettings settings = webViewLegalsContent.getSettings();
        settings.setJavaScriptEnabled(true);
        webViewLegalsContent.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webViewLegalsContent.loadUrl(getUrlLegals());
        webViewLegalsContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoader();
            }
        });
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

    private String getUrlLegals(){

        switch (typeLegal){

            case TERMINOS:

                return URL_LEGALES_TERMINOS;

            case PRIVACIDAD:

                return URL_LEGALES_PRIVACIDAD;

            default:

                return "";

        }

    }
}