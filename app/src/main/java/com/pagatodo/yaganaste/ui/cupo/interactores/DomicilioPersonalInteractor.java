package com.pagatodo.yaganaste.ui.cupo.interactores;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CargaDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.cupo.interactores.interfaces.IDomicilioPersonalInteractor;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;

/**
 * Created by Tato on 03/08/17.
 */

public class DomicilioPersonalInteractor implements IDomicilioPersonalInteractor, IRequestResult {

    INavigationView iNavigationView;
    private String TAG = DomicilioPersonalInteractor.class.getSimpleName();
    private IAccountManager accountManager;
    private Context context;

    public DomicilioPersonalInteractor(IAccountManager accountManager, Context ctx, INavigationView iNavigationView) {
        this.accountManager = accountManager;
        context = ctx;
        this.iNavigationView = iNavigationView;
    }

    /***
     * Metodo par recuperar las colonias por codigo postal
     * @param zipCode
     */
    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try {
            ApiAdtvo.obtenerColoniasPorCP(request, this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_COLONIAS_CP, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getClientAddress() {
        try {
            ApiAdtvo.obtenerDomicilioPrincipal(this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_DOMICILIO_PRINCIPAL, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getEstatusDocs() {
        try {
            ApiAdtvo.obtenerDocumentos(this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        if (((GenericResponse) dataSourceResult.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iNavigationView.errorSessionExpired(dataSourceResult);
        } else {
            switch (dataSourceResult.getWebService()) {
                case OBTENER_COLONIAS_CP:
                    processNeighborhoods(dataSourceResult);
                    break;
                case OBTENER_DOMICILIO_PRINCIPAL:
                    processAddress(dataSourceResult);
                    break;
                case CARGA_DOCUMENTOS:
                    processSendDocuments(dataSourceResult);
                    break;
            }
        }
    }

    /***
     * Metodo para procesar la respuesta cuando se envian los documentos
     *
     * @param response
     */
    private void processSendDocuments(DataSourceResult response) {

        CargaDocumentosResponse data = (CargaDocumentosResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            actualizaEstatusUsuario();
            accountManager.onSucces(CARGA_DOCUMENTOS, "Envio de documentos");
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iNavigationView.errorSessionExpired(response);
        } else {
            accountManager.onError(CARGA_DOCUMENTOS, data.getMensaje());
        }
    }

    private void actualizaEstatusUsuario() {
        String usuario = SingletonUser.getInstance().getDataUser().getUsuario().getNombreUsuario();
        RequestHeaders.setUsername(usuario); // Seteamos el usuario en el Header
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        try {
            ApiAdtvo.validarEstatusUsuario(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            accountManager.onError(VALIDAR_ESTATUS_USUARIO, App.getContext().getString(R.string.no_internet_access));
        }
    }




    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processNeighborhoods(DataSourceResult response) {
        ObtenerColoniasPorCPResponse data = (ObtenerColoniasPorCPResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<ColoniasResponse> listaColonias = data.getData();
            if (listaColonias != null && !listaColonias.isEmpty()) {
                accountManager.onSucces(response.getWebService(), listaColonias);
            } else {
                accountManager.onError(response.getWebService(), "Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(OBTENER_COLONIAS_CP, data.getMensaje());//Retornamos mensaje de error.
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }


    /**
     * Metodo para procesas la respuesta de domicilio
     *
     * @param result
     */
    private void processAddress(DataSourceResult result) {
        ObtenerDomicilioResponse data = (ObtenerDomicilioResponse) result.getData();

        if (data.getCodigoRespuesta() == CODE_OK) {
            DataObtenerDomicilio domicilio = data.getData();
            if (domicilio != null) {
                accountManager.onSucces(result.getWebService(), domicilio);
            } else {
                accountManager.onError(result.getWebService(), "Ocurrio un Error al Consultar el Domicilio");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(result.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }

    }


}
