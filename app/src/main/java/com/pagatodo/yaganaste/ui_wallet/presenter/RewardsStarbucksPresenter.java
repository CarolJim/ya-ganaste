package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IRewardsStarbucksPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.REWARDS;

public class RewardsStarbucksPresenter implements IRewardsStarbucksPresenter {

    private Context context;

    public RewardsStarbucksPresenter(Context context) {
        this.context = context;
    }

    @Override
    public List<Rewards> getMyRewards() {
        List<Rewards> rewards = new ArrayList<>();
        String json = App.getInstance().getPrefs().loadData(REWARDS);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Rewards>> mapType = new TypeReference<List<Rewards>>() {
        };
        try {
            rewards = mapper.readValue(json, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rewards;
    }
}
