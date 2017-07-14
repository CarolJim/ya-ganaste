package com.pagatodo.yaganaste.ui.adquirente;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link GenericFragment} subclass.
 */
public class HardSellAdqFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.btnHardSellNext)
    StyleButton btnHardSellNext;
    private View rootview;

    public static HardSellAdqFragment newInstance() {
        HardSellAdqFragment fragmentRegister = new HardSellAdqFragment();
        Bundle args = new Bundle();
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_hardsell_adq, container, false);
        initViews();
        return rootview;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnHardSellNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnHardSellNext:
                startActivity(BussinesActivity.createIntent(getActivity()));
                break;

            default:
                break;
        }
    }

}

