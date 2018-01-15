package com.pagatodo.yaganaste.ui_wallet.interfaces;


import android.content.Context;
import android.view.View;

import com.pagatodo.yaganaste.R;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementView implements Serializable{

    final public static int ID_ENVIAR = 8;
    final public static int ID_SOLICITAR = 9;
    private int idOperacion;
    private int resource;
    private String title;

    public ElementView(){}

    public ElementView(int idOperacion, int resource, String title) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ArrayList<ElementView> getListEmisor(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.mipmap.icono_movimientos,"Movimientos"));
        elementViews.add(new ElementView(2, R.mipmap.icono_deposito,"Depósitos"));
        elementViews.add(new ElementView(3, R.mipmap.card_icon,"Administración"));
        return elementViews;
    }

    public static ArrayList<ElementView> getListStartBuck(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.mipmap.icono_movimientos,"Movimientos"));
        elementViews.add(new ElementView(4, R.mipmap.icono_deposito,"Añadir Saldo"));
        elementViews.add(new ElementView(5, R.mipmap.card_icon,"Tarjetas"));
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorAdq(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.mipmap.icono_movimientos,"Movimientos"));
        elementViews.add(new ElementView(6, R.mipmap.icon_tab_dongle_white,"Cobros"));
        elementViews.add(new ElementView(3, R.mipmap.icon_tab_dongle_white,"Administración"));
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorEmi(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(7, R.mipmap.icon_tab_dongle_white,"Registrar"));
        return elementViews;
    }

    public static ArrayList<ElementView> getListEnviar(Context context){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(ID_ENVIAR, R.mipmap.send_icon,context.getResources().getString(R.string.enviar_dinero)));
        elementViews.add(new ElementView(ID_SOLICITAR, R.mipmap.request_icon,context.getResources().getString(R.string.solicitar_pago)));
        return elementViews;
    }
}
