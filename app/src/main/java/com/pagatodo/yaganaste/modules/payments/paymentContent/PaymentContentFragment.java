package com.pagatodo.yaganaste.modules.payments.paymentContent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.pagatodo.view_manager.components.HeadWallet;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Recursos.TYPEPAYMENT;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

public class PaymentContentFragment extends GenericFragment implements PaymentContentContracts.Listener {

    public static final int RECHARGE_FRAGMENT = 10;
    public static final int SERVICES_PAY_FRAGMENT = 20;

    @BindView(R.id.head_Wallet)
    HeadWallet headWallet;
    @BindView(R.id.view_pager_payments)
    ViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    private View rootView;
    private PaymentContentInteractor interactor;
    private int pageCurrent;

    public static PaymentContentFragment newInstance() {
        return new PaymentContentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.interactor = new PaymentContentInteractor(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payment_content_fragment, container, false);
        this.initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        headWallet.setAmount(App.getInstance().getPrefs().loadData(USER_BALANCE));

        pageCurrent = 0;
        pager.setCurrentItem(0);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //do stuff here

                if (tab.getPosition() == 0) {
                    App.getInstance().getPrefs().saveDataBool(TYPEPAYMENT, true);
                } else if (tab.getPosition() == 1) {
                    App.getInstance().getPrefs().saveDataBool(TYPEPAYMENT, false);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageCurrent = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.interactor.getFavorites();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.interactor.getFavorites();
    }

    @Override
    public void onFavoritesSuccess() {
        pager.setAdapter(new PaymentsFragmenAdapter(getContext(), getFragmentManager()));
        tabs.setupWithViewPager(pager);
        pager.setCurrentItem(pageCurrent);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void onError(String msj) {
        UI.showErrorSnackBar(Objects.requireNonNull(getActivity()), msj, Snackbar.LENGTH_SHORT);
    }
}
