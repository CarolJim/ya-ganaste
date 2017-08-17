package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHelpAcercaApp extends GenericFragment implements View.OnClickListener{

    @BindView(R.id.fragment_lista_opciones_version)
    StyleTextView tv_version_code;
    View rootview;
    public static MyHelpAcercaApp newInstance() {
        MyHelpAcercaApp fragmentacerca = new MyHelpAcercaApp();
        return fragmentacerca;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_help_acerca_app, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        tv_version_code.setText(App.getContext().getResources().getString(R.string.yaganaste_version)
                .concat(String.valueOf(BuildConfig.VERSION_NAME)));

    }
}