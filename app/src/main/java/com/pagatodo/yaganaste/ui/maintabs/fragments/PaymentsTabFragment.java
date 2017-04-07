package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ViewPagerData;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.controlles.TabsView;
import com.pagatodo.yaganaste.utils.customviews.NoSwipeViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 06/04/2017.
 */

public class PaymentsTabFragment extends GenericFragment implements View.OnClickListener {

    private View rootView;

    @BindView(R.id.child_framelayout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_recargas)
    Button botonRecargas;
    @BindView(R.id.tab_servicios)
    Button botonServicios;
    @BindView(R.id.tab_envios)
    Button botonEnvios;

    private FragmentManager fragmentManager;


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

        fragmentManager = getFragmentManager();

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);
        int widthButton = dm.widthPixels / 3;

        botonRecargas.setWidth(widthButton);
        botonServicios.setWidth(widthButton);
        botonEnvios.setWidth(widthButton);

        botonRecargas.setOnClickListener(this);
        botonServicios.setOnClickListener(this);
        botonEnvios.setOnClickListener(this);

        loadFragment(PaymentsFragmentCarousel.newInstance(), SupportFragmentActivity.DIRECTION.NONE, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_recargas:
                botonRecargas.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tab_selected));
                botonServicios.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_tab));
                botonEnvios.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tab_unselected));
                bringViewToFront((RelativeLayout) v.getParent(), v.getId());

                break;
            case R.id.tab_servicios:
                botonRecargas.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_tab));
                botonServicios.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tab_selected));
                botonEnvios.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_tab));
                bringViewToFront((RelativeLayout) v.getParent(), v.getId());
                break;
            case R.id.tab_envios:
                botonRecargas.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tab_unselected));
                botonServicios.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_tab));
                botonEnvios.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tab_selected));
                bringViewToFront((RelativeLayout) v.getParent(), v.getId());
                break;
            default:
                break;
        }
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

    protected void loadFragment(@NonNull GenericFragment fragment, @NonNull SupportFragmentActivity.DIRECTION direction,
                                boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!direction.equals(SupportFragmentActivity.DIRECTION.NONE)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation());
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.child_framelayout, fragment, fragment.getFragmentTag()).commit();
    }
}
