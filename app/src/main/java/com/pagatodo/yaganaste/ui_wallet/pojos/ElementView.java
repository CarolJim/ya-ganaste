package com.pagatodo.yaganaste.ui_wallet.pojos;


import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;

import java.io.Serializable;
import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_DESBLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;

/**
 * Created by icruz on 12/12/2017.
 */

public class ElementView implements Serializable {

    final public static int ID_ENVIAR = 8;
    final public static int ID_SOLICITAR = 9;

    public static final int OPTION_SIMPLE = 0;
    public static final int OPTION_ZONE = 1;
    public static final int OPTION_ZONE_UNO = 2;


    private int idOperacion;
    private int resource;
    private int title;
    private int description;
    private boolean status;
    private boolean color;
    private int textbutton;
    private int typeOptions;

    public ElementView() {
    }

    public ElementView(int idOperacion, int resource, int title) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;

    }

    public ElementView(int idOperacion, int resource, int title, int description, boolean status, boolean color, int textbutton, int typeOptions) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
        this.description = description;
        this.status = status;
        this.color = color;
        this.textbutton = textbutton;
        this.typeOptions = typeOptions;
    }

    public int getTypeOptions() {
        return typeOptions;
    }

    public void setTypeOptions(int typeOptions) {
        this.typeOptions = typeOptions;
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


    /*public static ArrayList<ElementView> getListLectorAdq() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(6, R.drawable.ico_cobrar_in, R.string.operation_cobro));
        elementViews.add(new ElementView(3, R.drawable.ico_admin, R.string.operation_configurar));
        return elementViews;
    }*/

    public static ArrayList<ElementView> getListLectorAdq() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);

        elementViews.add(new ElementView(1, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(6, R.drawable.ico_cobrar_in, R.string.operation_cobro));
        elementViews.add(new ElementView(3, R.drawable.ico_admin, R.string.operation_configurar));

        if (!isAgente){
            elementViews = ElementView.getListLectorEmi();
        }

        else {
            int Idestatus = App.getInstance().getPrefs().loadDataInt(ID_ESTATUS);

            if (isAgente && Idestatus == IdEstatus.I7.getId()) {
                elementViews =  ElementView.getListEstadoRevisando();
            }

            if (isAgente && Idestatus == IdEstatus.I8.getId()) {
                elementViews = ElementView.getListEstadoRevisando();
            }

            if (isAgente && Idestatus == IdEstatus.I9.getId()) {
                elementViews = ElementView.getListEstadoError();
            }

            if (isAgente && Idestatus == IdEstatus.I10.getId()){
                elementViews = ElementView.getListEstadoRechazado();
            }

            if (isAgente && Idestatus == IdEstatus.I11.getId()) {
                elementViews = ElementView.getListEstadoRevisando();
            }

            if (isAgente && Idestatus == IdEstatus.I13.getId()) {
                elementViews = ElementView.getListEstadoRechazado();
            }

        }

        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorEmi() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(7, R.drawable.portada_adq, -1,-1,true,false,R.string.comenzar_registro,OPTION_ZONE));
        return elementViews;
    }

    //Proceso Revisando
    public static ArrayList<ElementView> getListEstadoRevisando() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(10, R.drawable.ico_revision, R.string.title_tipo_uno, R.string.title_tipo_desc, false, false, R.string.next,OPTION_ZONE_UNO));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoAprobado() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(11, R.drawable.ic_check_success, R.string.felicidades, R.string.ya_se_puede, true, false, R.string.next,OPTION_ZONE));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListConfigCard() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_SETTINGSCARD, R.drawable.icon_mycard, R.string.title_main, R.string.title_second, true, false, R.string.title_button_card,OPTION_ZONE_UNO));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoError() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(12, R.drawable.ico_alert_red, R.string.ocurrio_error_doc, R.string.tuvimos_problema, true, true, R.string.btn_reenviar,OPTION_ZONE_UNO));
        return elementViews;
    }

    //Proceso Rechazado
    public static ArrayList<ElementView> getListEstadoRechazado() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(13, R.drawable.ico_alert_red, R.string.tu_solicitud_no, R.string.tuvimos_problema_solicitud, false, true, R.string.btn_reenviar,OPTION_ZONE_UNO));
        return elementViews;
    }

    public static ArrayList<ElementView> getListEnviar(Context context) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(ID_ENVIAR, R.mipmap.send_icon, R.string.enviar_dinero));
        elementViews.add(new ElementView(ID_SOLICITAR, R.mipmap.request_icon, R.string.solicitar_pago));
        return elementViews;
    }

    public static ArrayList<ElementView> getListEmisorBalance() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        if (App.getInstance().getStatusId().equals(ESTATUS_CUENTA_BLOQUEADA)) {
            elementViews.add(new ElementView(1, R.drawable.ic_bloquear, R.string.desbloquear_tarjeta));
        } else {
            elementViews.add(new ElementView(1, R.drawable.ic_bloquear, R.string.bloquear_tarjeta));
        }
        elementViews.add(new ElementView(2, R.drawable.ic_generar, R.string.generar_codigo));
        return elementViews;
    }

    public static ArrayList<ElementView> getListAdqBalance() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(3, R.drawable.ic_cobrar, R.string.realizar_cobro));
        //elementViews.add(new ElementView(4, R.drawable.ic_calc, context.getResources().getString(R.string.calcular_comisiones)));
        return elementViews;
    }

    public static ArrayList<ElementView> getListStartBuck(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_RECOMPENSAS,R.drawable.icon_star,R.string.opt_recompensas));
        elementViews.add(new ElementView(1,R.drawable.icono_movimientos,R.string.opt_consulta));
        elementViews.add(new ElementView(OPTION_SUCURSALES,R.drawable.ico_store,R.string.opt_sucursales));
        elementViews.add(new ElementView(3, R.drawable.ico_admin_tarj, R.string.operation_administracion));
        return elementViews;
    }

    static public final int OPTION_RECOMPENSAS = 6482;
    static public final int OPTION_SUCURSALES  = 6453;
    static public final int OPTION_SETTINGSCARD  = 2112;
}
