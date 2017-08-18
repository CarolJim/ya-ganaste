package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.interfaces.IAprovView;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.MainMenuPresenterImp;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.TabPresenter;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class SessionActivity extends ToolBarActivity implements TabsView, OnEventListener, View.OnClickListener, IAprovView {

    public final static int REQUESTCODE_OTP = 415;
    public final static String EVENT_GO_OTP = "EVENTGOOTP";
    public final static String EVENT_TO_PAYMENTS = "EVENTTOPAYMENTS";
    @BindView(R.id.session_view_pager)
    public ViewPager mainViewPager;
    @BindView(R.id.imgToRight)
    public ImageView arrowRight;
    @BindView(R.id.imgToLeft)
    public ImageView arrowLeft;
    private Preferencias pref;
    private TabPresenter tabPresenter;

    public static Intent createIntent(Context from) {
        return new Intent(from, SessionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_tab);
        this.tabPresenter = new MainMenuPresenterImp(this);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        pref = App.getInstance().getPrefs();
        tabPresenter.getPagerData(ViewPagerDataFactory.TABS.SESSION_EXIST);
        arrowLeft.setOnClickListener(this);
        arrowRight.setOnClickListener(this);
    }


    @Override
    public void loadViewPager(ViewPagerData viewPagerData) {
        mainViewPager.setAdapter(new GenericPagerAdapter<>(this, getSupportFragmentManager(), viewPagerData.getFragmentList(), viewPagerData.getTabData()));
        //mainViewPager.setOffscreenPageLimit(viewPagerData.getTabData().length - 1);
        mainViewPager.setCurrentItem(1);
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        arrowRight.setVisibility(VISIBLE);
                        arrowLeft.setVisibility(GONE);
                        break;

                    case 1:
                        arrowRight.setVisibility(GONE);
                        arrowLeft.setVisibility(GONE);
                        break;

                    case 2:
                        arrowRight.setVisibility(GONE);
                        arrowLeft.setVisibility(VISIBLE);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Glide.with(this).load(FlowUser.getImageProfileURL() + "?" + UtilsExtensions.getCurrentTime()).placeholder(R.mipmap.icon_user).dontAnimate().error(R.mipmap.icon_user).into(imgToolbarProfile);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_OTP:
                mainViewPager.setCurrentItem(0);
                break;

            case EVENT_TO_PAYMENTS:
                mainViewPager.setCurrentItem(2);
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_OTP) {
            if (resultCode == RESULT_OK) {
                mainViewPager.setCurrentItem(1);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgToRight:
                mainViewPager.setCurrentItem(1);
                break;
            case R.id.imgToLeft:
                mainViewPager.setCurrentItem(1);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
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
    public void showErrorAprov(ErrorObject error) {

    }

    @Override
    public void finishAssociation() {

    }
}
