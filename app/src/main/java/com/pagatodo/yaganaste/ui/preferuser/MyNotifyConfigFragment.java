package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyNotifyConfigFragment extends GenericFragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener{

    @BindView(R.id.tae_switch)
    SwitchCompat tae_switch;

    View rootview;

    public MyNotifyConfigFragment() {
        // Required empty public constructor
    }

    public static MyNotifyConfigFragment newInstance() {
        MyNotifyConfigFragment fragment = new MyNotifyConfigFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_config_notify, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);


        tae_switch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(getContext(), "Btn " + buttonView + " estado " + isChecked, Toast.LENGTH_SHORT).show();
    }
}