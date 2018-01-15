package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificacionesPrefFragment extends SupportFragment implements MenuAdapter.OnItemClickListener{

    @BindView(R.id.security_item)
    ListView listView;
    @BindView(R.id.title_menu)
    StyleTextView titleMenu;

    protected OnEventListener onEventListener;

    public static NotificacionesPrefFragment newInstance() {
        return new NotificacionesPrefFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_security, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        titleMenu.setText(getContext().getResources().getString(R.string.ajustes_notificar_option));
        listView.setAdapter(new MenuAdapter(getContext(),new OptionMenuItem(getContext()).SETTINGS_NOTIFICACIONES(),this));
    }

    @Override
    public void onItemClick(OptionMenuItem optionMenuItem) {

    }
}