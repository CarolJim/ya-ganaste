package com.pagatodo.yaganaste.modules.registerAggregator.RegisterComplete;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.registerAggregator.LinkedQRs.LinkedQRsFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCompleteFragment extends GenericFragment implements View.OnClickListener {
    private View rootView;
    private StyleButton btn_finish_register;

    public static RegisterCompleteFragment newInstance() {
        return new RegisterCompleteFragment();
    }

    public RegisterCompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_register_complete2, container, false);
        btn_finish_register=(StyleButton)rootView.findViewById(R.id.btn_finish_register);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        btn_finish_register.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finish_register:

                break;
        }
    }
}
