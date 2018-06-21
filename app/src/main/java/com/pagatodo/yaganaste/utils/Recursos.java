package com.pagatodo.yaganaste.utils;

import com.pagatodo.yaganaste.BuildConfig;

public class Recursos {

    //HOST
    public static final String URL_SERVER_ADTVO;
    public static final String URL_SERVER_TRANS;
    public static final String URL_SERVER_ADQ;
    public static final String URL_SERVER_FB;
    public static final String URL_STARBUCKS;
    public static final String URL_COUNTLY;

    //BASE DE DATOS
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ya_ganaste.db";

    public static final String PUBLIC_KEY_RSA;
    public static final String PUBLIC_STARBUCKS_KEY_RSA;

    public static final String PIN_ADVO;
    public static final String PIN_TRANS;
    public static final String PIN_YA;
    public static final String PIN_STARBUCKS;

    public static final int IDCOMERCIO_YA_GANASTE = 8609;
    public static final int GROUP_FORMAT = 4;

    public static final int WS_TIMEOUT = 60000;
    public static final long SESSION_TIMEOUT = 120000;

    /*Freja*/
    public static final String PT_CLIENT_CODE = null;

    //URL DE WS
    static {

        if (BuildConfig.DEBUG) {
            URL_SERVER_ADTVO = "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc";
            URL_SERVER_TRANS = "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc";
            URL_SERVER_ADQ = "https://agentemovildes.pagatodo.com/AgenteMovil_YG/Middleware.svc";
            URL_SERVER_FB = "http://10.10.45.13:6075/NotificacionesYG.svc/";
            URL_STARBUCKS = "https://crt-rewards.starbucks.mx";
            URL_COUNTLY = "https://us-try.count.ly";

            PUBLIC_KEY_RSA = "rk2QHAmXByr9wIf6d1cgU+f9NtKvj2xWvRv2wUcZSMVvhfTkcoWLG/CxEK+weoS3QcxxEWKFrWgwhYABXpkGhlXiqH7GyRIhv2kQtuZlGJJSIExd2asJrtjDnfStu7ZKbdIpLzqFUfo8naDhCuQTzhyApyJQ9HDcOSTFuRhJ7Mz3gXwUXqr98i+he+iYCzyrMViP+o4UPUqfNcpSafUw4NYre9KEZoHMaKcPMR4bMjax3Payt9LDAU3KgBOnWS9Ga6WffE03tpAWqE3ape61CmPw5QKPgRNKSnV70wu7f02jmstEepM35aSf3gL9SKMUv3DkwYIpifhNYPbdKCh+BQ==";
            PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";

            PIN_ADVO = "275a28946f92da9acab52475df6ec73a10a40811";
            PIN_TRANS = "275a28946f92da9acab52475df6ec73a10a40811";
            PIN_YA = "af0758ac6ce95cddd1ea59eceba5ba001636cc1d";
            PIN_STARBUCKS = "425965554aaa92372ccb807ff20a35d26f72d20d";
        } else {
            URL_SERVER_ADTVO = "https://wcf.yaganaste.com:8031/ServicioYaGanasteAdtvo.svc";
            //URL_SERVER_ADTVO = "https://wcfpreprod.yaganaste.com:8031/ServicioYaGanasteAdtvo.svc";
            URL_SERVER_TRANS = "https://wcf.yaganaste.com:8032/ServicioYaGanasteTrans.svc";
            //URL_SERVER_TRANS = "https://wcfpreprod.yaganaste.com:8032/ServicioYaGanasteTrans.svc";
            URL_SERVER_ADQ = "https://adqyaganaste.pagatodo.com:19447/Middleware.svc";
            //URL_SERVER_ADQ = "https://adqyaganastepreprod.yaganaste.com:19446/Middleware.svc";
            URL_SERVER_FB = "http://10.10.45.13:6075/NotificacionesYG.svc/";
            URL_STARBUCKS = "https://crt-rewards.starbucks.mx";
            URL_COUNTLY = "https://us-try.count.ly";

            PUBLIC_KEY_RSA = "pIznw1pWFzzOVI+Shkg56ujssxRhQv1DTHeU5LMtgSNCOY3iw1TacI6+Db/YUQsexjvfEcjQsg9QOJp3Q1maI5hEMiWG84tsKBpgBckZoDKcaoN7JtGo3p2BIG/eCm1yLmxSrDpcnNZ6Z8GnUGaQPWxy75E8/U57XrpAyURFNTbbeq0uSxkcoB/5xmyjoECTKpWfD+M8PpAnisBLd0oSYTZ+tmBdgLPQJOe794ZyV+DoX5eU9G7hKx1onCHpuPb/xtx3rnMUIR2qB0sD0hKAjXsyvEiNrrihfxLa3IdcEnWn5CcxcsYiaSJqLlDKlkr+07ji/CqYm5hcvr2CbhhUfw==";
            PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";

            PIN_ADVO = "3f3add61acd8b7a3ad1536566669e731ea6e9cea";
            PIN_TRANS = "3f3add61acd8b7a3ad1536566669e731ea6e9cea";
            PIN_YA = "275a28946f92da9acab52475df6ec73a10a40811";
            //PIN_YA = "3f3add61acd8b7a3ad1536566669e731ea6e9cea";
            PIN_STARBUCKS = "425965554aaa92372ccb807ff20a35d26f72d20d";
        }
    }

    /*Estatus Respuesta de Ws YaGanaste*/

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

    /*Adq / Dongle*/
    public static final int ENCENDIENDO = 100;
    public static final int ENCENDIDO = 110;
    public static final int APAGANDO = 120;
    public static final int APAGADO = 130;
    public static final int DESCONECTADO = 140;
    public static final int LEYENDO = 150;
    public static final int SW_ERROR = 160;
    public static final int LECTURA_OK = 170;
    public static final int SW_TIMEOUT = 180;
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

    public static final String URL_LEGALES_TERMINOS_LINEAC = "http://dev.yaganaste.com/terminos-y-condiciones-lc.html";
    public static final String URL_LEGALES_PRIVACIDAD_LINEAC = "http://dev.yaganaste.com/aviso-de-privacidad-lc.html";

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
    public static final String CODE_ACTIVATION_FREJA = "CODEACTIVATIONFREJA";
    public static String COUCHMARK_EMISOR = "COUCHMARKEMISOR";
    public static String COUCHMARK_ADQ = "COUCHMARKADQ";
    public static String COUCHMARK_EDIT_FAV = "COUCHMARKEF";

    public static String CO_QUICK_EM = "CO_QUICK_EM";
    public static String CO_QUICK_ADQ = "CO_QUICK_ADQ";

    public static String SHA_256_FREJA = "SHA_256_FREJA";
    public static String SHA_256_STARBUCKS = "SHA_256_STARBUCKS";
    public static String GENERO = "GENERO";
    public static String HUELLACADENA = "HUELLACADENA";
    public static String HUELLA_FAIL = "HUELLA_FAIL";
    public static String VERSION_APP = "VERSION_APP";
    public static String FIREBASE_KEY = "FIREBASE_KEY";
    public static String NOTIF_COUNT = "NOTIF_COUNT";

    public static String SEND_DOCUMENTS = "FLAG_DOCUMENTS";
    public static final String ADQ_PROCESS = "FLAG_PROCESS";
    public static final String CUPO_PROCESS = "FLAG_PROCESS_CUPO";
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
    public static final String CARD_NUMBER = "CARD_NUMBER";
    public static final String AGENTE_NUMBER = "AGENTE_NUMBER";
    public static final String ID_COMERCIOADQ = "ID_COMERCIO";
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

    public static final String HAS_PROVISIONING = "HAS_PROVISIONING";
    public static final String USER_PROVISIONED = "USER_PROVISIONED";
    public static final String HAS_PUSH = "HAS_PUSH";

    public static final String OLD_NIP = "OLD_NIP";
    public static final String HAS_TOKEN_ONLINE = "HAS_TOKEN_ONLINE";
    public static final String TOKEN_FIREBASE = "TOKEN_FIREBASE";
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
    public static final String MODE_CONNECTION_DONGLE = "MODE_CONNECTION_DONGLE"; // int
    public static final String BT_PAIR_DEVICE = "BT_PAIR_DEVICE"; // string

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

    /* Eventos de Countly */
    public static final String EVENT_SPLASH = "EVENT_SPLASH";
    public static final String EVENT_LOG_IN = "EVENT_LOG_IN";
    public static final String EVENT_MOVS_EMISOR = "EVENT_MOVS_EMISOR";
    public static final String EVENT_MOVS_ADQ = "EVENT_MOVS_ADQ";
    public static final String EVENT_APROV = "EVENT_APROV";
    public static final String EVENT_BALANCE_EMISOR = "EVENT_BALANCE_EMISOR";
    public static final String EVENT_BALANCE_ADQ = "EVENT_BALANCE_ADQ";

    /* Segmentaciones eventos County */
    public static final String CONNECTION_TYPE = "CONNECTION_TYPE";
}