package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.UtilsGraphics;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
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
    private Bitmap bitmap;
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

    public ElementWallet(int typeWallet, int resourceCard, Bitmap bitmap, String saldo, ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate) {
        this.typeWallet = typeWallet;
        this.resourceCard = resourceCard;
        this.bitmap = bitmap;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    //Datos seteado de prueb
    public ElementWallet getCardyaganaste() {

        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = UtilsGraphics.createSingleImageFromMultipleImages(backView, qrCode);

        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_blue, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisor(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardyaganasteBloqueda() {
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = UtilsGraphics.createSingleImageFromMultipleImages(backView, qrCode);

        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_gray, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisor(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardStarbucks() {

        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(),R.mipmap.main_card_zoom_blue_back);
        Bitmap starbucksCode = UtilsGraphics.getStarbucksCode(backView);
        Bitmap resultBitmat = UtilsGraphics.createSingleImageFromMultipleImages(backView, starbucksCode);
        return new ElementWallet(TYPE_STARBUCKS, R.drawable.card_sbux,resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)),
                new ElementView().getListStarbucks(),
                (R.string.saldo_disponible), true);
    }

    public ElementWallet getCardSettings() {
        int cardsAvailable = 0;
        if (!App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
            cardsAvailable++;
        }
        return new ElementWallet(TYPE_SETTINGS, R.drawable.config_card, null,
                App.getContext().getString(R.string.title_wallet_main_settings) + cardsAvailable,
                new ElementView().getListConfigCard(), R.string.title_wallet_second_settings, false);
    }

    public ElementWallet getCardLectorAdq() {
        if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false)) {
            String leyenda;
            int descripcion;
            boolean isReload = true;
            if (App.getInstance().getPrefs().loadDataInt(ID_ESTATUS) == IdEstatus.ADQUIRENTE.getId()) {
                leyenda = StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE));
                descripcion = R.string.saldo_reembolso;
            } else {
                leyenda = "Cobra con tarjeta";
                descripcion = R.string.mejor_precio;
                isReload = false;
            }
            return new ElementWallet(TYPE_ADQ, R.mipmap.lector_front, null, leyenda,
                    new ElementView().getListLectorAdq(), descripcion, isReload);
        } else {
            return getCardLectorEmi();
        }
    }


    public ElementWallet getCardLectorEmi() {
        return new ElementWallet(TYPE_ADQ, R.mipmap.lector_front,
                "Cobra con tarjeta",
                new ElementView().getListLectorEmi(),
                R.string.mejor_precio, false);
    }

    public ElementWallet getCardBalanceEmi() {
        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_blue, R.mipmap.main_card_zoom_blue_back,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisorBalance(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardBalanceEmiBloqueda() {
        return new ElementWallet(TYPE_EMISOR, R.mipmap.main_card_zoom_gray, R.mipmap.main_card_zoom_gray_back,
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

    public ElementWallet getCardBalanceStarbucks() {
        return new ElementWallet(TYPE_STARBUCKS, R.drawable.card_sbux, R.drawable.card_sbux_back,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)),
                new ElementView().getListStarbucksBalance(), R.string.saldo_disponible, false);
    }
}
