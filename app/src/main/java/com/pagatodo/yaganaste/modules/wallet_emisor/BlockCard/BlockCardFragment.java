package com.pagatodo.yaganaste.modules.wallet_emisor.BlockCard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.components.LabelArrow;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.wallet_emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlockCardFragment extends SupportFragment implements View.OnClickListener{

    private View rootView;
    private WalletMainActivity activity;

    @BindView(R.id.labelArrow)
    LabelArrow labelArrowTem;

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
        ButterKnife.bind(this, this.rootView);
        labelArrowTem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.labelArrow:

                break;
        }
    }
}
