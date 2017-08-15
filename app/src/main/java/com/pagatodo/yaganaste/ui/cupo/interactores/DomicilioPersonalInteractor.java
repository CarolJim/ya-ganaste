package com.pagatodo.yaganaste.ui.cupo.interactores;

import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterCupo;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CargaDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.cupo.CrearCupoSolicitudRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.cupo.CupoReferencia;
import com.pagatodo.yaganaste.data.model.webservice.request.cupo.Domicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CargaDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.CrearCupoSolicitudResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.cupo.interactores.interfaces.IDomicilioPersonalInteractor;
import com.pagatodo.yaganaste.utils.JsonManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREA_SOLICITUD_CUPO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;

/**
 * Created by Horacio on 03/08/17.
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

    /***
     * Envio de documentos
     * @param docs
     */
    @Override
    public void sendDocuments(ArrayList<DataDocuments> docs) {


        try {
            CargaDocumentosRequest cargaDocumentosRequest = new CargaDocumentosRequest();
            cargaDocumentosRequest.setDocumentos(docs);

            //Log.e("Test", "Datos a enviar: " + createParams(false, cargaDocumentosRequest ).toString() );

            ApiAdtvo.cargaDocumentosCupo(cargaDocumentosRequest, this);
        } catch (OfflineException e) {
            accountManager.onError(CARGA_DOCUMENTOS_CUPO, App.getInstance().getString(R.string.no_internet_access));
        }


    }




    @Override
    public void createSolicitudCupo() {

        RegisterCupo registerCupo = RegisterCupo.getInstance();

        CrearCupoSolicitudRequest request = new CrearCupoSolicitudRequest();
        request.setIdEstadoCivil(registerCupo.getIdEstadoCivil());
        request.setNumeroHijos(registerCupo.getHijos());
        request.setTieneCreditoBancario(registerCupo.getCreditoBancario());
        request.setTieneCreditoAuto(registerCupo.getCreditoAutomotriz());
        request.setTieneTarjetaCredito(registerCupo.getTarjetaCreditoBancario());
        request.setNumeroTarjeta(registerCupo.getNumeroTarjeta());

        List<CupoReferencia> referencias = new ArrayList<>();

        CupoReferencia referenciaFamiliar  = new CupoReferencia();
        referenciaFamiliar.setTipoReferencia(1);
        referenciaFamiliar.setNombre(registerCupo.getFamiliarNombre());
        referenciaFamiliar.setPrimerApellido(registerCupo.getFamiliarApellidoPaterno());
        referenciaFamiliar.setSegundoApellido(registerCupo.getFamiliarApellidoMaterno());
        referenciaFamiliar.setTelefono(registerCupo.getFamiliarTelefono());
        referenciaFamiliar.setIdRelacion(registerCupo.getFamiliarIdRelacion());

        CupoReferencia referenciaPersonal  = new CupoReferencia();
        referenciaPersonal.setTipoReferencia(2);
        referenciaPersonal.setNombre(registerCupo.getPersonalNombre());
        referenciaPersonal.setPrimerApellido(registerCupo.getPersonalApellidoPaterno());
        referenciaPersonal.setSegundoApellido(registerCupo.getPersonalApellidoMaterno());
        referenciaPersonal.setTelefono(registerCupo.getPersonalTelefono());
        referenciaPersonal.setIdRelacion(registerCupo.getPersonalIdRelacion());

        CupoReferencia referenciaProveedor = new CupoReferencia();
        referenciaProveedor.setTipoReferencia(3);
        referenciaProveedor.setNombre(registerCupo.getProveedorNombre());
        referenciaProveedor.setPrimerApellido(registerCupo.getProveedorApellidoPaterno());
        referenciaProveedor.setSegundoApellido(registerCupo.getProveedorApellidoMaterno());
        referenciaProveedor.setTelefono(registerCupo.getProveedorTelefono());
        referenciaProveedor.setProductoServicioProveedor(registerCupo.getProveedorProductoServicio());
        referenciaProveedor.setIdRelacion(30);

        referencias.add(referenciaFamiliar);
        referencias.add(referenciaPersonal);
        referencias.add(referenciaProveedor);

        request.setReferencias(referencias);

        Domicilio domicilioPersonal = new Domicilio();

        domicilioPersonal.setCalle(registerCupo.getCalle());
        domicilioPersonal.setNumeroExterior(registerCupo.getNumExterior());
        domicilioPersonal.setNumeroInterior(registerCupo.getNumInterior());
        domicilioPersonal.setCP(registerCupo.getCodigoPostal());
        domicilioPersonal.setIdEstado(registerCupo.getIdEstadoNacimineto());
        domicilioPersonal.setIdColonia(registerCupo.getIdColonia());

        List<Domicilio> domicilios = new ArrayList<>();
        domicilios.add(domicilioPersonal);

        request.setDomicilioPersonal(domicilios);
        Log.e("Cupo JSON", createParams(false, request ).toString()  );

        try {
            ApiAdtvo.CrearSolicitudCupo(request, this);
        } catch (OfflineException e) {
            accountManager.onError(CREA_SOLICITUD_CUPO, App.getInstance().getString(R.string.no_internet_access));
        }



    }

    private static JSONObject createParams(boolean envolve, Object oRequest) {

        if (oRequest != null) {
            JSONObject tmp = JsonManager.madeJsonFromObject(oRequest);
            if (envolve) {
                if (oRequest instanceof AdqRequest) {
                    return JsonManager.madeJsonAdquirente(tmp);
                } else {
                    return JsonManager.madeJson(tmp);
                }
            } else {
                return tmp;
            }
        }
        return null;
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
                case CARGA_DOCUMENTOS_CUPO:
                    processSendDocumentsCupo(dataSourceResult);
                    break;
                case CREA_SOLICITUD_CUPO:
                    processSolicitudCupo(dataSourceResult);
                    break;
            }
        }
    }


    @Override
    public void onFailed(DataSourceResult error) {
        accountManager.onError(error.getWebService(), error.getData().toString());
    }

    private void processSolicitudCupo(DataSourceResult response) {
        CrearCupoSolicitudResponse data = (CrearCupoSolicitudResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(CREA_SOLICITUD_CUPO, "Registro Cupo Enviado");
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iNavigationView.errorSessionExpired(response);
        } else {
            accountManager.onError(CREA_SOLICITUD_CUPO, data.getMensaje());
        }
    }

    // TODO: Probar
    private void processSendDocumentsCupo(DataSourceResult response) {
        CargaDocumentosResponse data = (CargaDocumentosResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            //actualizaEstatusUsuario();
            accountManager.onSucces(CARGA_DOCUMENTOS_CUPO, "Envio de documentos");
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iNavigationView.errorSessionExpired(response);
        } else {
            accountManager.onError(CARGA_DOCUMENTOS_CUPO, data.getMensaje());
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
