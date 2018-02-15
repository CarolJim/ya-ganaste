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
    private int resourceTitle;
    private int subtitle;
    private INDICATION indication;
    private boolean statusSwtich = false;

    public OptionMenuItem(int idItem, int resourceTitle, int subtitle, INDICATION indication) {
        this.idItem = idItem;
        this.resourceTitle = resourceTitle;
        this.indication = indication;
        this.resourceItem = -1;
        this.subtitle = subtitle;
        this.statusSwtich = false;
    }

    public OptionMenuItem(int idItem, @Nullable int resourceItem, @Nullable int resourceTitle) {
        this.idItem = idItem;
        this.resourceItem = resourceItem;
        this.resourceTitle = resourceTitle;
        this.statusSwtich = false;
    }

    public boolean isStatusSwtich() {
        return statusSwtich;
    }

    public void setStatusSwtich(boolean statusSwtich) {
        this.statusSwtich = statusSwtich;
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

    public int getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(int resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public int getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(int subtitle) {
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
