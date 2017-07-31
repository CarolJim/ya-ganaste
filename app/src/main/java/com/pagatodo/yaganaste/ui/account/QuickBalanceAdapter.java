package com.pagatodo.yaganaste.ui.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pagatodo.yaganaste.ui.account.login.AccessCodeGenerateFragment;
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

    /**
     * Nos entrega la posicion del fragment a mostrar dependiendo de lo que entregue el ViewPager
     * Si es Adquirente entrega 3 posiciones, si es emisor enonces entrega solo 2
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        if (isAdquirente) {
           switch (position){
               case 0:
                   return AccessCodeGenerateFragment.newInstance();
               case 1:
                   return QuickBalanceAdquirenteFragment.newInstance();
               case 2:
                   return GetMountFragment.newInstance();
               default:
                   return QuickBalanceAdquirenteFragment.newInstance();
           }
        } else {
            return position == 0 ? AccessCodeGenerateFragment.newInstance() : QuickBalanceFragment.newInstance();
        }
    }

    /**
     * El getCount entrega 3 o 2 posiciones dependiendo si es adquirente
     * @return
     */
    @Override
    public int getCount() {
        return isAdquirente ? 3 : 2;
    }
}
