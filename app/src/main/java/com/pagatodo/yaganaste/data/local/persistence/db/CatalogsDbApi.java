package com.pagatodo.yaganaste.data.local.persistence.db;

import android.content.Context;

import com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract;
import com.pagatodo.yaganaste.data.local.persistence.db.dao.GenericDao;
import com.pagatodo.yaganaste.data.model.db.MontoComercio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

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

    public static boolean isCatalogTableEmpty() {
        genericDao.open();
        boolean result = genericDao.isEmpty(ComercioResponse.class);
        genericDao.close();

        return result;
    }

    public static void insertComercios(List<ComercioResponse> comercios) {
        genericDao.open();
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

    public static List<ComercioResponse> getComerciosList(int comercioType) {
        genericDao.open();
        List<ComercioResponse> comerciosRespose = new ArrayList<>();
        List<ComercioResponse> comercios = genericDao.getListByQuery(ComercioResponse.class,
                DBContract.Comercios.ID_TIPO_COMERCIO + " = " + comercioType);

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
}
