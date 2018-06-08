package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IDetailAdqView;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IMovementDetailAdq;
import com.pagatodo.yaganaste.ui_wallet.patterns.facade.FacadeMovements;

public class MovementDetailAdqPresenter implements IMovementDetailAdq {

    private IDetailAdqView view;
    private FacadeMovements.listenerMovements listener;
    private DataMovimientoAdq data;
    private MovementDetailAdqIteractor iteractor;

    public MovementDetailAdqPresenter(FacadeMovements.listenerMovements listener, IDetailAdqView view, DataMovimientoAdq data) {
        this.listener = listener;
        this.view = view;
        this.data = data;
        iteractor = new MovementDetailAdqIteractor(this);
    }

    @Override
    public void getDetailMovement() {
        listener.showLoader(App.getContext().getString(R.string.load_detail_movimientos));
        iteractor.getDetailFromService(data);
    }

    @Override
    public void completeDetailMov(DataMovimientoAdq response) {
        listener.hideLoader();
        data.setComision(response.getComision());
        data.setComisionIva(response.getComisionIva());
        data.setConcepto(response.getConcepto());
        data.setEsClosedLoop(response.isEsClosedLoop());
        data.setEsReversada(response.isEsReversada());
        data.setMarcaTarjetaBancaria(response.getMarcaTarjetaBancaria());
        data.setNoAutorizacion(response.getNoAutorizacion());
        data.setNoTicket(response.getNoTicket());
        data.setReferencia(response.getReferencia());
        data.setTipoReembolso(response.getTipoReembolso());
        data.setTipoTransaccion(response.getTipoTransaccion());
        data.setTransactionIdentity(response.getTransactionIdentity());
        view.printTicket(data);
    }

    @Override
    public void onErrorTicket(Object data) {
        listener.hideLoader();
        view.onError(data.toString());
    }
}
