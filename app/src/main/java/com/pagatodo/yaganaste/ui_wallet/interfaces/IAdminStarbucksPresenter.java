package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;

import java.util.List;

public interface IAdminStarbucksPresenter {
    List<CardStarbucks> getCardsOfUser();
}
