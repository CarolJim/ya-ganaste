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

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_ABOUT;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_CONTACT;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_TUTORIALES;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAyudaLegalesFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.fragment_lista_ayuda_tutoriales)
    LinearLayout lllista_ayuda_tutoriales;
    @BindView(R.id.fragment_lista_ayuda_contactanos)
    LinearLayout lllista_ayuda_contactanos;
    @BindView(R.id.fragment_lista_ayuda_acerca_app)
    LinearLayout lllista_ayuda_acerca;
    @BindView(R.id.fragment_lista_legales_aviso)
    LinearLayout ll_aviso;
    @BindView(R.id.fragment_lista_legales_terminos)
    LinearLayout ll_terminos;

    View rootview;

    public ListaAyudaLegalesFragment() {
        // Required empty public constructor
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

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        lllista_ayuda_tutoriales.setOnClickListener(this);
        lllista_ayuda_contactanos.setOnClickListener(this);
        lllista_ayuda_acerca.setOnClickListener(this);
        ll_aviso.setOnClickListener(this);
        ll_terminos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_lista_ayuda_tutoriales:
                onEventListener.onEvent(PREFER_USER_HELP_TUTORIALES, 1);
                break;
            case R.id.fragment_lista_ayuda_contactanos:
                onEventListener.onEvent(PREFER_USER_HELP_CONTACT, 1);
                break;
            case R.id.fragment_lista_ayuda_acerca_app:
                onEventListener.onEvent(PREFER_USER_HELP_ABOUT, 1);
                break;
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
