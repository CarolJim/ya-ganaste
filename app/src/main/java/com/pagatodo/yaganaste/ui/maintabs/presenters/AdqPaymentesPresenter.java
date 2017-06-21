package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.util.Log;

import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.AdqPayMovementsIteractorImp;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.APROBADO;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CANCELADO;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.CARGO;
import static com.pagatodo.yaganaste.interfaces.enums.MovementsColors.PENDIENTE;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdqPaymentesPresenter<T extends IEnumTab> extends TabPresenterImpl implements MovementsPresenter<AdquirentePaymentsTab>, MovementsManager<ResumenMovimientosAdqResponse, String> {

    private MovementsIteractor<ResumenMovimientosMesRequest> movementsIteractor;
    private MovementsView<ItemMovements<DataMovimientoAdq>, T> movementsView;

    public AdqPaymentesPresenter(MovementsView<ItemMovements<DataMovimientoAdq>, T> movementsView) {
        super(movementsView);
        this.movementsView = movementsView;
        this.movementsIteractor = new AdqPayMovementsIteractorImp(this);
    }

    @Override
    public void getRemoteMovementsData(AdquirentePaymentsTab data) {
        movementsView.showLoader("");
        ResumenMovimientosMesRequest resumenMovimientosMesRequest = new ResumenMovimientosMesRequest();
        resumenMovimientosMesRequest.setFecha(data.getDate());
        movementsIteractor.getMovements(resumenMovimientosMesRequest);
        /*
        List<DataMovimientoAdq> movimientos = new ArrayList<>();
        DataMovimientoAdq movimientoAdq = new DataMovimientoAdq();
        movimientoAdq.setAfiliacion("123");
        movimientoAdq.setBancoEmisor("Banco felicidad");
        movimientoAdq.setCompania("PAtito");
        movimientoAdq.setDescripcionRechazo("Por que si");
        movimientoAdq.setEsAprobada(true);
        movimientoAdq.setEsCargo(true);
        movimientoAdq.setEsPendiente(true);
        movimientoAdq.setFecha("17/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq.setIdTransaction("12345678");
        movimientoAdq.setMarcaTarjetaBancaria("cualquierdato");
        movimientoAdq.setNoAutorizacion("123453221");
        movimientoAdq.setMonto("2000.00");
        movimientoAdq.setTipoTarjetaBancaria("VISA");
        movimientos.add(movimientoAdq);

        DataResultAdq result = new DataResultAdq();
        result.setId("123");
        result.setMessage("Ok");
        result.setTitle("Title");

        ResumenMovimientosAdqResponse response = new ResumenMovimientosAdqResponse(movimientos,
                result, "15000.00", "14000.00", "", "");

        onSuccesResponse(response);*/
    }

    @Override
    public void updateBalance() {
        movementsIteractor.getBalance();
    }


    @Override
    public void onSuccesResponse(ResumenMovimientosAdqResponse response) {
        Log.e("AdqPaymentesPresenter", " response " + response.getMovimientos());
        if (response.getMovimientos() == null) {
            movementsView.loadMovementsResult(new ArrayList<ItemMovements<DataMovimientoAdq>>());
        }
        List<ItemMovements<DataMovimientoAdq>> movementsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (DataMovimientoAdq movimientoAdq : response.getMovimientos()) {
            calendar.setTime(DateUtil.getAdquirenteMovementDate(movimientoAdq.getFecha()));
            movimientoAdq.setEsPendiente(true);
            int color;
            if (movimientoAdq.isEsCargo()) {
                color = CARGO.getColor();
            } else if (movimientoAdq.isEsReversada()) {
                color = CANCELADO.getColor();
            } else if (movimientoAdq.isEsPendiente()) {
                color = PENDIENTE.getColor();
            } else {
                color = APROBADO.getColor();
            }

            movementsList.add(new ItemMovements<>(movimientoAdq.getOperacion(), movimientoAdq.getCompania(),
                    StringUtils.getDoubleValue(movimientoAdq.getMonto()), String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                    DateUtil.getMonthShortName(calendar), color, movimientoAdq));
        }
        movementsView.loadMovementsResult(movementsList);
        movementsView.hideLoader();
    }

    @Override
    public void onSuccesBalance(String response) {
        SingletonUser.getInstance().getDatosSaldo().setSaldoAdq(response);
        movementsView.updateBalance();
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        movementsView.hideLoader();
        movementsView.showError(error);

    }
}