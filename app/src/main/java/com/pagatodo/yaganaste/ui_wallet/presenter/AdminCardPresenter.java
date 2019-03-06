package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoAdminCards;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IAdminCardsPresenter;
import com.pagatodo.yaganaste.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.adapters.AdminCardsAdapter.TYPE_HEADER;
import static com.pagatodo.yaganaste.ui_wallet.adapters.AdminCardsAdapter.TYPE_ITEM;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ACTUAL_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.EMAIL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.FAVORITE_DRINK;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_MIEMBRO_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.IS_COACHMARK;
import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_NUMBER_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_SINCE;
import static com.pagatodo.yaganaste.utils.Recursos.MISSING_STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NEXT_LEVEL_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.REWARDS;
import static com.pagatodo.yaganaste.utils.Recursos.SECURITY_TOKEN_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOYALTY;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_CARDS;
import static com.pagatodo.yaganaste.utils.Recursos.STARS_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_GOLD;

public class AdminCardPresenter implements IAdminCardsPresenter {

    private Context context;

    public AdminCardPresenter(Context context) {
        this.context = context;
    }

    @Override
    public List<DtoAdminCards> getCardsList() {
        List<DtoAdminCards> cards = new ArrayList<>();
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        cards.add(new DtoAdminCards(-1, 0, 0, 0, TYPE_HEADER, context.getString(R.string.admin_cards_active),
                null));

        cards.add(new DtoAdminCards(TYPE_EMISOR, R.mipmap.main_card_zoom_blue, 0, 1, TYPE_ITEM,
                context.getString(R.string.tarjeta_yg), StringUtils.getCreditCardFormat(App.getInstance().getPrefs().loadData(CARD_NUMBER))));
        if (!App.getInstance().getPrefs().loadDataBoolean(IS_COACHMARK, true)){
            cards.add(new DtoAdminCards(TYPE_ADQ, R.mipmap.lector_front, 1, 1, TYPE_ITEM,
                    context.getString(R.string.lector_yg), App.getInstance().getPrefs().loadData(COMPANY_NAME)));
        } else {
            cards.add(new DtoAdminCards(TYPE_ADQ, R.drawable.lector_bt, 1, 1, TYPE_ITEM,
                    context.getString(R.string.lector_yg), App.getInstance().getPrefs().loadData(COMPANY_NAME)));
        }

        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOYALTY, false)) {
            if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
                cards.add(new DtoAdminCards(TYPE_STARBUCKS, R.drawable.card_sbux, 3, 1, TYPE_ITEM,
                        context.getString(R.string.starbucks_card), StringUtils.getCreditCardFormat(App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS))));
            } else {
                cards.add(new DtoAdminCards(-1, 0, 0, 0, TYPE_HEADER, context.getString(R.string.admin_cards_available),
                        null));
                cards.add(new DtoAdminCards(TYPE_STARBUCKS, R.drawable.card_sbux, 3, 0, TYPE_ITEM,
                        context.getString(R.string.starbucks_card), context.getString(R.string.starbucks_available)));
            }
        }
        return cards;
    }

    @Override
    public void deleteStarbucksInfo() {
        App.getInstance().getPrefs().saveDataBool(HAS_STARBUCKS, false);
        App.getInstance().getPrefs().clearPreference(STARBUCKS_CARDS);
        App.getInstance().getPrefs().clearPreference(REWARDS);
        App.getInstance().getPrefs().clearPreference(FAVORITE_DRINK);
        App.getInstance().getPrefs().clearPreference(EMAIL_STARBUCKS);
        App.getInstance().getPrefs().clearPreference(MEMBER_SINCE);
        App.getInstance().getPrefs().clearPreference(STATUS_GOLD);
        App.getInstance().getPrefs().clearPreference(ID_MIEMBRO_STARBUCKS);
        App.getInstance().getPrefs().clearPreference(ACTUAL_LEVEL_STARBUCKS);
        App.getInstance().getPrefs().clearPreference(STARS_NUMBER);
        App.getInstance().getPrefs().clearPreference(MISSING_STARS_NUMBER);
        App.getInstance().getPrefs().clearPreference(NEXT_LEVEL_STARBUCKS);
        App.getInstance().getPrefs().clearPreference(MEMBER_NUMBER_STARBUCKS);
        App.getInstance().getPrefs().clearPreference(SECURITY_TOKEN_STARBUCKS);
        App.getInstance().getPrefs().clearPreference(NUMBER_CARD_STARBUCKS);
    }
}
