package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.utils.customviews.CustomRadioButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

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
    private boolean toggleState = false;
    private boolean divider = true;

    public OptionMenuItem(int idItem, int resourceTitle, int subtitle, INDICATION indication) {
        this.idItem = idItem;
        this.resourceTitle = resourceTitle;
        this.indication = indication;
        this.resourceItem = -1;
        this.subtitle = subtitle;
        this.toggleState = false;
    }

    public OptionMenuItem(int idItem, @Nullable int resourceItem, @Nullable int resourceTitle) {
        this.idItem = idItem;
        this.resourceItem = resourceItem;
        this.resourceTitle = resourceTitle;
        this.toggleState = false;
    }

    public OptionMenuItem(int idItem, @Nullable int resourceItem, @Nullable int resourceTitle, boolean divider) {
        this.idItem = idItem;
        this.resourceItem = resourceItem;
        this.resourceTitle = resourceTitle;
        this.toggleState = false;
        this.divider = divider;
    }

    public boolean isDivider() {
        return divider;
    }

    public void setDivider(boolean divider) {
        this.divider = divider;
    }

    public boolean isStatusSwtich() {
        return toggleState;
    }

    public void setStatusSwtich(boolean statusSwtich) {
        this.toggleState = statusSwtich;
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
        RAW, RADIOBUTTON,CHECKBOX
    }

    final public static int ID_SEGURIDAD = 1;
    final public static int ID_AJUSTES = 2;
    final public static int ID_ACERCA_DE = 3;
    final public static int ID_CCAMBIAR_PASS = 4;
    final public static int ID_NOTIFICACIONES = 5;
    final public static int ID_DESVINCULAR = 6;
    final public static int ID_LOGOUT = 7;
    final public static int ID_CODE = 8;
    final public static int ID_CANCELACION = 9;
    final public static int ID_CONTACTO = 10;


    /*public static class OptionMenuIViewHolder {
        public RelativeLayout relativeLayout;
        public ImageView imageView;
        public StyleTextView title;
        public View dividerList;
    }*/

    public static class ViewHolderMenuSegurity {
        public RelativeLayout relativeLayout;
        public ImageView raw;
        public StyleTextView title;
        public StyleTextView subtitle;
        public CustomRadioButton radioButtonNo;
        public CustomRadioButton radioButtonSi;
        public RadioGroup radioGroup;
        public CheckBox checkBox;
    }

    public interface OnMenuItemClickListener {
        void OnMenuItem(OptionMenuItem optionMenuItem);
    }
}
