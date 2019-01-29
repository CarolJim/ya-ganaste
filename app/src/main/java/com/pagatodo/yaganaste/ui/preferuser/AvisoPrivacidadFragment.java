package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD_CUENTA_YA;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisoPrivacidadFragment extends GenericFragment implements OnClickItemHolderListener {



    private View rootview;

    public static AvisoPrivacidadFragment newInstance(){
        return new AvisoPrivacidadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_aviso_privacidad, container, false);
        initViews();
        return rootview;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        //listView.setAdapter(ContainerBuilder.LEGALES(getContext(),this));
        //ArrayList<OptionMenuItem.ViewHolderMenuSegurity> list = ContainerBuilder.LEGALES_PRIVACY(getContext(),mLinearLayout,this);
    }

    @Override
    public void onItemClick(Object optionMenuItem) {
        OptionMenuItem item = (OptionMenuItem) optionMenuItem;
        switch (item.getIdItem()){
            case 1:
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD_CUENTA_YA, 1);
                break;
        }
    }
}
