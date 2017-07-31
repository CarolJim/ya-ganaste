package com.pagatodo.yaganaste.ui.account.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessCodeGenerateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessCodeGenerateFragment extends GenericFragment implements
        INavigationView<Void, Void>, View.OnClickListener {

    @BindView(R.id.imgArrowNext)
    ImageView imgArrowNext;
    View rootView;
    private IQuickBalanceManager quickBalanceManager;

    public AccessCodeGenerateFragment() {
        // Required empty public constructor
    }

    public static AccessCodeGenerateFragment newInstance() {
        AccessCodeGenerateFragment fragment = new AccessCodeGenerateFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quickBalanceManager = ((QuickBalanceContainerFragment) getParentFragment()).getQuickBalanceManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_access_code_generate, container, false);

        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        imgArrowNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btnGoToLogin:
                loginContainerManager.loadLoginFragment();
                break;*/
            case R.id.imgArrowNext:
                nextScreen(null, null);
                break;
        }
    }

    @Override
    public void nextScreen(String event, Void data) {
        quickBalanceManager.nextPage();
    }

    @Override
    public void backScreen(String event, Void data) {
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Void error) {

    }


}
