package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;

import java.util.List;

/**
 * Created by icruz on 12/02/2018.
 */

public interface IMovementsEmisorView extends IMainWalletView {
    void loadMovementsResult(List<ItemMovements<MovimientosResponse>> movementsList);
}
