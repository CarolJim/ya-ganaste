package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.manager.AddNewFavoritesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ListDialog;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.pagatodo.yaganaste.utils.customviews.carousel.CustomCarouselItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;

/**
 * Created by Jordan on 06/04/2017.
 */

public abstract class PaymentsFragmentCarousel extends GenericFragment implements PaymentsCarrouselManager {

    private static int MAX_CAROUSEL_ITEMS = 12;
    @BindView(R.id.carouselMain)
    Carousel carouselMain;
    @BindView(R.id.layoutCarouselMain)
    LinearLayout layoutCarouselMain;
    @BindView(R.id.txtLoadingServices)
    StyleTextView txtLoadingServices;
    @BindView(R.id.imgPagosFavs)
    ImageView imgPagosFavs;
    View rootView;
    Carousel.ImageAdapter mainImageAdapter = null;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    PaymentsTabPresenter paymentsTabPresenter;
    PaymentsTabFragment fragment;
    ListDialog dialog;
    MovementsTab current_tab;
    boolean isFromClick = false, showFavorites = false;
    ArrayList<CustomCarouselItem> backUpResponse;

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
        backUpResponse = new ArrayList<>();
    }

    public void setCarouselAdapter(ArrayList<CarouselItem> items) {
        txtLoadingServices.setVisibility(View.GONE);
        mainImageAdapter = new Carousel.ImageAdapter(getContext(), items);
        carouselMain.setAdapter(mainImageAdapter);
        carouselMain.setSelection(items.size() > 4 ? 2 : 0, false);
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
                    paymentsCarouselPresenter.getFavoriteCarouselItems();
                } else {
                    showFavorites = false;
                    paymentsCarouselPresenter.getCarouselItems();
                }
            }
        });
    }

    /**
     * Mandamos a AddNewFavoritesActivity un ArrayList<CustomCarouselItem>, el objeto es un Parceable
     * para que pueda viajar sin problemas entre actividades. Enviamos tambien el Tab que estamos
     * visualizando para mostrar o no algunos campos
     */
    @OnClick(R.id.paymentTabBtn)
    public void addNewFavorite() {
        //   Toast.makeText(getContext(), "Tab " + current_tab, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), AddNewFavoritesActivity.class);
        intent.putExtra("backUpResponse", backUpResponse);
        intent.putExtra("currentTab", current_tab);
        startActivity(intent);
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
                if (((CarouselItem)mainImageAdapter.getItem(position)).getComercio() == null) {
                    //ListDialog dialog = new ListDialog(getContext(), paymentsCarouselPresenter.getCarouselArray(), paymentsTabPresenter, fragment);
                    isFromClick = true;
                    paymentsCarouselPresenter.getCarouselItems();
                } else if (((CarouselItem)mainImageAdapter.getItem(position)).getComercio().getIdComercio() != 0) {
                    paymentsTabPresenter.setCarouselItem((CarouselItem) mainImageAdapter.getItem(position));
                    fragment.onItemSelected();
                }
            }
        });
    }

    @Override
    public void showError() {
        //Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        layoutCarouselMain.setVisibility(View.GONE);
        txtLoadingServices.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorService() {
        UI.createSimpleCustomDialog("", getString(R.string.error_respuesta), getActivity().getSupportFragmentManager(), getFragmentTag());
    }

    @Override
    public void onSuccess(Double monto) {

    }

    @Override
    public void onError(String error) {
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
            for (int pos = response.size() - 1 ; pos > 0 ; pos--) {
                currentItem = response.get(pos);
                if (currentItem.getComercio() == null || currentItem.getComercio().getIdComercio() == 0) {
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
                setBackUpResponse(subList);
                setCarouselAdapter(subList);
            } else {
                setBackUpResponse(response);
                setCarouselAdapter(response);
            }
        }
    }

    /**
     * Tomamos la lista ArrayList<CarouselItem> para solo obtener los datod que necesitamos, y los
     * guardamos en objetos de tipo CustomCarouselItem mas "ligeros" para viajar entre actividades
     * @param mResponse
     */
    private void setBackUpResponse(ArrayList<CarouselItem> mResponse) {
        for (CarouselItem carouselItem : mResponse) {
            if (carouselItem.getComercio() != null) {
                backUpResponse.add(new CustomCarouselItem(
                        carouselItem.getComercio().getIdComercio(),
                        carouselItem.getComercio().getIdTipoComercio(),
                        carouselItem.getComercio().getNombreComercio()
                ));
            }
        }
    }

    /*@Override
    public void onStop() {
        super.onStop();
        showFavorites=false;
        paymentsCarouselPresenter.getCarouselItems();
    }*/
}
