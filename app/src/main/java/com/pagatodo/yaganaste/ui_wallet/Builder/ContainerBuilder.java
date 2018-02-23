package com.pagatodo.yaganaste.ui_wallet.Builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.adapters.InputTexAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.MenuAdapter;
import com.pagatodo.yaganaste.ui_wallet.adapters.TextDataAdapter;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_ACERCA_DE;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_AJUSTES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_CCAMBIAR_PASS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_DESVINCULAR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_LOGOUT;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_NOTIFICACIONES;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.ID_SEGURIDAD;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.TOGGLE;

/**
 * Created by icruz on 09/02/2018.
 */

public class ContainerBuilder {

    public static MenuAdapter MAINMENU(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(ID_SEGURIDAD, R.mipmap.ic_seguridad, R.string.navigation_drawer_menu_seguridad));
        s.addOption(new OptionMenuItem(ID_AJUSTES, R.mipmap.ic_ajustes, R.string.navigation_drawer_menu_ajustes));
        s.addOption(new OptionMenuItem(ID_ACERCA_DE, R.mipmap.ic_acerca, R.string.navigation_drawer_menu_acerca));
        s.addOption(new OptionMenuItem(ID_LOGOUT, R.mipmap.ic_close_session, R.string.navigation_drawer_logout));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static MenuAdapter SECURITY_MENU(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(ID_CCAMBIAR_PASS, R.string.change_your_pass,0, RAW));
        s.addOption(new OptionMenuItem(-1, R.string.security_huella_option, R.string.security_huella_option_subtitle, TOGGLE));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static MenuAdapter SETTINGS_MENU(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(ID_NOTIFICACIONES, R.string.ajustes_notificar_option, 0,RAW));
        s.addOption(new OptionMenuItem(ID_DESVINCULAR, R.string.ajustes_desvincular_option,0, RAW));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static MenuAdapter SETTINGS_NOTIFICACIONES(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(-1, R.string.notific_pagos_option,0, TOGGLE));
        s.addOption(new OptionMenuItem(-1, R.string.notific_retiros_option,0, TOGGLE));
        s.addOption(new OptionMenuItem(-1, R.string.notific_depositos_option,0, TOGGLE));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static MenuAdapter ACERCA_DE(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(1, R.string.legales,0, RAW));
        s.addOption(new OptionMenuItem(2, R.string.aviso_privacidad,0, RAW));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static MenuAdapter LEGALES(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(1, R.string.aviso_privacidad_cuenta_ganaste,0, RAW));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static MenuAdapter ADMINISTRACION(Context context, MenuAdapter.OnItemClickListener listener){
        Container s = new Container();
        s.addOption(new OptionMenuItem(1, R.string.my_card_change_nip,0, RAW));
        s.addOption(new OptionMenuItem(2, R.string.my_card_report,0, RAW));
        s.addOption(new OptionMenuItem(3, R.string.bloquear_tarjeta,R.string.subtitle_bloquear_tarjeta, TOGGLE));
        return new MenuAdapter(context,s.getOptions(),listener);
    }

    public static TextDataAdapter DEPOSITO(Context context, Container s){
        return new TextDataAdapter(context,s.getTextDataList());
    }

    public static InputTexAdapter NIP(Context context){
        Container s = new Container();
        s.addInputText(new InputText("Nip Actual","PAS"));
        s.addInputText(new InputText("Nuevo Nip","PAS"));
        s.addInputText(new InputText("Confirma Nuevo Nip","PAS"));
        return new InputTexAdapter(context,s.getInputTextList());
    }
    public static InputTexAdapter DatosUsuario(Context context, InputTexAdapter.ChangeEditListener listener){
        Container s = new Container();
        s.addInputText(new InputText("Correo electrónico","EMA"));
        s.addInputText(new InputText("Confirma Correo","EMA"));
        s.addInputText(new InputText("Contraseña","PAS"));
        s.addInputText(new InputText("Confirma Contraseña","PAS"));
        return new InputTexAdapter(context,s.getInputTextList(),listener);
    }

    public static ArrayList<InputText.ViewHolderInputText> NIP(Context context, ViewGroup parent){
        Container s = new Container(context);
        s.addLayout(parent, new InputText("Nip Actual","PAS"));
        s.addLayout(parent, new InputText("Nuevo Nip","PAS"));
        s.addLayout(parent, new InputText("Confirma Nuevo Nip","PAS"));
        return s.getArrayListInput();
    }


}
