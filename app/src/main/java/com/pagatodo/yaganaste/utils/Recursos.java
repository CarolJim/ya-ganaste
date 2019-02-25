package com.pagatodo.yaganaste.utils;

import com.pagatodo.yaganaste.BuildConfig;

public class Recursos {

    //BASE DE DATOS
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "ya_ganaste.db";

    public static final String URL_BD_ODIN;
    public static final String URL_BD_ODIN_USERS;
    public static final String PUBLIC_KEY_RSA;
    public static final String PUBLIC_STARBUCKS_KEY_RSA;
    public static final String TOKEN_MIXPANEL;

    public static final int IDCOMERCIO_YA_GANASTE = 8609;
    public static final int GROUP_FORMAT = 4;

    public static final int WS_TIMEOUT = 60000;
    public static final long SESSION_TIMEOUT = 120000;

    /*Freja*/
    public static final String PT_CLIENT_CODE = null;

    /*Friggs*/
    public static final String URL_FRIGGS;
    /* Odin*/
    public static final String URL_ODIN;


    static {
        if (BuildConfig.DEBUG) {
            TOKEN_MIXPANEL = "bb0493fd161cef149b014e8a4e5f130e";
            URL_BD_ODIN = "https://odin-dd5ba.firebaseio.com";
            URL_BD_ODIN_USERS = "https://odin-mx-users.firebaseio.com";
            PUBLIC_KEY_RSA = "rk2QHAmXByr9wIf6d1cgU+f9NtKvj2xWvRv2wUcZSMVvhfTkcoWLG/CxEK+weoS3QcxxEWKFrWgwhYABXpkGhlXiqH7GyRIhv2kQtuZlGJJSIExd2asJrtjDnfStu7ZKbdIpLzqFUfo8naDhCuQTzhyApyJQ9HDcOSTFuRhJ7Mz3gXwUXqr98i+he+iYCzyrMViP+o4UPUqfNcpSafUw4NYre9KEZoHMaKcPMR4bMjax3Payt9LDAU3KgBOnWS9Ga6WffE03tpAWqE3ape61CmPw5QKPgRNKSnV70wu7f02jmstEepM35aSf3gL9SKMUv3DkwYIpifhNYPbdKCh+BQ==";
            PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";
            URL_FRIGGS = "https://us-central1-frigg-1762c.cloudfunctions.net";
            URL_ODIN = "https://us-central1-odin-dd5ba.cloudfunctions.net";
            //URL_FRIGGS = "https://us-central1-frigg-51525.cloudfunctions.net";


        } else {
            TOKEN_MIXPANEL = "bb0493fd161cef149b014e8a4e5f130e";
            URL_BD_ODIN = "https://odin-377e9.firebaseio.com";
            URL_BD_ODIN_USERS = "https://odin-mx-users-prod.firebaseio.com";
            PUBLIC_KEY_RSA = "pIznw1pWFzzOVI+Shkg56ujssxRhQv1DTHeU5LMtgSNCOY3iw1TacI6+Db/YUQsexjvfEcjQsg9QOJp3Q1maI5hEMiWG84tsKBpgBckZoDKcaoN7JtGo3p2BIG/eCm1yLmxSrDpcnNZ6Z8GnUGaQPWxy75E8/U57XrpAyURFNTbbeq0uSxkcoB/5xmyjoECTKpWfD+M8PpAnisBLd0oSYTZ+tmBdgLPQJOe794ZyV+DoX5eU9G7hKx1onCHpuPb/xtx3rnMUIR2qB0sD0hKAjXsyvEiNrrihfxLa3IdcEnWn5CcxcsYiaSJqLlDKlkr+07ji/CqYm5hcvr2CbhhUfw==";
            PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";

            URL_FRIGGS = "https://us-central1-frigg-51525.cloudfunctions.net";
            URL_ODIN = "https://us-central1-odin-dd5ba.cloudfunctions.net";
        }
    }

    //MAPS
    public final static int ZoomMap = 16;
    public static final int CODE_OK = 0;
    public static final int CODE_SESSION_EXPIRED = 16;

    public static final String CODE_ADQ_OK = "00";
    public static final int CODE_ERROR_INFO_AGENTE = 91;
    public static final int CODE_OFFLINE = -1;
    public static final int INVALID_TOKEN = 3001;
    public static final int INCORRECT_FORMAT = -2;
    public static final int DEVICE_ALREADY_ASSIGNED = 224;
    public static final int IDPROSPECTO = 13;

    /*WS Adq*/
    public static final String ADQ_CODE_OK = "00";
    public static final String ADQ_ACCES_DENIED = "3001";
    public static final int ADQ_TRANSACTION_APROVE = 0;
    public static final int ADQ_TRANSACTION_ERROR = 1;

    /**
     * Catálogos
     **/
    /*CRM – Adquiriente*/
    public static final int CRM_PENDIENTE = 1;
    public static final int CRM_DOCTO_APROBADO = 2;

    public static final int DOC_ID_FRONT = 5;
    public static final int DOC_ID_BACK = 6;
    public static final int DOC_DOM_FRONT = 7;
    public static final int DOC_DOM_BACK = 30;

    public static final int STATUS_DOCTO_RECHAZADO = 3;
    public static final int STATUS_DOCTO_APROBADO = 2;
    public static final int STATUS_DOCTO_PENDIENTE = 1;

    /*Id's de Preguntas  en Cuestionario Negocio*/ //TODO esto debería de ser un catálogo de ws
    public static final int PREGUNTA_RANGOS_MONTOS = 1;
    public static final int PREGUNTA_RANGO_OPERACIONES = 2;
    //public static final int PREGUNTA_GIRO = 3;
    public static final int PREGUNTA_FAMILIAR = 4;
    public static final int PREGUNTA_DOMICILIO = 5;
    //public static final int PREGUNTA_BENEFICIARIOS = 6;
    public static final int PREGUNTA_ERES_MEXICANO_NATURALIZADO = 10;
    public static final int PREGUNTA_COBROS = 1;
    public static final int PREGUNTA_MONTOS = 2;
    public static final int PREGUNTA_ORIGEN_RECURSOS = 11;
    public static final int PREGUNTA_DESTINO_RECURSOS = 12;

    //Constantes
    public static final String CARD_PAY = "PAGO CON TARJETA";

    /* Codigos de Acciones*/

    public static final int NO_ACTION = -2;
    public static final int GO_LOGIN = -3;

    /*Adq Dongle*/
    public static final int ENCENDIENDO = 100;
    public static final int ENCENDIDO = 110;
    public static final int APAGANDO = 120;
    public static final int APAGADO = 130;
    public static final int DESCONECTADO = 140;
    public static final int LEYENDO = 150;
    public static final int SW_ERROR = 160;
    public static final int LECTURA_OK = 170;
    public static final int SW_TIMEOUT = 180;
    public static final int SW_DEVICE_BUSY = 185;
    public static final int ENCENDIDO_TIMEOUT = 190;
    public static final int BATTERY_LOW = 200;
    public static final int READ_KSN = 210;
    public static final int READ_KSN_ERROR = 220;
    public static final int ERROR_LECTOR = 230;
    public static final int CONFIG_READER_OK = 240;
    public static final int CONFIG_READER_OK_ERROR = 250;
    public static final int READ_BATTERY_LEVEL = 260;
    public static final int EMV_DETECTED = 270;
    public static final int REQUEST_AMOUNT = 280;
    public static final int REQUEST_TIME = 290;
    public static final int REQUEST_IS_SERVER_CONNECTED = 300;
    public static final int REQUEST_FINAL_CONFIRM = 310;
    public static final int REQUEST_PIN = 320;
    public static final int REQUEST_SELECT_APP = 330;
    public static final int ONLINE_PROCESS_SUCCESS = 340;
    public static final int ONLINE_PROCESS_FAILED = 350;

    /* Cancelation Types EMV */
    public static final int TIME_OUT_EMV = 68;
    public static final int CANCELATION_EMV = 17;
    public static final int PINPAD_FAILED_EMV = 40;
    public static final int MALFUNCTION_EMV = 22;

    public static final String TRANSACTION_SEQUENCE = "TRANSACTION_SEQUENCE";
    public static final String IPOS_READER_STATES = "EMVSWIPE_STATES";
    public static final int READER_TRANSACTION = 0x31;

    public static final String MSJ = "MSJ";
    public static final String ESTADO = "ESTADO";
    public static final String VALOR = "VALOR";
    public static final String TRANSACTION = "TRANSACTION";
    public static final String APP_LIST = "APP_LIST";
    public static final String ERROR = "ERROR";
    public static final String IDERROR = "IDERROR";
    public static final String BATTERY_LEVEL = "BATTERY_LEVEL";
    public static final String READER_IS_CHARGING = "READER_IS_CHARGING";
    public static final String BATTERY_PORCENTAGE = "BATTERY_PORCENTAGE";
    public static final String KSN_LECTOR = "KSN_LECTOR";

    /*Url Legales*/
    public static final String URL_LEGALES_TERMINOS = "http://dev.yaganaste.com/terminos-y-condiciones.html";
    public static final String URL_LEGALES_PRIVACIDAD = "http://dev.yaganaste.com/aviso-de-privacidad.html";

    /*Url Legales Starbucks*/
    public static final String URL_LEGALES_TERMINOS_STARBUCKS = "https://rewards.starbucks.mx/LoyaltyWeb/TermsCards";
    public static final String URL_LEGALES_PRIVACIDAD_STARBUKS = "http://www.starbucks.com.mx/about-us/company-information/online-policies/privacy-statement";

    public static final String DEFAULT_CARD = "5389 84";

    /*
    Var para controlar los mensajes de session del servicio, se tiene una variacion porque tenemos un error de ortografia
     */
    public static final String MESSAGE_OPEN_SESSION = "Por Favor Inicia Sesión";
    public static final String MESSAGE_CHANGE_PASS = "Tu Contraseña ha Sido Cambiada Correctamente";
    public static final String MESSAGE_OPEN_SESSION2 = "Por favor inicia sesión";

    /**
     * EstatusCuenta
     */
    public static final String ESTATUS_CUENTA_DESBLOQUEADA = "1";
    public static final String ESTATUS_CUENTA_BLOQUEADA = "2";
    public static final String FLAG_BLOQUEAR_CUENTA = "1";
    public static final String FLAG_DESBLOQUEAR_CUENTA = "2";

    public static final String CURRENT_TAB = "current_tab";

    /**
     * Estatus de movimiento
     */
    public static final String ESTATUS_CANCELADO = "1";
    public static final String ESTATUS_POR_REMBOLSAR = "2";
    public static final String ESTATUS_REMBOLSADO = "3";

    /*Preferencias de Sesion*/
    public static String COUCHMARK_EMISOR = "COUCHMARKEMISOR";
    public static String COUCHMARK_ADQ = "COUCHMARKADQ";

    public static String SHA_256_FREJA = "SHA_256_FREJA";
    public static String SHA_256_STARBUCKS = "SHA_256_STARBUCKS";
    public static String GENERO = "GENERO";
    public static String HUELLA_FAIL = "HUELLA_FAIL";
    public static String NOTIF_COUNT = "NOTIF_COUNT";

    public static final String ADQ_PROCESS = "FLAG_PROCESS";
    public static String CUPO_COMPLETE = "CUPO_COMPLETE";

    // Preferencias para Foto de Usuario. URL
    public static final String URL_PHOTO_USER = "URL_PHOTO_USER";

    // Preferencias Uso de Huella Digital
    public static final String USE_FINGERPRINT = "USE_FINGERPRINT";
    public static final String FINGERPRINT_KEY = "YAGANASTEFINGERK";

    public static final String SPACE = " ";
    public static final String HAS_SESSION = "HAS_SESSION";
    public static final String IS_OPERADOR = "IS_OPERADOR";
    public static final String PASSWORD_CHANGE = "PASSWORD_CHANGE";

    public static final String IS_CUPO = "IS_CUPO";
    public static final String ESTADO_RECHAZADO = "ESTADO_RECHAZADO";
    public static final String NAME_USER = "NAME_USER";
    public static final String FULL_NAME_USER = "FULL_NAME_USER";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String SECOND_LAST_NAME = "SECOND_LAST_NAME";
    public static final String CARD_NUMBER = "CARD_NUMBER";
    public static final String AGENTE_NUMBER = "AGENTE_NUMBER";
    public static final String ID_COMERCIOADQ = "ID_COMERCIO";
    public static final String NOM_COM = "NOM_COM";
    public static final String FOLIOADQ = "FOLIOADQ";
    public static final String CARD_STATUS = "CARD_STATUS";
    public static final String SHOW_BALANCE = "SHOW_BALANCE";
    public static final String USER_BALANCE = "USER_BALANCE";
    public static final String ADQUIRENTE_BALANCE = "ADQUIRENTE_BALANCE";
    public static final String STARBUCKS_BALANCE = "STABUCKS_BALANCE";
    public static final String CUPO_BALANCE = "CUPO_BALANCE";
    public static final String UPDATE_DATE = "UPDATE_DATE";
    public static final String UPDATE_DATE_BALANCE_ADQ = "UPDATE_DATE_BALANCE_ADQ";
    public static final String UPDATE_DATE_BALANCE_CUPO = "UPDATE_DATE_BALANCE_CUPO";
    public static final String ID_CUENTA = "ID_CUENTA";
    public static final String ADQUIRENTE_APPROVED = "ADQUIRENTE_APPROVED";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CLABE_NUMBER = "CLABE_NUMBER";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String CATALOG_VERSION = "CATALOG_VERSION";
    public static final String SIMPLE_NAME = "SIMPLE_NAME";
    public static final String PSW_CPR = "PSW_CPR";
    public static final String SHOW_LOYALTY = "SHOW_LOYALTY";
    public static final String SHOW_LOGS_PROD = "SHOW_LOGS_PROD";
    public static final String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";

    public static final String HAS_PROVISIONING = "HAS_PROVISIONING";
    public static final String USER_PROVISIONED = "USER_PROVISIONED";
    public static final String HAS_PUSH = "HAS_PUSH";

    public static final String OLD_NIP = "OLD_NIP";
    public static final String HAS_FIREBASE_ACCOUNT = "HAS_FIREBASE_ACCOUNT";
    public static final String TOKEN_FIREBASE = "TOKEN_FIREBASE";
    public static final String TOKEN_FIREBASE_SESSION = "TOKEN_FIREBASE_SESSION";
    public static final String TOKEN_FIREBASE_AUTH = "TOKEN_FIREBASE_AUTH";
    public static final String TOKEN_FIREBASE_STATUS = "TOKEN_FIREBASE_STATUS";
    public static final String TOKEN_FIREBASE_SUCCESS = "TOKEN_FIREBASE_SUCCESS";
    public static final String TOKEN_FIREBASE_FAIL = "TOKEN_FIREBASE_FAIL";

    public static final String ES_AGENTE = "ES_AGENTE"; // boolean
    public static final String ID_ROL = "ID_ROL"; // boolean
    public static final String ESTATUS_AGENTE = "ESTATUS_AGENTE"; // int
    public static final String ESTATUS_DOCUMENTACION = "ESTATUS_DOCUMENTACION"; // int
    public static final String ID_USUARIO_ADQUIRIENTE = "ID_USUARIO_ADQUIRIENTE"; // string
    public static final String TIPO_AGENTE = "TOKEN_SESION_ADQUIRIENTE"; // string
    public static final String ID_ESTATUS_EMISOR = "ID_ESTATUS_EMISOR"; // int
    public static final String HAS_CONFIG_DONGLE = "HAS_CONFIG_DONGLE"; // boolean
    public static final String CONFIG_DONGLE_REEMBOLSO = "CONFIG_DONGLE_REEMBOLSO"; // boolean
    public static final String FIST_ADQ_LOGIN = "FIST_ADQ_LOGIN"; // boolean
    public static final String FIST_ADQ_REEMBOLSO = "FIST_ADQ_REEMBOLSO"; // boolean
    public static final String IS_UYU = "IS_UYU"; // boolean
    public static final String MODE_CONNECTION_DONGLE = "MODE_CONNECTION_DONGLE"; // int
    public static final String BT_PAIR_DEVICE = "BT_PAIR_DEVICE"; // string

    public static final String USMAL = "USMAL"; // string

    // Recursos referentes a Starbucks
    public static final String HAS_STARBUCKS = "HAS_STARBUCKS";  //boolean

    /* Datos de usuario al realizar login de starbucks */
    public static final String STARBUCKS_CARDS = "STARBUCKS_CARDS";
    public static final String REWARDS = "REWARDS";
    public static final String FAVORITE_DRINK = "FAVORITE_DRINK";
    public static final String EMAIL_STARBUCKS = "EMAIL_STARBUCKS";
    public static final String MEMBER_SINCE = "MEMBER_SINCE";
    public static final String STATUS_GOLD = "STATUS_GOLD";
    public static final String ID_MIEMBRO_STARBUCKS = "ID_MIEMBRO_STARBUCKS";
    public static final String ACTUAL_LEVEL_STARBUCKS = "ACTUAL_LEVEL_STARBUCKS";
    public static final String STARS_NUMBER = "STARS_NUMBER";
    public static final String MISSING_STARS_NUMBER = "MISSING_STARS_NUMBER";
    public static final String NEXT_LEVEL_STARBUCKS = "NEXT_LEVEL_STARBUCKS";
    public static final String MEMBER_NUMBER_STARBUCKS = "MEMBER_NUMBER_STARBUCKS";
    public static final String SECURITY_TOKEN_STARBUCKS = "SECURITY_TOKEN_STARBUCKS";
    public static final String NUMBER_CARD_STARBUCKS = "NUMBER_CARD_STARBUCKS";

    /* Eventos de Analytics */
    public static final String EVENT_SPLASH = "event_splash";
    public static final String EVENT_LOG_IN = "event_log_in";
    public static final String EVENT_MOVS_EMISOR = "event_movs_emisor";
    public static final String EVENT_MOVS_ADQ = "event_movs_adq";
    public static final String EVENT_APROV = "event_aprov";
    public static final String EVENT_BALANCE_EMISOR = "event_balance_emisor";
    public static final String EVENT_BALANCE_ADQ = "event_balance_adq";
    public static final String EVENT_SHORTCUT_CHARGE = "event_shortcut_charge";
    public static final String EVENT_BALANCE_UYU = "event_balance_uyu";
    public static final String EVENT_SEND_MONEY = "event_send_money";
    public static final String EVENT_ADD_FAV = "event_add_fav";
    public static final String EVENT_EDIT_FAV = "event_edit_fav";
    public static final String EVENT_DELETE_FAV = "event_delete_fav";
    public static final String EVENT_CHARGE_ADQ_REG = "event_charge_adq_reg";
    public static final String EVENT_CHARGE_ADQ_CL = "event_charge_adq_cl";
    public static final String EVENT_RECHARGE_PHONE = "event_recharge_phone";
    public static final String EVENT_SERV_PAYMENT = "event_serv_payment";
    public static final String EVENT_REGISTER_YG = "event_register_yg";
    public static final String EVENT_REGISTER_ADQ = "event_register_adq";

    /* Segmentaciones eventos Analytics */
    public static final String CONNECTION_TYPE = "connection_type";
    public static final String COMPANY = "company";
    public static final String AMOUNT = "amount";
    public static final String EMAIL_REGISTER = "email_register";

    /* */
    public static final String IS_COACHMARK = "IS_COACHMARK";
}