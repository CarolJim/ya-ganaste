package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.Builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD_CUENTA_YA;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PRIVACIDAD_LINEA_CREDITO;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_TERMINOS;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisoPrivacidadFragment extends GenericFragment implements OptionMenuItem.OnMenuItemClickListener{

    //private int Idestatus;
    @BindView(R.id.content_linearlayout)
    LinearLayout mLinearLayout;

    View rootview;

    public AvisoPrivacidadFragment() {
    }
    public static AvisoPrivacidadFragment newInstance(){
        return new AvisoPrivacidadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Idestatus= SingletonUser.getInstance().getDataUser().getIdEstatus();
        rootview = inflater.inflate(R.layout.fragment_aviso_privacidad, container, false);
        initViews();
        return rootview;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        //listView.setAdapter(ContainerBuilder.LEGALES(getContext(),this));
        ArrayList<OptionMenuItem.ViewHolderMenuSegurity> list = ContainerBuilder.LEGALES(getContext(),mLinearLayout,this);
    }

    @Override
    public void OnMenuItem(OptionMenuItem optionMenuItem) {
        switch (optionMenuItem.getIdItem()){
            case 1:
                onEventListener.onEvent(PREFER_USER_PRIVACIDAD_CUENTA_YA, 1);
                break;
        }
    }
}
