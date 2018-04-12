package com.pagatodo.yaganaste.ui.maintabs.factories;

import android.support.v4.app.Fragment;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.exceptions.IllegalFactoryParameterException;
import com.pagatodo.yaganaste.interfaces.enums.AdqEmTab;
import com.pagatodo.yaganaste.interfaces.enums.MainTab;
import com.pagatodo.yaganaste.interfaces.enums.SessionExistTab;
import com.pagatodo.yaganaste.interfaces.enums.VoidTab;
import com.pagatodo.yaganaste.ui.maintabs.fragments.EnviosFromFragmentNewVersion;
import com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment;
import com.pagatodo.yaganaste.ui.account.login.LoginFragment;
import com.pagatodo.yaganaste.ui.account.profile.BalanceFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.StatusRegisterAdquirienteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.AbstractAdEmFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.BlankFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DocumentsContainerFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsFragment;
import com.pagatodo.yaganaste.ui.otp.fragments.OtpGeneratorFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment;
import com.pagatodo.yaganaste.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Constants.MOVEMENTS_ADQ;
import static com.pagatodo.yaganaste.utils.Constants.MOVEMENTS_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public class ViewPagerDataFactory {

    private ViewPagerDataFactory() {
        throw new IllegalCallException("You Can't Create an Instance for ViewPagerDataFactory");
    }

    public static ViewPagerData createList(final TABS type) {
        List<Fragment> fragmentList = new ArrayList<>();
        switch (type) {
            case MAIN_LOGIN:
                fragmentList.add(LoginFragment.newInstance());
                return new ViewPagerData<>(fragmentList, VoidTab.values());

            case MAIN_TABS:
                addMainFragments(fragmentList);
                return new ViewPagerData<>(fragmentList, MainTab.values());
            case MAIN_ELECTION:
                addMainFragmentsselection(fragmentList);
                return new ViewPagerData<>(fragmentList, MainTab.values());
            /*case MAIN_ELECTION_ACTUALIZACIONDOCS:
                addMainFragmentsselection(fragmentList);
                return new ViewPagerData<>(fragmentList, MainTab.values());
*/
            /*case HOME_FRAGMENT:
                addHomeFragments(fragmentList);
                return new ViewPagerData<>(fragmentList, AdqEmTab.values());
*/
            case PERSONAL_ACCOUNT:
                return new ViewPagerData<>(null, DateUtil.getLastMovementstMonths().toArray(new MonthsMovementsTab[0]));

            case PAYMENTS:
                //return new ViewPagerData<>(null, DateUtil.getTabAdquirente());
                return new ViewPagerData<>(null, DateUtil.getTabsAdquirente().toArray(new AdquirentePaymentsTab[0]));

            case SESSION_EXIST:
                fragmentList.add(OtpGeneratorFragment.newInstance());
                fragmentList.add(BalanceFragment.newInstance());
                fragmentList.add(GetMountFragment.newInstance());
                return new ViewPagerData<>(fragmentList, SessionExistTab.values());

            case DEPOSITS:

                return null;

            default:
                throw new IllegalFactoryParameterException(type.toString());
        }
    }

    private static void addMainFragmentsselection(List<Fragment> fragmentList) {
        fragmentList.remove(StatusRegisterAdquirienteFragment.newInstance());
        fragmentList.add(DepositsFragment.newInstance());
        fragmentList.add(DocumentsContainerFragment.newInstance());

    }

    private static void addMainFragments(List<Fragment> fragmentList) {
        int Idestatus;
        fragmentList.add(EnviosFromFragmentNewVersion.newInstance());
        fragmentList.add(WalletTabFragment.newInstance());
        fragmentList.add(NewPaymentFragment.newInstance());

/*
        Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
        if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && SingletonUser.getInstance().getDataUser().getEstatusAgente() == CRM_DOCTO_APROBADO) {
            fragmentList.add(GetMountFragment.newInstance());
        }else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I6.getId() && SingletonUser.getInstance().getDataUser().getEstatusAgente() == STATUS_DOCTO_PENDIENTE) {
           // fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(InviteAdquirenteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I6.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
         //   fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
            fragmentList.add(InviteAdquirenteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I7.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && Idestatus == IdEstatus.I8.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                   Idestatus == IdEstatus.I9.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                Idestatus == IdEstatus.I11.getId()) {
            //fragmentList.add(DocumentsContainerFragment.newInstance());
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                && SingletonUser.getInstance().getDataUser().getEstatusAgente() == STATUS_DOCTO_PENDIENTE) {
            fragmentList.add(StatusRegisterAdquirienteFragment.newInstance());
        } else {
            fragmentList.add(InviteAdquirenteFragment.newInstance());
        }
        */
    }

    private static void addHomeFragments(List<Fragment> fragmentList) {
        /*fragmentList.add(AbstractAdEmFragment.newInstance(MOVEMENTS_EMISOR));

        if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false)
                && App.getInstance().getPrefs().loadDataInt(ESTATUS_AGENTE) == CRM_DOCTO_APROBADO) {
            fragmentList.add(AbstractAdEmFragment.newInstance(MOVEMENTS_ADQ));
        } else {
            fragmentList.add(BlankFragment.newInstance());
        }*/
    }


    public enum TABS {
        MAIN_LOGIN,
        MAIN_TABS,
        MAIN_ELECTION_ACTUALIZACIONDOCS,
        MAIN_ELECTION,
        HOME_FRAGMENT,
        PERSONAL_ACCOUNT,
        PAYMENTS,
        SESSION_EXIST,
        DEPOSITS
    }

}