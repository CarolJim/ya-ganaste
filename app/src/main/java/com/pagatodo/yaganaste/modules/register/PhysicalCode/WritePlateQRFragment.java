package com.pagatodo.yaganaste.modules.register.PhysicalCode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

public class WritePlateQRFragment extends GenericFragment {

    private View rootView;

    public static WritePlateQRFragment newInstance(){
        return new WritePlateQRFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.write_plateqr_fragment,container,false);
        initViews();
        return rootView;

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
    }
}
