package com.pagatodo.yaganaste.ui.account.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountPresenterNew;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import static com.pagatodo.yaganaste.utils.StringConstants.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.StringConstants.NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginContainerFragment extends SupportFragment implements TabsView<IEnumTab>, INavigationView, OnClickListener, ViewPager.OnPageChangeListener {

    private AccountPresenterNew accountPresenter;
    private NoSwipeViewPager vpLogin;
    private View mRootView;

    private ImageView imgPrevious;
    private ImageView imgNext;

    public static LoginContainerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginContainerFragment fragment = new LoginContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountPresenter = ((AccountActivity) getActivity()).getPresenter();
        accountPresenter.setIView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mRootView = view;
        loadViews();
        initViews();
    }


    private void loadViews() {
        vpLogin = (NoSwipeViewPager) mRootView.findViewById(R.id.vp_login);
        imgNext = (ImageView) mRootView.findViewById(R.id.img_arrow_next);
        imgPrevious = (ImageView) mRootView.findViewById(R.id.img_arrow_previous);

        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        vpLogin.addOnPageChangeListener(this);

    }


    @Override
    public void initViews() {
        accountPresenter.getPagerData(ViewPagerDataFactory.TABS.MAIN_LOGIN);

        Preferencias prefs = App.getInstance().getPrefs();

        imgPrevious.setVisibility(View.INVISIBLE);
        if (!prefs.containsData(HAS_SESSION)) {
            imgNext.setVisibility(View.INVISIBLE);
        }
        vpLogin.setIsSwipeable(prefs.containsData(HAS_SESSION));
    }

    @Override
    public void loadViewPager(ViewPagerData<IEnumTab> viewPagerData) {
        vpLogin.setAdapter(new GenericPagerAdapter<>(getActivity(), getChildFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData()));
        vpLogin.setIsSwipeable(true);
    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_arrow_previous:
                vpLogin.setCurrentItem(vpLogin.getCurrentItem() - 1);
                break;

            case R.id.img_arrow_next:
                vpLogin.setCurrentItem(vpLogin.getCurrentItem() + 1);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Nothing
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            imgPrevious.setVisibility(View.INVISIBLE);
            imgNext.setVisibility(View.VISIBLE);
        } else if (position == vpLogin.getAdapter().getCount()-1) {
            imgNext.setVisibility(View.INVISIBLE);
            imgPrevious.setVisibility(View.VISIBLE);
        } else {
            imgNext.setVisibility(View.VISIBLE);
            imgPrevious.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Nothing
    }
}
