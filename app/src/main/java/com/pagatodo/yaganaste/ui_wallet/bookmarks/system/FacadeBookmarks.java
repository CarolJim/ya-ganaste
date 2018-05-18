package com.pagatodo.yaganaste.ui_wallet.bookmarks.system;

import android.content.Context;

import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.PaymentsCarouselPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class FacadeBookmarks implements PaymentsCarrouselManager  {

    private IPaymentsCarouselPresenter paymentsCarouselPresenter;
    private ListenerFavorite listener;

    public FacadeBookmarks(Context context,ListenerFavorite listener){
        this.listener = listener;
        this.paymentsCarouselPresenter = new PaymentsCarouselPresenter(Constants.PAYMENT_ENVIOS, this, context, false);

    }

    public void getFavorites(){
        this.paymentsCarouselPresenter.getCarouselItems();
        this.paymentsCarouselPresenter.getFavoriteCarouselItems();
    }


    @Override
    public void setCarouselData(ArrayList<CarouselItem> response) {
        this.listener.setCarouselData(response);
    }

    @Override
    public void setDataBank(String idcomercio, String nombrebank) {

    }

    @Override
    public void errorgetdatabank() {

    }

    @Override
    public void setCarouselDataFavoritos(ArrayList<CarouselItem> response) {

    }

    @Override
    public void setFavolist(List<Favoritos> lista) {
        this.listener.setFavoList(lista);
    }

    @Override
    public void showErrorService() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Double importe) {

    }

    public interface ListenerFavorite{
        void setFavoList(List<Favoritos> lista);
        //void setCarouselDataFavoritos(ArrayList<CarouselItem> response);
        void setCarouselData(ArrayList<CarouselItem> response);
    }

}
