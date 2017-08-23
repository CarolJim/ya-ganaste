package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum;
import com.pagatodo.yaganaste.ui._controllers.LandingActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum.INICIO_SESION_ADQUIRENTE;
import static com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum.INICIO_SESION_EMISOR;
import static com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum.PANTALLA_COBROS;
import static com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum.PANTALLA_PRINCIPAL_EMISOR;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTutorialFragment extends GenericFragment implements View.OnClickListener {
    View rootview;
    @BindView(R.id.fragment_ayuda_tutoriales_inicio_sesion)
    LinearLayout layoutInicioSesion;

    @BindView(R.id.fragment_ayuda_tutoriales_tu_pantalla_principal)
    LinearLayout layoutPantallaPrincipal;

    @BindView(R.id.fragment_ayuda_tutoriales_tu_pantalla_Cobros)
    LinearLayout layoutPantallaCobros;

    @BindView(R.id.fragment_ayuda_tutoriales_tu_linea_credito)
    LinearLayout layoutLineaCredito;

    @BindView(R.id.fragment_ayuda_tutoriales_bloquear_tarjeta)
    LinearLayout layoutBloquearTarjeta;

    private boolean isAdquirente = true;


    public static MyTutorialFragment newInstance() {
        MyTutorialFragment fragmentListaLegales = new MyTutorialFragment();
        Bundle args = new Bundle();
        fragmentListaLegales.setArguments(args);
        return fragmentListaLegales;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_tutorial, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        layoutBloquearTarjeta.setOnClickListener(this);
        layoutInicioSesion.setOnClickListener(this);
        layoutLineaCredito.setOnClickListener(this);
        layoutPantallaCobros.setOnClickListener(this);
        layoutPantallaPrincipal.setOnClickListener(this);

        int idStatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
        if (idStatus < 12) {
            layoutPantallaCobros.setVisibility(View.GONE);
            layoutLineaCredito.setVisibility(View.GONE);
            isAdquirente = false;
        } else if (idStatus >= 12 && idStatus < 16) {
            layoutLineaCredito.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        //makeText(getContext(), "OnClick", LENGTH_SHORT).show();
        LandingActivitiesEnum activitiesEnum;
        switch (view.getId()) {
            case R.id.fragment_ayuda_tutoriales_inicio_sesion:
                activitiesEnum = isAdquirente ? INICIO_SESION_ADQUIRENTE : INICIO_SESION_EMISOR;
                break;
            case R.id.fragment_ayuda_tutoriales_tu_pantalla_principal:
                activitiesEnum = PANTALLA_PRINCIPAL_EMISOR;
                break;
            case R.id.fragment_ayuda_tutoriales_tu_pantalla_Cobros:
                activitiesEnum = PANTALLA_COBROS;
                break;
            case R.id.fragment_ayuda_tutoriales_tu_linea_credito:
                activitiesEnum = PANTALLA_PRINCIPAL_EMISOR;
                break;
            case R.id.fragment_ayuda_tutoriales_bloquear_tarjeta:
                activitiesEnum = PANTALLA_PRINCIPAL_EMISOR;
                break;
            default:
                activitiesEnum = PANTALLA_PRINCIPAL_EMISOR;
                break;
        }
        startActivity(LandingActivity.createIntent(getActivity(), activitiesEnum));
    }

}
