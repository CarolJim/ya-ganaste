package com.pagatodo.yaganaste.ui.preferuser;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.components.LabelArrowSimple;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS;


public class ListaAyudaLegalesFragment extends GenericFragment implements View.OnClickListener{

    @BindView(R.id.legal)
    LabelArrowSimple legal;
    @BindView(R.id.notice_privacy)
    LabelArrowSimple noticePrivacy;
    @BindView(R.id.txtVersionApp)
    TextView version;

    private View rootview;

    public static ListaAyudaLegalesFragment newInstance() {
        return new ListaAyudaLegalesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lista_ayuda_legales, container, false);
        initViews();
        return rootview;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        version.setText("Ya Ganaste Versión: " + BuildConfig.VERSION_NAME);
        legal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.legal:
                onEventListener.onEvent(PREFER_USER_TERMINOS, 1);
                break;
            case R.id.notice_privacy:
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD, 1);
                break;
        }
    }
}
