package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.LocalizarSucursalesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.LocalizarSucursalesResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IDepositMapInteractor;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IDepositMapPresenter;

import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 23/05/2017.
 */

public class DepositMapInteractor implements IDepositMapInteractor, IRequestResult {

    IDepositMapPresenter depositMapPresenter;

    String v = "[{\"Direccion1\":\"Periférico Blvd. Manuel Ávila Camacho 66\",\"Direccion2\":\"Lomas de Chapultepec V Secc, Miguel Hidalgo, 11000\",\"Horario\":\"8:30-19:00\",\"Latitud\":19.430567,\"Longitud\":-99.206999,\"Nombre\":\"Lomas Plaza\",\"NumTelefonico\":\"55 5249 5000\"},{\"Direccion1\":\"Periférico Blvd. Manuel Ávila Camacho 66\",\"Direccion2\":\"Lomas de Chapultepec V Secc, Miguel Hidalgo, 11000\",\"Horario\":\"8:30-19:00\",\"Latitud\":19.435104,\"Longitud\":-99.203169,\"Nombre\":\"Place 2\",\"NumTelefonico\":\"55 5249 5000\"},{\"Direccion1\":\"Periférico Blvd. Manuel Ávila Camacho 66\",\"Direccion2\":\"Lomas de Chapultepec V Secc, Miguel Hidalgo, 11000\",\"Horario\":\"8:30-19:00\",\"Latitud\":19.43825,\"Longitud\":-99.210089,\"Nombre\":\"Place 3\",\"NumTelefonico\":\"55 5249 5000\"},{\"Direccion1\":\"Periférico Blvd. Manuel Ávila Camacho 66\",\"Direccion2\":\"Lomas de Chapultepec V Secc, Miguel Hidalgo, 11000\",\"Horario\":\"8:30-19:00\",\"Latitud\":19.425674,\"Longitud\":-99.21732,\"Nombre\":\"Place 4\",\"NumTelefonico\":\"55 5249 5000\"},{\"Direccion1\":\"Periférico Blvd. Manuel Ávila Camacho 66\",\"Direccion2\":\"Lomas de Chapultepec V Secc, Miguel Hidalgo, 11000\",\"Horario\":\"8:30-19:00\",\"Latitud\":19.434456,\"Longitud\":-99.176314,\"Nombre\":\"Place 5\",\"NumTelefonico\":\"55 5249 5000\"},{\"Direccion1\":\"Periférico Blvd. Manuel Ávila Camacho 66\",\"Direccion2\":\"Lomas de Chapultepec V Secc, Miguel Hidalgo, 11000\",\"Horario\":\"8:30-19:00\",\"Latitud\":19.467345,\"Longitud\":-99.150661,\"Nombre\":\"Place 6\",\"NumTelefonico\":\"55 5249 5000\"}]";

    public DepositMapInteractor(IDepositMapPresenter presenter) {
        this.depositMapPresenter = presenter;
    }

    @Override
    public void getSucursalesWS(Location location) throws OfflineException {
        LocalizarSucursalesRequest request = new LocalizarSucursalesRequest();
        request.setLatitud(location.getLatitude());
        request.setLongitud(location.getLongitude());
        ApiAdtvo.localizarSucursales(request, this);
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        LocalizarSucursalesResponse response = ((LocalizarSucursalesResponse) result.getData());

        if (response.getCodigoRespuesta() == CODE_OK) {
            List<DataLocalizaSucursal> sucursales = response.getData();
            if (sucursales.size() > 0) {
                List<DataLocalizaSucursal> s = new Gson().fromJson(v, new TypeToken<List<DataLocalizaSucursal>>() {
                }.getType());
                depositMapPresenter.onGetSucursalesSuccess(s);//sucursales);
            } else {
                depositMapPresenter.onGetSucursalesFail(result);
            }
        } else {
            depositMapPresenter.onGetSucursalesFail(result);
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        Log.i("DepositMapInteractor", error.getData().toString());
        depositMapPresenter.onGetSucursalesFail(error);
    }
}
