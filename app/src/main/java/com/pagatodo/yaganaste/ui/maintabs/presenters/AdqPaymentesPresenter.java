package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.util.Log;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.DatosCupo;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ReembolsoDataRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.ResumenMovimientosMesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ReembolsoResponse;
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
import com.pagatodo.yaganaste.utils.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE_BALANCE_CUPO;

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
        movementsView.showLoader("Cargando mivimientos");
        ResumenMovimientosMesRequest resumenMovimientosMesRequest = new ResumenMovimientosMesRequest();
        resumenMovimientosMesRequest.setFecha(data.getDate());
        movementsIteractor.getMovements(resumenMovimientosMesRequest);
    }

    @Override
    public void getRemoteMovementsData(AdquirentePaymentsTab data, SwipyRefreshLayoutDirection direction, String lastId) {

    }

    @Override
    public void updateBalance() {
        if (SingletonUser.getInstance().getDataUser().getIdEstatus() == IdEstatus.CUPO.getId()) {
            movementsIteractor.getDatosCupo();
        } else {
            movementsIteractor.getBalance();
        }
    }

    @Override
    public void sendReembolso(DataMovimientoAdq dataMovimientoAdq) {
        movementsView.showLoader("Reembolsando cobro");
        ReembolsoDataRequest request = new ReembolsoDataRequest(dataMovimientoAdq);
        movementsIteractor.sendRemmbolso(request);
    }

    @Override
    public void onSuccesreembolso(ReembolsoResponse response) {
        movementsView.hideLoader();
        movementsView.loadReembolso();
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
                    movimientoAdq.getBancoEmisor()
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
        SingletonUser.getInstance().setDatosCupo(new DatosCupo(response.getLimiteDeCredito(), response.getSaldoDisponible(), response.getTotalADepositar(), response.getTotalAReembolsar()));
        App.getInstance().getPrefs().saveData(StringConstants.CUPO_BALANCE, response.getSaldoDisponible());
        App.getInstance().getPrefs().saveData(UPDATE_DATE_BALANCE_CUPO, DateUtil.getTodayCompleteDateFormat());
        movementsView.updateBalance();
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        movementsView.hideLoader();
        movementsView.showError(error);

    }
}