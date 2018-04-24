package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.adapters.AdminStarbucksAdapter;

import java.util.List;

public interface IAdminStarbucksPresenter {
    List<CardStarbucks> getCardsOfUser();
}
