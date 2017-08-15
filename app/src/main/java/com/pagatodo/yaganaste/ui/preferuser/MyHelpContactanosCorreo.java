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
public class MyHelpContactanosCorreo extends GenericFragment implements View.OnClickListener {


    public MyHelpContactanosCorreo() {
        // Required empty public constructor
    }
    public static MyHelpContactanosCorreo newInstance() {

        MyHelpContactanosCorreo fragmentcontactanoscorreo = new MyHelpContactanosCorreo();
        return fragmentcontactanoscorreo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_help_contactanos_correo, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initViews() {

    }
}
