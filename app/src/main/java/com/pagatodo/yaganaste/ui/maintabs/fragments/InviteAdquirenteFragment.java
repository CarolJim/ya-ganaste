package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.ui.maintabs.factories.ViewPagerDataFactory;
import com.pagatodo.yaganaste.ui.maintabs.presenters.HomeFragmentPresenter;
import com.pagatodo.yaganaste.utils.customviews.GenericPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.yaganasteviews.TabLayoutEmAd;


/**
 * @author Juan Guerra on 10/11/2016.
 */

public class InviteAdquirenteFragment extends GenericFragment implements View.OnClickListener{

    private View rootView;

    public static InviteAdquirenteFragment newInstance(){
        InviteAdquirenteFragment homeTabFragment = new InviteAdquirenteFragment();
        Bundle args = new Bundle();
        homeTabFragment.setArguments(args);
        return homeTabFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invite_adquirente, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        rootView.findViewById(R.id.btn_mas_tarde).setOnClickListener(this);
        rootView.findViewById(R.id.btn_mas_tarde).setOnClickListener(this);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (onEventListener != null) {
            onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, !menuVisible);
            onEventListener.onEvent(TabActivity.EVENT_CHANGE_MAIN_TAB_VISIBILITY, !menuVisible);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mas_tarde:
                if (onEventListener != null) {
                    onEventListener.onEvent(TabActivity.EVENT_GO_HOME, null);
                }
                break;

            default:
                break;
        }
    }
}