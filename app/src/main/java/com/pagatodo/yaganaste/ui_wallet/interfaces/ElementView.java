package com.pagatodo.yaganaste.ui_wallet.interfaces;


import android.content.Context;
import android.view.View;

import com.pagatodo.yaganaste.R;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementView {

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

    public ArrayList<ElementView> getListWow(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.mipmap.finance,"Movimientos"));
        elementViews.add(new ElementView(1, R.mipmap.star,"Promociones"));
        elementViews.add(new ElementView(1, R.mipmap.map_marker,"Ubicaciones"));
        return elementViews;
    }

    public ArrayList<ElementView> getListStartBuck(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.mipmap.finance,"Movimientos"));
        elementViews.add(new ElementView(1, R.mipmap.wallet,"Añadir Saldo"));
        elementViews.add(new ElementView(1, R.mipmap.credit_card,"Tarjetas"));
        return elementViews;
    }

    public ArrayList<ElementView> getListLector(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.mipmap.finance,"Movimientos"));
        elementViews.add(new ElementView(1, R.mipmap.settings,"Mi Lector"));
        return elementViews;
    }
}
