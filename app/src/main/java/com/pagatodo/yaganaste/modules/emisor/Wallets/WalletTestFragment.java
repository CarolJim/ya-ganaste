package com.pagatodo.yaganaste.modules.emisor.Wallets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WalletTestFragment extends GenericFragment {


    public static WalletTestFragment newInstance(){
        return new WalletTestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_test_fragment,container,false);
    }

    @Override
    public void initViews() {

    }
}
