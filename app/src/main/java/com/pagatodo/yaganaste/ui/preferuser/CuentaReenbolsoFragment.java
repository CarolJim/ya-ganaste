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
public class CuentaReenbolsoFragment  extends GenericFragment implements View.OnClickListener  {


    public static CuentaReenbolsoFragment newInstance(){

        CuentaReenbolsoFragment  cuentaReenbolsoFragment= new CuentaReenbolsoFragment();
        return cuentaReenbolsoFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuenta_reenbolso, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initViews() {

    }
}
