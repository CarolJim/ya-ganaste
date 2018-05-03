package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.ui_wallet.dto.DtoAdminCards;

import java.util.List;

public interface IAdminCardsPresenter {
    List<DtoAdminCards> getCardsList();
    void deleteStarbucksInfo();
}
