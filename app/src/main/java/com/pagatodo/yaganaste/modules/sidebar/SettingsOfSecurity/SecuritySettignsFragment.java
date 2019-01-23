package com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.pagatodo.view_manager.components.LabelArrow;
import com.pagatodo.view_manager.components.LabelArrowCheckbox;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.emisor.ChangeNip.MyChangeNip;
import com.pagatodo.yaganaste.modules.sidebar.ChangePassword.ChangePasswordFragment;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.DesasociarPhoneFragment;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENSAJE;
import static com.pagatodo.yaganaste.ui_wallet.fragments.SecurityFragment.MENU;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

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
    private LabelArrowCheckbox checkBoxBiometrics, checkBalances;
    private CheckBox biometrics, balances;


    public SecuritySettignsFragment() {
        // Required empty public constructor
    }

    public static SecuritySettignsFragment newInstance() {
        SecuritySettignsFragment securitySettignsFragment = new SecuritySettignsFragment();
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
        checkBoxBiometrics = (LabelArrowCheckbox) rootView.findViewById(R.id.checkBoxBiometrics);
        checkBalances = (LabelArrowCheckbox) rootView.findViewById(R.id.checkBalances);
        biometrics = (CheckBox) rootView.findViewById(R.id.check);

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
        checkBoxBiometrics.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, true);
                }else {
                    App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, false);
                }
            }
        });
        checkBalances.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    App.getInstance().getPrefs().saveDataBool(SHOW_BALANCE, true);
                }else {
                    App.getInstance().getPrefs().saveDataBool(SHOW_BALANCE, false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goChangeNip:
                activity.loadFragment(ChangePasswordFragment.newInstance(), R.id.container, Direction.FORDWARD, false);
                break;

        }
    }
}
