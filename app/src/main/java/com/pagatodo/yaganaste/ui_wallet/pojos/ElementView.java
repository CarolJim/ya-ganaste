package com.pagatodo.yaganaste.ui_wallet.pojos;


import android.content.Context;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.net.RequestHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_STATUS;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_LOGIN;
import static com.pagatodo.yaganaste.utils.Recursos.FIST_ADQ_REEMBOLSO;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;

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
    static public final int OPTION_MVIMIENTOS_STARBUCKSD = 1033;
    static public final int OPTION_MVIMIENTOS_BUSSINES = 105;
    static public final int OPTION_VENTAS_ADQ = 109;
    static public final int OPTION_VENTAS_ADQAFUERA = 210;
    static public final int OPTION_TUTORIALS = 401;
    static public final int OPTION_CONTINUE_DOCS = 106;
    static public final int OPTION_ERROR_ADDRESS = 107;
    static public final int OPTION_ERROR_ADDRESS_DOCS = 108;
    static public final int OPTION_BALANCE_CLOSED_LOOP = 110;
    static public final int OPTION_ESTADOS_CUENTA = 111;
    static public final int OPTION_ADD_NEW_FAV_SUCCES = 112;
    static public final int OPTION_CHARGE_WITH_CARD = 113;
    static public final int OPTION_MY_CARD_SALES = 114;
    static public final int OPTION_TRANSFER_BALANCE = 115;


    static public final int OPTION_ADMON_EMISOR = 301;
    static public final int OPTION_ADMON_ADQ = 302;
    static public final int OPTION_ADMON_STARBUCK = 303;
    static public final int OPTION_PAGO_QR = 304;

    static public final int OPTION_BLOCK_CARD = 200;
    static public final int OPTION_GENERATE_TOKEN = 201;
    static public final int OPTION_PAYMENT_ADQ = 202;
    static public final int OPTION_DEPOSITO = 203;
    static public final int OPTION_RECOMPENSAS = 6482;
    static public final int OPTION_RECOMPENSASD = 64822;
    static public final int OPTION_SUCURSALESD = 64533;
    static public final int OPTION_SUCURSALES = 6453;
    static public final int OPTION_SETTINGSCARD = 2112;
    static public final int OPTION_ADDFAVORITE_PAYMENT = 3001;
    static public final int OPTION_CONFIG_DONGLE = 14;
    static public final int OPTION_FIRST_ADQ = 15;
    static public final int OPTION_REMBOLSO_FIRST = 16;


    //Help
    static public final int OPTION_EMAIL = 501;
    static public final int OPTION_CALL = 502;
    static public final int OPTION_CHAT = 503;


    public static final int OPTION_SIMPLE = 100;
    public static final int OPTION_ZONE = 1;
    public static final int OPTION_ZONE_UNO = 2;
    public static final int OPTION_ZONE_DOS = 3;
    public static final int OPTION_ZONE_FIRST = 4;
    public static final int OPTION_ZONE_REENBOLSO = 5;
    public static final int OPTION_ZONE_REENBOLSO_AGREGADOR = 6;

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
        elementViews.add(new ElementView(OPTION_DEPOSITO, R.drawable.icon_user, R.string.operation_deposito));
        elementViews.add(new ElementView(OPTION_PAGO_QR, R.drawable.ico_scan_qr, R.string.operation_payment_qr));
        //elementViews.add(new ElementView(OPTION_ESTADOS_CUENTA, R.drawable.ic_edo_cuenta, R.string.operation_estado_cuenta));
        return elementViews;
    }

    public static ArrayList<ElementView> getListHelp() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_CHAT, R.drawable.icon_chat, R.string.chat));
        elementViews.add(new ElementView(OPTION_EMAIL, R.drawable.ico_correo, R.string.correo));
        elementViews.add(new ElementView(OPTION_CALL, R.drawable.ic_telefono, R.string.llamada));
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorAdq(int idEstatusAgente, List<Operadores> list,
                      String nombreN, String numeroAgente, String idComercio, boolean isComercioUyu,
                                                          boolean isAgregador) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        boolean isAgente = App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false);
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        //elementViews.add(new ElementView(OPTION_MVIMIENTOS_ADQ, R.drawable.icono_movimientos, R.string.operation_movimientos));
        //elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.operation_cobro, nombreN));

        if (!App.getInstance().getPrefs().loadDataBoolean(IS_OPERADOR, false) && isComercioUyu) {
            elementViews.add(new ElementView(OPTION_CHARGE_WITH_CARD, R.drawable.ic_ico_cobros_tarjeta, R.string.realizar_cobro));
            elementViews.add(new ElementView(OPTION_MY_CARD_SALES, R.drawable.ic_ico_ventas_tarjeta_uyu, R.string.my_card_sales));
            elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_consulta_saldo_uyu, R.string.operation_consultar_saldo));
            elementViews.add(new ElementView(OPTION_VENTAS_ADQ, R.drawable.ic_ico_ventas_dia_uyu, R.string.ventas_dia, list, nombreN, numeroAgente, idComercio));
            elementViews.add(new ElementView(OPTION_OPERADORES_ADQ, R.drawable.ic_ico_wallet_uyu, R.string.mis_operadores, list, nombreN, numeroAgente, idComercio));
        } else {
            List<Agentes> agentes = new ArrayList<>();
            try {
                agentes = new DatabaseManager().getAgentes();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (isAgente && isAgregador && idEstatusAgente == IdEstatus.ADQUIRENTE.getId()) {
                elementViews =  getListAdq();
            } else if (!App.getInstance().getPrefs().loadDataBoolean(FIST_ADQ_REEMBOLSO, false)){
                elementViews = ElementView.getListEstadoContinuarRegistro();
            } else {
                elementViews =  getListAdq();

            }


            /*if (isAgente && isAgregador && idEstatusAgente == IdEstatus.ADQUIRENTE.getId()) {
                elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_ventas_tarjeta, R.string.operation_consultar_saldo));
=======
                elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_consulta_saldo, R.string.operation_consultar_saldo));
>>>>>>> e0a834752d071098fc61832058d77de03a693cea
                //elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
                elementViews.add(new ElementView(OPTION_CHARGE_WITH_CARD, R.drawable.ic_ico_cobros_tarjeta, R.string.realizar_cobro));
                elementViews.add(new ElementView(OPTION_MY_CARD_SALES, R.drawable.ic_ico_ventas_tarjeta, R.string.my_card_sales));
            }*/ /*else{
                elementViews = ElementView.getListEstadoContinuarRegistro();
            }*/
        }
        return elementViews;
    }

        /*
        //elementViews.add(new ElementView(OPTION_TRANSFER_BALANCE, R.drawable.ic_transfer, R.string.transfer_balance));
        if (agentes.size() < 2) {
            elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_ventas_tarjeta, R.string.operation_consultar_saldo));
            //elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
            elementViews.add(new ElementView(OPTION_CHARGE_WITH_CARD, R.drawable.ic_ico_cobros_tarjeta, R.string.realizar_cobro));
            elementViews.add(new ElementView(OPTION_MY_CARD_SALES, R.drawable.ic_ico_ventas_tarjeta, R.string.my_card_sales));
        }

        if (!isAgente) {
            elementViews = ElementView.getListLectorEmi();
        } else {
            //idEstatusAgente = 12;


            if (idEstatusAgente == IdEstatus.I6.getId()) {
                elementViews = ElementView.getListEstadoContinuarRegistro(idComercio);
            } else if (idEstatusAgente == IdEstatus.I7.getId() ||
                    idEstatusAgente == IdEstatus.I8.getId() ||
                    idEstatusAgente == IdEstatus.I11.getId() ||
                    App.getInstance().getPrefs().loadDataInt(ESTATUS_DOCUMENTACION) == STATUS_DOCTO_PENDIENTE) {
                elementViews = ElementView.getListEstadoRevisando(idComercio);
            } else if (idEstatusAgente == IdEstatus.I9.getId()) {
                elementViews = ElementView.getListEstadoError(idComercio);
            } else if (idEstatusAgente == IdEstatus.I10.getId() || idEstatusAgente == IdEstatus.I13.getId()) {
                elementViews = ElementView.getListEstadoRechazado(idComercio);
            } else

                if (idEstatusAgente == IdEstatus.ADQUIRENTE.getId() && !isComercioUyu) {

                    //App.getInstance().getPrefs().saveDataBool(FIST_ADQ_REEMBOLSO, false);
                    if (isAgregador) {
                        elementViews = ElementView.getListAdqBalance(false);
                        //elementViews = ElementView.getListSeleccionarTipoReevolso(idComercio);
                    } //else {
                        //elementViews = ElementView.getListAdqBalance(false);
                    //}
                    for (Operadores opr : list) {
                        if (opr.getIsAdmin()) {
                            RequestHeaders.setIdCuentaAdq(opr.getIdUsuarioAdquirente());
                        }
                    }
                } else if (idEstatusAgente == IdEstatus.ADQUIRENTE.getId() &&
                        !App.getInstance().getPrefs().loadDataBoolean(FIST_ADQ_LOGIN, false)) {
                    App.getInstance().getPrefs().saveDataInt(ESTATUS_DOCUMENTACION, STATUS_DOCTO_APROBADO);
                    elementViews = ElementView.getListEstadoAprobado(idComercio);
                } else {
                    App.getInstance().getPrefs().saveDataBool(FIST_ADQ_REEMBOLSO, true);
                }

        }*/
        public static ArrayList<ElementView> getListAdq() {
            ArrayList<ElementView> elementViews = new ArrayList<>();
            elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_ventas_tarjeta, R.string.operation_consultar_saldo));
            //elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
            elementViews.add(new ElementView(OPTION_CHARGE_WITH_CARD, R.drawable.ic_ico_cobros_tarjeta, R.string.realizar_cobro));
            elementViews.add(new ElementView(OPTION_MY_CARD_SALES, R.drawable.ic_ico_ventas_tarjeta, R.string.my_card_sales));
            return elementViews;
        }

    public static ArrayList<ElementView> getListLectorOperador(int idEstatusAgente, List<Operadores> list, String nombreN, String numeroAgente, String idComercio, boolean isComercioUyu) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_ADQ, R.drawable.icono_movimientos, R.string.operation_movimientos));
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.operation_cobro, nombreN));
        elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_consulta_saldo, R.string.operation_consultar_saldo));
        elementViews.add(new ElementView(OPTION_ADMON_ADQ, isBluetooth ? R.drawable.ico_admin_chip : R.drawable.ico_admin, R.string.operation_configurar));
        return elementViews;
    }

    public static ArrayList<ElementView> getListLectorEmi() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(7, R.drawable.portada_adq, -1, -1,
                true, false, R.string.comenzar_registro, OPTION_ZONE));
        return elementViews;
    }

    //Proceso Revisando
    private static ArrayList<ElementView> getListEstadoRevisando(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(10, R.drawable.ico_revision, R.string.title_tipo_uno, R.string.title_tipo_desc, false, false, R.string.next, OPTION_ZONE_UNO, idComercio));
        return elementViews;
    }

    //Proceso Aprobado
    private static ArrayList<ElementView> getListEstadoAprobado(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_FIRST_ADQ, R.drawable.ic_check_success, R.string.felicidades, R.string.ya_se_puede, true, false, R.string.indication_adq_text_button, OPTION_ZONE_FIRST, idComercio));
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
        elementViews.add(new ElementView(OPTION_REMBOLSO_FIRST, R.drawable.ic_check_success, R.string.time_repayment_desc, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE_REENBOLSO, idComercio));
        return elementViews;
    }

    // Seleccion de Reembolso Agregador
    public static ArrayList<ElementView> getListTipoReembolsoAgregador(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_REMBOLSO_FIRST, R.drawable.ic_check_success, R.string.time_repayment_desc, R.string.ya_se_puede, true, false, R.string.next, OPTION_ZONE_REENBOLSO_AGREGADOR, idComercio));
        return elementViews;
    }

    //Proceso Aprobado
    public static ArrayList<ElementView> getListConfigCard(String idComercio) {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_SETTINGSCARD, R.drawable.icon_card, R.string.title_main, R.string.title_second, true, false, R.string.title_button_card, OPTION_ZONE_UNO, idComercio));
        return elementViews;
    }

    //Proceso Continuar Registro Documentacion
    private static ArrayList<ElementView> getListEstadoContinuarRegistro() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_REMBOLSO_FIRST, R.drawable.portada_adq, R.string.conf_reem,
                -1, true, false, R.string.continuar_registro, OPTION_ZONE_REENBOLSO));
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
                elementViews.add(new ElementView(OPTION_BLOCK_CARD, R.drawable.ic_desbloquear, R.string.desbloquear_tarjeta));
            } else {
                elementViews.add(new ElementView(OPTION_BLOCK_CARD, R.drawable.ic_bloquear, R.string.bloquear_tarjeta));
            }
            elementViews.add(new ElementView(OPTION_PAGO_QR, R.drawable.ico_scanqrbalance, R.string.operation_payment_qr));
            //elementViews.add(new ElementView(OPTION_PAGO_QR, R.drawable.ico_qr, R.string.operation_pago_qr));
        }
        //    elementViews.add(new ElementView(OPTION_GENERATE_TOKEN, R.drawable.ic_generar, R.string.generar_codigo));
        return elementViews;
    }

    public static ArrayList<ElementView> getListAdqBalance(boolean isuyu) {
        boolean isBluetooth = App.getInstance().getPrefs().loadDataInt(MODE_CONNECTION_DONGLE) == QPOSService.CommunicationMode.BLUETOOTH.ordinal();
        ArrayList<ElementView> elementViews = new ArrayList<>();
        //elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, isBluetooth ? R.drawable.ic_bluetooth_dongle : R.drawable.ico_cobrar_in, R.string.realizar_cobro));
        elementViews.add(new ElementView(OPTION_PAYMENT_ADQ, R.drawable.ic_ico_cobros_tarjeta, R.string.realizar_cobro));
        if (isuyu)
            elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_ico_ventas_tarjeta, R.string.operation_consultar_saldo));
        if (!App.getInstance().getPrefs().loadDataBoolean(IS_OPERADOR, false) && isuyu) {
            elementViews.add(new ElementView(OPTION_VENTAS_ADQAFUERA, R.drawable.ic_ico_ventas_dia_uyu, R.string.ventas_dia));
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
        //elementViews.add(new ElementView(OPTION_ADMON_STARBUCK, R.drawable.ico_admin_tarj, R.string.operation_administracion));
        return elementViews;
    }

    public static ArrayList<ElementView> getListStarbucksdes() {
        ArrayList<ElementView> elementViews = new ArrayList<>();
        elementViews.add(new ElementView(OPTION_RECOMPENSASD, R.drawable.icon_star, R.string.opt_recompensas));
        elementViews.add(new ElementView(OPTION_MVIMIENTOS_STARBUCKSD, R.drawable.icono_movimientos, R.string.opt_consulta));
        elementViews.add(new ElementView(OPTION_SUCURSALESD, R.drawable.ico_store, R.string.opt_sucursales));
        //elementViews.add(new ElementView(OPTION_ADMON_STARBUCK, R.drawable.ico_admin_tarj, R.string.operation_administracion));
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
        elementViews.add(new ElementView(OPTION_BALANCE_CLOSED_LOOP, R.drawable.ic_consulta, R.string.operation_consultar_saldo));
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
