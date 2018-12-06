package com.pagatodo.yaganaste.modules.register.fragments.physicalcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

public class PhysicalCodeFragment extends GenericFragment {

    private View rootView;


    public static PhysicalCodeFragment newInstance(){
        return new PhysicalCodeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.physical_code_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
    }
}
