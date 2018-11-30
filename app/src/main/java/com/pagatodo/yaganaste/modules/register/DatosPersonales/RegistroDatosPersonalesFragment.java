package com.pagatodo.yaganaste.modules.register.DatosPersonales;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroDatosPersonalesFragment extends GenericFragment {

    private static RegActivity activityf;


    public static RegistroDatosPersonalesFragment newInstance(){
        return new RegistroDatosPersonalesFragment();
    }

    public static RegistroDatosPersonalesFragment newInstance(RegActivity activity){
        activityf = activity;
        return  new RegistroDatosPersonalesFragment();
    }
    public RegistroDatosPersonalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_datos_personales, container, false);
    }

    @Override
    public void initViews() {

    }
}
