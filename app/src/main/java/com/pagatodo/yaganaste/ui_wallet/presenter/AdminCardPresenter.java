package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

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
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;

public class AdminCardPresenter implements IAdminCardsPresenter {

    private Context context;

    public AdminCardPresenter(Context context) {
        this.context = context;
    }

    @Override
    public List<DtoAdminCards> getCardsList() {
        List<DtoAdminCards> cards = new ArrayList<>();
        cards.add(new DtoAdminCards(-1, 0, 0, 0, TYPE_HEADER, context.getString(R.string.admin_cards_active),
                null));
        cards.add(new DtoAdminCards(TYPE_EMISOR, R.mipmap.main_card_zoom_blue, 0, 1, TYPE_ITEM,
                context.getString(R.string.tarjeta_yg), StringUtils.getCreditCardFormat(App.getInstance().getPrefs().loadData(CARD_NUMBER))));
        cards.add(new DtoAdminCards(TYPE_ADQ, R.mipmap.lector_front, 1, 1, TYPE_ITEM,
                context.getString(R.string.lector_yg), App.getInstance().getPrefs().loadData(COMPANY_NAME)));
        if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
            cards.add(new DtoAdminCards(TYPE_STARBUCKS, R.drawable.card_sbux, 3, 1, TYPE_ITEM,
                    context.getString(R.string.starbucks_card), ""));
        } else {
            cards.add(new DtoAdminCards(-1, 0, 0, 0, TYPE_HEADER, context.getString(R.string.admin_cards_available),
                    null));
            cards.add(new DtoAdminCards(TYPE_STARBUCKS, R.drawable.card_sbux, 3, 0, TYPE_ITEM,
                    context.getString(R.string.starbucks_card), context.getString(R.string.starbucks_available)));
        }

        return cards;
    }
}
