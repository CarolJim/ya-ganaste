package com.pagatodo.yaganaste.modules.register.CodigoVinculados;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodigosVinculadosFragment extends GenericFragment {

    public  static RegActivity activityF;
    View rootView;

    @BindView(R.id.cleanqralias)
    LinearLayout cleanqralias;

    @BindView(R.id.editqralias)
    EditText editqralias;

    public  static  CodigosVinculadosFragment newInstance(RegActivity activity){
        activityF=activity;
        return new CodigosVinculadosFragment();
    }

    public CodigosVinculadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_codigos_vinculados, container, false);
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);

        cleanqralias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editqralias.setText("");
            }
        });

    }
}
