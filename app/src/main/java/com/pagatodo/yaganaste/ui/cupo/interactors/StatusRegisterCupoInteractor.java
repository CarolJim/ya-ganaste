package com.pagatodo.yaganaste.ui.cupo.interactors;

import android.os.CountDownTimer;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.IStatusRegisterCupo;

/**
 * Created by Dell on 26/07/2017.
 */

public class StatusRegisterCupoInteractor implements IRequestResult {


    public void requestStatusRegister(final IStatusRegisterCupo callBack)  {

        //// TODO modificar  la consulta al ws
        try {
            ApiAdtvo.consultaStatusRegistroCupo(new IRequestResult() {
                @Override
                public void onSuccess(DataSourceResult dataSourceResult) {
                    callBack.onObtainStatusSuccess(dataSourceResult);
                }

                @Override
                public void onFailed(DataSourceResult error) {
                    callBack.onObtainStatusFailed(error);
                    //todo quitar cuando se tengan los ws
                    callBack.onObtainStatusSuccess(error);
                }
            });
        } catch (OfflineException e) {
            DataSourceResult data = new DataSourceResult(WebService.CONSULTA_SALDO_CUPO, null, new OfflineException());
            callBack.onObtainStatusFailed(data);
           //todo quitar
            callBack.onObtainStatusSuccess(data);
        }


    }






    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

    }

    @Override
    public void onFailed(DataSourceResult error) {

    }
}
