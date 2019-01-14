package com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.modules.emisor.ChangeNip.MyChangeNip;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENSAJE;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecuritySettignsFragment extends SupportFragment implements View.OnClickListener {
    private PreferUserActivity activity;
    private int TYPE_MENU;
    public String msj;
    private SidebarRouter router;
    protected OnEventListener onEventListener;
    private ImageView goChangeNip;

    public SecuritySettignsFragment() {
        // Required empty public constructor
    }

    public static SecuritySettignsFragment newInstance(int menu, String msj) {
        SecuritySettignsFragment securitySettignsFragment = new SecuritySettignsFragment();
        Bundle args = new Bundle();
        args.putInt(MENU, menu);
        args.putString(MENSAJE, msj);
        securitySettignsFragment.setArguments(args);
        return securitySettignsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PreferUserActivity) context;
        if (context instanceof OnEventListener) {
            this.onEventListener = (OnEventListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_security_settigns, container, false);
        ButterKnife.bind(this, rootView);
        goChangeNip = (ImageView) rootView.findViewById(R.id.goChangeNip);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        goChangeNip.setOnClickListener(this::onClick);
        if (getArguments() != null) {
            TYPE_MENU = getArguments().getInt(MENU);
            if (!getArguments().getString(MENSAJE).isEmpty()) {
                msj = getArguments().getString(MENSAJE);
                UI.showSuccessSnackBar(getActivity(), msj, Snackbar.LENGTH_SHORT);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goChangeNip:
                activity.loadFragment(MyChangeNip.newInstance(), R.id.container, false);
        }
    }
}
