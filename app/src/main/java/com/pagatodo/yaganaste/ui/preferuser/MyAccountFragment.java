package com.pagatodo.yaganaste.ui.preferuser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CUENTA_REEMBOLSO;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends GenericFragment implements View.OnClickListener{
    private int Idestatus;
    @BindView(R.id.fragment_my_account_reembolso)
    LinearLayout txtreebolso;
    @BindView(R.id.lineareebolso)
    View lreembolso;

    @BindView(R.id.fragment_my_account_linea_credito)
    LinearLayout solicita_credito;
    @BindView(R.id.llcredito)
    View llcredito;

    View rootview;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        MyAccountFragment fragment = new MyAccountFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Idestatus= SingletonUser.getInstance().getDataUser().getIdEstatus();
        rootview = inflater.inflate(R.layout.fragment_my_account, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        txtreebolso.setOnClickListener(this);
        solicita_credito.setOnClickListener(this);

        if (Idestatus== IdEstatus.I16.getId()){
            txtreebolso.setVisibility(View.VISIBLE);
            lreembolso.setVisibility(View.VISIBLE);
            llcredito.setVisibility(View.GONE);
            solicita_credito.setVisibility(View.GONE);
        }
        if (Idestatus== IdEstatus.I5.getId()|| Idestatus== IdEstatus.I6.getId()|| Idestatus== IdEstatus.I7.getId()|| Idestatus== IdEstatus.I8.getId()|| Idestatus== IdEstatus.I9.getId() ||Idestatus== IdEstatus.I10.getId() ||Idestatus== IdEstatus.I11.getId()||Idestatus== IdEstatus.ADQUIRENTE.getId()|| Idestatus== IdEstatus.I13.getId()||Idestatus== IdEstatus.I14.getId() ){
            txtreebolso.setVisibility(View.GONE);
            lreembolso.setVisibility(View.GONE);
            llcredito.setVisibility(View.VISIBLE);
            solicita_credito.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_my_account_reembolso:
                onEventListener.onEvent(PREFER_USER_CUENTA_REEMBOLSO, 1);
                break;

            case R.id.fragment_my_account_linea_credito:
                Intent intent = new Intent(getActivity(), RegistryCupoActivity.class);
                startActivity(intent);
                break;
        }
    }
}