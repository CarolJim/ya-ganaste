package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS_CUENTA_YA;

/**
 * A simple {@link Fragment} subclass.
 */
public class TerminosyCondicionesFragment extends GenericFragment implements OnClickItemHolderListener {

    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;

    View rootview;


    public TerminosyCondicionesFragment() {
    }

    public static TerminosyCondicionesFragment newInstance() {
        return  new TerminosyCondicionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
        rootview = inflater.inflate(R.layout.fragment_terminosy_condiciones, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        //listView.setAdapter(ContainerBuilder.LEGALES(getContext(),this));
        ContainerBuilder.LEGALES(getContext(),mLinearLayout,this);
    }

    @Override
    public void onClick(Object optionMenuItem) {
        OptionMenuItem item = (OptionMenuItem) optionMenuItem;
        switch (item.getIdItem()){
            case 1:
                onEventListener.onEvent(PREFER_USER_TERMINOS_CUENTA_YA, 1);
                break;
        }
    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onEventListener.onEvent(PREFER_USER_TERMINOS_BACK, 1);
                break;

            case R.id.fragment_terminos_lcredito_linearlayout:
                onEventListener.onEvent(PREFER_USER_TERMINOS_LINEA_CREDITO, 1);
                break;

        }
    }
*/



}
