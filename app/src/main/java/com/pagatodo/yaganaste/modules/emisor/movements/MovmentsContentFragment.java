package com.pagatodo.yaganaste.modules.emisor.movements;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;
import com.pagatodo.view_manager.pages.Months;
import com.pagatodo.view_manager.pages.MovData;
import com.pagatodo.view_manager.pages.NonSwipeableViewPager;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.emisor.WalletMainActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovmentsContentFragment extends GenericFragment {

    private View rootView;
    private MovementsAdapter adapter;
    private WalletMainActivity activity;

    @BindView(R.id.view_page_mov)
    NonSwipeableViewPager pagesMovements;
    @BindView(R.id.tabs_months)
    TabLayout tabas;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (WalletMainActivity) context;
    }

    public static MovmentsContentFragment newInstance(){
        return new MovmentsContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movments_content_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        ArrayList<Months> movData = new ArrayList<>();
        movData.add(Months.ENERO);
        movData.add(Months.FEBRERO);
        movData.add(Months.FEBRERO);
        movData.add(Months.MARZO);
        movData.add(Months.ABRIL);
        movData.add(Months.MAYO);
        adapter = new MovementsAdapter(getFragmentManager(),movData);
        pagesMovements.setAdapter(adapter);
        tabas.setupWithViewPager(pagesMovements);
        pagesMovements.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 Fragment page = Objects.requireNonNull(getFragmentManager())
                            .findFragmentByTag("android:switcher:" + R.id.view_page_mov + ":" + position);

                    if (page != null) {
                        if (((MovementsFragment) page).isUpdate()) {
                            ((MovementsFragment) page).setListener(new MovmentsContentListener() {
                                @Override
                                public void showLoad() {
                                    activity.showLoad();
                                }

                                @Override
                                public void hideLoad() {
                                    activity.hideLoad();
                                }
                            });
                            ((MovementsFragment) page).getMovements(
                                    String.valueOf(movData.get(position).getMonth()), "2019");
                        }
                    }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    interface MovmentsContentListener{
        void showLoad();
        void hideLoad();
    }

}
