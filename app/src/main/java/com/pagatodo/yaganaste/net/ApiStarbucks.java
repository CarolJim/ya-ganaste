package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerColoniasPorCPRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.CardRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksCompleteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.StarbucksStoresRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerColoniasPorCPResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.ConsultaMovimientosSBResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.LoginStarbucksResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.PreRegisterStarbucksRespónse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.SaldoSBRespons;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.StarbucksStoresResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interactors.RegisterCompleteIteractorStarbucks;

import java.util.Map;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_MOV_SB;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_SALDO_SB;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_STARBUCKS_STORES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COLONIAS_CP;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.PREREGISTRO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REGISTROCOMPLETE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADTVO;
import static com.pagatodo.yaganaste.utils.Recursos.URL_STARBUCKS;

/**
 * Created by asandovals on 20/04/2018.
 */

public class ApiStarbucks extends Api {

    public static void loginStarbucks(LoginStarbucksRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersStarbucks();
        NetFacade.consumeWS(LOGINSTARBUCKS,
                METHOD_POST, URL_STARBUCKS + App.getContext().getString(R.string.loginstarbucks),
                headers, request, LoginStarbucksResponse.class, result);
    }

    public static void preregistroStarbucks(RegisterStarbucksRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersStarbucks();
        NetFacade.consumeWS(PREREGISTRO,
                METHOD_POST, URL_STARBUCKS + App.getContext().getString(R.string.preregisterstarbucks),
                headers, request, PreRegisterStarbucksRespónse.class, result);
    }

    public static void searchStores(StarbucksStoresRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersStarbucks();
        NetFacade.consumeWS(GET_STARBUCKS_STORES,
                METHOD_POST, URL_STARBUCKS + App.getContext().getString(R.string.get_stores_starbucks),
                headers, request, StarbucksStoresResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desean obtener movimientos de starbuck.
     *
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void consultarMovimientosSb(IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersSb();
        NetFacade.consumeWS(CONSULTAR_MOV_SB,
                METHOD_POST, URL_STARBUCKS + App.getContext().getString(R.string.sbMovements),
                headers, null, ConsultaMovimientosSBResponse.class, result);
    }

    /**
     * Método que se invoca cuando se desean obtener movimientos de starbuck.
     *
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void saldoSb(CardRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersSb();
        headers.put("Content-type", "application/json");
        NetFacade.consumeWS(CONSULTAR_SALDO_SB,
                METHOD_POST, URL_STARBUCKS + App.getContext().getString(R.string.registrocomplete),
                headers, request, SaldoSBRespons.class, result);
    }
    /**
     * Método que se invoca para obtener las Colonias a partir de un Código Postal.
     *
     * @param request {@link ObtenerColoniasPorCPRequest} body de la petición.
     * @param result  {@link IRequestResult} listener del resultado de la petición.
     */
    public static void registroCompleteStarbucks(RegisterStarbucksCompleteRequest request, IRequestResult result) throws OfflineException {
        NetFacade.consumeWS(REGISTROCOMPLETE,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.getNeighborhoodByZipUrl),
                getHeadersYaGanaste(), request, ObtenerColoniasPorCPResponse.class, result);
    }
}
