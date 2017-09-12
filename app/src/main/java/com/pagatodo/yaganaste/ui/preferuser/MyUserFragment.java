package com.pagatodo.yaganaste.ui.preferuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_DESASOCIAR;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_EMAIL;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_PASS;

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
        myEmail.setOnClickListener(this);
        myPass.setOnClickListener(this);
        txtDesvincula.setOnClickListener(this);
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
