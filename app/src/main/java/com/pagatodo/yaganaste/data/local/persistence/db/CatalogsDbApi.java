package com.pagatodo.yaganaste.data.local.persistence.db;

import android.content.Context;

import com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract;
import com.pagatodo.yaganaste.data.local.persistence.db.dao.GenericDao;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.db.MontoComercio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 24/04/2017.
 */

public class CatalogsDbApi {
    private static GenericDao genericDao;

    public CatalogsDbApi(Context context) {
        genericDao = GenericDao.getNewInstance(context);
    }

    public static boolean isPaisesTableEmpty() {
        genericDao.open();
        boolean result = genericDao.isEmpty(Countries.class);
        genericDao.close();

        return result;
    }

    public static boolean isCatalogTableEmpty() {
        genericDao.open();
        boolean result = genericDao.isEmpty(ComercioResponse.class);
        genericDao.close();

        return result;
    }

    public static void insertPaises(List<Countries> paises) {
        genericDao.open();
        genericDao.deleteAll(Countries.class);

        for (Countries pais : paises) {
            genericDao.insert(pais);
        }
        genericDao.close();
    }

    public static void insertComercios(List<ComercioResponse> comercios) {
        genericDao.open();
        genericDao.deleteAll(ComercioResponse.class);
        genericDao.deleteAll(MontoComercio.class);
        int i;
        for (ComercioResponse comercio : comercios) {
            i = genericDao.insert(comercio);
            if (i != -1) {
                for (Double monto : comercio.getListaMontos()) {
                    MontoComercio montoComercio = new MontoComercio();
                    montoComercio.setIdComercio(comercio.getIdComercio());
                    montoComercio.setMonto(monto);
                    genericDao.insert(montoComercio);
                }
            }
        }

        genericDao.close();
    }

    public static void insertFavorites(List<DataFavoritos> favorites) {
        genericDao.open();
        genericDao.deleteAll(DataFavoritos.class);
        for (DataFavoritos favorite : favorites) {
            genericDao.insert(favorite);
        }
        genericDao.close();
    }

    public static void insertFavorite(DataFavoritos favorite) {
        genericDao.open();
        genericDao.insert(favorite);
        genericDao.close();
    }

    public static List<ComercioResponse> getComerciosList(int comercioType) {
        genericDao.open();
        List<ComercioResponse> comerciosRespose = new ArrayList<>();
        List<ComercioResponse> comercios = genericDao.getListByQueryOrderBy(ComercioResponse.class,
                DBContract.Comercios.ID_TIPO_COMERCIO + " = " + comercioType, DBContract.Comercios.ORDEN);

        for (ComercioResponse comercioResponse : comercios) {

            List<MontoComercio> montosComercio = genericDao.getListByQuery(MontoComercio.class,
                    DBContract.MontosComercio.ID_COMERCIO + " = '" +
                            comercioResponse.getIdComercio() + "'");

            List<Double> montos = new ArrayList<>();
            for (MontoComercio montoComercio : montosComercio) {
                montos.add(montoComercio.getMonto());
            }

            comercioResponse.setListaMontos(montos);

            comerciosRespose.add(comercioResponse);
        }

        genericDao.close();

        return comerciosRespose;
    }

    public static List<DataFavoritos> getFavoritesList(int comercioType) {
        genericDao.open();
        List<DataFavoritos> favorites = genericDao.getListByQueryOrderBy(DataFavoritos.class,
                DBContract.Favoritos.ID_TIPO_COMERCIO + " = " + comercioType, DBContract.Favoritos.ID_FAVORITO);
        genericDao.close();
        return favorites;
    }

    public static ArrayList<Countries> getPaisesList() {
        genericDao.open();
        ArrayList<Countries> paises = genericDao.getArrayListByQueryOrderBy(Countries.class, DBContract.Paises.ID, null);
        genericDao.close();

        return paises;
    }

    public static String getURLIconComercio(String nombreComercio) {
        genericDao.open();
        ComercioResponse comercioResponse = genericDao.getByQuery(ComercioResponse.class,
                DBContract.Comercios.COMERCIO + "= '" + nombreComercio + "'");

        genericDao.close();

        return comercioResponse != null ? comercioResponse.getLogoURL() : "";
    }
}
