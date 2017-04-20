package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.PaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselPresenter implements IPaymentsCarouselPresenter {
    MovementsTab current_tab;
    IPaymentsCarouselIteractor paymentsTabIteractor;

    public PaymentsCarouselPresenter(MovementsTab current_tab) {
        this.current_tab = current_tab;
        this.paymentsTabIteractor = new PaymentsCarouselIteractor();
    }

    @Override
    public ArrayList<CarouselItem> getCarouselItems() {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        List<ComercioResponse> comercios = paymentsTabIteractor.getCatalogos().getData().getComercios();
        for (int i = 0; i < comercios.size(); i++) {
            ComercioResponse comercio = comercios.get(i);
            if (comercio.getIdTipoComercio() == current_tab.getId()) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio));
                    } else {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio));
                    }
                } else {
                    carouselItems.add(new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio));
                }
            }
        }
        return carouselItems;
    }
}
