package com.pagatodo.yaganaste.ui_wallet.pojos;


import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import java.io.Serializable;
import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
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
    private int description;
    private boolean status;
    private boolean color;
    private int textbutton;


    public ElementView() {
    }

    public ElementView(int idOperacion, int resource, int title) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
    }

    public ElementView(int idOperacion, int resource, int title, int description, boolean status, boolean color, int textbutton) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
        this.description = description;
        this.status = status;
        this.color = color;
        this.textbutton = textbutton;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getTextbutton() {
        return textbutton;
    }

    public void setTextbutton(int textbutton) {
        this.textbutton = textbutton;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
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

    //Proceso Revisando
    public static ArrayList<ElementView> getListEstadoRevisando() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(10, R.drawable.ico_revision, R.string.title_tipo_uno, R.string.title_tipo_desc, false, false, R.string.next));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoAprobado() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(11, R.drawable.ic_check_success, R.string.felicidades, R.string.ya_se_puede, true, false, R.string.next));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoError() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(12, R.drawable.ico_alert_red, R.string.ocurrio_error_doc, R.string.tuvimos_problema, true, true, R.string.btn_reenviar));
        return elementViews;
    }

    //Proceso Rechazado
    public static ArrayList<ElementView> getListEstadoRechazado() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(13, R.drawable.ico_alert_red, R.string.tu_solicitud_no, R.string.tuvimos_problema_solicitud, false, true, R.string.btn_reenviar));
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
        if (App.getInstance().getStatusId().equals(ESTATUS_CUENTA_BLOQUEADA)) {
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
