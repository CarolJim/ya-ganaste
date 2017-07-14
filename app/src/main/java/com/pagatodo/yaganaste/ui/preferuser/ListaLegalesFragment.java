package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaLegalesFragment extends GenericFragment implements View.OnClickListener {


    @BindView(R.id.fragment_lista_legales_aviso)
    LinearLayout ll_aviso;
    @BindView(R.id.fragment_lista_legales_terminos)
    LinearLayout ll_terminos;

    View rootview;

    public ListaLegalesFragment() {
        // Required empty public constructor
    }


    public static ListaLegalesFragment newInstance() {

        ListaLegalesFragment fragmentListaLegales = new ListaLegalesFragment();
        return fragmentListaLegales;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_lista_legales, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        ll_aviso.setOnClickListener(this);
        ll_terminos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_lista_legales_aviso:
                //   Toast.makeText(getContext(), "Click User", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD, 1);
                break;
            case R.id.fragment_lista_legales_terminos:
                //  Toast.makeText(getContext(), "Click Cuenta", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_TERMINOS, 1);
                break;
        }
    }


}
