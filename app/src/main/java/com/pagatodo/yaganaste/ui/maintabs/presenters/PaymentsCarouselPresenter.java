package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarFavoritosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.PaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselPresenter implements IPaymentsCarouselPresenter {
    MovementsTab current_tab;
    IPaymentsCarouselIteractor paymentsTabIteractor;
    PaymentsCarrouselManager paymentsManager;
    Context mContext;
    private CatalogsDbApi api;
    boolean showFavorite; //Sirve para saber si el presenter se manda a llamar desde el carrusel de Favoritos

    public PaymentsCarouselPresenter(MovementsTab current_tab,
                                     PaymentsCarrouselManager paymentsManager, Context context, boolean showFavorite) {
        this.current_tab = current_tab;
        this.paymentsManager = paymentsManager;
        this.mContext = context;
        this.api = new CatalogsDbApi(context);
        this.paymentsTabIteractor = new PaymentsCarouselIteractor(this, api);
        this.showFavorite = showFavorite;
    }

    @Override
    public MovementsTab getCurrenTab() {
        return this.current_tab;
    }

    @Override
    public ArrayList<CarouselItem> getCarouselArray() {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        CarouselItem ite = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        ite.setSearchImageViewMargin();
        carouselItems.add(0, ite);
        List<ComercioResponse> comercios = paymentsTabIteractor.getCatalogos().getData().getComercios();
        for (int i = 0; i < comercios.size(); i++) {
            ComercioResponse comercio = comercios.get(i);
            if (comercio.getIdTipoComercio() == current_tab.getId()) {
                if (comercio.getIdComercio() != 0) {
                    CarouselItem item;
                    if (comercio.getColorMarca().isEmpty()) {
                        item = new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio);
                        item.getmImage().setPadding(0, 0, 0, 0);
                        carouselItems.add(item);
                    } else {
                        item = new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio);
                        item.getmImage().setPadding(0, 0, 0, 0);
                        carouselItems.add(item);
                    }
                } else {
                    CarouselItem item = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio, null);
                    item.setSearchImageViewMargin();
                    carouselItems.add(item);

                }
            }
        }
        return carouselItems;
    }

    @Override
    public void getCarouselItems() {
        //Revisar que la tabla no esté
        if (api.isCatalogTableEmpty()) {
            paymentsTabIteractor.getCatalogosFromService();
        } else {
            paymentsTabIteractor.getCatalogosFromDB(current_tab.getId());
        }
    }

    @Override
    public void getFavoriteCarouselItems() {
        if (App.getInstance().getPrefs().loadDataBoolean(CONSULT_FAVORITE)) {
            paymentsTabIteractor.getFavoritesFromDB(current_tab.getId());
        } else {
            paymentsTabIteractor.getFavoritesFromService();
        }

    }

    @Override
    public void onErrorService() {
        paymentsManager.showErrorService();
    }

    @Override
    public void onEmptyListFavorites() {
        paymentsManager.onError(App.getContext().getString(R.string.empty_list_favorites));
    }

    @Override
    public void onSuccessWSObtenerCatalogos(DataSourceResult result) {
        ObtenerCatalogosResponse response = (ObtenerCatalogosResponse) result.getData();

        if (response.getCodigoRespuesta() == CODE_OK) {
            try {
                App.getInstance().getPrefs().saveData(StringConstants.CATALOG_VERSION, response.getData().getVersion());
                api.insertComercios(response.getData().getComercios());
                paymentsManager.setCarouselData(getCarouselItems(response.getData().getComercios()));
            } catch (Exception e) {
                e.printStackTrace();
                paymentsManager.showError();
            }
        } else {
            paymentsManager.showError();
        }
    }

    @Override
    public void onSuccessWSFavorites(DataSourceResult result) {
        ConsultarFavoritosResponse response = (ConsultarFavoritosResponse) result.getData();
        if (response.getCodigoRespuesta() == CODE_OK) {
            try {
                App.getInstance().getPrefs().saveDataBool(CONSULT_FAVORITE, true);
                if (response.getData().size() > 0) {
                    api.insertFavorites(response.getData());
                }
                paymentsTabIteractor.getFavoritesFromDB(current_tab.getId());
            } catch (Exception e) {
                e.printStackTrace();
                paymentsManager.showError();
            }
        } else {
            paymentsManager.showError();
        }
    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<ComercioResponse> comercios) {
        ArrayList<CarouselItem> items = getCarouselItems(comercios);
        paymentsManager.setCarouselData(items);
    }

    private CarouselItem createItemToAddFav() {
        //Se agrega un id en -1 en el constructor para hacer referencia a que el item responde a la accion de agregar favorito desde 0
        CarouselItem carouselItemAdd = new CarouselItem(App.getInstance(), R.mipmap.agregar_favorito, "#747E84", CarouselItem.DRAG, new ComercioResponse(-1), null);
        carouselItemAdd.setAddImageViewMargin();
        return carouselItemAdd;
    }

    @Override
    public void onSuccessDBFavorites(List<DataFavoritos> favoritos) {
        if (showFavorite) {
            paymentsManager.setCarouselData(getCarouselItemsFavoritos(favoritos));
        } else {
            paymentsManager.showFavorites();
        }
    }

    private ArrayList<CarouselItem> getCarouselItems(List<ComercioResponse> comercios) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();

        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        carouselItems.add(0, carouselItemSearch);
        for (ComercioResponse comercio : comercios) {
            if (comercio.getIdTipoComercio() == current_tab.getId()) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca().isEmpty()) {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), "#10B2E6", CarouselItem.DRAG, comercio));
                    } else {
                        carouselItems.add(new CarouselItem(App.getContext(), comercio.getLogoURL(), comercio.getColorMarca().toUpperCase(), CarouselItem.DRAG, comercio));
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

    private ArrayList<CarouselItem> getCarouselItemsFavoritos(List<DataFavoritos> favoritos) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        carouselItems.add(0, carouselItemSearch);
        carouselItems.add(1, createItemToAddFav());

        for (DataFavoritos favorito : favoritos) {
            if (favorito.getIdTipoComercio() == current_tab.getId()) {
                if (favorito.getIdComercio() != 0) {
                    if (favorito.getColorMarca().isEmpty()) {
                        if (favorito.getImagenURL().isEmpty()) {
                            carouselItems.add(new CarouselItem(App.getContext(), favorito.getImagenURLComercio(), "#10B2E6", CarouselItem.DRAG, favorito));
                        } else {
                            carouselItems.add(new CarouselItem(App.getContext(), favorito.getImagenURL(), "#10B2E6", CarouselItem.DRAG, favorito));
                        }
                    } else {
                        if (favorito.getImagenURL().isEmpty()) {
                            carouselItems.add(new CarouselItem(App.getContext(), favorito.getImagenURLComercio(), favorito.getColorMarca().toUpperCase(), CarouselItem.DRAG, favorito));
                        } else {
                            carouselItems.add(new CarouselItem(App.getContext(), favorito.getImagenURL(), favorito.getColorMarca().toUpperCase(), CarouselItem.DRAG, favorito));
                        }
                    }
                } else {
                    //carouselItems.add(new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, comercio));
                    CarouselItem carouselItemSearch2 = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, favorito);
                    carouselItemSearch2.setSearchImageViewMargin();
                    carouselItems.add(carouselItemSearch2);
                }
            }
        }

        int toAdd = 5 - carouselItems.size();
        for (int n = 0; n < toAdd; n++) {
            carouselItems.add(createItemToAddFav());
        }

        return carouselItems;
    }
}
