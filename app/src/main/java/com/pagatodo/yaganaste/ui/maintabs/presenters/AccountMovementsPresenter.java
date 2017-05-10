package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.AccountMovementsIteractorImp;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.utils.MovementColorsFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AccountMovementsPresenter implements MovementsPresenter<MonthsMovementsTab>, MovementsManager<ConsultarMovimientosMesResponse> {

    private MovementsIteractor<ConsultarMovimientosRequest>  movementsIteractor;
    private MovementsView<ItemMovements<MovimientosResponse>> movementsView;

    public AccountMovementsPresenter(MovementsView<ItemMovements<MovimientosResponse>> movementsView) {
        this.movementsView = movementsView;
        this.movementsIteractor = new AccountMovementsIteractorImp(this);
    }

    @Override
    public void getRemoteMovementsData(MonthsMovementsTab data) {
        movementsView.showLoader("");

        ConsultarMovimientosRequest request = new ConsultarMovimientosRequest();
        if (data.getYear() == -1){
            request.setAnio("");
            request.setMes("");
        } else {
            request.setAnio(String.valueOf(data.getYear()));
            request.setMes(String.valueOf(data.getMonth()+1));
        }
        request.setDireccion("");
        request.setIdMovimiento("");

        movementsIteractor.getMovements(request);
    }


    @Override
    public void onSuccesResponse(ConsultarMovimientosMesResponse response) {

        if (response.getData() == null){
            movementsView.loadMovementsResult(new ArrayList<ItemMovements<MovimientosResponse>>());
        }
        List<ItemMovements<MovimientosResponse>> movementsList = new ArrayList<>();
        String[] date;
        for (MovimientosResponse movimientosResponse : response.getData()) {
            date = movimientosResponse.getFechaMovimiento().split(" ");
            // TODO: 28/03/2017 Verificar si el color debe ser local o si viene del servicio
            movementsList.add(new ItemMovements<>(movimientosResponse.getDetalle(), movimientosResponse.getDescripcion(),
                    (movimientosResponse.getTipoMovimiento() != 1 ? movimientosResponse.getImporte(): -movimientosResponse.getImporte())
            , date[0], date[1], MovementColorsFactory.getColorMovement(movimientosResponse.getTipoMovimiento()), movimientosResponse));
        }
        movementsView.loadMovementsResult(movementsList);
        movementsView.hideLoader();
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        movementsView.hideLoader();
        movementsView.showError(error);

    }
}