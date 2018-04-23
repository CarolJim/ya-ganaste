package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.LoginStarBucksResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

import java.util.Map;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.URL_STARBUCKS;

/**
 * Created by asandovals on 20/04/2018.
 */

public class ApiStarbucks extends Api{



    public static void loginstarbucks(LoginStarbucksRequest request, IRequestResult result) throws OfflineException {
        Map<String, String> headers = getHeadersStarbucks();
        NetFacade.consumeWS(LOGINSTARBUCKS,
                METHOD_POST, URL_STARBUCKS + App.getContext().getString(R.string.loginstarbucks),
                headers, request, LoginStarBucksResponse.class, result);
    }
}
