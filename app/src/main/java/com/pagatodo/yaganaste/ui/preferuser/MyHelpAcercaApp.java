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
public class MyHelpAcercaApp extends GenericFragment implements View.OnClickListener{


    public static MyHelpAcercaApp newInstance() {
        MyHelpAcercaApp fragmentacerca = new MyHelpAcercaApp();
        return fragmentacerca;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_help_acerca_app, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initViews() {

    }
}
