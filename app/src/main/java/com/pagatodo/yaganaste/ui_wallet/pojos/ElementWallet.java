package com.pagatodo.yaganaste.ui_wallet.pojos;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementWallet {

    public static final int TYPE_EMISOR = 11;
    public static final int TYPE_ADQ = 12;
    public static final int TYPE_STARBUCKS = 13;
    public static final int TYPE_SETTINGS = 14;

    private int typeWallet;
    private int resourceCard, resourceBack;
    private String saldo;
    private ArrayList<ElementView> elementViews;
    private int tipoSaldo;
    private boolean isUpdate;

    public ElementWallet() {
    }

    public ElementWallet(int typeWallet, int resourceCard, String saldo, ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate) {
        this.typeWallet = typeWallet;
        this.resourceCard = resourceCard;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
        this.isUpdate = isUpdate;
    }


    public ElementWallet(int typeWallet, int resourceCard, int resourceBack, String saldo, ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate) {
        this.typeWallet = typeWallet;
        this.resourceCard = resourceCard;
        this.resourceBack = resourceBack;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public int getTypeWallet() {
        return typeWallet;
    }

    public void setgetTypeWallet(int tipo_wallet) {
        this.typeWallet = tipo_wallet;
    }

    public int getResourceBack() {
        return resourceBack;
    }

    public void setResourceBack(int resourceBack) {
        this.resourceBack = resourceBack;
    }

    public int getResourceCard() {
        return resourceCard;
    }

    public void setResourceCard(int resourceCard) {
        this.resourceCard = resourceCard;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public ArrayList<ElementView> getElementViews() {
        return elementViews;
    }

    public void setElementViews(ArrayList<ElementView> elementViews) {
        this.elementViews = elementViews;
    }

    public int getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(int tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    //Datos seteado de prueb
    public ElementWallet getCardyaganaste() {
        return new ElementWallet(TYPE_EMISOR, R.drawable.tarjeta_yg,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisor(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardyaganasteBloqueda() {
        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_gray,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisor(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardStarbucks() {
        return new ElementWallet(TYPE_STARBUCKS, R.drawable.card_sbux,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)), new ElementView().getListStarbucks(),
                (R.string.saldo_disponible), true);
    }

    public ElementWallet getCardSettings() {
        return new ElementWallet(TYPE_SETTINGS, R.drawable.config_card,
                "Mis Tarjetas", new ElementView().getListConfigCard(),
                R.string.title_wallet_second_settings, false);
    }

    public ElementWallet getCardLectorAdq() {
        if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false)) {
            String leyenda;
            if (App.getInstance().getPrefs().loadDataInt(ID_ESTATUS) == IdEstatus.ADQUIRENTE.getId()) {
                leyenda = StringUtils.getCurrencyValue("0.00");
            } else {
                leyenda = "Cobra con tarjeta";
            }
            return new ElementWallet(TYPE_ADQ, R.mipmap.lector_front, leyenda,
                    new ElementView().getListLectorAdq(),
                    R.string.saldo_reembolso, true);
        } else {
            return getCardLectorEmi();
        }
    }


    public ElementWallet getCardLectorEmi() {
        return new ElementWallet(TYPE_ADQ, R.mipmap.lector_front,
                "Cobra con tarjeta",
                new ElementView().getListLectorEmi(),
                R.string.mejor_precio, true);
    }

    public ElementWallet getCardBalanceEmi() {
        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_blue, R.mipmap.back_yg_card_white,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisorBalance(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardBalanceEmiBloqueda() {
        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_gray, R.mipmap.card_back_backmara_2,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisorBalance(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardBalanceAdq() {
        return new ElementWallet(TYPE_ADQ, R.mipmap.lector_front, R.mipmap.lector_back,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)),
                new ElementView().getListAdqBalance(),
                R.string.saldo_reembolso, true);
    }


}
