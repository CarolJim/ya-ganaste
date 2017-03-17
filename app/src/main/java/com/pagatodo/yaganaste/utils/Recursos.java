package com.pagatodo.yaganaste.utils;

import java.util.ArrayList;
import java.util.List;

public class Recursos {

	public final static Boolean DEBUG = true;
	public static final String URL_SERVER_ADTVO;
	public static final String URL_SERVER_TRANS;
	public static final String URL_SERVER_ADQ;

	//BASE DE DATOS
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "";

	//URL DE WS
	static {
		if (DEBUG) {
			URL_SERVER_ADTVO = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov/";
			URL_SERVER_TRANS = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov/";
			URL_SERVER_ADQ = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov/";
		} else {
			URL_SERVER_ADTVO = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov/";
			URL_SERVER_TRANS = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov/";
			URL_SERVER_ADQ = "http://agentemovildes.pagatodo.com/agentemovil.svc/agMov/";
		}
	}

	//MAPS
	public final static int ZoomMap = 16;


	/*Preferencias de Sesion*/
	//public static final String DATE_SESION = "DATE_SESION";


}