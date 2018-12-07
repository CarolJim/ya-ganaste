package com.pagatodo.yaganaste.modules.register.PhysicalCode;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

public class NewLinkedCodeFragment extends GenericFragment {

    private View rootView;

    public static NewLinkedCodeFragment newInstance(String textDisplay){
        NewLinkedCodeFragment fragment = new NewLinkedCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DISPLAY",textDisplay);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_linked_ode_fragment,container,false);
        initViews();
        return rootView;

    }


    public void initViews() {
        ButterKnife.bind(this, rootView);
    }


}
