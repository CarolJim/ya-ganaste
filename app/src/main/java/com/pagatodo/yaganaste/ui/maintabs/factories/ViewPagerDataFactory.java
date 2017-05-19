package com.pagatodo.yaganaste.ui.maintabs.factories;

import android.support.v4.app.Fragment;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.exceptions.IllegalFactoryParameterException;
import com.pagatodo.yaganaste.interfaces.enums.AdqEmTab;
import com.pagatodo.yaganaste.interfaces.enums.MainTab;
import com.pagatodo.yaganaste.interfaces.enums.SessionExistTab;
import com.pagatodo.yaganaste.ui.account.profile.BalanceFragment;
import com.pagatodo.yaganaste.ui.adquirente.Documentos;
import com.pagatodo.yaganaste.ui.adquirente.GetMountFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.HomeTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.AbstractAdEmFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.InviteAdquirenteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsTabFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsDataFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.otp.fragments.OtpGeneratorFragment;
import com.pagatodo.yaganaste.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public class ViewPagerDataFactory {

    private ViewPagerDataFactory() {
        throw new IllegalCallException("You can not create an instance for ViewPagerDataFactory");
    }

    public enum TABS {
        MAIN,
        HOME_FRAGMENT,
        PERSONAL_ACCOUNT,
        PAYMENTS,
        SESSION_EXIST
    }

    public static ViewPagerData createList(final TABS type) {
        List<Fragment> fragmentList = new ArrayList<>();
        Preferencias pref = App.getInstance().getPrefs();
        switch (type) {
            case MAIN:

                fragmentList.add(HomeTabFragment.newInstance());
                fragmentList.add(PaymentsTabFragment.newInstance());
                fragmentList.add(DepositsFragment.newInstance());

                if (SingletonUser.getInstance().getDataUser().isEsAgente()
                        && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {

                    fragmentList.add(GetMountFragment.newInstance());

                } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                        && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_PENDIENTE) {

                    fragmentList.add(Documentos.newInstance());

                } else {

                    fragmentList.add(InviteAdquirenteFragment.newInstance());
                }

                return new ViewPagerData<>(fragmentList, MainTab.values());

            case HOME_FRAGMENT:
                fragmentList.add(AbstractAdEmFragment.newInstance(AbstractAdEmFragment.MOVEMENTS));
                fragmentList.add(AbstractAdEmFragment.newInstance(AbstractAdEmFragment.PAYMENTS));
                return new ViewPagerData<>(fragmentList, AdqEmTab.values());

            case PERSONAL_ACCOUNT:
                return new ViewPagerData<>(null, DateUtil.getLastMovementstMonths().toArray(new MonthsMovementsTab[0]));

            case PAYMENTS:
                return new ViewPagerData<>(null, DateUtil.getTabAdquirente());

            case SESSION_EXIST:
                fragmentList.add(OtpGeneratorFragment.newInstance());
                fragmentList.add(BalanceFragment.newInstance());
                fragmentList.add(GetMountFragment.newInstance());
                return new ViewPagerData<>(fragmentList, SessionExistTab.values());


            default:
                throw new IllegalFactoryParameterException(type.toString());
        }
    }

}
