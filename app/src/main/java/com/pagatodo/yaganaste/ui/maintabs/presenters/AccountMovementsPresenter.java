package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.text.TextUtils;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ConsultarMovimientosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ObtieneDatosCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ReembolsoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarMovimientosMesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarSaldoResponse;
import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.interfaces.enums.MovementsColors;
import com.pagatodo.yaganaste.ui.maintabs.controlles.MovementsView;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.AccountMovementsIteractorImp;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.MovementsIteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;
import com.pagatodo.yaganaste.utils.DateUtil;
import com.pagatodo.yaganaste.utils.StringConstants;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.REEMBOLSO_ADQUIRIENTE;
import static com.pagatodo.yaganaste.interfaces.enums.TipoTransaccionPCODE.getTipoTransaccionById;
import static com.pagatodo.yaganaste.utils.StringConstants.UPDATE_DATE;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;

public class AccountMovementsPresenter<T extends IEnumTab> extends TabPresenterImpl implements MovementsPresenter<MonthsMovementsTab>,
        MovementsManager<ConsultarMovimientosMesResponse, ConsultarSaldoResponse> {

    private MovementsIteractor<ConsultarMovimientosRequest> movementsIteractor;
    private MovementsView<ItemMovements<MovimientosResponse>, T> movementsView;

    public AccountMovementsPresenter(MovementsView<ItemMovements<MovimientosResponse>, T> movementsView) {
        super(movementsView);
        this.movementsView = movementsView;
        this.movementsIteractor = new AccountMovementsIteractorImp(this);
    }



    @Override
    public void getRemoteMovementsData(MonthsMovementsTab data) {
        movementsView.showLoader("Cargando movimientos");
        ConsultarMovimientosRequest request = new ConsultarMovimientosRequest();
        if (data.getYear() == -1) {
            request.setAnio("");
            request.setMes("");
        } else {
            request.setAnio(String.valueOf(data.getYear()));
            request.setMes(String.valueOf(data.getMonth() + 1));
        }
        request.setDireccion("");
        request.setIdMovimiento("");

        movementsIteractor.getMovements(request);
    }

    @Override
    public void getRemoteMovementsData(MonthsMovementsTab data, SwipyRefreshLayoutDirection direction, String lastId) {
        movementsView.showLoader("Cargando movimientos");
        ConsultarMovimientosRequest request = new ConsultarMovimientosRequest();
        if (data.getYear() == -1) {
            request.setAnio("");
            request.setMes("");
        } else {
            request.setAnio(String.valueOf(data.getYear()));
            request.setMes(String.valueOf(data.getMonth() + 1));
        }
        String directionReq = "";
        if (!TextUtils.isEmpty(lastId)) {
            directionReq = direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom";
        }
        request.setDireccion(directionReq);
        request.setIdMovimiento(lastId);

        movementsIteractor.getMovements(request);
    }

    @Override
    public void updateBalance() {
        movementsIteractor.getBalance();
    }

    @Override
    public void sendReembolso(DataMovimientoAdq dataMovimientoAdq) {

    }

    @Override
    public void onSuccesResponse(ConsultarMovimientosMesResponse response) {
        if (response.getData() == null) {
            movementsView.loadMovementsResult(new ArrayList<ItemMovements<MovimientosResponse>>());
        }
        List<ItemMovements<MovimientosResponse>> movementsList = new ArrayList<>();
        String[] date;
        for (MovimientosResponse movimientosResponse : response.getData()) {
            date = movimientosResponse.getFechaMovimiento().split(" ");
            // TODO: 28/03/2017 Verificar si el color debe ser local o si viene del servicio
            if (movimientosResponse.getIdTipoTransaccion()!=14) {
                movementsList.add(new ItemMovements<>(movimientosResponse.getDescripcion(), movimientosResponse.getDetalle(),
                        movimientosResponse.getTotal(), date[0], date[1],
                        MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                        movimientosResponse));
            } else {
                movementsList.add(new ItemMovements<>(movimientosResponse.getDetalle(), movimientosResponse.getConcepto(),
                        movimientosResponse.getTotal(), date[0], date[1],
                        MovementsColors.getMovementColorByType(movimientosResponse.getTipoMovimiento()).getColor(),
                        movimientosResponse));
            }
        }
        movementsView.loadMovementsResult(movementsList);
        movementsView.hideLoader();
    }

    @Override
    public void onSuccesBalance(ConsultarSaldoResponse response) {
        App.getInstance().getPrefs().saveData(UPDATE_DATE, DateUtil.getTodayCompleteDateFormat());
        App.getInstance().getPrefs().saveData(USER_BALANCE, response.getData().getSaldo());
        movementsView.updateBalance();
    }

    @Override
    public void onSuccessDataCupo(ObtieneDatosCupoResponse response) {

    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        movementsView.hideLoader();
        movementsView.showError(error);
    }

    @Override
    public void onSuccesreembolso(ReembolsoResponse response) {

    }
}