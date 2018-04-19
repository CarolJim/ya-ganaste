package com.pagatodo.yaganaste.ui.preferuser;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS;


public class ListaAyudaLegalesFragment extends GenericFragment implements OnClickItemHolderListener {

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;

    @BindView(R.id.txtVersionApp)
    StyleTextView version;

    View rootview;

    public ListaAyudaLegalesFragment() {

    }


    public static ListaAyudaLegalesFragment newInstance() {

        ListaAyudaLegalesFragment fragmentListaLegales = new ListaAyudaLegalesFragment();
        return fragmentListaLegales;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_lista_ayuda_legales, container, false);
        initViews();

        return rootview;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        //listView.setAdapter(ContainerBuilder.ACERCA_DE(getContext(),this));
        ContainerBuilder.ACERCA_DE(getContext(),mLinearLayout,this);
        version.setText("Ya Ganaste Versión: " + BuildConfig.VERSION_NAME);
    }

    @Override
    public void onClick(Object optionMenuItem) {
        OptionMenuItem item = (OptionMenuItem) optionMenuItem;
        switch (item.getIdItem()){
            case 1:
                onEventListener.onEvent(PREFER_USER_TERMINOS, 1);
                break;
            case 2:
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD, 1);
                break;
        }
    }
}
