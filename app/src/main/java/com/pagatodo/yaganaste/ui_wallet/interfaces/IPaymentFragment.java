package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.util.List;

/**
 * Created by FranciscoManzo on 27/12/2017.
 */

public interface IPaymentFragment {
    void sendData(int position, int mType, int typePosition);

    void editFavorite(int position, int mType, int typePosition);

    void setDataFavorite(List<Favoritos> catalogos, int typeDataFav);

    void sendFavoriteToView(Favoritos favoritos, int mType);

    void errorFail(DataSourceResult error);

    void errorService();
}
