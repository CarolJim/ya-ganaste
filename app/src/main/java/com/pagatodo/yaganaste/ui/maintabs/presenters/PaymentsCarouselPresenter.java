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
import com.pagatodo.yaganaste.ui.maintabs.iteractors.PaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.PaymentsCarrouselManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IPaymentsCarouselPresenter;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_ENVIOS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselPresenter implements IPaymentsCarouselPresenter {
    int current_tab;
    IPaymentsCarouselIteractor paymentsTabIteractor;
    PaymentsCarrouselManager paymentsManager;
    Context mContext;
    private CatalogsDbApi api;
    boolean showFavorite; //Sirve para saber si el presenter se manda a llamar desde el carrusel de Favoritos

    public PaymentsCarouselPresenter(int current_tab,
                                     PaymentsCarrouselManager paymentsManager, Context context, boolean showFavorite) {
        this.current_tab = current_tab;
        this.paymentsManager = paymentsManager;
        this.mContext = context;
        this.api = new CatalogsDbApi(context);
        this.paymentsTabIteractor = new PaymentsCarouselIteractor(this, api);
        this.showFavorite = showFavorite;
    }

    /*@Override
    public MovementsTab getCurrenTab() {
        return this.current_tab;
    }*/

    /*@Override
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
    }*/

    @Override
    public void getCarouselItems() {
        //Revisar que la tabla no esté
        if (api.isCatalogTableEmpty()) {
            paymentsTabIteractor.getCatalogosFromService();
        } else {
            paymentsTabIteractor.getCatalogosFromDB(current_tab);
        }
    }

    @Override
    public void getFavoriteCarouselItems() {
        //if (App.getInstance().getPrefs().loadDataBoolean(CONSULT_FAVORITE, false)) {
        paymentsTabIteractor.getFavoritesFromService();
            /* paymentsTabIteractor.getFavoritesFromDB(current_tab.getId());
        } else {
            paymentsTabIteractor.getFavoritesFromService();
        }*/
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

                paymentsTabIteractor.getFavoritesFromDB(current_tab);
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
        items = orderList(items);
        paymentsManager.setCarouselData(items);
    }

    private ArrayList<CarouselItem> orderList(ArrayList<CarouselItem> originalList) {
        ArrayList<Integer> orderBy = new ArrayList<>();
        ArrayList<CarouselItem> finalList = new ArrayList<>();

        /**
         * Agregamos la lupa a la primera posicion y eliminamos de nuestro arreglo
         */
        finalList.add(originalList.get(0));
        originalList.remove(0);

        /**
         * Agregamos a nuestro OrderKey la forma en que necesitamos los elementos
         */
        switch (current_tab) {
            case 1:
                orderBy.add(18);
                orderBy.add(7);
                orderBy.add(12);
                orderBy.add(8);
                orderBy.add(22);
                orderBy.add(13);
                orderBy.add(28);
                orderBy.add(23);
                orderBy.add(26);
                break;
            case 2:
                orderBy.add(4);
                orderBy.add(21);
                orderBy.add(20);
                orderBy.add(6);
                orderBy.add(3);
                orderBy.add(17);
                orderBy.add(25);
                orderBy.add(27);
                orderBy.add(2);
                break;
            case 3:
                orderBy.add(8609);
                orderBy.add(785);
                orderBy.add(779);
                orderBy.add(787);
                orderBy.add(809);
                orderBy.add(790);
                orderBy.add(799);
                orderBy.add(796);
                orderBy.add(832);
                break;
        }

        /**
         * Buscamos en nuestro orderBy cada elemento en un ciclo adicional de originalList, si el ID existe
         * lo agregamos a nuesta finalList. Y eliminamos ese elemnto de originalList
         */
        for (Integer miList : orderBy) {
            for (int x = 0; x < originalList.size(); x++) {
                if (originalList.get(x).getComercio().getIdComercio() == miList) {
                    finalList.add(originalList.get(x));
                    originalList.remove(x);
                }
            }
        }

        /**
         * Hacemos un ajuste adicional para mostrar la lupa en la posicion del nuevo cambio. El item
         * pos. 9 debe de pasar a pos 0, y el resto en las posiciones posteriores
         */
        ArrayList<CarouselItem> auxList = new ArrayList<>();
        int sizeCarousel;
        if (finalList.size() > 9) {
            sizeCarousel = 9;
        } else {
            sizeCarousel = finalList.size() - 1;
        }
        auxList.add(finalList.get(sizeCarousel));
        for (int x = 0; x < sizeCarousel; x++) {
            auxList.add(finalList.get(x));
        }
        // Igualamos la lista auxiliar ordenada para que sea nuestra nueva finalList
        finalList = auxList;
        /**
         * Terminado el proceso anterior, tomamos el resto de la originalList y lo agregamos a nuestra
         * finalList
         */
        for (int x = 0; x < originalList.size(); x++) {
            finalList.add(originalList.get(x));
        }


        return finalList;

        /**
         * Acomodos de referencia
         Telcel 18
         Telcel Datos 28
         Movistar  12
         AT&T 8
         Unefon 22
         Nextel 13
         Virgin 23
         ALÓ 26
         IAVE/Pase Urbano 7

         Avon 2
         CFE 4
         Cablemás 27
         Cablevision 25
         Sky 17
         Gas Natural 6
         IZZI 3
         Telmex c/recibo 20
         Telmex s/recibo 21

         Ya Ganaste 8609
         BBVA Bancomer 785
         Banamex 779
         HSBC 790
         Inbursa 796
         Santander 787
         ScotiaBank Inverlat 799
         American Exppress 814
         Banorte/IXE 809
         */
    }

    private CarouselItem createItemToAddFav() {
        //Se agrega un id en -1 en el constructor para hacer referencia a que el item responde a la accion de agregar favorito desde 0
        CarouselItem carouselItemAdd = new CarouselItem(App.getInstance(), R.drawable.new_fav_add, "#747E84", CarouselItem.DRAG, new ComercioResponse(-1), null);
        carouselItemAdd.setAddImageViewMargin();
        return carouselItemAdd;
    }

    @Override
    public void onSuccessDBFavorites(List<DataFavoritos> favoritos) {
        if (showFavorite) {
            paymentsManager.setCarouselData(getCarouselItemsFavoritos(favoritos));
        } else {
            // paymentsManager.showFavorites();
            paymentsManager.setFavolist(favoritos);
        }
    }

    private ArrayList<CarouselItem> getCarouselItems(List<ComercioResponse> comercios) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();

        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), current_tab != PAYMENT_ENVIOS ? R.drawable.new_fav_search : R.drawable.new_fav_add,
                current_tab != PAYMENT_ENVIOS ? "#FFFFFF" : "#808080", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        carouselItems.add(0, carouselItemSearch);
        CarouselItem carouselItemCommerce;
        for (ComercioResponse comercio : comercios) {
            if (comercio.getIdTipoComercio() == current_tab) {
                if (comercio.getIdComercio() != 0) {
                    if (comercio.getColorMarca() == null || comercio.getColorMarca().isEmpty()) {
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

    private ArrayList<CarouselItem> getCarouselItemsFavoritos(List<DataFavoritos> favoritos) {
        ArrayList<CarouselItem> carouselItems = new ArrayList<>();
        CarouselItem carouselItemSearch = new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null, null);
        carouselItemSearch.setSearchImageViewMargin();

        //carouselItems.add(0, new CarouselItem(App.getContext(), R.mipmap.buscar_con_texto, "#FFFFFF", CarouselItem.CLICK, null));
        if (current_tab != PAYMENT_ENVIOS) {
            carouselItems.add(0, carouselItemSearch);
        } else {
            carouselItems.add(0, createItemToAddFav());
        }

        for (DataFavoritos favorito : favoritos) {
            if (favorito.getIdTipoComercio() == current_tab) {
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

        if(current_tab!=PAYMENT_ENVIOS) {
            int toAdd = 8 - carouselItems.size();
            for (int n = 0; n < toAdd; n++) {
                carouselItems.add(createItemToAddFav());
            }
        }

        return carouselItems;
    }
}
