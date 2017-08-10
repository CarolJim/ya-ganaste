package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CargaDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DataDocuments;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CargaDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataEstatusUsuario;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.interfaces.INavigationView;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesAddressBack;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesAddressFront;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesIFEBack;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesIFEfront;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CARGA_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOCUMENTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DOMICILIO_PRINCIPAL;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_DOM_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.DOC_ID_FRONT;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountAdqInteractor implements IAdqAccountIteractor, IRequestResult {

    INavigationView iSessionExpired;
    private String TAG = AccountAdqInteractor.class.getSimpleName();
    private IAccountManager accountManager;
    private Context context;

    public AccountAdqInteractor(IAccountManager accountManager, Context ctx, INavigationView iSessionExpired) {
        this.accountManager = accountManager;
        context = ctx;
        this.iSessionExpired = iSessionExpired;
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

    /***
     * Metodo que sea la vista de lo documentos con los estatus que le corresponde
     * @param view
     * @param mListaDocumentos
     */
    @Override
    public void setListDocuments(View view, List<EstatusDocumentosResponse> mListaDocumentos) {
        UploadDocumentView IFEfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEfront);
        UploadDocumentView IFEback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEBack);
        UploadDocumentView Addressfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressFront);
        UploadDocumentView Addressback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressBack);

        IFEfront.setStatusImage(null);
        IFEback.setStatusImage(null);
        Addressfront.setStatusImage(null);
        Addressback.setStatusImage(null);

        view.findViewById(R.id.btnWeNeedSmFilesNext).setVisibility(View.INVISIBLE);
        LinearLayout lnrButtons = (LinearLayout) view.findViewById(R.id.lnr_buttons);
        Button btnNext = (Button) view.findViewById(R.id.btnWeNeedSmFilesNext);
        btnNext.setClickable(false);

        Bitmap mBitmap = null;
        Drawable mDrawable = null;

        if (mListaDocumentos != null && mListaDocumentos.size() > 0) {
            for (EstatusDocumentosResponse estatusDocs : mListaDocumentos) {
                int tipoDoc = estatusDocs.getTipoDocumento();

                switch (estatusDocs.getIdEstatus()) {
                    case STATUS_DOCTO_APROBADO:
                        if (tipoDoc == DOC_ID_FRONT) {
                            IFEfront.setClickable(false);
                        } else if (tipoDoc == DOC_ID_BACK) {
                            IFEback.setClickable(false);
                        } else if (tipoDoc == DOC_DOM_FRONT) {
                            Addressfront.setClickable(false);
                        } else if (tipoDoc == DOC_DOM_BACK) {
                            Addressback.setClickable(false);
                        }

                        mBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.ic_status_ok);
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_ok);

                        break;
                    case STATUS_DOCTO_PENDIENTE:
                        if (tipoDoc == DOC_ID_FRONT) {
                            IFEfront.setClickable(false);
                        } else if (tipoDoc == DOC_ID_BACK) {
                            IFEback.setClickable(false);
                        } else if (tipoDoc == DOC_DOM_FRONT) {
                            Addressfront.setClickable(false);
                        } else if (tipoDoc == DOC_DOM_BACK) {
                            Addressback.setClickable(false);
                        }
                        
                        mBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.ic_status_pending);
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_pending);

                        break;
                    case STATUS_DOCTO_RECHAZADO:
                        if (tipoDoc == DOC_ID_FRONT) {
                            IFEfront.setClickable(true);
                        } else if (tipoDoc == DOC_ID_BACK) {
                            IFEback.setClickable(true);
                        } else if (tipoDoc == DOC_DOM_FRONT) {
                            Addressfront.setClickable(true);
                        } else if (tipoDoc == DOC_DOM_BACK) {
                            Addressback.setClickable(true);
                        }
                        lnrButtons.setVisibility(View.VISIBLE);
                        btnNext.setClickable(true);
                        mBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.ic_status_warning);
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_warning);
                        break;
                    default:
                        mBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.ic_status_pending);
                        mDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_pending);
                        break;
                }
                if (estatusDocs.getTipoDocumento() == DOC_ID_FRONT) {
                    IFEfront.setVisibilityStatus(true);

                    IFEfront.setImageDrawable(mDrawable);
                } else if (estatusDocs.getTipoDocumento() == DOC_ID_BACK) {
                    IFEback.setVisibilityStatus(true);

                    IFEback.setImageDrawable(mDrawable);
                } else if (estatusDocs.getTipoDocumento() == DOC_DOM_FRONT) {
                    Addressfront.setVisibilityStatus(true);

                    Addressfront.setImageDrawable(mDrawable);
                } else if (estatusDocs.getTipoDocumento() == DOC_DOM_BACK) {
                    Addressback.setVisibilityStatus(true);

                    Addressback.setImageDrawable(mDrawable);
                }
            }

            /**
             * Lineas para probar los estados y carga de imagenes, sin importar los estados del servidor
             */
            //IFEfront.setClickable(true);
            //IFEback.setClickable(true);
            //Addressfront.setClickable(true);
            //Addressback.setClickable(true);
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
    public void getClientAddress() {
        try {
            ApiAdtvo.obtenerDomicilioPrincipal(this);
        } catch (OfflineException e) {
            accountManager.onError(OBTENER_DOMICILIO_PRINCIPAL, App.getInstance().getString(R.string.no_internet_access));
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
            ApiAdtvo.cargaDocumentos(cargaDocumentosRequest, this);
        } catch (OfflineException e) {
            accountManager.onError(CARGA_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }

    }

    /***
     * Envio de documentos pendientes y actualizacion
     * @param data
     */
    @Override
    public void sendDocumentsPendientes(ArrayList<DataDocuments> data) {

        try {
            CargaDocumentosRequest cargaDocumentosRequest = new CargaDocumentosRequest();
            cargaDocumentosRequest.setDocumentos(data);
            ApiAdtvo.actualizarDocumentos(cargaDocumentosRequest, this);
        } catch (OfflineException e) {
            accountManager.onError(ACTUALIZAR_DOCUMENTOS, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void registerAdq() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        CrearAgenteRequest request = new CrearAgenteRequest();
        request.setNombreComercio(registerAgent.getNombre());
        // TODO: 16/05/2017  
        request.setSubGiro(registerAgent.getGiro().getIdSubgiro());
        request.setNumeroTelefono(registerAgent.getTelefono());
        request.setTipoAgente(SingletonUser.getInstance().getDataUser().getUsuario().getTipoAgente());
        request.setCuestionario(registerAgent.getCuestionario());

        DataObtenerDomicilio dataObtenerDomicilio = new DataObtenerDomicilio();
        dataObtenerDomicilio.setCp(registerAgent.getCodigoPostal());
        dataObtenerDomicilio.setCalle(registerAgent.getCalle());
        dataObtenerDomicilio.setColonia(registerAgent.getColonia());
        dataObtenerDomicilio.setEstado(registerAgent.getEstadoDomicilio());
        dataObtenerDomicilio.setIdColonia(registerAgent.getIdColonia());
        dataObtenerDomicilio.setNumeroExterior(registerAgent.getNumExterior());
        dataObtenerDomicilio.setNumeroInterior(registerAgent.getNumInterior());
        dataObtenerDomicilio.setIdEstado(registerAgent.getIdEstado());

        request.setDomicilioNegocio(dataObtenerDomicilio);

        //   onSuccess(new DataSourceResult(CREAR_AGENTE, WS, null));

        try {
            ApiAdtvo.crearAgente(request, this);
        } catch (OfflineException e) {
            accountManager.onError(CREAR_AGENTE, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        // if (16 == CODE_SESSION_EXPIRED) {
        if (((GenericResponse) dataSourceResult.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(dataSourceResult);
        } else {
            switch (dataSourceResult.getWebService()) {

                case OBTENER_COLONIAS_CP:
                    processNeighborhoods(dataSourceResult);
                    break;
                case CREAR_AGENTE:
                    processAgentCreated(dataSourceResult);
                    break;
                case CARGA_DOCUMENTOS:
                    processSendDocuments(dataSourceResult);
                    break;
                case ACTUALIZAR_DOCUMENTOS:
                    processSendDocumentsPendientes(dataSourceResult);
                    break;
                case OBTENER_DOCUMENTOS:
                    processStatusDocuments(dataSourceResult);
                    break;
                case VALIDAR_ESTATUS_USUARIO:
                    processEstatusUsuario(dataSourceResult);
                    break;
                case OBTENER_DOMICILIO:
                    break;
                case OBTENER_DOMICILIO_PRINCIPAL:
                    processAddress(dataSourceResult);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Cambios el estatis del Singleton
     *
     * @param response
     */
    private void processEstatusUsuario(DataSourceResult response) {
        try {
            ValidarEstatusUsuarioResponse data = (ValidarEstatusUsuarioResponse) response.getData();
            DataEstatusUsuario userStatus = data.getData();
            SingletonUser user = SingletonUser.getInstance();
            user.getDataUser().setEsAgente(userStatus.isEsAgente());
            user.getDataUser().setEstatusAgente(CRM_PENDIENTE);
        } catch (Exception e) {
            Log.d("AccountAdqInteractor", "Exception " + e);
        }

    }

    private void processSendDocumentsPendientes(DataSourceResult response) {
        CargaDocumentosResponse data = (CargaDocumentosResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(ACTUALIZAR_DOCUMENTOS, data.getMensaje());
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } else {
            accountManager.onError(ACTUALIZAR_DOCUMENTOS, "error" + data.getMensaje());
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
            iSessionExpired.errorSessionExpired(response);
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
     * @param error
     **/

    @Override
    public void onFailed(DataSourceResult error) {
        /**TODO Casos de Servicio fallido*/
        accountManager.onError(error.getWebService(), error.getData().toString());
    }

    /**
     * Metodo para procesar la respuesta con los estatus de los documentos
     *
     * @param response {respuesta del servicio}
     */
    private void processStatusDocuments(DataSourceResult response) {

        ObtenerDocumentosRequest data = (ObtenerDocumentosRequest) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<EstatusDocumentosResponse> listaDocumentos = data.getData();
            if (listaDocumentos != null && listaDocumentos.size() > 0) {
                accountManager.onSucces(response.getWebService(), listaDocumentos);

            } else {
                accountManager.onError(response.getWebService(), "No se pudo recuperar el estatus de los documentos");
            }
        } else if (((GenericResponse) response.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
            iSessionExpired.errorSessionExpired(response);
        } else {
            accountManager.onError(response.getWebService(), "error " + data.getMensaje());


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
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processAgentCreated(DataSourceResult response) {

        CrearAgenteResponse data = (CrearAgenteResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            accountManager.onSucces(CREAR_AGENTE, null);
        } else {
            accountManager.onError(CREAR_AGENTE, data.getMensaje());//Retornamos mensaje de error.
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