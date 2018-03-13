package com.pagatodo.yaganaste.ui.tarjeta;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarDatosCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarCorreoContactanosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ESTATUS_CUENTA;

/**
 * Created by Armando Sandoval on 10/10/2017.
 */

public class TarjetaUserIteractor implements IPreferUserIteractor, IRequestResult {
    TarjetaUserPresenter tarjetaUserPresenter;


    public TarjetaUserIteractor(TarjetaUserPresenter tarjetaUserPresenter) {
        this.tarjetaUserPresenter = tarjetaUserPresenter;
    }


    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa de enviar mensaje contactanos
         */
        if (dataSourceResult.getData() instanceof EnviarCorreoContactanosResponse) {

            EnviarCorreoContactanosResponse response = (EnviarCorreoContactanosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "DataSource Sucess " + response.getMensaje());
                tarjetaUserPresenter.successGenericToPresenter(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "DataSource Sucess with Error " + response.getMensaje());
                tarjetaUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }
        if (dataSourceResult.getData() instanceof EstatusCuentaResponse) {
            EstatusCuentaResponse response = (EstatusCuentaResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
                tarjetaUserPresenter.successGenericToPresenter(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
                tarjetaUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }

        if (dataSourceResult.getData() instanceof BloquearCuentaResponse) {
            BloquearCuentaResponse response = (BloquearCuentaResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess " + response.getMensaje());
                tarjetaUserPresenter.successGenericToPresenter(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess with Error " + response.getMensaje());
                tarjetaUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }

    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error.getWebService().equals(ESTATUS_CUENTA)) {
            //Log.d("PreferUserIteractor", "EstatusCuentaResponse ErrorServer " + error.toString());
            tarjetaUserPresenter.sendErrorServerEstatusCuentaToPresenter(error.getData().toString());
        }
    }

    @Override
    public void desasociarToIteracto(Request request) {

    }

    @Override
    public void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest) {

    }

    @Override
    public void showExceptionBitmapDownloadToIteractor(String s) {

    }

    @Override
    public void sendChangePassToIteractor(CambiarContraseniaRequest cambioPassRequest) {

    }

    @Override
    public void changeEmailToIteractor(CambiarEmailRequest cambiarEmailRequest) {

    }

    @Override
    public void changePassToIteractor(CambiarContraseniaRequest cambiarContraseniaRequest) {

    }

    @Override
    public void sendIteractorDatosCuenta(ActualizarDatosCuentaRequest datosCuentaRequest) {

    }

    @Override
    public void toIteractorBloquearCuenta(BloquearCuentaRequest request) {
        try {
            ApiTrans.bloquearCuenta(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            tarjetaUserPresenter.showExceptionBloquearCuentaToPresenter(e.toString());
        }

    }

    @Override
    public void toIteractorEstatusCuenta(EstatusCuentaRequest request) {
        try {
            ApiTrans.estatusCuenta(request, this);

        } catch (OfflineException e) {
            // e.printStackTrace();
            tarjetaUserPresenter.showExceptionBloquearCuentaToPresenter(e.toString());
        }
    }

    @Override
    public void enviarCorreoContactanos(EnviarCorreoContactanosRequest enviarCorreoContactanosRequest) {
        try {
            ApiAdtvo.enviarCorreo(enviarCorreoContactanosRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            tarjetaUserPresenter.showExceptionCorreoContactanosPresenter(App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void logOutSession() {

    }
}
