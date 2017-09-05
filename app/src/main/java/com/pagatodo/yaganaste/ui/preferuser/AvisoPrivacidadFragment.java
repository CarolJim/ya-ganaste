package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD_CUENTA_YA;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD_LINEA_CREDITO;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisoPrivacidadFragment extends GenericFragment implements View.OnClickListener {


    private int Idestatus;

    @BindView(R.id.fragment_aviso_cuenta_ya_ganaste)
    LinearLayout ll_cuenta_ganaste;
    @BindView(R.id.fragment_aviso_linea_credito_linearlayout)
    LinearLayout ll_lcredito;

// fragment_aviso_linea_credito fragment_aviso_credit_view
    @BindView(R.id.fragment_aviso_linea_credito_view)
    View view_lcredito;



    View rootview;

    public AvisoPrivacidadFragment() {
        // Required empty public constructor
    }


    public static AvisoPrivacidadFragment newInstance(){

        AvisoPrivacidadFragment  avisoPrivacidadFragment = new AvisoPrivacidadFragment();
        return avisoPrivacidadFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Idestatus= SingletonUser.getInstance().getDataUser().getIdEstatus();
        rootview= inflater.inflate(R.layout.fragment_aviso_privacidad, container, false);
        initViews();
        return rootview;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.fragment_aviso_cuenta_ya_ganaste:
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD_CUENTA_YA, 1);
                break;
            case R.id.fragment_aviso_linea_credito_linearlayout:
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD_LINEA_CREDITO, 1);
                break;


        }

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        ll_cuenta_ganaste.setOnClickListener(this);
        ll_lcredito.setOnClickListener(this);

        if (Idestatus== IdEstatus.I17.getId()){
            ll_lcredito.setVisibility(View.VISIBLE);
            view_lcredito.setVisibility(View.VISIBLE);

        }

    }
}
