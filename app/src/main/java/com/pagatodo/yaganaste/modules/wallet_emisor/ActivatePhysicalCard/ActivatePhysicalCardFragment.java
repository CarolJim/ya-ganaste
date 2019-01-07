package com.pagatodo.yaganaste.modules.wallet_emisor.ActivatePhysicalCard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.wallet_emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivatePhysicalCardFragment extends SupportFragment implements View.OnClickListener{

    private WalletMainActivity activity;
    private View rootView;

    @BindView(R.id.btn_continue_active_card)
    StyleButton btnContinue;

    public static ActivatePhysicalCardFragment newInstance(){
        return new ActivatePhysicalCardFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (WalletMainActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activate_physical_card_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.activity.getRouter().onShowGeneratePIN();
    }
}
