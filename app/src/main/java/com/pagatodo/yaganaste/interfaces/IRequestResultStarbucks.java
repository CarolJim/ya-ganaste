package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.LoginStarbucksResponse;

/**
 * Created by asandovals on 23/04/2018.
 */

public interface IRequestResultStarbucks<Data extends LoginStarbucksResponse> {

    void onSuccess(Data data);

    void onFailed(Data error);
}