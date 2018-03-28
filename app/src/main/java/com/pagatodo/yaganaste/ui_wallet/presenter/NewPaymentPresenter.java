package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarFavoritosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui_wallet.fragments.NewPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.NewPaymentInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentPresenter;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentPresenter implements INewPaymentPresenter {
    MovementsTab current_tab;
    NewPaymentFragment mView;
    INewPaymentInteractor mInteractor;
    Context mContext;
    private int typeData;
    private int typeDataFav;

    public NewPaymentPresenter(NewPaymentFragment mView, Context context) {
        this.mView = mView;
        mInteractor = new NewPaymentInteractor(this);
        this.mContext = context;
    }

    @Override
    public void getCarriersItems(int typeData) {
        this.typeData = typeData;
        try {
            if (new DatabaseManager().isComerciosEmpty()) {
                mInteractor.getCatalogosRecargarFromService();
            } else {
                mInteractor.getCatalogosFromDB(typeData);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFavoritesItems(int typeDataFav) {
        this.typeDataFav = typeDataFav;
        mInteractor.getFavoritesFromService(typeDataFav);
    }

    @Override
    public void onSuccessWSFavorites(DataSourceResult result, int typeDataFav) {
        ConsultarFavoritosResponse response = (ConsultarFavoritosResponse) result.getData();
        if (response.getCodigoRespuesta() == CODE_OK) {
            try {
                if (response.getData().size() > 0) {
                    new DatabaseManager().insertListFavorites(response.getData());
                }
                mInteractor.getFavoritesFromDB(typeDataFav);
            } catch (Exception e) {
                e.printStackTrace();
                //   mView.showError();
            }
        } else {
            // mView.showError();
        }
    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<Comercio> comercios) {
        mView.setCarouselData(comercios, typeData);
    }

    @Override
    public void onSuccessDBFavorites(List<Favoritos> catalogos) {
        mView.setDataFavorite(catalogos, typeDataFav);


//        if (showFavorite) {
//            paymentsManager.setCarouselData(getCarouselItemsFavoritos(favoritos));
//
//        } else {
//            paymentsManager.showFavorites();
//        }
    }

    @Override
    public void sendChoiceCarrier(Comercio mComercio, int mType) {
        mView.sendCarrierToView(mComercio, mType);
    }

    @Override
    public void sendChoiceFavorite(Favoritos favoritos, int mType) {
        mView.sendFavoriteToView(favoritos, mType);
    }

    @Override
    public void onFail(DataSourceResult error) {
        mView.errorFail(error);
    }

    @Override
    public void onErrorService() {
        mView.errorService();
    }


    /**
     * METODO AUXILIARES
     */
    private ArrayList<CarouselItem> getCarouselItems(List<Comercio> comercios) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();

        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        carouselItems.add(0, carouselItemSearch);
        CarouselItem carouselItemCommerce;
        for (Comercio comercio : comercios) {
            if (comercio.getIdTipoComercio() == typeData) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItemCommerce = new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio);
                        carouselItemCommerce.setCommerceImageViewMargin();
                        carouselItems.add(carouselItemCommerce);
                    } else {
                        carouselItemCommerce = new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio);
                        carouselItemCommerce.setCommerceImageViewMargin();
                        carouselItems.add(carouselItemCommerce);
                    }
                } else {
                    //carouselItems.add(new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio));
                    CarouselItem carouselItemSearch2 = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio, null);
                    carouselItemSearch2.setSearchImageViewMargin();
                    carouselItems.add(carouselItemSearch2);
                }
            }
        }
        return carouselItems;
    }
}
