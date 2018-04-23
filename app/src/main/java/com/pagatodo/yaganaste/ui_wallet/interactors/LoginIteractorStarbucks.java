package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.LoginStarbucksResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginStarbucksss;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_USUARIO_CLIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.EMAIL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_MIEMBRO_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MIEMBRO_DESDE;
import static com.pagatodo.yaganaste.utils.Recursos.NIVEL_ACTUAL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.NUMERO_ESTRELLAS;
import static com.pagatodo.yaganaste.utils.Recursos.NUMERO_ESTRELLAS_FALTANTES;
import static com.pagatodo.yaganaste.utils.Recursos.NUMERO_MIEMBRO_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.BEBIDA_FAVORITA;
import static com.pagatodo.yaganaste.utils.Recursos.REWARDS;
import static com.pagatodo.yaganaste.utils.Recursos.SIGUIENTE_NIVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_GOLD;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_SEGURIDAD_STARBUCKS;

/**
 * Created by asandovals on 19/04/2018.
 */

public class LoginIteractorStarbucks implements IloginIteractorStarbucks, IRequestResult {
    IloginStarbucksss iloginStarbucksss;



    private Preferencias prefs = App.getInstance().getPrefs();
    public LoginIteractorStarbucks(IloginStarbucksss iloginStarbucksss) {
        this.iloginStarbucksss = iloginStarbucksss;
    }



    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {

            case LOGINSTARBUCKS:
                saveDataUsuStarBucks(dataSourceResult);
                break;

        }
    }

    private void saveDataUsuStarBucks(DataSourceResult dataSourceResult) {
        LoginStarbucksResponse data = (LoginStarbucksResponse) dataSourceResult.getData();

        if (data.getData().getCodigoRespuesta()!=0){
            iloginStarbucksss.onError(LOGINSTARBUCKS,data.getData().getMensaje());
        }else if (data.getData().getCodigoRespuesta()==0) {
            if (!data.getBeneficiosRewards().isEmpty()){
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
                App.getInstance().getPrefs().saveData(BEBIDA_FAVORITA, "Sin Bebida Favorita");
            } else {
                App.getInstance().getPrefs().saveData(BEBIDA_FAVORITA, data.getDatosMiembro().getBebidaFavorita());
            }
            App.getInstance().getPrefs().saveData(EMAIL_STARBUCKS, data.getDatosMiembro().getEmail());
            App.getInstance().getPrefs().saveData(MIEMBRO_DESDE, data.getDatosMiembro().getMiembroDesde());
            App.getInstance().getPrefs().saveData(STATUS_GOLD, data.getDatosMiembro().getStatusGold());
            App.getInstance().getPrefs().saveData(ID_MIEMBRO_STARBUCKS, data.getId_Miembro());
            App.getInstance().getPrefs().saveData(NIVEL_ACTUAL_STARBUCKS, data.getInfoRewardsStarbucks().getNivelActual());
            App.getInstance().getPrefs().saveData(NUMERO_ESTRELLAS, data.getInfoRewardsStarbucks().getNumEstrellas());
            App.getInstance().getPrefs().saveData(NUMERO_ESTRELLAS_FALTANTES, ""+data.getInfoRewardsStarbucks().getNumEstrellasFaltantes());
            App.getInstance().getPrefs().saveData(SIGUIENTE_NIVEL_STARBUCKS, data.getInfoRewardsStarbucks().getSiguienteNivel());
            App.getInstance().getPrefs().saveData(NUMERO_MIEMBRO_STARBUCKS, data.getNumeroMiembro());
            App.getInstance().getPrefs().saveData(TOKEN_SEGURIDAD_STARBUCKS, data.getTokenSeguridad());
            App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, true);
            iloginStarbucksss.onSucces(LOGINSTARBUCKS, data.getData());
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error != null && error.getWebService() == LOGINSTARBUCKS) {
            iloginStarbucksss.onError(LOGINSTARBUCKS,error.getData().toString());
        }

    }

    @Override
    public void login(LoginStarbucksRequest request) {

        try {
            ApiStarbucks.loginStarbucks(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iloginStarbucksss.onError(LOGINSTARBUCKS, e.toString());
        }


    }
}

