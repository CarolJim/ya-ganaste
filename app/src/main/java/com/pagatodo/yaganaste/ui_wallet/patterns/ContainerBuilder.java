package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.InputTexAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.TextDataAdapter;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.IndicationZoneViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.holders.OptionsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.PaletteViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.StatusZoneViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.TextDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ICardBalance;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.Recursos;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ZONE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementView.OPTION_ZONE_UNO;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_ACERCA_DE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CANCELACION;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CODE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CONTACTO;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_SEGURIDAD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RADIOBUTTON;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOYALTY;


/**
 * Created by icruz on 09/02/2018.
 */

public class ContainerBuilder {

    public static final String MAINMENU = "MAINMENU";
    public static final String SETTINGS_MENU = "SETTINGS_MENU";

    public static void builder(Context context, ViewGroup parent, OnClickItemHolderListener listener, String type) {
        Container s = new Container(context, parent, listener);
        switch (type) {
            case MAINMENU:
                mainMenu(s);
                break;
            case SETTINGS_MENU:
                settingsMenu(s);
                break;
        }
    }

    private static void mainMenu(Container s) {
        int res = R.layout.option_menu_tem_view;
        //s.addItemOptionMenuIViewHolder(res, new OptionMenuItem(ID_CODE, R.drawable.ico_qr, R.string.navigation_drawer_menu_mi_qr));
        s.addItemOptionMenuIViewHolder(res, new OptionMenuItem(ID_SEGURIDAD, R.mipmap.ic_seguridad, R.string.navigation_drawer_menu_seguridad));
        s.addItemOptionMenuIViewHolder(res, new OptionMenuItem(ID_AJUSTES, R.mipmap.ic_ajustes, R.string.navigation_drawer_menu_ajustes));
        s.addItemOptionMenuIViewHolder(res, new OptionMenuItem(ID_ACERCA_DE, R.mipmap.ic_acerca, R.string.navigation_drawer_menu_acerca));
        s.addItemOptionMenuIViewHolder(res, new OptionMenuItem(ID_CONTACTO, R.mipmap.contacto, R.string.navigation_drawer_menu_contacto));
        s.addItemOptionMenuIViewHolder(res, new OptionMenuItem(ID_LOGOUT, R.mipmap.ic_close_session, R.string.navigation_drawer_logout, false));
    }

    private static void settingsMenu(Container s) {
        s.addItemViewHolderMenuSegurity(new OptionMenuItem(ID_DESVINCULAR, R.string.ajustes_desvincular_option, 0, RAW));
        //s.addItemViewHolderMenuSegurity(new OptionMenuItem(ID_CANCELACION, R.string.ajustes_cancelacion_option, 0, RAW));
    }

    /*public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> SECURITY_MENU(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenuSegurity(parent,new OptionMenuItem(ID_CCAMBIAR_PASS, R.string.change_your_pass,0, RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(-1, R.string.security_huella_option, R.string.security_huella_option_subtitle, RADIOBUTTON));
        return s.getArrayListOptionMenuSegurity();
    }*/

    /*public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> SETTINGS_MENU(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        //s.addOptionMenuSegurity(parent,new OptionMenuItem(ID_NOTIFICACIONES, R.string.ajustes_notificar_option, 0,RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(ID_DESVINCULAR, R.string.ajustes_desvincular_option,0, RAW));
        return s.getArrayListOptionMenuSegurity();
    }*/


    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> SETTINGS_NOTIFICACIONES(Context context, ViewGroup parent, OnClickItemHolderListener listener) {
        Container s = new Container(context, listener);
        s.addOptionMenuSegurity(parent, new OptionMenuItem(-1, R.string.notific_pagos_option, 0, RADIOBUTTON));
        s.addOptionMenuSegurity(parent, new OptionMenuItem(-1, R.string.notific_retiros_option, 0, RADIOBUTTON));
        s.addOptionMenuSegurity(parent, new OptionMenuItem(-1, R.string.notific_depositos_option, 0, RADIOBUTTON));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> ACERCA_DE(Context context, ViewGroup parent, OnClickItemHolderListener listener) {
        Container s = new Container(context, listener);
        s.addOptionMenuSegurity(parent, new OptionMenuItem(1, R.string.legales, 0, RAW));
        s.addOptionMenuSegurity(parent, new OptionMenuItem(2, R.string.aviso_privacidad, 0, RAW));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> LEGALES(Context context, ViewGroup parent, OnClickItemHolderListener listener) {
        Container s = new Container(context, listener);
        s.addOptionMenuSegurity(parent, new OptionMenuItem(1, R.string.aviso_privacidad_cuenta_ganaste, 0, RAW));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> ADMINISTRACION(Context context, ViewGroup parent, OnClickItemHolderListener listener) {
        Container s = new Container(context, listener);
        s.addOptionMenuSegurity(parent, new OptionMenuItem(1, R.string.my_card_change_nip, 0, RAW));
        s.addOptionMenuSegurity(parent, new OptionMenuItem(2, R.string.my_card_report, 0, RAW));
        s.addOptionMenuSegurity(parent, new OptionMenuItem(3, R.string.bloquear_tarjeta_admin, R.string.subtitle_bloquear_tarjeta, RADIOBUTTON));
        return s.getArrayListOptionMenuSegurity();
    }

    public static TextDataAdapter DEPOSITO(Context context, Container s) {
        return new TextDataAdapter(context, s.getTextDataList());
    }

    public static InputTexAdapter NIP(Context context) {
        Container s = new Container();
        s.addInputText(new InputText(R.string.nip_actual));
        s.addInputText(new InputText(R.string.nip_nuevo));
        s.addInputText(new InputText(R.string.nip_confima));
        return new InputTexAdapter(context, s.getInputTextList());
    }


    public static CardWalletAdpater getCardWalletAdapter(boolean error) {
        CardWalletAdpater adapter = new CardWalletAdpater();
        if (!error) {
            String statusCard = SingletonUser.getInstance().getCardStatusId();
            if (statusCard != null) {
                if (statusCard.equalsIgnoreCase(Recursos.ESTATUS_CUENTA_BLOQUEADA) || App.getInstance().getPrefs().loadData(CARD_NUMBER).equals("")) {
                    adapter.addCardItem(new ElementWallet().getCardyaganasteBloqueda());
                } else {
                    adapter.addCardItem(new ElementWallet().getCardyaganaste());
                }
            } else {
                adapter.addCardItem(new ElementWallet().getCardyaganaste());
            }

        } else {
            adapter.addCardItem(new ElementWallet().getCardyaganaste());
        }
        adapter.addCardItem(new ElementWallet().getCardLectorAdq());
        if (App.getInstance().getPrefs().loadDataBoolean(HAS_STARBUCKS, false)) {
            adapter.addCardItem(new ElementWallet().getCardStarbucks());
        }
        adapter.addCardItem(new ElementWallet().getCardSettings());
        return adapter;
    }

    private static ArrayList<PaletteViewHolder> list;

    public static void FAVORITOS(Context context, ViewGroup parent, List<Favoritos> listF, OnClickItemHolderListener listener) {
        Container builder = new Container(context, parent);
        Favoritos itemAdd = new Favoritos(0);
        itemAdd.setNombre("Agregar");
        builder.addSimpleHolder(itemAdd, listener);
        list = new ArrayList<>();
        for (Favoritos carouselItem : listF) {
            builder.addHolder(carouselItem, listener);
        }
        list = builder.getHoldersList();
    }

    public static void edition(boolean edition) {
        for (PaletteViewHolder item : list) {
            item.edition(edition);
        }
    }

    public static OptionsViewHolder getViewHolder(Activity context, ViewGroup parent, int typeholder) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        OptionsViewHolder op = null;
        //OPTION_ZONE
        switch (typeholder) {
            case OPTION_ZONE:
                op = new IndicationZoneViewHolder(context, inflater.inflate(R.layout.indicator_zone, parent, false));
                break;
            case OPTION_ZONE_UNO:
                op = new StatusZoneViewHolder(context, inflater.inflate(R.layout.indicator_zone_tipo_uno, parent, false));
                break;
            default:
                op = new ButtonsViewHolder(context, inflater.inflate(R.layout.view_element, parent, false));
                break;
        }

        return op;
    }

    public static TextDataViewHolder getViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TextDataViewHolder(inflater.inflate(R.layout.item_detail_mov, parent, false));
    }

}
