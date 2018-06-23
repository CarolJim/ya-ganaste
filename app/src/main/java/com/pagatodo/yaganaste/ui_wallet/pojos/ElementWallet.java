package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UtilsGraphics;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ADQUIRENTE_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOYALTY;
import static com.pagatodo.yaganaste.utils.Recursos.STARBUCKS_BALANCE;
import static com.pagatodo.yaganaste.utils.Recursos.USER_BALANCE;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.frontCardBusiness;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.frontCardYg;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.overlayImages;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementWallet {

    public static final int TYPE_EMISOR = 11;
    public static final int TYPE_ADQ = 12;
    public static final int TYPE_STARBUCKS = 13;
    public static final int TYPE_SETTINGS = 14;
    public static final int TYPE_BUSINESS = 15;

    private int typeWallet;
    private Bitmap frontBitmap, rearBitmap;
    private String saldo;
    private ArrayList<ElementView> elementViews;
    private int tipoSaldo;
    private boolean isUpdate;
    private Agentes agentes;
    private int titleDesRes;
    private String cardNumberRes;

    public ElementWallet() {
    }

    public ElementWallet(int typeWallet, Bitmap frontBitmap, String saldo,
                         ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate) {
        this.typeWallet = typeWallet;
        this.frontBitmap = frontBitmap;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
        this.isUpdate = isUpdate;
    }

    public ElementWallet(int typeWallet, Bitmap frontBitmap, Bitmap rearBitmap, String saldo,
                         ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate,
                         Agentes agentes) {
        this.typeWallet = typeWallet;
        this.frontBitmap = frontBitmap;
        this.rearBitmap = rearBitmap;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
        this.isUpdate = isUpdate;
        this.agentes = agentes;
    }

    public ElementWallet(int typeWallet, Bitmap frontBitmap, Bitmap rearBitmap, String saldo,
                         ArrayList<ElementView> elementViews, int tipoSaldo, boolean isUpdate,
                         int titleDes, String cardNumber) {
        this.typeWallet = typeWallet;
        this.frontBitmap = frontBitmap;
        this.rearBitmap = rearBitmap;
        this.saldo = saldo;
        this.elementViews = elementViews;
        this.tipoSaldo = tipoSaldo;
        this.isUpdate = isUpdate;
        this.titleDesRes = titleDes;
        this.cardNumberRes = cardNumber;
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

    public void setFrontBitmap(Bitmap frontBitmap) {
        this.frontBitmap = frontBitmap;
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

    public Agentes getAgentes() {
        return agentes;
    }

    public void setAgentes(Agentes agentes) {
        this.agentes = agentes;
    }

    public int getTitleDesRes() {
        return titleDesRes;
    }

    public void setTitleDesRes(int titleDesRes) {
        this.titleDesRes = titleDesRes;
    }

    public String getCardNumberRes() {
        return cardNumberRes;
    }

    public void setCardNumberRes(String cardNumberRes) {
        this.cardNumberRes = cardNumberRes;
    }

    public static ElementWallet getCardyaganaste() {
        Bitmap frontView = frontCardYg(BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue));
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap rearView = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));
        Log.e("YA GANASTE", "Screen Density: " + App.getContext().getResources().getDisplayMetrics());
        return new ElementWallet(TYPE_EMISOR, frontView, rearView,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                ElementView.getListEmisor(),
                R.string.saldo_disponible, true, null);
    }

    public static ElementWallet getCardyaganasteBloqueda() {
        Bitmap frontView = frontCardYg(BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray));
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));

        return new ElementWallet(TYPE_EMISOR, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                ElementView.getListEmisor(),
                R.string.saldo_disponible, true, null);
    }

    public static ElementWallet getCardStarbucks() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux_back);
        Bitmap starbucksCode = UtilsGraphics.getStarbucksCode(backView);
        Bitmap resultBitmat = overlayImages(backView, starbucksCode,
                (backView.getWidth() / 2) - (starbucksCode.getWidth() / 2),
                (backView.getHeight() / 2) - (starbucksCode.getHeight() / 2));
        return new ElementWallet(TYPE_STARBUCKS, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)),
                ElementView.getListStarbucks(),
                (R.string.saldo_disponible), true, null);
    }

    public static ElementWallet getCardSettings() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.config_card);
        int cardsAvailable = 0;
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOYALTY, false)) {
            if (!App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
                cardsAvailable++;
            }
        }
        return new ElementWallet(TYPE_SETTINGS, frontView, null,
                App.getContext().getString(R.string.title_wallet_main_settings) + cardsAvailable,
                ElementView.getListConfigCard(""), R.string.title_wallet_second_settings, false, null);
    }

    public static ElementWallet getCardLectorAdq(Agentes agentes) {
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), isBluetooth ? R.drawable.chip_pin : R.mipmap.lector_front);

        if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false) && agentes != null) {
            if (isBluetooth && !agentes.getNombreNegocio().equals("")) {
                frontView = frontCardBusiness(frontView, agentes.getNombreNegocio());
            }
            String leyenda;
            int descripcion;
            boolean isReload = true;
            if (SingletonUser.getInstance().getDataUser().getUsuario().getIdEstatusEmisor() == IdEstatus.ADQUIRENTE.getId()) {
                /*if (agentes.getIdEstatus() == IdEstatus.ADQUIRENTE.getId()) {*/
                leyenda = StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE));
                descripcion = R.string.saldo_reembolso;
            } else {
                leyenda = "Cobra con tarjeta";
                descripcion = R.string.mejor_precio;
                isReload = false;
            }
            if (isBluetooth) {
                return new ElementWallet(TYPE_ADQ, null, null, leyenda,
                        ElementView.getListLectorAdq(agentes.getIdEstatus(), agentes.getOperadores(), agentes.getNombreNegocio(),agentes.getNumeroAgente(),""+agentes.getIdComercio(), agentes.isEsComercioUYU()), descripcion, isReload, agentes);
            } else {
                return new ElementWallet(TYPE_ADQ, frontView, null, leyenda,
                        ElementView.getListLectorAdq(agentes.getIdEstatus(), agentes.getOperadores(), agentes.getNombreNegocio(),agentes.getNumeroAgente(),""+agentes.getIdComercio(), agentes.isEsComercioUYU()), descripcion, isReload, agentes);
            }
        } else {
            return getCardLectorEmi();
        }
    }

    private static ElementWallet getCardLectorEmi() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_front);
        return new ElementWallet(TYPE_ADQ, frontView,
                "Cobra con tarjeta",
                ElementView.getListLectorEmi(),
                R.string.mejor_precio, false);
    }

    public static ElementWallet getCardMisNegocios() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.negocios);
        return new ElementWallet(TYPE_BUSINESS, frontView,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)),
                ElementView.getListMyBusiness(),
                R.string.saldo_reembolso_total, false);
    }

    public static ElementWallet getCardBalanceEmi() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_blue_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));

        return new ElementWallet(TYPE_EMISOR, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                ElementView.getListEmisorBalance(),
                R.string.saldo_disponible, true, R.string.tarjeta_yg, StringUtils.ocultarCardNumberFormat(App.getInstance().getPrefs().loadData(CARD_NUMBER)));
    }

    public static ElementWallet getCardBalanceEmiBloqueda() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.main_card_zoom_gray_back);
        Bitmap qrCode = UtilsGraphics.getQrCode(backView);
        Bitmap resultBitmat = overlayImages(backView, qrCode,
                (backView.getWidth() / 2) - (qrCode.getWidth() / 2),
                (backView.getHeight() / 2) - (qrCode.getHeight() / 2));

        return new ElementWallet(TYPE_EMISOR, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(USER_BALANCE)),
                ElementView.getListEmisorBalance(),
                R.string.saldo_disponible, true, R.string.tarjeta_yg, StringUtils.ocultarCardNumberFormat(App.getInstance().getPrefs().loadData(CARD_NUMBER)));
    }

    public static ElementWallet getCardBalanceAdq(Agentes agentes) {
        Bitmap frontView, backView;
        if (App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal()) {
            frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.lector_bt);
            backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.lector_bt_back);
        } else {
            frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_front);
            backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.lector_back);
        }
        return new ElementWallet(TYPE_ADQ, frontView, backView,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(ADQUIRENTE_BALANCE)),
                ElementView.getListAdqBalance(),
                R.string.saldo_reembolso, true, R.string.cobros_con_tarjeta, agentes.getNombreNegocio());
    }

    public static ElementWallet getCardBalanceStarbucks() {
        Bitmap frontView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux);
        Bitmap backView = BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.card_sbux_back);
        Bitmap starbucksCode = UtilsGraphics.getStarbucksCode(backView);
        Bitmap resultBitmat = overlayImages(backView, starbucksCode,
                (backView.getWidth() / 2) - (starbucksCode.getWidth() / 2),
                (backView.getHeight() / 2) - (starbucksCode.getHeight() / 2));
        return new ElementWallet(TYPE_STARBUCKS, frontView, resultBitmat,
                StringUtils.getCurrencyValue(App.getInstance().getPrefs().loadData(STARBUCKS_BALANCE)),
                ElementView.getListStarbucksBalance(), R.string.saldo_disponible, false, R.string.starbucks_card, App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS));
    }


}
