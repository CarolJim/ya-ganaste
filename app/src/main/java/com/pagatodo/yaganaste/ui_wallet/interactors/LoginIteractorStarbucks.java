package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.ForgetPassRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.LoginStarbucksResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginStarbucksss;

import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.FORGETPASSWORD;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ACTUAL_LEVEL_STARBUCKS;
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
            case FORGETPASSWORD:

                break;
        }
    }
    private void saveDataUsuStarBucks(DataSourceResult dataSourceResult) {
        LoginStarbucksResponse data = (LoginStarbucksResponse) dataSourceResult.getData();

        if (data.getData().getCodigoRespuesta() != 0) {
            iloginStarbucksss.onError(LOGINSTARBUCKS, data.getData().getMensaje());
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
            App.getInstance().getPrefs().saveDataInt(ID_MIEMBRO_STARBUCKS, data.getId_Miembro());
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

            iloginStarbucksss.onSucces(LOGINSTARBUCKS, data.getData());
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error != null && error.getWebService() == LOGINSTARBUCKS) {
            iloginStarbucksss.onError(LOGINSTARBUCKS, error.getData().toString());
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

    @Override
    public void forgetpass(ForgetPassRequest request) {
        try {
            ApiStarbucks.forgetpasswordStarbucks(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iloginStarbucksss.onError(FORGETPASSWORD, e.toString());
        }
    }
}

