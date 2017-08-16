package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_ABOUT;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_CONTACT;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_TUTORIALES;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHelpFragment extends GenericFragment implements View.OnClickListener  {
    @BindView(R.id.fragment_lista_ayuda_tutoriales)
    LinearLayout lllista_ayuda_tutoriales;

    @BindView(R.id.fragment_lista_ayuda_contactanos)
    LinearLayout lllista_ayuda_contactanos;

    @BindView(R.id.fragment_lista_ayuda_acerca_app)
    LinearLayout lllista_ayuda_acerca;

    View rootview;

    public MyHelpFragment() {
        // Required empty public constructor
    }


    public static MyHelpFragment newInstance() {

        MyHelpFragment fragmentHelp = new MyHelpFragment();
        return fragmentHelp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_help, container, false);

        initViews();

        return rootview;
    }

    @Override
    public void onClick(View c) {
        switch (c.getId()){

            case R.id.fragment_lista_ayuda_tutoriales:
                onEventListener.onEvent(PREFER_USER_HELP_TUTORIALES, 1);
                break;
            case R.id.fragment_lista_ayuda_contactanos:
                onEventListener.onEvent(PREFER_USER_HELP_CONTACT, 1);
                break;
            case R.id.fragment_lista_ayuda_acerca_app:
                onEventListener.onEvent(PREFER_USER_HELP_ABOUT, 1);
                break;
        }


    }
    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        lllista_ayuda_tutoriales.setOnClickListener(this);
        lllista_ayuda_contactanos.setOnClickListener(this);
        lllista_ayuda_acerca.setOnClickListener(this);
    }
}
