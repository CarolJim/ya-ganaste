package com.pagatodo.yaganaste.modules.sidebar.HelpYaGanaste;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.UtilsIntents;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpYaGanasteFragment extends SupportFragment implements View.OnClickListener {
    private LinearLayout chat, email, call;

    public HelpYaGanasteFragment() {
        // Required empty public constructor
    }

    public static HelpYaGanasteFragment newInstance() {
        HelpYaGanasteFragment helpYaGanasteFragment = new HelpYaGanasteFragment();
        return helpYaGanasteFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_help_ya_ganaste, container, false);
        ButterKnife.bind(this, rootView);
        chat = (LinearLayout) rootView.findViewById(R.id.chat);
        email = (LinearLayout) rootView.findViewById(R.id.email);
        call = (LinearLayout) rootView.findViewById(R.id.call);
        initViews();

        return rootView;
    }

    @Override
    public void initViews() {

        chat.setOnClickListener(this::onClick);
        email.setOnClickListener(this::onClick);
        call.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat:
                UtilsIntents.createCallIntent(getActivity());
                break;
            case R.id.email:
                UtilsIntents.sendEmail(getActivity());
                break;
            case R.id.call:
                UtilsIntents.createCallIntent(getActivity());
                break;
        }
    }
}
