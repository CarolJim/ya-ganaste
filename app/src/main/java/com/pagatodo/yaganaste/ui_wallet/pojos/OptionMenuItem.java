package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.content.Context;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.R;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.SWITCH;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.SWITCHNORMAL;

/**
 * Created by icruz on 08/01/2018.
 */

public class OptionMenuItem {

    private int idItem;
    private Context context;
    private int resourceItem;
    private String title;
    private String subtitle;
    private INDICATION indication;

    public OptionMenuItem(int idItem, String title, String subtitle, INDICATION indication) {
        this.idItem = idItem;
        this.title = title;
        this.indication = indication;
        this.resourceItem = -1;
        this.subtitle = subtitle;
    }


    public OptionMenuItem(int idItem, @Nullable int resourceItem, String title) {
        this.idItem = idItem;
        this.resourceItem = resourceItem;
        this.title = title;
    }

    public OptionMenuItem(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getResourceItem() {
        return resourceItem;
    }

    public void setResourceItem(int resourceItem) {
        this.resourceItem = resourceItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public INDICATION getIndication() {
        return indication;
    }

    public void setIndication(INDICATION indication) {
        this.indication = indication;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public ArrayList<OptionMenuItem> MAINMENU() {
        ArrayList<OptionMenuItem> optionMenuItems = new ArrayList<>();
        optionMenuItems.add(new OptionMenuItem(ID_SEGURIDAD, R.mipmap.ic_seguridad, this.context.getResources().getString(R.string.navigation_drawer_menu_seguridad)));
        //optionMenuItems.add(new OptionMenuItem(R.mipmap.ic_chat,this.context.getResources().getString(R.string.navigation_drawer_menu_chat)));
        optionMenuItems.add(new OptionMenuItem(ID_AJUSTES, R.mipmap.ic_ajustes, this.context.getResources().getString(R.string.navigation_drawer_menu_ajustes)));
        optionMenuItems.add(new OptionMenuItem(ID_ACERCA_DE, R.mipmap.ic_acerca, this.context.getResources().getString(R.string.navigation_drawer_menu_acerca)));
        optionMenuItems.add(new OptionMenuItem(ID_LOGOUT, R.mipmap.ic_close_session, this.context.getResources().getString(R.string.navigation_drawer_logout)));
        return optionMenuItems;
    }

    public ArrayList<OptionMenuItem> SECURITY_MENU() {
        ArrayList<OptionMenuItem> optionMenuItems = new ArrayList<>();
        optionMenuItems.add(new OptionMenuItem(ID_CCAMBIAR_PASS, this.context.getResources().getString(R.string.change_your_pass),"", RAW));
        optionMenuItems.add(new OptionMenuItem(-1, this.context.getResources().getString(R.string.security_huella_option), this.context.getResources().getString(R.string.security_huella_option_subtitle), SWITCH));
        return optionMenuItems;
    }

    public ArrayList<OptionMenuItem> SETTINGS_MENU() {
        ArrayList<OptionMenuItem> optionMenuItems = new ArrayList<>();
        optionMenuItems.add(new OptionMenuItem(ID_NOTIFICACIONES, this.context.getResources().getString(R.string.ajustes_notificar_option), "",RAW));
        optionMenuItems.add(new OptionMenuItem(ID_DESVINCULAR, this.context.getResources().getString(R.string.ajustes_desvincular_option),"", RAW));
        return optionMenuItems;
    }

    public ArrayList<OptionMenuItem> SETTINGS_NOTIFICACIONES() {
        ArrayList<OptionMenuItem> optionMenuItems = new ArrayList<>();
        optionMenuItems.add(new OptionMenuItem(-1, this.context.getResources().getString(R.string.notific_pagos_option),"", SWITCHNORMAL));
        optionMenuItems.add(new OptionMenuItem(-1, this.context.getResources().getString(R.string.notific_retiros_option),"", SWITCHNORMAL));
        optionMenuItems.add(new OptionMenuItem(-1, this.context.getResources().getString(R.string.notific_depositos_option),"", SWITCHNORMAL));
        return optionMenuItems;
    }

    public ArrayList<OptionMenuItem> ACERCA_DE() {
        ArrayList<OptionMenuItem> optionMenuItems = new ArrayList<>();
        optionMenuItems.add(new OptionMenuItem(1, this.context.getResources().getString(R.string.legales),"", RAW));
        optionMenuItems.add(new OptionMenuItem(2, this.context.getResources().getString(R.string.aviso_privacidad),"", RAW));
        return optionMenuItems;
    }

    public ArrayList<OptionMenuItem> LEGALES() {
        ArrayList<OptionMenuItem> optionMenuItems = new ArrayList<>();
        optionMenuItems.add(new OptionMenuItem(1, this.context.getResources().getString(R.string.aviso_privacidad_cuenta_ganaste),"", RAW));
        return optionMenuItems;
    }

    public enum INDICATION {
        RAW, SWITCH, SWITCHNORMAL
    }

    final public static int ID_SEGURIDAD = 1;
    final public static int ID_AJUSTES = 2;
    final public static int ID_ACERCA_DE = 3;
    final public static int ID_CCAMBIAR_PASS = 4;
    final public static int ID_NOTIFICACIONES = 5;
    final public static int ID_DESVINCULAR = 6;
    final public static int ID_LOGOUT = 7;

}
