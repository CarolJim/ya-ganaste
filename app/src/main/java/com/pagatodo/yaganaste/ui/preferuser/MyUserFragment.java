package com.pagatodo.yaganaste.ui.preferuser;


import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.FINGERPRINT_SERVICE;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_EMAIL;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_MY_ACCOUNT_CONFIG_NOTIFY;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PASS;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Recursos.USE_FINGERPRINT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyUserFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.fragment_myuser_email)
    LinearLayout myEmail;
    @BindView(R.id.fragment_myuser_pass)
    LinearLayout myPass;
    @BindView(R.id.fragment_my_account_desvincula)
    LinearLayout txtDesvincula;
    @BindView(R.id.fragment_myuser_fingerprint)
    LinearLayout myFingerprint;
    @BindView(R.id.fingerprint_switch)
    SwitchCompat fingerprintSwitch;
    boolean useFingerprint = true;

    View rootview;


    public MyUserFragment() {
        // Required empty public constructor
    }

    public static MyUserFragment newInstance() {
        MyUserFragment fragment = new MyUserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_my_user, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        useFingerprint = App.getInstance().getPrefs().loadDataBoolean(USE_FINGERPRINT, true);
        myEmail.setOnClickListener(this);
        myPass.setOnClickListener(this);
        txtDesvincula.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);
            if (fingerprintManager.isHardwareDetected()) {
                myFingerprint.setVisibility(View.VISIBLE);
                fingerprintSwitch.setChecked(useFingerprint);
            }
        }
        fingerprintSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                App.getInstance().getPrefs().saveDataBool(USE_FINGERPRINT, isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_myuser_email:
                onEventListener.onEvent(PREFER_USER_EMAIL, null);
                break;
            case R.id.fragment_myuser_pass:
                onEventListener.onEvent(PREFER_USER_PASS, null);
                break;
            case R.id.fragment_my_account_desvincula:
                onEventListener.onEvent(PREFER_USER_DESASOCIAR, 1);
                break;
        }
    }
}
