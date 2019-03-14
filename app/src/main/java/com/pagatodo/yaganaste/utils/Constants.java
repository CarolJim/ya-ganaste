package com.pagatodo.yaganaste.utils;

/**
 * Created by mfernandez on 08/02/17.
 */

public class Constants {

    /*Permissions*/
    public static final int PERMISSION_GENERAL = 111;
    public static final int PERMISSION_CAMERA_RESQUEST = 112;
    public static final int PERMISSION_LOCATION_RESQUEST = 113;
    public static final int PERMISSION_SMS_RESQUEST = 114;

    public static final int DELAY_MESSAGE_PROGRESS = 1500;

    //region requests codes
    public static final int CONTACTS_CONTRACT = 50;
    public static final int CONTACTS_CONTRACT_FAMILIAR = 51;
    public static final int CONTACTS_CONTRACT_PERSONAL = 52;
    public static final int CONTACTS_CONTRACT_PROVEEDOR = 53;

    public static final int BARCODE_READER_REQUEST_CODE = 60;
    public static final int CREDITCARD_READER_REQUEST_CODE = 61;
    public static final int BARCODE_READER_REQUEST_CODE_COMERCE = 62;
    public static final int BARCODE_READER_REQUEST_QR_SENDS = 72;
    public static final int BACK_FROM_PAYMENTS = 190;


    public static final int PAYMENTS_ADQUIRENTE = 818;
    public static final int ACTIVITY_LANDING = 181;

    public static final String RESULT = "Resultado";
    public static final String MESSAGE = "Mensaje";
    public static final String RESULT_ERROR = "Fail";
    public static final String RESULT_SUCCESS = "Success";

    public static final int RESULT_CODE_OK = 1;
    public static final int RESULT_CODE_OK_CLOSE = 2;
    public static final int RESULT_CODE_FAIL = 0;
    public static final int RESULT_CODE_BACK_PRESS = 22;
    //endregion

    //region Comercios Id
    public static final int IAVE_ID = 7;
    public static final int IECISA = 5;
    public static final int AVON = 2;
    public static final int CABLEV = 25;
    public static final int SKY = 17;
    public static final int TELMEXSR = 21;
    public static final int MAFER = 9;

    //endregion
    public static final String TIPO_TRANSACCION_CHIP = "1";
    public static final String TIPO_TRANSACCION_BANDA = "2";

    /* FAVORITE CONSTANTS */
    public static final int NEW_FAVORITE_FROM_CERO = 1;
    public static final int NEW_FAVORITE_FROM_OPERATION = 2;
    public static final int EDIT_FAVORITE = 3;

    // PaymentActivity
    public static final int PAYMENT_RECARGAS = 1;
    public static final int PAYMENT_SERVICIOS = 2;
    public static final int PAYMENT_ENVIOS = 3;

    /* Movements Id's */
    public static final int MOVEMENTS_EMISOR = 1;
    public static final int MOVEMENTS_ADQ = 2;
    public static final int MOVEMENTS_STARBUCKS = 3;

    /* Estatus de registro */
    public static final int SIN_REGISTRO = 0;
    public static final int USUARIO_CREADO = 1;
    public static final int CUENTA_ASIGNADA = 2;
    public static final int NIP_ASIGNADO = 3;
    public static final int AGENTE_CREADO = 4;
    public static final int USUARIO_APROVISIONADO = 5;
}
