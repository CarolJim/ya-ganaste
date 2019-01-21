package com.pagatodo.yaganaste.modules.sidebar.Settings;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity.SecuritySettignsFragment;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.DesasociarPhoneFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends SupportFragment implements View.OnClickListener {

    private PreferUserActivity activity;
    private SettingsRouter router;
    private LinearLayout goSettings_security, goConfig_card_reader, goUnlink_phone, goCancel_account;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(){
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(PreferUserActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, rootView);
        goSettings_security = (LinearLayout) rootView.findViewById(R.id.settings_security);
        goConfig_card_reader = (LinearLayout) rootView.findViewById(R.id.config_card_reader);
        goUnlink_phone = (LinearLayout) rootView.findViewById(R.id.unlink_phone);
        goCancel_account = (LinearLayout) rootView.findViewById(R.id.cancel_account);


        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        goSettings_security.setOnClickListener(this::onClick);
        goConfig_card_reader.setOnClickListener(this::onClick);
        goUnlink_phone.setOnClickListener(this::onClick);
        goCancel_account.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_security:
                //router.showSettingsOfSecurity(Direction.FORDWARD);
                activity.loadFragment(SecuritySettignsFragment.newInstance(),R.id.container,false);
                break;
                case R.id.unlink_phone:
                //router.showSettingsOfSecurity(Direction.FORDWARD);
                activity.loadFragment(DesasociarPhoneFragment.newInstance(),R.id.container,false);
                break;
        }




    }
}
