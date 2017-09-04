package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.util.Log;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.DatosCupo;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ResumenMovimientosAdqResponse;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.enums.EstatusMovimientoAdquirente;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.AdqPayMovementsIteractorImp;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_ADQ;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdqPaymentesPresenter<T extends IEnumTab> extends TabPresenterImpl implements MovementsPresenter<AdquirentePaymentsTab>,
        MovementsManager<ResumenMovimientosAdqResponse, String> {

    private MovementsIteractor<ResumenMovimientosMesRequest> movementsIteractor;
    private MovementsView<ItemMovements<DataMovimientoAdq>, T> movementsView;

    public AdqPaymentesPresenter(MovementsView<ItemMovements<DataMovimientoAdq>, T> movementsView) {
        super(movementsView);
        this.movementsView = movementsView;
        this.movementsIteractor = new AdqPayMovementsIteractorImp(this);
    }

    @Override
    public void getRemoteMovementsData(AdquirentePaymentsTab data) {
        //movementsView.showLoader("");
        ResumenMovimientosMesRequest resumenMovimientosMesRequest = new ResumenMovimientosMesRequest();
        resumenMovimientosMesRequest.setFecha(data.getDate());
        movementsIteractor.getMovements(resumenMovimientosMesRequest);


        /*List<DataMovimientoAdq> movimientos = new ArrayList<>();
        DataMovimientoAdq movimientoAdq = new DataMovimientoAdq();
        movimientoAdq.setAfiliacion("123");
        movimientoAdq.setOperacion("Venta Con Tarjeta 5334");
        movimientoAdq.setBancoEmisor("Bancomer");
        movimientoAdq.setCompania("Bancomer");
        movimientoAdq.setDescripcionRechazo("Por que si");
        movimientoAdq.setEsAprobada(false);
        movimientoAdq.setEsCargo(false);
        movimientoAdq.setEsPendiente(true);
        movimientoAdq.setEsReversada(false);
        movimientoAdq.setFecha("17/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq.setIdTransaction("12345678");
        movimientoAdq.setMarcaTarjetaBancaria("cualquierdato");
        movimientoAdq.setNoAutorizacion("123453221");
        movimientoAdq.setMonto("2000.00");
        movimientoAdq.setTipoTarjetaBancaria("VISA");


        DataMovimientoAdq movimientoAdq2 = new DataMovimientoAdq();
        movimientoAdq2.setOperacion("Venta Con Tarjeta 5332");
        movimientoAdq2.setBancoEmisor("Bancomer");
        movimientoAdq2.setEsAprobada(true);
        movimientoAdq2.setEsCargo(false);
        movimientoAdq2.setEsPendiente(false);
        movimientoAdq2.setEsReversada(false);
        movimientoAdq2.setFecha("16/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq2.setMonto("1700.00");


        DataMovimientoAdq movimientoAdq3 = new DataMovimientoAdq();
        movimientoAdq3.setOperacion("Venta Con Tarjeta 5603");
        movimientoAdq3.setBancoEmisor("Santander");
        movimientoAdq3.setEsAprobada(true);
        movimientoAdq3.setEsCargo(false);
        movimientoAdq3.setEsPendiente(false);
        movimientoAdq3.setEsReversada(false);
        movimientoAdq3.setFecha("15/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq3.setMonto("120.50");

        DataMovimientoAdq movimientoAdq4 = new DataMovimientoAdq();
        movimientoAdq4.setOperacion("Venta Con Tarjeta 5345(Cancelada)");
        movimientoAdq4.setBancoEmisor("Bancomer");
        movimientoAdq4.setEsAprobada(false);
        movimientoAdq4.setEsCargo(false);
        movimientoAdq4.setEsPendiente(false);
        movimientoAdq4.setEsReversada(true);
        movimientoAdq4.setFecha("15/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq4.setMonto("300.00");


        DataMovimientoAdq movimientoAdq5 = new DataMovimientoAdq();
        movimientoAdq5.setOperacion("Venta Con Tarjeta 3495(Cancelada)");
        movimientoAdq5.setBancoEmisor("Santander");
        movimientoAdq5.setEsAprobada(false);
        movimientoAdq5.setEsCargo(false);
        movimientoAdq5.setEsPendiente(false);
        movimientoAdq5.setEsReversada(true);
        movimientoAdq5.setFecha("14/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq5.setMonto("1000.00");

        DataMovimientoAdq movimientoAdq6 = new DataMovimientoAdq();
        movimientoAdq6.setOperacion("Venta Con Tarjeta 7463");
        movimientoAdq6.setBancoEmisor("Banamex");
        movimientoAdq6.setEsAprobada(false);
        movimientoAdq6.setEsCargo(true);
        movimientoAdq6.setEsPendiente(false);
        movimientoAdq6.setEsReversada(false);
        movimientoAdq6.setFecha("13/Jul/2017 17:00");//dd/MMM/yyyy hh:mm
        movimientoAdq6.setMonto("1570.00");


        movimientos.add(movimientoAdq);
        movimientos.add(movimientoAdq2);
        movimientos.add(movimientoAdq3);
        movimientos.add(movimientoAdq4);
        movimientos.add(movimientoAdq5);
        movimientos.add(movimientoAdq6);


        DataResultAdq result = new DataResultAdq();
        result.setId("123");
        result.setMessage("Ok");
        result.setTitle("Title");


        ResumenMovimientosAdqResponse response = new ResumenMovimientosAdqResponse(movimientos,
                result, "15000.00", "14000.00", "", "");

        onSuccesResponse(response);*/
    }

    @Override
    public void getRemoteMovementsData(AdquirentePaymentsTab data, SwipyRefreshLayoutDirection direction, String lastId) {

    }

    @Override
    public void updateBalance() {
        if (SingletonUser.getInstance().getDataUser().getIdEstatus() == IdEstatus.I16.getId()) {
            movementsIteractor.getDatosCupo();
        } else {
            movementsIteractor.getBalance();
        }
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

            //movimientoAdq.setEsPendiente(true);
            //movimientoAdq.setEsAprobada(false);
            int color = EstatusMovimientoAdquirente.getEstatusById(movimientoAdq.getEstatus()).getColor();

            /*int color;
            if (movimientoAdq.isEsCargo()) {
                color = CARGO.getColor();
            } else if (movimientoAdq.isEsReversada()) {
                color = CANCELADO.getColor();
            } else if (movimientoAdq.isEsPendiente()) {
                color = PENDIENTE.getColor();
            } else {
                color = APROBADO.getColor();
            }*/

            movementsList.add(new ItemMovements<>(movimientoAdq.getOperacion(),
                    movimientoAdq.getConcepto()
                    /*movimientoAdq.getBancoEmisor().concat(SPACE).concat(
                            movimientoAdq.isEsReversada() ? "- " + App.getInstance().getString(R.string.cancelada) :
                                    movimientoAdq.isEsPendiente() ? "- " + App.getInstance().getString(R.string.pendiente) : SPACE)*/,
                    StringUtils.getDoubleValue(movimientoAdq.getMonto()), String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                    DateUtil.getMonthShortName(calendar), color, movimientoAdq));
        }
        movementsView.loadMovementsResult(movementsList);
        movementsView.hideLoader();
    }

    @Override
    public void onSuccesBalance(String response) {
        SingletonUser.getInstance().getDatosSaldo().setSaldoAdq(response);
        App.getInstance().getPrefs().saveData(StringConstants.ADQUIRENTE_BALANCE, response);
        App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_ADQ, DateUtil.getTodayCompleteDateFormat());
        movementsView.updateBalance();
    }

    @Override
    public void onSuccessDataCupo(ObtieneDatosCupoResponse response) {
        SingletonUser.getInstance().setDatosCupo(new DatosCupo(response.getLimiteDeCredito(), response.getSaldoDisponible(), response.getTotalADepositar()));
        movementsView.updateBalance();
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        movementsView.hideLoader();
        movementsView.showError(error);

    }
}