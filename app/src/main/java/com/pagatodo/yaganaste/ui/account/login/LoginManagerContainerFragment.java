package com.pagatodo.yaganaste.ui.account.login;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.AdqActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;
import com.pagatodo.yaganaste.ui.account.register.AsignarNIPFragment;
import com.pagatodo.yaganaste.ui.account.register.TienesTarjetaFragment;
import com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment;
import com.pagatodo.yaganaste.ui.preferuser.presenters.MyDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.BalanceWalletFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.MapStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.PairBluetoothFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.RewardsStarbucksFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.SelectDongleFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.VentasDiariasFragment;
import com.pagatodo.yaganaste.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.TYPE_TRANSACTION;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

/**
 * Created by Jordan on 06/07/2017.
 */

public class LoginManagerContainerFragment extends SupportFragment implements ILoginContainerManager {

    @BindView(R.id.container)
    FrameLayout container;
    private View rootView;
    private Preferencias prefs = App.getInstance().getPrefs();

    public static LoginManagerContainerFragment newInstance() {
        LoginManagerContainerFragment fragment = new LoginManagerContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_login_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        if ((prefs.containsData(IS_OPERADOR)) || (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty())) {
            /*loadFragment(BalanceWalletFragment.newInstance(), Direction.FORDWARD, false);*/
            loadFragment(TienesTarjetaFragment.newInstance(), Direction.FORDWARD, true);
            showBack(false);
        } else {
            loadFragment(LoginFragment.newInstance(), Direction.FORDWARD, false);
            showBack(true);
        }
    }

    @Override
    public void loadLoginFragment() {
        loadFragment(LoginFragment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    @Override
    public void loadRecoveryFragment() {
        loadFragment(RecoveryFragment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    /**
     * Se encarga de cargar BlockCardFragment a nivel correcto para las transiciones
     */
    public void loadBlockFragment() {
        loadFragment(BlockCardFragment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    public void loadSecureCodeContainer() {
        loadFragment(OtpContainerFratgment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    public void loadQuickPayment() {
        loadFragment(GetMountFragment.newInstance(App.getInstance().getPrefs().loadData(COMPANY_NAME)), Direction.FORDWARD, true);
        showBack(true);
    }

    public void loadGetBalanceClosedLoop() {
        UI.showAlertDialog(getContext(), getString(R.string.consultar_saldo_uyu_title), getString(R.string.consultar_saldo_uyu_desc),
                getString(R.string.consultar_saldo_uyu_btn), (dialogInterface, i) -> {
                    TransactionAdqData.getCurrentTransaction().setAmount("");
                    TransactionAdqData.getCurrentTransaction().setDescription("");
                    Intent intentAdq = new Intent(getActivity(), AdqActivity.class);
                    intentAdq.putExtra(TYPE_TRANSACTION, QPOSService.TransactionType.INQUIRY.ordinal());
                    startActivity(intentAdq);
                });
    }

    public void loadConfigDongle() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()) {
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enabler);
        } else {
            loadFragment(MyDongleFragment.newInstance(prefs.loadDataInt(MODE_CONNECTION_DONGLE)), Direction.FORDWARD, true);
            showBack(true);
        }
    }

    public void loadVentas() {
        loadFragment(VentasDiariasFragment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    public void loadRewards() {
        loadFragment(RewardsStarbucksFragment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    public void loadStores() {
        loadFragment(MapStarbucksFragment.newInstance(), Direction.FORDWARD, true);
        showBack(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackActions();
            }
        }, 0);
    }

    public void onBackActions() {
        Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof GetMountFragment
                || currentFragment instanceof OtpContainerFratgment
                || currentFragment instanceof BlockCardFragment
                || currentFragment instanceof RewardsStarbucksFragment
                || currentFragment instanceof MapStarbucksFragment
                || currentFragment instanceof MyDongleFragment
                || currentFragment instanceof SelectDongleFragment
                || currentFragment instanceof PairBluetoothFragment
                || currentFragment instanceof VentasDiariasFragment
                ) {
            showBack(false);
            loadFragment(BalanceWalletFragment.newInstance(), Direction.BACK, false);
        } else if (currentFragment instanceof RecoveryFragment) {
            showBack(true);
            loadFragment(LoginFragment.newInstance(), Direction.BACK, false);
        } else if (currentFragment instanceof LoginFragment) {
            if ((prefs.containsData(IS_OPERADOR)) || (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty())) {
                showBack(false);
                loadFragment(BalanceWalletFragment.newInstance(), Direction.BACK, false);
            } else {
                getActivity().finish();
            }
        } else {
            getActivity().finish();
        }
    }

    public ILoginContainerManager getLoginContainerManager() {
        return this;
    }
}
