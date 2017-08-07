package com.pagatodo.yaganaste.ui.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
import com.pagatodo.yaganaste.ui.account.login.OtpContainerFratgment;
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceAdquirenteFragment;
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceFragment;
import com.pagatodo.yaganaste.ui.adquirente.GetMountFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.BlankFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 14/07/2017.
 */

public class QuickBalanceAdapter extends FragmentPagerAdapter {

    private boolean isAdquirente;
    private List<Fragment> fragmentList;

    public QuickBalanceAdapter(FragmentManager fm, Boolean isAdquirente) {
        super(fm);
        this.isAdquirente = isAdquirente;
        fragmentList = new ArrayList<>();

        fragmentList.add(OtpContainerFratgment.newInstance());
        if (isAdquirente) {
            fragmentList.add(QuickBalanceAdquirenteFragment.newInstance());
            fragmentList.add(GetMountFragment.newInstance());
        } else {
            fragmentList.add(QuickBalanceFragment.newInstance());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return isAdquirente ? 3 : 2;
    }
}
