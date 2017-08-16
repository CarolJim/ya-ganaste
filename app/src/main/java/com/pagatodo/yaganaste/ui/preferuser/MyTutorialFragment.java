package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTutorialFragment extends GenericFragment implements View.OnClickListener {
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_tutorial, container, false);

        return rootview;
    }

    public static MyTutorialFragment newInstance() {

        MyTutorialFragment fragmentListaLegales = new MyTutorialFragment();
        return fragmentListaLegales;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void initViews() {

    }
}
