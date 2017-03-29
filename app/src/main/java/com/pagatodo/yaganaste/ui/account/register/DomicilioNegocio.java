package com.pagatodo.yaganaste.ui.account.register;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class DomicilioNegocio extends GenericFragment implements View.OnClickListener{

    private View rootview;

    public DomicilioNegocio() {
    }

    public static DomicilioNegocio newInstance() {
        DomicilioNegocio fragmentRegister = new DomicilioNegocio();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_direccion_negocio, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            default:
                break;
        }
    }

}

