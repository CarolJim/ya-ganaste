package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.customviews.ListDialog;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jordan on 06/04/2017.
 */

public abstract class PaymentsFragmentCarousel extends GenericFragment implements PaymentsCarrouselManager {

    @BindView(R.id.carouselMain)
    Carousel carouselMain;
    @BindView(R.id.layoutCarouselMain)
    LinearLayout layoutCarouselMain;

    @BindView(R.id.txtLoadingServices)
    StyleTextView txtLoadingServices;

    View rootView;

    Carousel.ImageAdapter mainImageAdapter = null;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    PaymentsTabPresenter paymentsTabPresenter;
    PaymentsTabFragment fragment;
    ListDialog dialog;

    MovementsTab current_tab;

    boolean isFromClick = false;

    private static int MAX_CAROUSEL_ITEMS = 18;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.current_tab = MovementsTab.valueOf(getArguments().getString("TAB"));
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab, this, getContext());
        try {
            fragment = (PaymentsTabFragment) getParentFragment();
            paymentsTabPresenter = fragment.getPresenter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCarouselAdapter(ArrayList<CarouselItem> items) {
        txtLoadingServices.setVisibility(View.GONE);
        mainImageAdapter = new Carousel.ImageAdapter(getContext(), items);
        carouselMain.setAdapter(mainImageAdapter);
        carouselMain.setSelection(2, false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pagos_carousel, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        layoutCarouselMain.setVisibility(View.VISIBLE);
        paymentsCarouselPresenter.getCarouselItems();
        //setCarouselAdapter(this.paymentsCarouselPresenter.getCarouselArray());

    }

    @Override
    public void onResume() {
        super.onResume();
        carouselMain.setOnDragCarouselListener(new CarouselAdapter.OnDragCarouselListener() {
            @Override
            public void onStarDrag(CarouselItem item) {
                Log.e(getTag(), "onStarDrag: " + item.getIndex() + ", ItemId: " + item.getComercio().getIdComercio());
                paymentsTabPresenter.setCarouselItem(item);
            }
        });

        carouselMain.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselAdapter<?> parent, CarouselItem view, int position, long id) {
                if (position == 0) {
                    //ListDialog dialog = new ListDialog(getContext(), paymentsCarouselPresenter.getCarouselArray(), paymentsTabPresenter, fragment);
                    isFromClick = true;
                    paymentsCarouselPresenter.getCarouselItems();
                }
            }
        });
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        if (isFromClick) {
            dialog = new ListDialog(getContext(), response, paymentsTabPresenter, fragment);
            dialog.show();
            isFromClick = false;
        } else {
            if (response.size() > MAX_CAROUSEL_ITEMS) {
                ArrayList<CarouselItem> subList = new ArrayList<>(response.subList(0, MAX_CAROUSEL_ITEMS));
                setCarouselAdapter(subList);
            } else {
                setCarouselAdapter(response);
            }
        }

    }
}
