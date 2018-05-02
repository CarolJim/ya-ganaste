package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksCompleteRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultaDatosPersonaStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DatosPersonaRegistroStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.LoginStarbucksResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
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
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.PREREGISTRO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTROCOMPLETE;
import static com.pagatodo.yaganaste.utils.Recursos.ACTUAL_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.EMAIL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.FAVORITE_DRINK;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_MIEMBRO_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_NUMBER_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_SINCE;
import static com.pagatodo.yaganaste.utils.Recursos.MISSING_STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.NEXT_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.REWARDS;
import static com.pagatodo.yaganaste.utils.Recursos.SECURITY_TOKEN_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_CARDS;
import static com.pagatodo.yaganaste.utils.Recursos.STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_GOLD;

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
                case REGISTROCOMPLETE:
                    saveDataUsuStarBucks(dataSourceResult);
                    break;
        }
    }

    private void saveDataUsuStarBucks(DataSourceResult dataSourceResult) {
        LoginStarbucksResponse data = (LoginStarbucksResponse) dataSourceResult.getData();

        if (data.getData().getCodigoRespuesta() != 0) {
            iregisterCompleteStarbuckss.onError(REGISTROCOMPLETE, data.getData().getMensaje());
        } else if (data.getData().getCodigoRespuesta() == 0) {
            if (!data.getBeneficiosRewards().isEmpty()) {
                List<Rewards> lista = data.getBeneficiosRewards();
                Gson gson = new Gson();
                String json = gson.toJson(lista);
                App.getInstance().getPrefs().saveData(REWARDS, json);
                /**
                 * para obtener el valor
                 * SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                 Gson gson = new Gson(); //Instancia Gson.
                 String json = prefs.getString("myObjeto", "");
                 myObject myobjeto = gson.fromJson(json, myObject.class);
                 *
                 */
            }
            if (data.getDatosMiembro().getBebidaFavorita() == null) {
                App.getInstance().getPrefs().saveData(FAVORITE_DRINK, "Sin Bebida Favorita");
            } else {
                App.getInstance().getPrefs().saveData(FAVORITE_DRINK, data.getDatosMiembro().getBebidaFavorita());
            }
            App.getInstance().getPrefs().saveData(EMAIL_STARBUCKS, data.getDatosMiembro().getEmail());
            App.getInstance().getPrefs().saveData(MEMBER_SINCE, data.getDatosMiembro().getMiembroDesde());
            App.getInstance().getPrefs().saveDataInt(STATUS_GOLD, data.getDatosMiembro().getStatusGold());
            App.getInstance().getPrefs().saveData(ID_MIEMBRO_STARBUCKS, data.getId_Miembro());
            App.getInstance().getPrefs().saveData(ACTUAL_LEVEL_STARBUCKS, data.getInfoRewardsStarbucks().getNivelActual());
            App.getInstance().getPrefs().saveDataInt(STARS_NUMBER, data.getInfoRewardsStarbucks().getNumEstrellas());
            App.getInstance().getPrefs().saveDataInt(MISSING_STARS_NUMBER, data.getInfoRewardsStarbucks().getNumEstrellasFaltantes());
            App.getInstance().getPrefs().saveData(NEXT_LEVEL_STARBUCKS, data.getInfoRewardsStarbucks().getSiguienteNivel());
            App.getInstance().getPrefs().saveData(MEMBER_NUMBER_STARBUCKS, data.getNumeroMiembro());
            App.getInstance().getPrefs().saveData(SECURITY_TOKEN_STARBUCKS, data.getTokenSeguridad());
            App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, true);
            if (!data.getTarjetas().isEmpty()) {
                List<CardStarbucks> listadetarjetas = data.getTarjetas();
                Gson gson = new Gson();
                String json = gson.toJson(listadetarjetas);
                App.getInstance().getPrefs().saveData(STARBUCKS_CARDS, json);
                /**
                 * para obtener el valor
                 * SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                 Gson gson = new Gson(); //Instancia Gson.
                 String json = prefs.getString("myObjeto", "");
                 myObject myobjeto = gson.fromJson(json, myObject.class);
                 *
                 */
            }

            iregisterCompleteStarbuckss.onSucces(REGISTROCOMPLETE, data.getData());
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
