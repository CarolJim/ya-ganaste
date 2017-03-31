package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.AdqPayMovementsIteractorImp;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.MovementColorsFactory;
import com.pagatodo.yaganaste.utils.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdqPaymentesPresenter implements MovementsPresenter<AdquirentePaymentsTab>, MovementsManager<ResumenMovimientosAdqResponse> {

    private MovementsIteractor<ResumenMovimientosMesRequest>  movementsIteractor;
    private MovementsView<ItemMovements<DataMovimientoAdq>> movementsView;

    public AdqPaymentesPresenter(MovementsView<ItemMovements<DataMovimientoAdq>> movementsView) {
        this.movementsView = movementsView;
        this.movementsIteractor = new AdqPayMovementsIteractorImp(this);
    }

    @Override
    public void getRemoteMovementsData(AdquirentePaymentsTab data) {

        ResumenMovimientosMesRequest resumenMovimientosMesRequest = new ResumenMovimientosMesRequest();
        resumenMovimientosMesRequest.setFecha(data.getDate());
        movementsIteractor.getMovements(resumenMovimientosMesRequest);
    }


    @Override
    public void onSuccesResponse(ResumenMovimientosAdqResponse response) {
        if (response.getMovimientos() == null){
            movementsView.loadMovementsResult(new ArrayList<ItemMovements<DataMovimientoAdq>>());
        }
        List<ItemMovements<DataMovimientoAdq>> movementsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (DataMovimientoAdq movimientoAdq : response.getMovimientos()){
            calendar.setTime(DateUtil.getAdquirenteMovementDate(movimientoAdq.getFecha()));

            movementsList.add(new ItemMovements<>(movimientoAdq.getOperacion(), movimientoAdq.getCompania(),
                    StringUtils.getDoubleValue(movimientoAdq.getMonto()), String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                    DateUtil.getMonthShortName(calendar), MovementColorsFactory.getColorMovement(3), movimientoAdq));
            //// TODO: 28/03/2017 Verificar que codigo es 3
        }
        movementsView.loadMovementsResult(movementsList);
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {

    }
}