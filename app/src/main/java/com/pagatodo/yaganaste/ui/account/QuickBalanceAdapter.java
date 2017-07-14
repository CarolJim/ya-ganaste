package com.pagatodo.yaganaste.ui.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pagatodo.yaganaste.ui.account.login.QuickBalanceAdquirenteFragment;
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceFragment;
import com.pagatodo.yaganaste.ui.adquirente.GetMountFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.BlankFragment;

/**
 * Created by Jordan on 14/07/2017.
 */

public class QuickBalanceAdapter extends FragmentPagerAdapter {

    private boolean isAdquirente;

    public QuickBalanceAdapter(FragmentManager fm, Boolean isAdquirente) {
        super(fm);
        this.isAdquirente = isAdquirente;
    }

    @Override
    public Fragment getItem(int position) {
        if (isAdquirente) {
            return position == 0 ? QuickBalanceAdquirenteFragment.newInstance() : GetMountFragment.newInstance();
        } else {
            return position == 0 ? QuickBalanceFragment.newInstance() : BlankFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return isAdquirente ? 2 : 1;
    }
}
