package com.pagatodo.yaganaste.ui.account.register;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.interfaces.IProgressView;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_PRIVACIDAD;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_PRIVACIDAD_STARBUKS;
import static com.pagatodo.yaganaste.utils.Recursos.URL_LEGALES_TERMINOS_STARBUCKS;

/**
 * Created by flima on 24/04/2017.
 */

public class LegalsDialog extends DialogFragment implements IProgressView, View.OnClickListener {

    public static String TAG = "LegalsDialog";
    public static String TYPE_LEGALS = "TYPE_LEGALS";
    @BindView(R.id.webViewLegalsContent)
    WebView webViewLegalsContent;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    @BindView(R.id.txt_content_legal)
    StyleTextView txtContent;
    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;
    private View rootview;
    private Legales typeLegal;

    public static LegalsDialog newInstance(Legales typeLegal) {
        LegalsDialog legalsDialog = new LegalsDialog();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_LEGALS, typeLegal);
        legalsDialog.setArguments(args);
        return legalsDialog;
    }

    @Override
    public void errorSessionExpired(DataSourceResult response) {

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_legals, container, false);
        initViews();
        return rootview;
    }

    public void initViews() {
        ButterKnife.bind(this, rootview);
        showLoader(getString(R.string.cargando));
        WebSettings settings = webViewLegalsContent.getSettings();
        settings.setJavaScriptEnabled(true);
        if (typeLegal != Legales.TERMINOS && typeLegal != Legales.PRIVACIDAD) {
            webViewLegalsContent.setVisibility(VISIBLE);
            webViewLegalsContent.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            webViewLegalsContent.loadUrl(getUrlLegals());
            webViewLegalsContent.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    hideLoader();
                }
            });
        } else {
            txtContent.setVisibility(VISIBLE);
            txtContent.setMovementMethod(new ScrollingMovementMethod());
            getFirebaseLegals();
        }
        btnBack.setOnClickListener(this);
    }

    private String getUrlLegals() {
        switch (typeLegal) {
            /*case TERMINOS:
                return URL_LEGALES_TERMINOS;*/
            case TERMINOSSRABUCKS:
                return URL_LEGALES_TERMINOS_STARBUCKS;
            case AVISOSTARBUCKS:
                return URL_LEGALES_PRIVACIDAD_STARBUKS;
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            this.dismiss();
        }
    }

    private void getFirebaseLegals() {
        DatabaseReference ref = App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/Url/Banking/YG_EMISOR").child(typeLegal==Legales.TERMINOS?"CTrmns":"CPrvd");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    txtContent.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public enum Legales implements Serializable {
        TERMINOS,
        PRIVACIDAD,
        TERMINOSSRABUCKS,
        AVISOSTARBUCKS
    }
}
