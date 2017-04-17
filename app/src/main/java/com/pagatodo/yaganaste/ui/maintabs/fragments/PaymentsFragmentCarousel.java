package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsTabPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Jordan on 06/04/2017.
 */

public abstract class PaymentsFragmentCarousel extends GenericFragment {

    @BindView(R.id.carouselMain)
    Carousel carouselMain;
    @BindView(R.id.layoutCarouselMain)
    LinearLayout layoutCarouselMain;

    @BindView(R.id.txtLoadingServices)
    StyleTextView txtLoadingServices;

    Carousel.ImageAdapter mainImageAdapter = null;
    IPaymentsCarouselPresenter paymentsCarouselPresenter;
    PaymentsTabPresenter paymentsTabPresenter;

    MovementsTab current_tab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.current_tab = MovementsTab.valueOf(getArguments().getString("TAB"));
        paymentsCarouselPresenter = new PaymentsCarouselPresenter(this.current_tab);
        try {
            paymentsTabPresenter = ((PaymentsTabFragment) getParentFragment()).getPresenter();
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

    @Override
    public void onResume() {
        super.onResume();
        carouselMain.setOnDragCarouselListener(new CarouselAdapter.OnDragCarouselListener() {
            @Override
            public void onStarDrag(CarouselItem item) {
                Log.e(getTag(), "onStarDrag: " + item.getIndex() + ", ItemId: " + item.getIdComercio());
                paymentsTabPresenter.setCarouselItem(item);
            }
        });

        carouselMain.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselAdapter<?> parent, CarouselItem view, int position, long id) {

            }
        });
    }
}
