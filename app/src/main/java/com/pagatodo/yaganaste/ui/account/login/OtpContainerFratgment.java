package com.pagatodo.yaganaste.ui.account.login;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.IQuickBalanceManager;
import com.pagatodo.yaganaste.ui.otp.fragments.OtpViewFragment;

import butterknife.BindView;

/**
 * @author Juan Guerra on 01/08/2017.
 */

public class OtpContainerFratgment  extends SupportFragment implements
        AccessCodeGenerateFragment.OtpInterface, View.OnClickListener {

    private View rootView;
    private ImageView imgArrowNext;

    private IQuickBalanceManager quickBalanceManager;

    public static OtpContainerFratgment newInstance() {
        OtpContainerFratgment fragment = new OtpContainerFratgment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(boolean flag){
        return new Fragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  quickBalanceManager = ((LoginManagerContainerFragment) getParentFragment()).getQuickBalanceManager();
        loadFragment(AccessCodeGenerateFragment.newInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp_container, container, false);
    }

    /**
     * Metodo para funcionalidad back,
     * @return true en caso de que el back sea tratado por
     * completo y false en caso de que se requiera alguna accion adicional por parte de quien llama
     * a este metodo
     */

    public boolean onBack() {
        if (getCurrentFragment() instanceof AccessCodeGenerateFragment) {
            return false;
        } else {
            loadFragment(AccessCodeGenerateFragment.newInstance());//, Direction.BACK);
            //quickBalanceManager.setViewPagerSwipeable(true);
            imgArrowNext.setVisibility(View.GONE);
            return false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        imgArrowNext = (ImageView) rootView.findViewById(R.id.imgArrowNext);
        imgArrowNext.setOnClickListener(this);
        imgArrowNext.bringToFront();
    }

    @Override
    public void loadCode(String code) {
        imgArrowNext.setVisibility(View.GONE);
        loadFragment(OtpViewFragment.newInstance(code), Direction.FORDWARD);
        //quickBalanceManager.setViewPagerSwipeable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgArrowNext:
               // quickBalanceManager.nextPage();
                break;

        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (isAdded()) {
            getCurrentFragment().setMenuVisibility(menuVisible);
        }
    }
}