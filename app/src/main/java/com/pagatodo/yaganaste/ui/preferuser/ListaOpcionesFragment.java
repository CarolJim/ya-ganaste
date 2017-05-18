package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CLOSE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_LEGALES;
import static com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment.KEY_PAGE_RESULT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaOpcionesFragment extends GenericFragment implements View.OnClickListener {

    public static String IS_ES_AGENTE = "IS_ES_AGENTE";
    private boolean isEsAgente;

    @BindView(R.id.fragment_list_opciones_name)
    TextView tv_name;
    @BindView(R.id.fragment_list_opciones_email)
    TextView tv_email;
    @BindView(R.id.fragment_lista_opciones_user)
    LinearLayout ll_usuario;
    @BindView(R.id.fragment_lista_opciones_account)
    LinearLayout ll_cuenta;
    @BindView(R.id.fragment_lista_opciones_card)
    LinearLayout ll_card;
    @BindView(R.id.fragment_lista_opciones_help)
    LinearLayout ll_help;
    @BindView(R.id.fragment_lista_opciones_legal)
    LinearLayout ll_legal;
    @BindView(R.id.fragment_lista_opciones_close)
    LinearLayout ll_close;

    View rootview;

    public ListaOpcionesFragment() {
        // Required empty public constructor
    }

    public static ListaOpcionesFragment newInstance(boolean isEsAgente) {

        ListaOpcionesFragment fragmentRegister = new ListaOpcionesFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_ES_AGENTE, isEsAgente);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isEsAgente = getArguments().getBoolean(IS_ES_AGENTE);

        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_lista_opciones, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        ll_usuario.setOnClickListener(this);

        if (isEsAgente) {
            ll_cuenta.setVisibility(View.VISIBLE);
            View view_cuenta = rootview.findViewById(R.id.content_prefer_view_acount);
            view_cuenta.setVisibility(View.VISIBLE);

            ll_cuenta.setOnClickListener(this);
        }

        ll_card.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_legal.setOnClickListener(this);
        ll_close.setOnClickListener(this);

        // Hacemos SET de la infromacion del user
        tv_name.setText("Mi nombre");
        tv_email.setText("micorreo@gmail.com");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_lista_opciones_user:
                Toast.makeText(getContext(), "Click User", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_account:
                Toast.makeText(getContext(), "Click Cuenta", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_card:
                Toast.makeText(getContext(), "Click Card", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_help:
                Toast.makeText(getContext(), "Click Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_lista_opciones_legal:
                //Toast.makeText(getContext(), "Click Legales", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_LEGALES, 1);
                break;
            case R.id.fragment_lista_opciones_close:
                // Toast.makeText(getContext(), "Click Close Session", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_CLOSE, 1);
                break;
        }
    }
}
