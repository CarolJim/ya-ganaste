package com.pagatodo.yaganaste.modules.wallet_emisor.BlockCard;

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

public class BlockCardFragment extends SupportFragment {

    private View rootView;
    private WalletMainActivity activity;

    public static BlockCardFragment newInstance(){
        return new BlockCardFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (WalletMainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.block_card_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {

    }
}
