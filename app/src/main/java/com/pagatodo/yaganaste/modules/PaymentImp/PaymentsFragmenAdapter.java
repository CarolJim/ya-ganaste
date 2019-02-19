package com.pagatodo.yaganaste.modules.PaymentImp;

import android.content.Context;

import com.pagatodo.yaganaste.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static com.pagatodo.yaganaste.modules.PaymentImp.PaymentContentFragment.RECHARGE_FRAGMENT;
import static com.pagatodo.yaganaste.modules.PaymentImp.PaymentContentFragment.SERVICES_PAY_FRAGMENT;

public class PaymentsFragmenAdapter extends FragmentPagerAdapter {

    private Context context;

    public PaymentsFragmenAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PayFragment.newInstance(RECHARGE_FRAGMENT);
        } else if (position == 1){
            return PayFragment.newInstance(SERVICES_PAY_FRAGMENT);
        } else return PayFragment.newInstance(RECHARGE_FRAGMENT);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.tab_recharge);
            case 1:
                return context.getString(R.string.tab_pay_services);

            default:
                return null;
        }
    }
}
