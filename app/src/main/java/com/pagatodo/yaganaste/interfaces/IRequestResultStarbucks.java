package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.LoginStarBucksResponse;

/**
 * Created by asandovals on 23/04/2018.
 */

public interface IRequestResultStarbucks<Data extends LoginStarBucksResponse> {

    void onSuccess(Data data);

    void onFailed(Data error);
}