package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CardStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IAdminStarbucksPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_CARDS;

public class AdminStarbucksPresenter implements IAdminStarbucksPresenter {

    private Context context;

    public AdminStarbucksPresenter(Context context) {
        this.context = context;
    }

    @Override
    public List<CardStarbucks> getCardsOfUser() {
        List<CardStarbucks> cards = new ArrayList<>();
        String json = App.getInstance().getPrefs().loadData(STARBUCKS_CARDS);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CardStarbucks>> mapType = new TypeReference<List<CardStarbucks>>() {};
        try {
            cards = mapper.readValue(json, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
