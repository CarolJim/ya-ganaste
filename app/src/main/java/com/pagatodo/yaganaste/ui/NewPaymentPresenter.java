package com.pagatodo.yaganaste.ui;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarFavoritosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentPresenter implements INewPaymentPresenter {
    MovementsTab current_tab;
    NewPaymentFragment mView;
    INewPaymentInteractor mInteractor;
    Context mContext;
    private CatalogsDbApi api;
    private int typeData;
    private int typeDataFav;

    public NewPaymentPresenter(NewPaymentFragment mView, Context context) {
        this.mView = mView;
        mInteractor = new NewPaymentInteractor(this);
        this.mContext = context;
        this.api = new CatalogsDbApi(context);
    }

    @Override
    public void getCarriersItems(int typeData) {
        this.typeData = typeData;
        //Revisar que la tabla no esté
        if (api.isCatalogTableEmpty()) {
            mInteractor.getCatalogosRecargarFromService();
        } else {
            mInteractor.getCatalogosFromDB(typeData);
        }
    }

    @Override
    public void getFavoritesItems(int typeDataFav) {
        this.typeDataFav = typeDataFav;
        if (App.getInstance().getPrefs().loadDataBoolean(CONSULT_FAVORITE, false)) {
            mInteractor.getFavoritesFromService(typeDataFav);
            // paymentsTabIteractor.getFavoritesFromDB(current_tab.getId());
        } else {
            mInteractor.getFavoritesFromService(typeDataFav);
        }
    }

    @Override
    public void onSuccessWSFavorites(DataSourceResult result, int typeDataFav) {
        ConsultarFavoritosResponse response = (ConsultarFavoritosResponse) result.getData();
        if (response.getCodigoRespuesta() == CODE_OK) {
            try {
                App.getInstance().getPrefs().saveDataBool(CONSULT_FAVORITE, true);
                if (response.getData().size() > 0) {
                    api.insertFavorites(response.getData());
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
    public void resToPresenter() {
        mView.resToView();
    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<ComercioResponse> comercios) {
        mView.setCarouselData(comercios, typeData);
    }

    @Override
    public void onSuccessDBFavorites(List<DataFavoritos> catalogos) {
        mView.setDataFavorite(catalogos, typeDataFav);


//        if (showFavorite) {
//            paymentsManager.setCarouselData(getCarouselItemsFavoritos(favoritos));
//
//        } else {
//            paymentsManager.showFavorites();
//        }
    }

    @Override
    public void onErrorService() {

    }


    /**
     * METODO AUXILIARES
     */
    private ArrayList<CarouselItem> getCarouselItems(List<ComercioResponse> comercios) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();

        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        carouselItems.add(0, carouselItemSearch);
        CarouselItem carouselItemCommerce;
        for (ComercioResponse comercio : comercios) {
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
