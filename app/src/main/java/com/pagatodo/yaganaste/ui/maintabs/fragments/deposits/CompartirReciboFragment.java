package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

public class CompartirReciboFragment extends Fragment {

    public CompartirReciboFragment() {
        // Required empty public constructor
    }

    public static CompartirReciboFragment newInstance() {
        CompartirReciboFragment fragment = new CompartirReciboFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compartir_recibo, container, false);
    }

}
