package com.pagatodo.yaganaste.modules.register.SeleccionaCP;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.RegisterUser;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionaCPFragment extends GenericFragment {

public  static RegActivity activityf;

View rootView;

    @BindView(R.id.btnNextDataBusiness)
    StyleButton btnNextDataBusiness;
    @BindView(R.id.sub_titulo_datos_cp_usuario)
    StyleTextView sub_titulo_datos_cp_usuario;

String street,numExterior,interiorNumber,codigoPostal;


    public SeleccionaCPFragment() {
        // Required empty public constructor
    }

    public  static  SeleccionaCPFragment newInstance(RegActivity activity){
       activityf=activity;
        return new SeleccionaCPFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_selecciona_c, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {

        ButterKnife.bind(this,rootView);
        setData();

        btnNextDataBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityf.showFragmentDatosNegocio();
            }
        });

    }

    private void setData() {
        RegisterUserNew registerUser = RegisterUserNew.getInstance();
        street =registerUser.getCalle();
        numExterior= registerUser.getNumExterior();
        interiorNumber=registerUser.getNumInterior();
        codigoPostal =registerUser.getCodigoPostal();
        if (interiorNumber.isEmpty()) {
            sub_titulo_datos_cp_usuario.setText(street + " #" + numExterior + " C.P. "+codigoPostal);
        }else {
            sub_titulo_datos_cp_usuario.setText(street + " #" + numExterior + "\nInterior "+interiorNumber+ " C.P. "+codigoPostal);
        }

    }
}
