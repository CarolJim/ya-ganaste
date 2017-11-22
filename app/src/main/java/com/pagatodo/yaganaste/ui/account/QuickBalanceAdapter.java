package com.pagatodo.yaganaste.ui.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.ui.account.login.OtpContainerFratgment;
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceAdquirenteFragment;
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceCupoFragment;
import com.pagatodo.yaganaste.ui.account.login.QuickBalanceFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.utils.StringConstants;

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

        //fragmentList.add(OtpContainerFratgment.newInstance());
        if (isAdquirente) {
            if (!App.getInstance().getPrefs().loadDataBoolean(StringConstants.IS_CUPO)) {
                fragmentList.add(QuickBalanceAdquirenteFragment.newInstance());
            } else {
                fragmentList.add(QuickBalanceCupoFragment.newInstance());
            }

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
        return isAdquirente ? 2 : 1;
    }
}
