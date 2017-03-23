package com.pagatodo.yaganaste.iteractors;

import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.UsuarioClienteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.interfaces.IAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;

import static com.pagatodo.yaganaste.ui.account.login.LetsStartFragment.EVENT_GET_CARD;
import static com.pagatodo.yaganaste.ui.account.login.LetsStartFragment.EVENT_LOGIN;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountIteractor implements IAccountIteractor,IRequestResult {

    private String TAG = AccountIteractor.class.getName();
    private IAccountManager accountManager;

    public AccountIteractor(IAccountManager accountManager){
        this.accountManager = accountManager;

    }


    @Override
    public void validateUserStatus(String usuario) {
        RequestHeaders.setUsername(usuario); // Seteamos el usuario en el Header
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        ApiAdtvo.validarEstatusUsuario(request,this);
    }


    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case VALIDAR_ESTATUS_USUARIO:
                Log.i(TAG,"onSuccess:");
                setUserStatus((ValidarEstatusUsuarioResponse)dataSourceResult.getData());
                break;

            default:
                break;

        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }

    private void setUserStatus(ValidarEstatusUsuarioResponse data){

        DataEstatusUsuario userStatus = data.getData();
        String eventTypeUser = "";
        if(data.getCodigoRespuesta() == CODE_OK){
            RequestHeaders.setOperation(String.valueOf(data.getIdOperacion()));//TODO validar razon de esta asignación
            //Seteamos los datos del usuario en eli SingletonUser.
            SingletonUser user = SingletonUser.getInstance();
            user.setDataUser(new DataIniciarSesion(userStatus.isEsUsuario(),userStatus.isEsCliente(),
                    userStatus.isEsAgente(),userStatus.isConCuenta(),new UsuarioClienteResponse(userStatus.getIdUsuario())));

            if(!userStatus.isEsUsuario() && !userStatus.isEsCliente()){
                eventTypeUser = EVENT_GET_CARD;
            }else if(userStatus.isEsUsuario()){
                eventTypeUser = EVENT_LOGIN;
            }

            accountManager.setUserStatus(eventTypeUser);
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
        }

    }
}
