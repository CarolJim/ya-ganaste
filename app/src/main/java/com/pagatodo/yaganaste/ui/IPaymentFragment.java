package com.pagatodo.yaganaste.ui;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;

import java.util.List;

/**
 * Created by FranciscoManzo on 27/12/2017.
 */

public interface IPaymentFragment {
    void sendData(int position, int mType);

    void resToView();

    void setDataFavorite(List<DataFavoritos> catalogos, int typeDataFav);
}
