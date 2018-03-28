package com.pagatodo.yaganaste.ui_wallet.builder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui_wallet.adapters.CardWalletAdpater;
import com.pagatodo.yaganaste.ui_wallet.adapters.ElementsWalletAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.InputTexAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.TextDataAdapter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.Recursos;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_ACERCA_DE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CODE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_SEGURIDAD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RADIOBUTTON;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;


/**
 * Created by icruz on 09/02/2018.
 */

public class ContainerBuilder {

    public static ArrayList<InputText.ViewHolderInputText> NIP(Context context, ViewGroup parent){
        Container s = new Container(context);
        s.addLayout(parent, new InputText(R.string.nip_actual));
        s.addLayout(parent, new InputText(R.string.nip_nuevo));
        s.addLayout(parent, new InputText(R.string.nip_confima));
        return s.getArrayListInput();
    }

    public static ArrayList<OptionMenuItem.ViewHolderOptionMenuItme> MAINMENU(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenu(parent,new OptionMenuItem(ID_CODE, R.drawable.ico_qr, R.string.navigation_drawer_menu_mi_qr));
        s.addOptionMenu(parent,new OptionMenuItem(ID_SEGURIDAD, R.mipmap.ic_seguridad, R.string.navigation_drawer_menu_seguridad));
        s.addOptionMenu(parent,new OptionMenuItem(ID_AJUSTES, R.mipmap.ic_ajustes, R.string.navigation_drawer_menu_ajustes));
        s.addOptionMenu(parent,new OptionMenuItem(ID_ACERCA_DE, R.mipmap.ic_acerca, R.string.navigation_drawer_menu_acerca));
        s.addOptionMenu(parent,new OptionMenuItem(ID_LOGOUT, R.mipmap.ic_close_session, R.string.navigation_drawer_logout,false));
        return s.getArrayListOptionMenu();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> SECURITY_MENU(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenuSegurity(parent,new OptionMenuItem(ID_CCAMBIAR_PASS, R.string.change_your_pass,0, RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(-1, R.string.security_huella_option, R.string.security_huella_option_subtitle, RADIOBUTTON));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> SETTINGS_MENU(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        //s.addOptionMenuSegurity(parent,new OptionMenuItem(ID_NOTIFICACIONES, R.string.ajustes_notificar_option, 0,RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(ID_DESVINCULAR, R.string.ajustes_desvincular_option,0, RAW));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> SETTINGS_NOTIFICACIONES(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenuSegurity(parent,new OptionMenuItem(-1, R.string.notific_pagos_option,0, RADIOBUTTON));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(-1, R.string.notific_retiros_option,0, RADIOBUTTON));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(-1, R.string.notific_depositos_option,0, RADIOBUTTON));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> ACERCA_DE(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenuSegurity(parent,new OptionMenuItem(1, R.string.legales,0, RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(2, R.string.aviso_privacidad,0, RAW));
        return s.getArrayListOptionMenuSegurity();
    }

    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> LEGALES(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenuSegurity(parent,new OptionMenuItem(1, R.string.aviso_privacidad_cuenta_ganaste,0, RAW));
        return s.getArrayListOptionMenuSegurity();
    }
    public static ArrayList<OptionMenuItem.ViewHolderMenuSegurity> ADMINISTRACION(Context context, ViewGroup parent, OptionMenuItem.OnMenuItemClickListener listener){
        Container s = new Container(context,listener);
        s.addOptionMenuSegurity(parent,new OptionMenuItem(1, R.string.my_card_change_nip,0, RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(2, R.string.my_card_report,0, RAW));
        s.addOptionMenuSegurity(parent,new OptionMenuItem(3, R.string.bloquear_tarjeta_admin,R.string.subtitle_bloquear_tarjeta, RADIOBUTTON));
        return s.getArrayListOptionMenuSegurity();
    }

    public static TextDataAdapter DEPOSITO(Context context, Container s){
        return new TextDataAdapter(context,s.getTextDataList());
    }

    public static InputTexAdapter NIP(Context context){
        Container s = new Container();
        s.addInputText(new InputText(R.string.nip_actual));
        s.addInputText(new InputText(R.string.nip_nuevo));
        s.addInputText(new InputText(R.string.nip_confima));
        return new InputTexAdapter(context,s.getInputTextList());
    }

    public static ElementsWalletAdapter getAdapter(Activity activity, OnItemClickListener listener){
        ElementsWalletAdapter elementsWalletAdapter;
        boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE,false);
        int Idestatus = App.getInstance().getPrefs().loadDataInt(ID_ESTATUS);
        if (isAgente && Idestatus == IdEstatus.I7.getId()) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListEstadoRevisando(), 2);
        } else if (isAgente && Idestatus == IdEstatus.I8.getId()) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListEstadoRevisando(), 2);
        } else if (isAgente && Idestatus == IdEstatus.I9.getId()) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListEstadoError(), 2);
        } else if (isAgente && Idestatus == IdEstatus.I10.getId()) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListEstadoRechazado(), 2);
        } else if (isAgente && Idestatus == IdEstatus.I11.getId()) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListEstadoRevisando(), 2);
        } //else if (isAgente && Idestatus == IdEstatus.ADQUIRENTE.getId()) {
            //elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListLectorAdq(), 0);
        //}
        else if (isAgente && Idestatus == IdEstatus.I13.getId()) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListEstadoRechazado(), 2);
        } /*else if (isAgente && SingletonUser.getInstance().getDataUser().getEstatusDocumentacion() == Recursos.CRM_DOCTO_APROBADO) {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListLectorAdq(), 0);
        }*/ else {
            elementsWalletAdapter = new ElementsWalletAdapter(activity, listener, ElementView.getListLectorEmi(), 1);
        }
        return elementsWalletAdapter;
    }

    public static CardWalletAdpater getCardWalletAdapter(boolean error){
        CardWalletAdpater adpater = new CardWalletAdpater();
        boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);
        int statusDoc = App.getInstance().getPrefs().loadDataInt(ESTATUS_DOCUMENTACION);
        String statusCard = SingletonUser.getInstance().getCardStatusId();

        if (error) {
            adpater.addCardItem(new ElementWallet().getCardyaganaste());
        } else if (statusCard.equalsIgnoreCase(Recursos.ESTATUS_CUENTA_BLOQUEADA)) {
            adpater.addCardItem(new ElementWallet().getCardyaganasteBloqueda());
            if (isAgente && statusDoc == Recursos.CRM_DOCTO_APROBADO) {
                adpater.addCardItem(new ElementWallet().getCardLectorAdq());
            } else {
                adpater.addCardItem(new ElementWallet().getCardLectorEmi());
            }
        } else {
            adpater.addCardItem(new ElementWallet().getCardyaganaste());
            if (isAgente && statusDoc == Recursos.CRM_DOCTO_APROBADO) {
                adpater.addCardItem(new ElementWallet().getCardLectorAdq());
            } else {
                adpater.addCardItem(new ElementWallet().getCardLectorEmi());
            }
        }
        return adpater;
    }


}
