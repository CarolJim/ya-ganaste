package com.pagatodo.yaganaste.ui.account;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerDocumentosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.interfaces.IAdqAccountIteractor;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesAddressBack;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesAddressFront;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesIFEBack;
import static com.pagatodo.yaganaste.R.id.itemWeNeedSmFilesIFEfront;
import static com.pagatodo.yaganaste.interfaces.enums.DataSource.WS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
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

    private String TAG = AccountAdqInteractor.class.getName();
    private IAccountManager accountManager;
    private Context context;
    private List<EstatusDocumentosResponse> mListaDocumentos;

    public AccountAdqInteractor(IAccountManager accountManager, Context ctx) {
        this.accountManager = accountManager;
        context = ctx;
    }

    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try {
            ApiAdtvo.obtenerColoniasPorCP(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setListDocuments(View view) {
        Drawable mDrawable = null;
        Bitmap bitmapEstatus = null;

        if( mListaDocumentos!=null && mListaDocumentos.size()>0) {
            for (EstatusDocumentosResponse estatusDocs : this.mListaDocumentos) {
                int tipoDoc = estatusDocs.getTipoDocumento();

                switch (estatusDocs.getIdEstatus()) {
                    case STATUS_DOCTO_APROBADO:
                        mDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_validate_blue);
                        bitmapEstatus = ((BitmapDrawable) mDrawable).getBitmap();
                        break;
                    case STATUS_DOCTO_PENDIENTE:
                        mDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_validate_blue);
                        bitmapEstatus = ((BitmapDrawable) mDrawable).getBitmap();
                        break;
                    case STATUS_DOCTO_RECHAZADO:
                        mDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_validate_blue);
                        bitmapEstatus = ((BitmapDrawable) mDrawable).getBitmap();
                        break;
                    default:
                        mDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_validate_blue);
                        bitmapEstatus = ((BitmapDrawable) mDrawable).getBitmap();
                        break;
                }
                if (tipoDoc == DOC_ID_FRONT) {
                    UploadDocumentView IFEfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEfront);
                    IFEfront.setStatusImage(bitmapEstatus);
                } else if (tipoDoc == DOC_ID_BACK) {
                    UploadDocumentView IFEback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEBack);
                    IFEback.setStatusImage(bitmapEstatus);
                } else if (tipoDoc == DOC_DOM_FRONT) {
                    UploadDocumentView Addressfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressFront);
                    Addressfront.setStatusImage(bitmapEstatus);
                } else if (tipoDoc == DOC_DOM_BACK) {
                    UploadDocumentView Addressback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressBack);
                    Addressback.setStatusImage(bitmapEstatus);
                }
            }
        }else{
            UploadDocumentView IFEfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEfront);
            IFEfront.setVisibilityStatus(false);
            UploadDocumentView IFEBack = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesIFEBack);
            IFEBack.setVisibilityStatus(false);
            UploadDocumentView Addressfront = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressFront);
            Addressfront.setVisibilityStatus(false);
            UploadDocumentView Addressback = (UploadDocumentView) view.findViewById(itemWeNeedSmFilesAddressBack);
            Addressback.setVisibilityStatus(false);

        }
    }

    @Override
    public void getEstatusDocs(View view) {

        try {
            ApiAdtvo.obtenerDocumentos(this);

        } catch (OfflineException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registerAdq() {

        RegisterAgent registerAgent = RegisterAgent.getInstance();
        CrearAgenteRequest request = new CrearAgenteRequest();
        request.setNombreComercio(registerAgent.getNombre());
        request.setGiro(registerAgent.getGiro());
        request.setNumeroTelefono(registerAgent.getTelefono());
        request.setCuestionario(registerAgent.getCuestionario());
        onSuccess(new DataSourceResult(CREAR_AGENTE, WS, null));
       /*
        try {
            ApiAdtvo.crearAgente(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case OBTENER_COLONIAS_CP:
                processNeighborhoods(dataSourceResult);
                break;
            case CREAR_AGENTE:
                processAgentCreated(dataSourceResult);
                break;
            case OBTENER_DOCUMENTOS:

                processStatusDocuments(dataSourceResult);
                break;

            default:
                break;
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
        mListaDocumentos = new ArrayList<>();
        //TODO REVISAR
        ObtenerDocumentosRequest data = (ObtenerDocumentosRequest) response.getData();

        if (data.getCodigoRespuesta() == CODE_OK) {
            List<EstatusDocumentosResponse> listaDocumentos = data.getData();
            if (listaDocumentos != null && listaDocumentos.size() > 0) {
                mListaDocumentos= listaDocumentos;
                accountManager.onSucces(response.getWebService(), response.getData());

            } else {
                accountManager.onError(response.getWebService(), "No se pudo recuperar el estatus de los documentos");
            }

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
            if (listaColonias != null && listaColonias.size() > 0) {
                accountManager.onSucces(response.getWebService(), listaColonias);
            } else {
                accountManager.onError(response.getWebService(), "Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.

        }
    }

    /**
     * Método para procesar la respuesta con la lista de colonias en el código postal.
     *
     * @param response {@link DataSourceResult} respuesta del servicio
     */
    private void processAgentCreated(DataSourceResult response) {

        accountManager.onSucces(response.getWebService(), null);
        /*
        if(data.getCodigoRespuesta() == CODE_OK){
            List<ColoniasResponse> listaColonias = data.getData();
            if(listaColonias != null && listaColonias.size() > 0){
                accountManager.onSucces(response.getWebService(),listaColonias);
            }else{
                accountManager.onError(response.getWebService(),"Verifica tu Código Postal");//Retornamos mensaje de error.
            }
        }else{
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            accountManager.onError(response.getWebService(),data.getMensaje());//Retornamos mensaje de error.
        }*/
    }

}