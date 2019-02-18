package com.pagatodo.yaganaste.modules.PaymentImp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

public class PaymentContentFragment extends GenericFragment {

    public static final int RECHARGE_FRAGMENT = 10;
    public static final int SERVICES_PAY_FRAGMENT = 20;

    @BindView(R.id.head_Wallet)
    HeadWallet headWallet;
    @BindView(R.id.view_pager_payments)
    ViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    private View rootView;

    public static PaymentContentFragment newInstance(){
        return new PaymentContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payment_content_fragment,container,false);
        this.initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));
        pager.setAdapter(new PaymentsFragmenAdapter(getContext(),getFragmentManager()));
        tabs.setupWithViewPager(pager);
    }
}
