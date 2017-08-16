package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.dto.ErrorObject;

/**
 * Created by flima on 20/02/2017.
 */

public interface IAprovView {

    void showErrorAprov(ErrorObject error);

    void provisingCompleted();

    void subscribeNotificationSuccess();

}
