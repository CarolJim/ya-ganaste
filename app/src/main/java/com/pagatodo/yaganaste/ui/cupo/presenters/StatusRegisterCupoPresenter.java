package com.pagatodo.yaganaste.ui.cupo.presenters;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.ui.cupo.interactors.StatusRegisterCupoInteractor;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.IStatusRegisterCupo;
import com.pagatodo.yaganaste.ui.cupo.view.IViewStatusRegisterCupo;

/**
 * Created by Dell on 26/07/2017.
 */

public class StatusRegisterCupoPresenter  implements IStatusRegisterCupo {

    private StatusRegisterCupoInteractor mInteractor;
    private IViewStatusRegisterCupo mView;



    public StatusRegisterCupoPresenter(StatusRegisterCupoInteractor statusRegisterCupoInteractor) {
        mInteractor = statusRegisterCupoInteractor;
    }

    public void setView(IViewStatusRegisterCupo view){
        this.mView = view;
    }

    @Override
    public void doRequestStatusRegister() {
        if(mView != null){
            mView.showLoader(true);
        }

        if(mInteractor != null){
            mInteractor.requestStatusRegister(this);
        }
    }

    @Override
    public void onObtainStatusSuccess(DataSourceResult dataSourceResult) {
        if(mView != null){
            mView.showLoader(false);
            mView.showStatusRegister();
        }


    }

    @Override
    public void onObtainStatusFailed(DataSourceResult dataSourceResult) {
        if(mView != null){
            mView.showLoader(false);

            //todo CUPO ajustar flujo en caso de que el request falle
            if(dataSourceResult.getData() instanceof OfflineException){
                mView.showError(dataSourceResult.getData().toString());
            }
        }
    }
}
