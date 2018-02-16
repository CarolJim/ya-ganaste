package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.StringConstants.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.StringConstants.USER_BALANCE;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementWallet {
    private int resourceCard, resourceBack;
    private String saldo;
    private ArrayList<ElementView> elementViews;
    private String tipoSaldo;

    public ElementWallet() {
    }

    public ElementWallet(int resourceCard, String saldo, ArrayList<ElementView> elementViews, String tipoSaldo) {
        this.resourceCard = resourceCard;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
    }

    public ElementWallet(int resourceCard, int resourceBack, String saldo, ArrayList<ElementView> elementViews, String tipoSaldo) {
        this.resourceCard = resourceCard;
        this.resourceBack = resourceBack;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
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

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    //Datos seteado de prueb
    public ElementWallet getCardyaganaste(Context context) {
        return new ElementWallet(R.drawable.tarjeta_yg,
                StringUtils.getCurrencyValue(SingletonUser.getInstance().getDatosSaldo().getSaldoEmisor()),
                new ElementView().getListEmisor(),
                context.getResources().getString(R.string.saldo_disponible));
    }

    public ElementWallet getCardyaganasteBloqueda(Context context) {
        return new ElementWallet(R.mipmap.main_card_zoom_gray,
                StringUtils.getCurrencyValue(SingletonUser.getInstance().getDatosSaldo().getSaldoEmisor()),
                new ElementView().getListEmisor(),
                context.getResources().getString(R.string.saldo_disponible));
    }

    public ElementWallet getCardStarBucks(Context context) {
        return new ElementWallet(R.drawable.starbucks_card,
                "$11,350.00", new ElementView().getListStartBuck(),
                context.getResources().getString(R.string.saldo_disponible));
    }

    public ElementWallet getCardLectorAdq(Context context) {
        return new ElementWallet(R.mipmap.lector_front,
                //Utils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)),
                Utils.getCurrencyValue("0.00"),
                new ElementView().getListLectorAdq(),
                context.getResources().getString(R.string.saldo_reembolso));
    }

    public ElementWallet getCardLectorEmi(Context context) {
        return new ElementWallet(R.mipmap.lector_front,
                Utils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListLectorEmi(),
                context.getResources().getString(R.string.saldo_reembolso));
    }

    public ElementWallet getCardBalanceEmi(Context context) {
        return new ElementWallet(R.mipmap.main_card_zoom_blue, R.mipmap.back_yg_card_white,
                Utils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisorBalance(context),
                context.getResources().getString(R.string.saldo_disponible));
    }

    public ElementWallet getCardBalanceEmiBloqueda(Context context) {
        return new ElementWallet(R.mipmap.main_card_zoom_gray, R.mipmap.card_back_backmara_2,
                StringUtils.getCurrencyValue(SingletonUser.getInstance().getDatosSaldo().getSaldoEmisor()),
                new ElementView().getListEmisorBalance(context),
                context.getResources().getString(R.string.saldo_disponible));
    }

    public ElementWallet getCardBalanceAdq(Context context) {
        return new ElementWallet(R.mipmap.lector_front, R.mipmap.lector_back,
                Utils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)),
                new ElementView().getListAdqBalance(context),
                context.getResources().getString(R.string.saldo_reembolso));
    }
}
