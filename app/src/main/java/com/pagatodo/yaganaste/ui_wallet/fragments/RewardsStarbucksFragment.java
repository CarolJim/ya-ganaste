package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.RequestPaymentHorizontalAdapter;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asandovals on 13/04/2018.
 */

public class RewardsStarbucksFragment  extends GenericFragment {
    private View rootView;

    @BindView(R.id.titulo_datos_usuario)
    StyleTextView titulo_datos_usuario;
    @BindView(R.id.txt_reward_subtitul)
    StyleTextView txt_reward_subtitul;
    @BindView(R.id.edt_message_payment)
    StyleEdittext edtMessagePayment;
    @BindView(R.id.btn_send_payment)
    StyleButton btnSendPayment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_starbucks_rewards, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
    }
}
