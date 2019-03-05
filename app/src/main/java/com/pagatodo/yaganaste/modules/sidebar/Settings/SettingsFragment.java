package com.pagatodo.yaganaste.modules.sidebar.Settings;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity.SecuritySettignsFragment;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.preferuser.DesasociarPhoneFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;

import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_REEMBOLSO;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends SupportFragment implements View.OnClickListener {

    private PreferUserActivity activity;
    private SettingsRouter router;
    private LinearLayout goSettings_security, goConfig_card_reader, goUnlink_phone;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (PreferUserActivity) context;
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

        Agentes agentes = SingletonUser.getInstance().getDataUser().getAdquirente().getAgentes().get(0);

        if (agentes != null) {
            if (SingletonUser.getInstance().getDataUser().getUsuario().getIdEstatusEmisor()
                    == IdEstatus.ADQUIRENTE.getId()){
                    //!App.getInstance().getPrefs().loadDataBoolean(FIST_ADQ_REEMBOLSO, false)) {
                goConfig_card_reader.setVisibility(View.GONE);
            } else {
                goConfig_card_reader.setVisibility(View.VISIBLE);
            }
        } else {
            goConfig_card_reader.setVisibility(View.GONE);
        }

        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        goSettings_security.setOnClickListener(this::onClick);
        goConfig_card_reader.setOnClickListener(this::onClick);
        goUnlink_phone.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_security:
                //router.showSettingsOfSecurity(Direction.FORDWARD);
                activity.loadFragment(SecuritySettignsFragment.newInstance(), R.id.container, Direction.FORDWARD, false);
                break;
            case R.id.unlink_phone:
                //router.showSettingsOfSecurity(Direction.FORDWARD);
                activity.loadFragment(DesasociarPhoneFragment.newInstance(),R.id.container,Direction.FORDWARD,false);
                break;
            case R.id.config_card_reader:
                activity.loadFragment(MyDongleFragment.newInstance(App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE)),
                        R.id.container, Direction.FORDWARD, false);
                break;
        }
    }
}
