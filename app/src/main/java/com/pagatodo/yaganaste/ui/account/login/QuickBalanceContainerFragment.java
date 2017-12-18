package com.pagatodo.yaganaste.ui.account.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.LandingActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;
import com.pagatodo.yaganaste.ui.account.QuickBalanceAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum.INICIO_SESION_ADQUIRENTE;
import static com.pagatodo.yaganaste.interfaces.enums.LandingActivitiesEnum.INICIO_SESION_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.CO_QUICK_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.CO_QUICK_EM;
import static com.pagatodo.yaganaste.utils.Recursos.HUELLA_FAIL;

/**
 * Created by Jordan on 14/07/2017.
 */

public class QuickBalanceContainerFragment extends SupportFragment implements IQuickBalanceManager {

    private QuickBalanceAdapter quickBalanceAdapter;
    private View rootView;
    private Preferencias prefs = App.getInstance().getPrefs();
    @BindView(R.id.viewPagerQuickBalance)
    NoSwipeViewPager viewPagerQuickBalance;
    ILoginContainerManager loginContainerManager;
    private Preferencias pref;

    public static QuickBalanceContainerFragment newInstance() {
        QuickBalanceContainerFragment fragment = new QuickBalanceContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs.saveDataBool(HUELLA_FAIL, false);
        quickBalanceAdapter = new QuickBalanceAdapter(getChildFragmentManager(), !RequestHeaders.getTokenAdq().isEmpty());
        loginContainerManager = ((LoginManagerContainerFragment) getParentFragment()).getLoginContainerManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quickbalance_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        viewPagerQuickBalance.setAdapter(quickBalanceAdapter);
        viewPagerQuickBalance.setCurrentItem(1);
        //viewPagerQuickBalance.setIsSwipeable(true);
        // Se deshabilita el Swipe para los nuevos cambios.
        viewPagerQuickBalance.setIsSwipeable(false);
        pref = App.getInstance().getPrefs();
        if (RequestHeaders.getTokenAdq().isEmpty() && !pref.containsData(CO_QUICK_EM)) {
            pref.saveDataBool(CO_QUICK_EM, true);
            startActivity(LandingActivity.createIntent(getActivity(), INICIO_SESION_EMISOR));
        } else if (!RequestHeaders.getTokenAdq().isEmpty() && !pref.containsData(CO_QUICK_ADQ)) {
            pref.saveDataBool(CO_QUICK_ADQ, true);
            startActivity(LandingActivity.createIntent(getActivity(), INICIO_SESION_ADQUIRENTE));
        }
    }

    public ILoginContainerManager getLoginContainerManager() {
        return loginContainerManager;
    }

    public IQuickBalanceManager getQuickBalanceManager() {
        return this;
    }

    /**
     * GEstion de los eventos de oprimir la flecha Back (Navegacion), se comporta dependiendo el caso de los
     * fragmento que esten seleccionados, recordemos que puede cargar 2 o 3 fragmntos
     */
    @Override
    public void onBackPress() {
        if (viewPagerQuickBalance.getCurrentItem() == 0) {
            if (!((OtpContainerFratgment) quickBalanceAdapter.getItem(0)).onBack()) {
                viewPagerQuickBalance.setCurrentItem(1);
            } else {
                viewPagerQuickBalance.setIsSwipeable(true);
            }

        } else if (viewPagerQuickBalance.getCurrentItem() == 2) {
            viewPagerQuickBalance.setCurrentItem(1);
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void setViewPagerSwipeable(boolean isSwipeable) {
        viewPagerQuickBalance.setIsSwipeable(isSwipeable);
    }


    /**
     * GEstion de los eventos de oprimir la flecha Back (Izquierda), se comporta dependiendo el caso de los
     * fragmento que esten seleccionados, recordemos que puede cargar 2 o 3 fragmntos
     */
    @Override
    public void backPage() {
        if (viewPagerQuickBalance.getCurrentItem() == 2) {
            viewPagerQuickBalance.setCurrentItem(1);
        } else if (viewPagerQuickBalance.getCurrentItem() == 1) {
            viewPagerQuickBalance.setCurrentItem(0);
        }
    }


    /**
     * GEstion de los eventos de oprimir la flecha NExt (Derecha), se comporta dependiendo el caso de los
     * fragmento que esten seleccionados, recordemos que puede cargar 2 o 3 fragmntos
     */
    @Override
    public void nextPage() {
        if (viewPagerQuickBalance.getCurrentItem() == 0) {
            viewPagerQuickBalance.setCurrentItem(1);
        } else if (viewPagerQuickBalance.getCurrentItem() == 1) {
            viewPagerQuickBalance.setCurrentItem(2);
        }
    }
}
