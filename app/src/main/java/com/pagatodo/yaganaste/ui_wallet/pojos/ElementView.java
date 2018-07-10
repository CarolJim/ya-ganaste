package com.pagatodo.yaganaste.ui_wallet.pojos;


import android.content.Context;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FELICIDADES_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_LOGIN;
import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_REEMBOLSO;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.IS_UYU;
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
    static public final int OPTION_MVIMIENTOS_BUSSINES = 105;
    static public final int OPTION_VENTAS_ADQ = 109;
    static public final int OPTION_VENTAS_ADQAFUERA = 210;
    static public final int OPTION_TUTORIALS = 401;
    static public final int OPTION_CONTINUE_DOCS = 106;
    static public final int OPTION_ERROR_ADDRESS = 107;
    static public final int OPTION_ERROR_ADDRESS_DOCS = 108;
    static public final int OPTION_BALANCE_CLOSED_LOOP = 110;

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
    static public final int OPTION_FIRST_ADQ = 15;
    static public final int OPTION_REENVOLSO_FIRST = 16;

    //Help
    static public final int OPTION_EMAIL = 501;
    static public final int OPTION_CALL = 502;


    public static final int OPTION_SIMPLE = 0;
    public static final int OPTION_ZONE = 1;
    public static final int OPTION_ZONE_UNO = 2;
    public static final int OPTION_ZONE_DOS = 3;
    public static final int OPTION_ZONE_FIRST = 4;
    public static final int OPTION_ZONE_REENBOLSO = 5;




    private int idOperacion;
    private int resource;
    private int title;
    private int description;
    private boolean status;
    private boolean color;
    private int textbutton;
    private int typeOptions;

    private List<Operadores> list = new ArrayList<>();
    private String nombreNegocio;

    private String numeroAgente;

    private String IdComercio;

    public String getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(String idComercio) {
        IdComercio = idComercio;
    }

    public String getNumeroAgente() {
        return numeroAgente;
    }

    public void setNumeroAgente(String numeroAgente) {
        this.numeroAgente = numeroAgente;
    }

    public List<Operadores> getList() {
        return list;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public void setList(ArrayList<Operadores> list) {
        this.list = list;
    }

    public ElementView() {
    }

    public static ElementView newInstance() {
        return new ElementView();
    }

    public ElementView(int idOperacion, int resource, int title, List<Operadores> list, String nombreNegocio, String numeroAgente, String idComercio) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
        this.list = list;
        this.nombreNegocio = nombreNegocio;
        this.numeroAgente = numeroAgente;
        this.IdComercio = idComercio;

    }

    public ElementView(int idOperacion, int resource, int title) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
    }

    public ElementView(int idOperacion, int resource, int title, String nombreComercio) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
        this.nombreNegocio = nombreComercio;
    }

    public ElementView(int idOperacion, int resource, int title, int description, boolean status, boolean color, int textbutton, int typeOptions, String idComercio) {
        this.idOperacion = idOperacion;
        this.resource = resource;
        this.title = title;
        this.description = description;
        this.status = status;
        this.color = color;
        this.textbutton = textbutton;
        this.typeOptions = typeOptions;
        this.IdComercio = idComercio;
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

    public static ArrayList<ElementView> getListHelp(){
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_EMAIL, R.drawable.ico_correo, R.string.correo));
        elementViews.add(new ElementView(OPTION_CALL, R.drawable.ic_telefono, R.string.llamada));
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorAdq(int idEstatusAgente, List<Operadores> list, String nombreN, String numeroAgente, String idComercio, boolean isComercioUyu) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_ADQ, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.operation_cobro, nombreN));
        elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_consulta, R.string.operation_consultar_saldo));
        if (!App.getInstance().getPrefs().loadDataBoolean(IS_OPERADOR, false) && isComercioUyu) {
            elementViews.add(new ElementView(OPTION_OPERADORES_ADQ, R.drawable.ico_operador, R.string.mis_operadores, list, nombreN, numeroAgente, idComercio));
            elementViews.add(new ElementView(OPTION_VENTAS_ADQ, R.drawable.ico_reportes, R.string.ventas_dia, list, nombreN, numeroAgente, idComercio));
        }
        List<Agentes> agentes = new ArrayList<>();
        try {
            agentes = new DatabaseManager().getAgentes();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (agentes.size() < 2) {
            elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
        }



        if (!isAgente) {
            elementViews = ElementView.getListLectorEmi();
        } else {
            if (isAgente && idEstatusAgente == IdEstatus.I6.getId()) {
                elementViews = ElementView.getListEstadoContinuarRegistro(idComercio);
            }
            if (isAgente && idEstatusAgente == IdEstatus.I7.getId()) {
                elementViews = ElementView.getListEstadoRevisando(idComercio);
            }

            if (isAgente && idEstatusAgente == IdEstatus.I8.getId()) {
                elementViews = ElementView.getListEstadoRevisando(idComercio);
            }

            if (isAgente && idEstatusAgente == IdEstatus.I9.getId()) {
                elementViews = ElementView.getListEstadoError(idComercio);
            }

            if (isAgente && idEstatusAgente == IdEstatus.I10.getId()) {
                elementViews = ElementView.getListEstadoRechazado(idComercio);
            }

            if (isAgente && idEstatusAgente == IdEstatus.I11.getId()) {
                elementViews = ElementView.getListEstadoRevisando(idComercio);
            }

            if (isAgente && idEstatusAgente == IdEstatus.I13.getId()) {
                elementViews = ElementView.getListEstadoRechazado(idComercio);
            }
            if (App.getInstance().getPrefs().loadDataBoolean(FELICIDADES_ADQ, false)) {
                elementViews = ElementView.getListEstadoRechazado(idComercio);
            }
            if (isAgente && idEstatusAgente == IdEstatus.ADQUIRENTE.getId() &&
                    !App.getInstance().getPrefs().loadDataBoolean(HAS_CONFIG_DONGLE, false)) {
                elementViews = ElementView.getListSeleccionarLector(idComercio);
            }
            if (isAgente && idEstatusAgente == IdEstatus.ADQUIRENTE.getId() &&
                    !App.getInstance().getPrefs().loadDataBoolean(FIST_ADQ_REEMBOLSO, false)){
                elementViews = ElementView.getListSeleccionarTipoReevolso(idComercio);
            }
            if (isAgente && idEstatusAgente == IdEstatus.ADQUIRENTE.getId() &&
                    !App.getInstance().getPrefs().loadDataBoolean(FIST_ADQ_LOGIN, false)){
                elementViews = ElementView.getListEstadoAprobado(idComercio);
            }

        }
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorEmi() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(7, R.drawable.portada_adq, -1, -1, true, false, R.string.comenzar_registro, OPTION_ZONE));
        return elementViews;
    }

    //Proceso Revisando
    public static ArrayList<ElementView> getListEstadoRevisando(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(10, R.drawable.ico_revision, R.string.title_tipo_uno, R.string.title_tipo_desc, false, false, R.string.next, OPTION_ZONE_UNO, idComercio));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoAprobado(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_FIRST_ADQ, R.drawable.ic_check_success, R.string.felicidades, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE_FIRST, idComercio));
        return elementViews;
    }

    // Seleccion de Lector
    public static ArrayList<ElementView> getListSeleccionarLector(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_CONFIG_DONGLE, R.drawable.ic_check_success, R.string.title_tipo_desc_tres, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE_DOS, idComercio));
        return elementViews;
    }

    // Seleccion de Lector
    public static ArrayList<ElementView> getListSeleccionarTipoReevolso(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_REENVOLSO_FIRST, R.drawable.ic_check_success, R.string.time_repayment_desc, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE_REENBOLSO, idComercio));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListConfigCard(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_SETTINGSCARD, R.drawable.icon_card, R.string.title_main, R.string.title_second, true, false, R.string.title_button_card, OPTION_ZONE_UNO, idComercio));
        return elementViews;
    }

    //Proceso Continuar Registro Documentacion
    public static ArrayList<ElementView> getListEstadoContinuarRegistro(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_CONTINUE_DOCS, R.drawable.portada_adq, -1, -1, true, false, R.string.continuar_registro, OPTION_ZONE, idComercio));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListEstadoError(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(12, R.drawable.ico_alert_red, R.string.ocurrio_error_doc, R.string.tuvimos_problema, true, true, R.string.btn_revisar, OPTION_ZONE_UNO, idComercio));
        return elementViews;
    }

    //Proceso Rechazado
    public static ArrayList<ElementView> getListEstadoRechazado(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(13, R.drawable.ico_alert_red, R.string.tu_solicitud_no, R.string.tuvimos_problema_solicitud, false, true, R.string.btn_reenviar, OPTION_ZONE_UNO, idComercio));
        return elementViews;
    }

    //Proceso Felicidades
    public static ArrayList<ElementView> getListEstadoFelicidadesADQ(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(100, R.drawable.ic_action_done, R.string.tu_solicitud_no, R.string.tuvimos_problema_solicitud, false, true, R.string.btn_reenviar, OPTION_ZONE_UNO, idComercio));
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
            if (App.getInstance().getPrefs().loadData(CARD_STATUS).equals(ESTATUS_CUENTA_BLOQUEADA)) {
                elementViews.add(new ElementView(OPTION_BLOCK_CARD, R.drawable.ic_bloquear, R.string.desbloquear_tarjeta));
            } else {
                elementViews.add(new ElementView(OPTION_BLOCK_CARD, R.drawable.ic_bloquear, R.string.bloquear_tarjeta));
            }
        }
        //    elementViews.add(new ElementView(OPTION_GENERATE_TOKEN, R.drawable.ic_generar, R.string.generar_codigo));
        return elementViews;
    }

    public static ArrayList<ElementView> getListAdqBalance(boolean isuyu) {
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.realizar_cobro));
        elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_consulta, R.string.operation_consultar_saldo));
        if (!App.getInstance().getPrefs().loadDataBoolean(IS_OPERADOR, false) && isuyu) {
            elementViews.add(new ElementView(OPTION_VENTAS_ADQAFUERA, R.drawable.ico_reportes, R.string.ventas_dia));
        }
        //elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
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

    public static ArrayList<ElementView> getListMyBusiness() {
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_BUSSINES, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
        //elementViews.add(new ElementView(OPTION_MVIMIENTOS_BUSSINES, R.drawable.icono_movimientos, R.string.operation_movimientos));
        return elementViews;
    }


    @Override
    public int getIdOperacion() {
        return this.idOperacion;
    }

    @Override
    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }
}
