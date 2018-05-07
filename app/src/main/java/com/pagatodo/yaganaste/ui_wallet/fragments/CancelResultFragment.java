package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_CANCEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelResultFragment extends SupportFragment implements View.OnClickListener{

    @BindView(R.id.btnSalir)
    StyleButton btnSalir;

    public static CancelResultFragment newInstance() {
        return new CancelResultFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cancel_result, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    @Override
    public void initViews() {
        btnSalir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        App.getInstance().getPrefs().clearPreferences();
        onEventListener.onEvent(PREFER_CANCEL,null);
    }
}
