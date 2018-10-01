package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromocionesDetalleFragment extends GenericFragment {


    public PromocionesDetalleFragment() {
        // Required empty public constructor
    }

    public  static  PromocionesDetalleFragment newInstance(){
        return  new PromocionesDetalleFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promociones_detalle, container, false);
    }

    @Override
    public void initViews() {

    }
}
