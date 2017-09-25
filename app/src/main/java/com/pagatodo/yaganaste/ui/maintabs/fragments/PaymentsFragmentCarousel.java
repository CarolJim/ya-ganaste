package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.ListDialog;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * Created by Jordan on 06/04/2017.
 */

public abstract class PaymentsFragmentCarousel extends GenericFragment implements PaymentsCarrouselManager {

    private static int MAX_CAROUSEL_ITEMS = 12;
    @BindView(R.id.carouselMain)
    Carousel carouselMain;
    /* @BindView(R.id.carouselFavorite)
     Carousel carouselFavorite;*/
    @BindView(R.id.layoutCarouselMain)
    LinearLayout layoutCarouselMain;
    @BindView(R.id.txtLoadingServices)
    StyleTextView txtLoadingServices;
    @BindView(R.id.imgPagosFavs)
    ImageView imgPagosFavs;
    View rootView;
    Carousel.ImageAdapter mainImageAdapter = null, favoriteImageAdapter = null;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    PaymentsTabPresenter paymentsTabPresenter;
    PaymentsTabFragment fragment;
    ListDialog dialog;
    MovementsTab current_tab;
    boolean isFromClick = false, showFavorites = false;
    //private int itemsMain = 0, itemsFav = 0;

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
        if (!showFavorites) {
            /*if (itemsMain != items.size()) {
                itemsMain = items.size();*/
            mainImageAdapter = new Carousel.ImageAdapter(getContext(), items);
            carouselMain.setAdapter(mainImageAdapter);
            carouselMain.setSelection(items.size() > 4 ? 2 : 0, false);
            //}
        } else {
            /*if (itemsFav != items.size()) {
                itemsFav = items.size();*/
            favoriteImageAdapter = new Carousel.ImageAdapter(getContext(), items);
            carouselMain.setAdapter(favoriteImageAdapter);
            carouselMain.setSelection(items.size() > 4 ? 2 : 0, false);
                /*carouselMain.setVisibility(View.GONE);
                carouselFavorite.setVisibility(View.VISIBLE);
            }*/
        }
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
        paymentsCarouselPresenter.getCarouselItems();
        txtLoadingServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentsCarouselPresenter.getCarouselItems();
            }
        });
        imgPagosFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showFavorites) {
                    showFavorites = true;
                    onEventListener.onEvent(EVENT_SHOW_LOADER, getString(R.string.synch_favorites));
                    if (Utils.isDeviceOnline()) {
                        paymentsCarouselPresenter.getFavoriteCarouselItems();
                    } else {
                        showErrorService();
                    }
                } else {
                    showFavorites = false;
                    paymentsCarouselPresenter.getCarouselItems();
                    /*carouselMain.setVisibility(View.VISIBLE);
                    carouselFavorite.setVisibility(View.GONE);*/
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        carouselMain.setOnDragCarouselListener(new CarouselAdapter.OnDragCarouselListener() {
            @Override
            public void onStarDrag(CarouselItem item) {
                //Log.e(getTag(), "onStarDrag: " + item.getIndex() + ", ItemId: " + item.getComercio().getIdComercio());
                paymentsTabPresenter.setCarouselItem(item);
            }
        });
        /*carouselFavorite.setOnDragCarouselListener(new CarouselAdapter.OnDragCarouselListener() {
            @Override
            public void onStarDrag(CarouselItem item) {
                Log.e(getTag(), "onStarDrag: " + item.getIndex() + ", ItemId: " + item.getFavoritos().getIdComercio());
                paymentsTabPresenter.setCarouselItem(item);
            }
        });*/

        carouselMain.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselAdapter<?> parent, CarouselItem view, int position, long id) {
                if (!showFavorites) {
                    if (((CarouselItem) mainImageAdapter.getItem(position)).getComercio() == null) {
                        //ListDialog dialog = new ListDialog(getContext(), paymentsCarouselPresenter.getCarouselArray(), paymentsTabPresenter, fragment);
                        isFromClick = true;
                        paymentsCarouselPresenter.getCarouselItems();
                    } else if (((CarouselItem) mainImageAdapter.getItem(position)).getComercio().getIdComercio() != 0) {
                        paymentsTabPresenter.setCarouselItem((CarouselItem) mainImageAdapter.getItem(position));
                        fragment.onItemSelected();
                    }
                } else {
                    if (((CarouselItem) favoriteImageAdapter.getItem(position)).getFavoritos() == null) {
                        //ListDialog dialog = new ListDialog(getContext(), paymentsCarouselPresenter.getCarouselArray(), paymentsTabPresenter, fragment);
                        isFromClick = true;
                        paymentsCarouselPresenter.getFavoriteCarouselItems();
                    } else if (((CarouselItem) favoriteImageAdapter.getItem(position)).getFavoritos().getIdComercio() != 0) {
                        paymentsTabPresenter.setCarouselItem((CarouselItem) favoriteImageAdapter.getItem(position));
                        fragment.onItemSelected();
                    }
                }
            }
        });

       /* carouselFavorite.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselAdapter<?> parent, CarouselItem view, int position, long id) {
                if (position == 0) {
                    //ListDialog dialog = new ListDialog(getContext(), paymentsCarouselPresenter.getCarouselArray(), paymentsTabPresenter, fragment);
                    isFromClick = true;
                    paymentsCarouselPresenter.getCarouselItems();
                } else {
                    paymentsTabPresenter.setCarouselItem((CarouselItem) favoriteImageAdapter.getItem(position));
                    fragment.onItemSelected();
                }
            }
        });*/
    }

    @Override
    public void showError() {
        //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        layoutCarouselMain.setVisibility(View.GONE);
        txtLoadingServices.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorService() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        UI.createSimpleCustomDialog("", getString(R.string.error_respuesta), getActivity().getSupportFragmentManager(), getFragmentTag());
    }

    @Override
    public void onSuccess(Double monto) {

    }

    @Override
    public void onError(String error) {
        showFavorites = false;
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        UI.createSimpleCustomDialog("", error, getActivity().getSupportFragmentManager(), getFragmentTag());
    }

    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        onEventListener.onEvent(EVENT_HIDE_LOADER, "");
        layoutCarouselMain.setVisibility(View.VISIBLE);
        txtLoadingServices.setVisibility(View.GONE);
        if (isFromClick) {
            CarouselItem currentItem;
            for (int pos = response.size() - 1; pos > -1; pos--) {
                currentItem = response.get(pos);
                if (!showFavorites && (currentItem.getComercio() == null || currentItem.getComercio().getIdComercio() == 0)) {
                    response.remove(pos);
                } else if (showFavorites && (currentItem.getFavoritos() == null || currentItem.getFavoritos().getIdComercio() == 0)) {
                    response.remove(pos);
                }
            }
            Collections.sort(response, new Comparator<CarouselItem>() {
                @Override
                public int compare(CarouselItem o1, CarouselItem o2) {
                    if (o1.getComercio() != null) {
                        return o1.getComercio().getNombreComercio().compareToIgnoreCase(o2.getComercio().getNombreComercio());
                    } else {
                        return o1.getFavoritos().getNombreComercio().compareToIgnoreCase(o2.getFavoritos().getNombreComercio());
                    }
                }
            });
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
