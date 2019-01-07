package com.pagatodo.yaganaste.modules.wallet_emisor.GeneratePIN;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;

public class GeneratePINFragment extends SupportFragment {

    private View rootView;

    public static GeneratePINFragment newInstance(){
        return new GeneratePINFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.generate_pin_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {

    }
}
