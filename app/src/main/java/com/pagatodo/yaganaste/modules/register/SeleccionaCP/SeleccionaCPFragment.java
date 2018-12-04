package com.pagatodo.yaganaste.modules.register.SeleccionaCP;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

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

        btnNextDataBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityf.showFragmentDatosNegocio();
            }
        });

    }
}
