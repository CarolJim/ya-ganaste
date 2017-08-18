package com.pagatodo.yaganaste.ui.preferuser;


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
public class CuentaReembolsoFragment extends GenericFragment implements View.OnClickListener  {


    public static CuentaReembolsoFragment newInstance(){

        CuentaReembolsoFragment cuentaReenbolsoFragment= new CuentaReembolsoFragment();
        return cuentaReenbolsoFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuenta_reembolso, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initViews() {

    }
}
