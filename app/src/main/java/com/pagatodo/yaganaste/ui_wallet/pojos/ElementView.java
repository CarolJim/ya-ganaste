package com.pagatodo.yaganaste.ui_wallet.pojos;


import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import java.io.Serializable;
import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_DESBLOQUEADA;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementView implements Serializable {

    final public static int ID_ENVIAR = 8;
    final public static int ID_SOLICITAR = 9;
    private int idOperacion;
    private int resource;
    private int title;

    public ElementView() {
    }

    public ElementView(int idOperacion, int resource, int title) {
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

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public static ArrayList<ElementView> getListEmisor() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(2, R.drawable.ico_depositos, R.string.operation_deposito));
        elementViews.add(new ElementView(3, R.drawable.ico_admin_tarj, R.string.operation_administracion));
        return elementViews;
    }


    public static ArrayList<ElementView> getListLectorAdq() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(6, R.drawable.ico_cobrar_in, R.string.operation_cobro));
        elementViews.add(new ElementView(3, R.drawable.ico_admin, R.string.operation_configurar));
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorEmi() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(7, R.mipmap.icon_tab_dongle_white, R.string.operation_registrar));
        return elementViews;
    }

    public static ArrayList<ElementView> getListEnviar(Context context) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(ID_ENVIAR, R.mipmap.send_icon, R.string.enviar_dinero));
        elementViews.add(new ElementView(ID_SOLICITAR, R.mipmap.request_icon, R.string.solicitar_pago));
        return elementViews;
    }

    public static ArrayList<ElementView> getListEmisorBalance(Context context) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        if (App.getInstance().getStatusId() == ESTATUS_CUENTA_DESBLOQUEADA) {
            elementViews.add(new ElementView(1, R.drawable.ic_bloquear, R.string.desbloquear_tarjeta));
        } else {
            elementViews.add(new ElementView(1, R.drawable.ic_bloquear, R.string.bloquear_tarjeta));
        }
        elementViews.add(new ElementView(2, R.drawable.ic_generar, R.string.generar_codigo));
        return elementViews;
    }

    public static ArrayList<ElementView> getListAdqBalance(Context context) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(3, R.drawable.ic_cobrar, R.string.realizar_cobro));
        //elementViews.add(new ElementView(4, R.drawable.ic_calc, context.getResources().getString(R.string.calcular_comisiones)));
        return elementViews;
    }
}
