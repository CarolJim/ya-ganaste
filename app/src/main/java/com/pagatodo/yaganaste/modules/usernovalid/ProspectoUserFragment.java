package com.pagatodo.yaganaste.modules.usernovalid;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProspectoUserFragment extends GenericFragment {


    public ProspectoUserFragment() {
        // Required empty public constructor
    }

    public static ProspectoUserFragment newInstance() {
        Bundle args = new Bundle();
        ProspectoUserFragment fragment = new ProspectoUserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prospecto_user, container, false);
    }

    @Override
    public void initViews() {



    }
}
