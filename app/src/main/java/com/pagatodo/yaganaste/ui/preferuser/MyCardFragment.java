package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.MaterialLinearLayout;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.CardEmisorSelected;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_CHANGE_NIP;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCardFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.fragment_my_card_change_nip)
    StyleTextView changeNip;

    private CardEmisorSelected cardEmisorSelected;
    private MaterialLinearLayout llMaterialEmisorContainer;
    View rootview;

    public MyCardFragment() {
        // Required empty public constructor
    }

    public static MyCardFragment newInstance() {
        MyCardFragment fragment = new MyCardFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_card, container, false);
        initViews();

        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);

        cardEmisorSelected = new CardEmisorSelected(getContext());
        llMaterialEmisorContainer = (MaterialLinearLayout) rootview.findViewById(R.id.ll_material_emisor_container);

        changeNip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_my_card_change_nip:
                onEventListener.onEvent(PREFER_USER_CHANGE_NIP, 1);
                break;
          /*  case R.id.fragment_lista_opciones_account:
                //Toast.makeText(getContext(), "Click Cuenta", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_ACCOUNT, 1);
                break;
            case R.id.fragment_lista_opciones_card:
                //Toast.makeText(getContext(), "Click Card", Toast.LENGTH_SHORT).show();
                onEventListener.onEvent(PREFER_USER_MY_CARD, 1);
                break;*/
        }
    }
}
