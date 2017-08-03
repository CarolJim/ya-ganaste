package com.pagatodo.yaganaste.ui.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui.account.ILoginContainerManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;

/**
 * Created by Jordan on 06/07/2017.
 */

public class LoginManagerContainerFragment extends SupportFragment implements ILoginContainerManager {

    @BindView(R.id.container)
    FrameLayout container;
    private View rootView;

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

        Preferencias prefs = App.getInstance().getPrefs();
        if (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty()) {
            loadFragment(QuickBalanceContainerFragment.newInstance(), Direction.FORDWARD, true);
        } else {
            loadFragment(LoginFragment.newInstance(), Direction.FORDWARD, false);
        }
    }

    @Override
    public void loadLoginFragment() {
        loadFragment(LoginFragment.newInstance(), Direction.FORDWARD, true);
    }

    @Override
    public void loadRecoveryFragment() {
        loadFragment(RecoveryFragment.newInstance(), Direction.FORDWARD, true);
    }

    @Override
    public void popBackStack() {
        getChildFragmentManager().popBackStack();
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
        if (getChildFragmentManager().findFragmentById(R.id.container) instanceof QuickBalanceContainerFragment) {
            ((QuickBalanceContainerFragment) getChildFragmentManager().findFragmentById(R.id.container)).onBackPress();
        } else {
            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                popBackStack();
            } else {
                getActivity().finish();
            }
        }
    }

    public ILoginContainerManager getLoginContainerManager() {
        return this;
    }
}
