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
			URL_SERVER_ADQ = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov";
		} else {
			URL_SERVER_ADTVO = "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc";
			URL_SERVER_TRANS = "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc";
			URL_SERVER_ADQ = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov";
		}

	}

	/*Estatus Respuesta de Ws YaGanaste*/

	//MAPS
	public final static int ZoomMap = 16;
	public static final int CODE_OK = 0;
	public static final int ERROR_LOGIN= 91;

	/*Preferencias de Sesion*/
	//public static final String DATE_SESION = "DATE_SESION";

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






}