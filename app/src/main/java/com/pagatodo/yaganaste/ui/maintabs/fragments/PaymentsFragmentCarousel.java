package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;
import com.pagatodo.yaganaste.utils.customviews.carousel.Carousel;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselAdapter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Jordan on 06/04/2017.
 */

public abstract class PaymentsFragmentCarousel extends GenericFragment {

    ObtenerCatalogosResponse catalogos;

    @BindView(R.id.txtLoadingServices)
    StyleTextView txtLoadingServices;

    Carousel.ImageAdapter mainImageAdapter;
    Carousel carouselMain;
    MovementsTab current_tab;

    protected void loadCatalogos() {
        Gson gson = new Gson();
        String catalogosJSONStringResponse = Utils.getJSONStringFromAssets(getContext(), "files/catalogos.json");
        catalogos = gson.fromJson(catalogosJSONStringResponse, ObtenerCatalogosResponse.class);
        setCarouselAdapter();
    }

    protected void setCarouselAdapter() {
        txtLoadingServices.setVisibility(View.GONE);
        mainImageAdapter = new Carousel.ImageAdapter(getContext(), getCarouselItems());
        carouselMain.setAdapter(mainImageAdapter);
        carouselMain.setSelection(2, false);
    }

    private ArrayList<CarouselItem> getCarouselItems() {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(new CarouselItem(getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK));
        List<ComercioResponse> comercios = catalogos.getData().getComercios();
        for (int i = 0; i < comercios.size(); i++) {
            ComercioResponse comercio = comercios.get(i);
            if (comercio.getIdTipoComercio() == current_tab.getId()) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItems.add(new CarouselItem(getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG));
                    } else {
                        carouselItems.add(new CarouselItem(getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG));
                    }
                } else {
                    carouselItems.add(new CarouselItem(getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK));
                }
            }
        }
        return carouselItems;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.carouselMain.setSelection(2, false);
        carouselMain.setOnDragCarouselListener(new CarouselAdapter.OnDragCarouselListener() {
            @Override
            public void onStarDrag(CarouselItem item) {
                Log.e(getTag(), "onStarDrag: " + item.getIndex());
            }
        });

        carouselMain.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselAdapter<?> parent, CarouselItem view, int position, long id) {

            }
        });
    }

}
