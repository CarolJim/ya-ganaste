package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.UtilsGraphics;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;
import static com.pagatodo.yaganaste.utils.StringUtils.ocultarCardNumberFormat;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.frontCardYg;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.getTextInBitmap;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.overlayImages;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementWallet {

    public static final int TYPE_EMISOR = 11;
    public static final int TYPE_ADQ = 12;
    public static final int TYPE_STARBUCKS = 13;
    public static final int TYPE_SETTINGS = 14;

    private int typeWallet;
    private Bitmap frontBitmap, rearBitmap;
    private String saldo;
    private ArrayList<ElementView> elementViews;
    private int tipoSaldo;
    private boolean isUpdate;

    public ElementWallet() {
    }

    public ElementWallet(int typeWallet, Bitmap frontBitmap, String saldo, ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate) {
        this.typeWallet = typeWallet;
        this.frontBitmap = frontBitmap;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
        this.isUpdate = isUpdate;
    }


    public ElementWallet(int typeWallet, Bitmap frontBitmap, Bitmap rearBitmap, String saldo, ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate) {
        this.typeWallet = typeWallet;
        this.frontBitmap = frontBitmap;
        this.rearBitmap = rearBitmap;
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

    public Bitmap getFrontBitmap() {
        return frontBitmap;
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

    public Bitmap getRearBitmap() {
        return rearBitmap;
    }

    public void setRearBitmap(Bitmap rearBitmap) {
        this.rearBitmap = rearBitmap;
    }

    public ElementWallet getCardyaganaste() {
        Bitmap frontView = frontCardYg(BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue));
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap rearView = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));
        Log.e("YA GANASTE", "Screen Density: " + App.getContext().getResources().getDisplayMetrics());
        return new ElementWallet(TYPE_EMISOR, frontView, rearView,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisor(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardyaganasteBloqueda() {
        Bitmap frontView = frontCardYg(BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray));
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));

        return new ElementWallet(TYPE_EMISOR, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisor(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardStarbucks() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux_back);
        Bitmap starbucksCode = UtilsGraphics.getStarbucksCode(backView);
        Bitmap resultBitmat = overlayImages(backView, starbucksCode,
                (backView.getWidth() / 2) - (starbucksCode.getWidth() / 2),
                (backView.getHeight() / 2) - (starbucksCode.getHeight() / 2));
        return new ElementWallet(TYPE_STARBUCKS, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)),
                new ElementView().getListStarbucks(),
                (R.string.saldo_disponible), true);
    }

    public ElementWallet getCardSettings() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.config_card);
        int cardsAvailable = 0;
        if (!App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
            cardsAvailable++;
        }
        return new ElementWallet(TYPE_SETTINGS, frontView, null,
                App.getContext().getString(R.string.title_wallet_main_settings) + cardsAvailable,
                new ElementView().getListConfigCard(), R.string.title_wallet_second_settings, false);
    }

    public ElementWallet getCardLectorAdq() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_front);
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
            return new ElementWallet(TYPE_ADQ, frontView, null, leyenda,
                    new ElementView().getListLectorAdq(), descripcion, isReload);
        } else {
            return getCardLectorEmi();
        }
    }


    public ElementWallet getCardLectorEmi() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_front);
        return new ElementWallet(TYPE_ADQ, frontView,
                "Cobra con tarjeta",
                new ElementView().getListLectorEmi(),
                R.string.mejor_precio, false);
    }

    public ElementWallet getCardBalanceEmi() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));

        return new ElementWallet(TYPE_EMISOR, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisorBalance(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardBalanceEmiBloqueda() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));

        return new ElementWallet(TYPE_EMISOR, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                new ElementView().getListEmisorBalance(),
                R.string.saldo_disponible, true);
    }

    public ElementWallet getCardBalanceAdq() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_front);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_back);
        return new ElementWallet(TYPE_ADQ, frontView, backView,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)),
                new ElementView().getListAdqBalance(),
                R.string.saldo_reembolso, true);
    }

    public ElementWallet getCardBalanceStarbucks() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux_back);
        Bitmap starbucksCode = UtilsGraphics.getStarbucksCode(backView);
        Bitmap resultBitmat = overlayImages(backView, starbucksCode,
                (backView.getWidth() / 2) - (starbucksCode.getWidth() / 2),
                (backView.getHeight() / 2) - (starbucksCode.getHeight() / 2));
        return new ElementWallet(TYPE_STARBUCKS, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)),
                new ElementView().getListStarbucksBalance(), R.string.saldo_disponible, false);
    }
}
