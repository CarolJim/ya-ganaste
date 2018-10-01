package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GanarFragment extends Fragment {



    public GanarFragment() {
        // Required empty public constructor
    }

    public  static GanarFragment newInstance(){
        return  new GanarFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ganar, container, false);
    }

}
