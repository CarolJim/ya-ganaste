package com.pagatodo.yaganaste.ui_wallet.pojos;


import android.content.Context;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AgentesRespose;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.OperadoresResponse;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;

import java.io.Serializable;
import java.util.ArrayList;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

/**
 * Created by icruz on 12/12/2017.
 */

@SuppressWarnings("serial")
public class ElementView implements ElementGlobal {

    final public static int ID_ENVIAR = 8;
    final public static int ID_SOLICITAR = 9;

    static public final int OPTION_MVIMIENTOS_EMISOR = 101;
    static public final int OPTION_MVIMIENTOS_ADQ = 102;
    static public final int OPTION_OPERADORES_ADQ = 104;
    static public final int OPTION_MVIMIENTOS_STARBUCKS = 103;
    //static public final int OPTION_MVIMIENTOS_STARBUCKS = 103;

    static public final int OPTION_ADMON_EMISOR = 301;
    static public final int OPTION_ADMON_ADQ = 302;
    static public final int OPTION_ADMON_STARBUCK = 303;

    static public final int OPTION_BLOCK_CARD = 200;
    static public final int OPTION_GENERATE_TOKEN = 201;
    static public final int OPTION_PAYMENT_ADQ = 202;
    static public final int OPTION_DEPOSITO = 203;
    static public final int OPTION_RECOMPENSAS = 6482;
    static public final int OPTION_SUCURSALES = 6453;
    static public final int OPTION_SETTINGSCARD = 2112;
    static public final int OPTION_ADDFAVORITE_PAYMENT = 3001;
    static public final int OPTION_CONFIG_DONGLE = 14;

    public static final int OPTION_SIMPLE = 0;
    public static final int OPTION_ZONE = 1;
    public static final int OPTION_ZONE_UNO = 2;
    public static final int OPTION_ZONE_DOS = 3;

    private int idOperacion;
    private int resource;
    private int title;
    private int description;
    private boolean status;
    private boolean color;
    private int textbutton;
    private int typeOptions;

    private ArrayList<OperadoresResponse> list = new ArrayList<>();

    private String nombreNegocio;

    public ArrayList<OperadoresResponse> getList() {
        return list;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public void setList(ArrayList<OperadoresResponse> list) {
        this.list = list;
    }

    public ElementView() {
    }

    public static ElementView newInstance() {
        return new ElementView();
    }

    public ElementView(int idOperacion, int resource, int title, ArrayList<OperadoresResponse> list, String nombreNegocio) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
        this.list = list;
        this.nombreNegocio = nombreNegocio;

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
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_EMISOR, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(OPTION_DEPOSITO, R.drawable.ico_depositos, R.string.operation_deposito));
        elementViews.add(new ElementView(OPTION_ADMON_EMISOR, R.drawable.ico_admin_tarj, R.string.operation_administracion));
        return elementViews;
    }


    /*public static ArrayList<ElementView> getListLectorAdq() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(1, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(6, R.drawable.ico_cobrar_in, R.string.operation_cobro));
        elementViews.add(new ElementView(3, R.drawable.ico_admin, R.string.operation_configurar));
        return elementViews;
    }*/

    /*public static ArrayList<ElementView> getListBusiness(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_ADQ, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, R.drawable.ico_cobrar_in, R.string.realizar_cobro));
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_ADQ,R.drawable.ico_operador, R.string.mis_operadores));

        return elementViews;
    }*/

    public static ArrayList<ElementView> getListLectorAdq(ArrayList<OperadoresResponse> list, String nombreN, boolean isComercioUyu) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        int Idestatus = App.getInstance().getPrefs().loadDataInt(ID_ESTATUS);
        boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_ADQ, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.operation_cobro));
        if (SingletonUser.getInstance().getDataUser().getUsuario().getRoles().get(0).getIdRol() != 129 && isComercioUyu) {
            elementViews.add(new ElementView(OPTION_OPERADORES_ADQ, R.drawable.ico_operador, R.string.mis_operadores, list, nombreN));
        }
        elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));

        if (!isAgente) {
            elementViews = ElementView.getListLectorEmi();
        } else {
            if (isAgente && Idestatus == IdEstatus.I6.getId()) {
                elementViews = ElementView.getListEstadoContinuarRegistro();
            }
            if (isAgente && Idestatus == IdEstatus.I7.getId()) {
                elementViews = ElementView.getListEstadoRevisando();
            }

            if (isAgente && Idestatus == IdEstatus.I8.getId()) {
                elementViews = ElementView.getListEstadoRevisando();
            }

            if (isAgente && Idestatus == IdEstatus.I9.getId()) {
                elementViews = ElementView.getListEstadoError();
            }

            if (isAgente && Idestatus == IdEstatus.I10.getId()) {
                elementViews = ElementView.getListEstadoRechazado();
            }

            if (isAgente && Idestatus == IdEstatus.I11.getId()) {
                elementViews = ElementView.getListEstadoRevisando();
            }

            if (isAgente && Idestatus == IdEstatus.I13.getId()) {
                elementViews = ElementView.getListEstadoRechazado();
            }
            /*if (isAgente && Idestatus == IdEstatus.ADQUIRENTE.getId() &&
                    !App.getInstance().getPrefs().loadDataBoolean(HAS_CONFIG_DONGLE, false)) {
                elementViews = ElementView.getListSeleccionarLector();
            }*/
        }
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorEmi() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(7, R.drawable.portada_adq, -1, -1, true, false, R.string.comenzar_registro, OPTION_ZONE));
        return elementViews;
    }

    //Proceso Revisando
    public static ArrayList<ElementView> getListEstadoRevisando() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(10, R.drawable.ico_revision, R.string.title_tipo_uno, R.string.title_tipo_desc, false, false, R.string.next, OPTION_ZONE_UNO));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoAprobado() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(11, R.drawable.ic_check_success, R.string.felicidades, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE));
        return elementViews;
    }

    // Seleccion de Lector
    public static ArrayList<ElementView> getListSeleccionarLector() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_CONFIG_DONGLE, R.drawable.ic_check_success, R.string.title_tipo_desc_tres, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE_DOS));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListConfigCard() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_SETTINGSCARD, R.drawable.icon_card, R.string.title_main, R.string.title_second, true, false, R.string.title_button_card, OPTION_ZONE_UNO));
        return elementViews;
    }

    //Proceso Continuar Registro Documentacion
    public static ArrayList<ElementView> getListEstadoContinuarRegistro() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(12, R.drawable.portada_adq, -1, -1, true, false, R.string.continuar_registro, OPTION_ZONE));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoError() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(12, R.drawable.ico_alert_red, R.string.ocurrio_error_doc, R.string.tuvimos_problema, true, true, R.string.btn_reenviar, OPTION_ZONE_UNO));
        return elementViews;
    }

    //Proceso Rechazado
    public static ArrayList<ElementView> getListEstadoRechazado() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(13, R.drawable.ico_alert_red, R.string.tu_solicitud_no, R.string.tuvimos_problema_solicitud, false, true, R.string.btn_reenviar, OPTION_ZONE_UNO));
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
        if (!App.getInstance().getPrefs().loadData(CARD_NUMBER).isEmpty()) {
            if (App.getInstance().getStatusId().equals(ESTATUS_CUENTA_BLOQUEADA)) {
                elementViews.add(new ElementView(OPTION_BLOCK_CARD, R.drawable.ic_bloquear, R.string.desbloquear_tarjeta));
            } else {
                elementViews.add(new ElementView(OPTION_BLOCK_CARD, R.drawable.ic_bloquear, R.string.bloquear_tarjeta));
            }
        }
        elementViews.add(new ElementView(OPTION_GENERATE_TOKEN, R.drawable.ic_generar, R.string.generar_codigo));
        return elementViews;
    }

    public static ArrayList<ElementView> getListAdqBalance() {
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.realizar_cobro));
        elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
        //elementViews.add(new ElementView(4, R.drawable.ic_calc, context.getResources().getString(R.string.calcular_comisiones)));
        return elementViews;
    }

    public static ArrayList<ElementView> getListStarbucks() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_RECOMPENSAS, R.drawable.icon_star, R.string.opt_recompensas));
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_STARBUCKS, R.drawable.icono_movimientos, R.string.opt_consulta));
        elementViews.add(new ElementView(OPTION_SUCURSALES, R.drawable.ico_store, R.string.opt_sucursales));
        elementViews.add(new ElementView(OPTION_ADMON_STARBUCK, R.drawable.ico_admin_tarj, R.string.operation_administracion));
        return elementViews;
    }

    public static ArrayList<ElementView> getListStarbucksBalance() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_RECOMPENSAS, R.drawable.icon_star, R.string.opt_recompensas));
        elementViews.add(new ElementView(OPTION_SUCURSALES, R.drawable.ico_store, R.string.opt_sucursales));
        return elementViews;
    }


    @Override
    public int getIdOperacion() {
        return this.idOperacion;
    }
}
