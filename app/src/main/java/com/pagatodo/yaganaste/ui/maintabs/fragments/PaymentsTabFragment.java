package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.adapters.FragmentPagerAdapter;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 06/04/2017.
 */

public class PaymentsTabFragment extends SupportFragment implements View.OnClickListener, View.OnDragListener {

    private View rootView;

    //@BindView(R.id.container)
    //FrameLayout frameLayout;
    @BindView(R.id.payment_view_pager)
    NoSwipeViewPager payment_view_pager;
    @BindView(R.id.tab_recargas)
    Button botonRecargas;
    @BindView(R.id.tab_servicios)
    Button botonServicios;
    @BindView(R.id.tab_envios)
    Button botonEnvios;
    @BindView(R.id.rlimgPagosServiceToPay)
    RelativeLayout rlimgPagosServiceToPay;

    MovementsTab prevTAB;

    public static PaymentsTabFragment newInstance() {
        PaymentsTabFragment paymentsTabFragment = new PaymentsTabFragment();
        Bundle args = new Bundle();
        paymentsTabFragment.setArguments(args);
        return paymentsTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        //initFragment();

        payment_view_pager.setAdapter(new FragmentPagerAdapter(getFragmentManager()));
        payment_view_pager.setOffscreenPageLimit(1);
        payment_view_pager.setCurrentItem(0);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthButton = dm.widthPixels / 3;

        botonRecargas.setWidth(widthButton);
        botonServicios.setWidth(widthButton);
        botonEnvios.setWidth(widthButton);

        botonRecargas.setOnClickListener(this);
        botonServicios.setOnClickListener(this);
        botonEnvios.setOnClickListener(this);

        rlimgPagosServiceToPay.setOnDragListener(this);
    }

    @Override
    public void initFragment() {
        this.fragmentManager = getFragmentManager();
        loadFragment(RecargasCarouselFragment.newInstance(), Direction.NONE, false);
        prevTAB = MovementsTab.TAB1;
    }

    @Override
    public void onClick(View v) {
        //Fragment curentFragment = fragmentManager.findFragmentById(R.id.container);
        switch (v.getId()) {
            case R.id.tab_recargas:
                changeBackTabs(v, MovementsTab.TAB1);
                //loadFragment(RecargasCarouselFragment.newInstance(), Direction.BACK, false);
                //prevTAB = MovementsTab.TAB1;
                payment_view_pager.setCurrentItem(0);
                break;
            case R.id.tab_servicios:
                changeBackTabs(v, MovementsTab.TAB2);
                //Direction direction = prevTAB == MovementsTab.TAB1 ? Direction.FORDWARD : Direction.BACK;
                //loadFragment(ServiciosCarouselFragment.newInstance(), direction, false);
                //prevTAB = MovementsTab.TAB2;
                payment_view_pager.setCurrentItem(1);
                break;
            case R.id.tab_envios:
                changeBackTabs(v, MovementsTab.TAB3);
                //loadFragment(EnviosCarouselFragment.newInstance(), Direction.FORDWARD, false);
                //prevTAB = MovementsTab.TAB3;
                payment_view_pager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    private void changeBackTabs(View v, MovementsTab TAB){
        botonRecargas.setBackground(ContextCompat.getDrawable(getContext(),
                TAB == MovementsTab.TAB1 ? R.drawable.tab_selected
                : TAB == MovementsTab.TAB2 ?  R.drawable.left_tab
                : TAB == MovementsTab.TAB3 ?   R.drawable.tab_unselected
                : 0));
        botonServicios.setBackground(ContextCompat.getDrawable(getContext(),
                TAB == MovementsTab.TAB1 ? R.drawable.right_tab
                        : TAB == MovementsTab.TAB2 ?  R.drawable.tab_selected
                        : TAB == MovementsTab.TAB3 ?   R.drawable.left_tab
                        : 0));
        botonEnvios.setBackground(ContextCompat.getDrawable(getContext(),
                TAB == MovementsTab.TAB1 ? R.drawable.tab_unselected
                        : TAB == MovementsTab.TAB2 ?  R.drawable.right_tab
                        : TAB == MovementsTab.TAB3 ?   R.drawable.tab_selected
                        : 0));
        bringViewToFront((RelativeLayout) v.getParent(), v.getId());
    }

    public void bringViewToFront(RelativeLayout parent, int id) {
        int childPosition;

        for (childPosition = 0; childPosition < parent.getChildCount() - 1; childPosition++) {
            if (parent.getChildAt(childPosition).getId() == id) {
                break;
            }
        }

        parent.getChildAt(childPosition).bringToFront();
    }


    @Override
    public boolean onDrag(View v, DragEvent event) {

        if (event.getAction() == DragEvent.ACTION_DROP) {
            Log.i(getTag(), String.valueOf(v.getId()));
        }
        return true;
    }
}
