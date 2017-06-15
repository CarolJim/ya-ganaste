package com.pagatodo.yaganaste.utils;

public class Recursos {

	public final static Boolean DEBUG = true;
	//HOST
	public static final String URL_SERVER_ADTVO;
	public static final String URL_SERVER_TRANS;
	public static final String URL_SERVER_ADQ;

	//BASE DE DATOS
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "";

	public static final int TIMEOUT = 15000;

	//URL DE WS
	static {
		if (DEBUG) {
			URL_SERVER_ADTVO = "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc";
			URL_SERVER_TRANS = "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc";
			URL_SERVER_ADQ = "http://agentemovildes.pagatodo.com/agentemovil.svc";
			//URL_SERVER_ADQ = "http://10.10.45.18:7777/Middleware.svc";


		} else {
			URL_SERVER_ADTVO = "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc";
			URL_SERVER_TRANS = "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc";
			URL_SERVER_ADQ = "http://agentemovildes.pagatodo.com/agentemovil.svc";
		}
	}

	/*Estatus Respuesta de Ws YaGanaste*/

	//MAPS
	public final static int ZoomMap = 16;
	public static final int CODE_OK = 0;

	public static final String CODE_ADQ_OK = "00";
	public static final int ERROR_LOGIN= 91;
	public static final int CODE_OFFLINE = -1;
	public static final int INVALID_TOKEN = 3001;
    public static final int INCORRECT_FORMAT = -2;
	public static final int DEVICE_ALREADY_ASSIGNED = 224;

	/*WS Adq*/
	public static final String ADQ_CODE_OK = "00";
	public static final String ADQ_ACCES_DENIED = "3001";
	public static final int ADQ_TRANSACTION_APROVE = 0;
	public static final int ADQ_TRANSACTION_ERROR = 1;

	/*Preferencias de Sesion*/
	public static final String CODE_ACTIVATION_FREJA = "CODEACTIVATIONFREJA";
	public static String COUCHMARK_EMISOR = "COUCHMARKEMISOR";
	public static String COUCHMARK_ADQ = "COUCHMARKADQ";
	public static String CRC32_FREJA = "CRC32FREJA";
	public static String SEND_DOCUMENTS = "FLAG_DOCUMENTS";
	public static final String ADQ_PROCESS = "FLAG_PROCESS";

	/**Catálogos**/
	/*PTH – Estatus Comercios*/
	public static final int PTH_DOCUMENTACION_INCOMPLETA = 7;
	public static final int PTH_PENDIENTE_DE_REVISION = 8;
	public static final int PTH_EN_REVISION = 9;
	public static final int PTH_RECHAZADO = 10;
	public static final int PTH_AGENTE_EN_TRAMITE = 11;
	/*PTH – Estatus Documentos*/
	public static final int PTH_PENDIENTE = 1;
	public static final int PTH_DOCTO_APROBADO = 2;
	public static final int PTH_DOCTO_RECHAZADO = 3;
	/*CRM – Adquiriente*/
	public static final int CRM_PENDIENTE = 1;
	public static final int CRM_DOCTO_APROBADO = 2;
	public static final int CRM_DOCTO_RECHAZADO = 3;

	public static final int DOC_ID_FRONT = 5;
	public static final int DOC_ID_BACK = 6;
	public static final int DOC_DOM_FRONT = 7;
	public static final int DOC_DOM_BACK = 30;


	public static final int STATUS_DOCTO_ACTUALIZADO = 5;
	public static final int STATUS_DOCTO_RECHAZADO = 3;
	public static final int STATUS_DOCTO_APROBADO = 2;
	public static final int STATUS_DOCTO_PENDIENTE = 1;
    public static final int STATUS_DOCTO_SIN_ENVIAR = 0;
	/*Tipo de Comercios*/
	public static final int TELEFONICA = 1;
	public static final int COMPANIA_DE_SERVICIOS = 2;
	public static final int INSTITUCIONES_FINANCIERAS = 3;
	/*Tipo de Movimientos*/
	public static final int CARGO = 1;
	public static final int ABONO = 2;
	public static final int PROMOCODE = 3;
	/*Tipo de Transaccion*/
	public static final int RECARGA = 1;
	public static final int PAGO_DE_SERVICIO = 2;
	public static final int ENVIO_DE_DINERO = 3;
	/*Id's de Preguntas  en Cuestionario Negocio*/ //TODO esto debería de ser un catálogo de ws
	public static final int PREGUNTA_VENTAS = 1;
	public static final int PREGUNTA_TRANSACCIONES = 2;
	public static final int PREGUNTA_GIRO = 3;
	public static final int PREGUNTA_FAMILIAR = 4;
	public static final int PREGUNTA_DOMICILIO = 5;
	public static final int PREGUNTA_BENEFICIARIOS = 6;



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
	public static final String KSN_LECTOR = "KSN_LECTOR";

	/*Url Legales*/
	public static final String URL_LEGALES_TERMINOS = "https://www.yaganaste.com/#";
	public static final String URL_LEGALES_PRIVACIDAD = "http://www.movistar.com.mx/terminos-y-condiciones";
	public static final String PUBLIC_KEY_RSA = "rk2QHAmXByr9wIf6d1cgU+f9NtKvj2xWvRv2wUcZSMVvhfTkcoWLG/CxEK+weoS3QcxxEWKFrWgwhYABXpkGhlXiqH7GyRIhv2kQtuZlGJJSIExd2asJrtjDnfStu7ZKbdIpLzqFUfo8naDhCuQTzhyApyJQ9HDcOSTFuRhJ7Mz3gXwUXqr98i+he+iYCzyrMViP+o4UPUqfNcpSafUw4NYre9KEZoHMaKcPMR4bMjax3Payt9LDAU3KgBOnWS9Ga6WffE03tpAWqE3ape61CmPw5QKPgRNKSnV70wu7f02jmstEepM35aSf3gL9SKMUv3DkwYIpifhNYPbdKCh+BQ==";
	public static final String DEFAULT_CARD = "5389 84";

}