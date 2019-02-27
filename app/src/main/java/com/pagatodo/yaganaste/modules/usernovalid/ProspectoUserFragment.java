package com.pagatodo.yaganaste.modules.usernovalid;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProspectoUserFragment extends GenericFragment {

    View rootView;

    @BindView(R.id.btn_close)
    StyleButton btn_close;

    @BindView(R.id.txt_subtitle_associate_phone)
    StyleTextView txt_subtitle_associate_phone;


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
        rootView = inflater.inflate(R.layout.fragment_prospecto_user, container, false);
        initViews();
        return  rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        txt_subtitle_associate_phone.setText("Estamos validando tu información,\npodría llevarnos hasta 1 día hábil \n " +
                " \n \n" +
                " Te pedimos estar al pendiente del\ncorreo electrónico que registraste \nya que probablemente\nnecesitemos contactarte");


    }
}
