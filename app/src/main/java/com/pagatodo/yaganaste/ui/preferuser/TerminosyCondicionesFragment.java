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
import com.pagatodo.yaganaste.interfaces.enums.States;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS_CUENTA_YA;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS_LINEA_CREDITO;

/**
 * A simple {@link Fragment} subclass.
 */
public class TerminosyCondicionesFragment extends GenericFragment implements View.OnClickListener {
    View rootview;
    int res;
    private int Idestatus;

    @BindView(R.id.fragment_terminos_cuenta_ya_ganaste_Terminos)
    LinearLayout ll_cuenta_ya_terminos;

    @BindView(R.id.fragment_terminos_linea_credito_terminos)
    LinearLayout ll_linea_terminos;

    @BindView(R.id.fragment_contrato_terminos)
    LinearLayout ll_contrato_terminos;


    @BindView(R.id.lineacontrato)
    View lcontrato;

    @BindView(R.id.lineancredito)
    View lcredito;

    @BindView(R.id.lineacuenta)
    View lcuenta;






    public TerminosyCondicionesFragment() {
        // Required empty public constructor
    }

    public  static TerminosyCondicionesFragment newInstance() {
        TerminosyCondicionesFragment  fragmentterminosycondiciones = new TerminosyCondicionesFragment();
        return fragmentterminosycondiciones;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Idestatus= SingletonUser.getInstance().getDataUser().getIdEstatus();
        res = Idestatus;
        rootview=inflater.inflate(R.layout.fragment_terminosy_condiciones, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.fragment_terminos_cuenta_ya_ganaste_Terminos:
                onEventListener.onEvent(PREFER_USER_TERMINOS_CUENTA_YA, 1);
                break;
            case R.id.fragment_terminos_linea_credito_terminos:
                onEventListener.onEvent(PREFER_USER_TERMINOS_LINEA_CREDITO, 1);
                break;
        }

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        ll_cuenta_ya_terminos.setOnClickListener(this);
        ll_linea_terminos.setOnClickListener(this);
        ll_contrato_terminos.setOnClickListener(this);
        if (Idestatus == IdEstatus.I5.getId()){
            ll_contrato_terminos.setVisibility(View.INVISIBLE);
            lcontrato.setVisibility(View.INVISIBLE);
            lcuenta.setVisibility(View.VISIBLE);
            ll_cuenta_ya_terminos.setVisibility(View.VISIBLE);

        }
        if (Idestatus == IdEstatus.I16.getId()){
            ll_contrato_terminos.setVisibility(View.VISIBLE);
            lcontrato.setVisibility(View.VISIBLE);
        }
        if (Idestatus == IdEstatus.I5.getId()){
            ll_linea_terminos.setVisibility(View.INVISIBLE);
            lcredito.setVisibility(View.INVISIBLE);
        }
        if (Idestatus == IdEstatus.I16.getId()){
            ll_linea_terminos.setVisibility(View.VISIBLE);
            lcredito.setVisibility(View.VISIBLE);
        }
    }
}
