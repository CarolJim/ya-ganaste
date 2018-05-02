package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksCompleteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultaDatosPersonaStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DatosPersonaRegistroStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterCompleteIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterCompleteStarbuckss;
import com.pagatodo.yaganaste.ui_wallet.presenter.RegisterCompletePresenterStarbucks;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.DATOSPERSONAREGISTROSTAR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.PREREGISTRO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTROCOMPLETE;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by asandovals on 26/04/2018.
 */

public class RegisterCompleteIteractorStarbucks implements IregisterCompleteIteractorStarbucks, IRequestResult {

    IregisterCompleteStarbuckss iregisterCompleteStarbuckss;


   public RegisterCompleteIteractorStarbucks( IregisterCompleteStarbuckss iregisterCompleteStarbuckss){
       this.iregisterCompleteStarbuckss= iregisterCompleteStarbuckss;
    }


    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case DATOSPERSONAREGISTROSTAR:
                ConsultaDatosPersonaStarbucks data = (ConsultaDatosPersonaStarbucks) dataSourceResult.getData();
                RegisterUserStarbucks registerUser = RegisterUserStarbucks.getInstance();
                registerUser.setUdid(Utils.getUdid(App.getContext()));
                registerUser.setNombre(data.getData().getNombre());
                if (data.getData().getIdGenero()==2){
                    registerUser.setGenero("H");
                }else {
                    registerUser.setGenero("M");
                }
                registerUser.setPrimerApellido(data.getData().getPrimerApellido());
                registerUser.setSegundoApellido(data.getData().getSegundoApellido());
                String cumple= data.getData().getFechaNacimiento();
                cumple= cumple.replace("-","/");
                registerUser.setFechaNacimiento(cumple);
                registerUser.setEmail(data.getData().getCorreo());
                registerUser.setCodigoPostal(data.getData().getCP());
                registerUser.setIdColonia(data.getData().getIdColoniaSepomex());
                registerUser.setColonia(data.getData().getColonia());
                registerUser.setCalleNumero(data.getData().getCalleYNumero());
                registerUser.setCalleyNumero(data.getData().getCalleYNumero());
                registerUser.setBebidaFavorita("");
                iregisterCompleteStarbuckss.onSucces(DATOSPERSONAREGISTROSTAR,dataSourceResult);
                break;


            case OBTENER_COLONIAS_CP:
                processNeighborhoods(dataSourceResult);
                break;
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
                iregisterCompleteStarbuckss.onSucces(response.getWebService(), listaColonias);

            } else {
                iregisterCompleteStarbuckss.onError(response.getWebService(), App.getContext().getString(R.string.emisor_validate_postalcode));//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            iregisterCompleteStarbuckss.onError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {

    }

    @Override
    public void onError(WebService ws, Object error) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void consultaInfoPersona() {
        try {
            ApiAdtvo.consultarDatosPersonaRegistroStarbucks( this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iregisterCompleteStarbuckss.onError(DATOSPERSONAREGISTROSTAR, e.toString());
        }

    }

    @Override
    public void registerStarBucks(RegisterStarbucksCompleteRequest request) {

        try {
            ApiStarbucks.registroCompleteStarbucks(request, this);

        } catch (OfflineException e) {
            // e.printStackTrace();
            iregisterCompleteStarbuckss.onError(REGISTROCOMPLETE, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getNeighborhoodByZipCode(String zipCode) {
        ObtenerColoniasPorCPRequest request = new ObtenerColoniasPorCPRequest(zipCode);
        try {
            ApiAdtvo.obtenerColoniasPorCP(request, this);

        } catch (OfflineException e) {
            // e.printStackTrace();
            iregisterCompleteStarbuckss.onError(OBTENER_COLONIAS_CP, App.getContext().getString(R.string.no_internet_access));
        }
    }
}
