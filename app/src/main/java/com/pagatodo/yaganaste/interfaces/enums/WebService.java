package com.pagatodo.yaganaste.interfaces.enums;

/**
 * ENUM DE LOS METODOS
 */
public enum WebService {
    /**
     * WS Administrativo
     **/
    ACTIVACION_SERVICIO_MOVIL,
    ACTUALIZAR_AVATAR,
    ACTUALIZAR_INFO_SESION,
    ASIGNAR_CONTRASENIA,
    CAMBIAR_CONTRASENIA,
    CARGA_DOCUMENTOS,
    ACTUALIZAR_DOCUMENTOS,
    CERRAR_SESION,
    CONSULTAR_MOVIMIENTOS_MES,
    VALIDAR_DATOS_PERSONA,
    CREAR_AGENTE,
    CREAR_USUARIO_FWS,
    CREAR_USUARIO_FWS_LOGIN,
    CREAR_USUARIO_CLIENTE,
    ELIMINAR_AVATAR,
    INICIAR_SESION,
    INICIAR_SESION_SIMPLE,
    LOCALIZAR_SUCURSALES,
    OBTENER_CATALOGOS,
    OBTENER_COLONIAS_CP,
    OBTENER_DOCUMENTOS,
    OBTENER_NUMDOCS_PENDIENTES,
    OBTENER_NUMERO_SMS,
    RECUPERAR_CONTRASENIA,
    VALIDAR_ESTATUS_USUARIO,
    VALIDAR_FORMATO_CONTRASENIA,
    VERIFICAR_ACTIVACION,
    RECURSO_IMAGEN,
    ACTIVACION_APROV_SOFTTOKEN,
    VERIFICAR_ACTIVACION_APROV_SOFTTOKEN,
    INICIAR_TRANSACCION_ONLINE,
    GET_JSONWEBTOKEN,
    OBTENER_SUBGIROS,
    OBTENER_DOMICILIO_PRINCIPAL,
    OBTENER_DOMICILIO,
    /**
     * WS Transaccional
     **/
    ASIGNAR_CUENTA_DISPONIBLE,
    ASIGNAR_NIP,
    ASIGNAR_NEW_NIP,
    ASOCIAR_TARJETA_CUENTA,
    BLOQUEAR_TEMPORALMENTE_TARJETA,
    CONSULTAR_ASIGNACION_TARJETA,
    CONSULTAR_SALDO,
    CONSULTAR_TITULAR_CUENTA,
    CREAR_CLIENTE,
    EJECUTAR_TRANSACCION,
    VALIDAR_ESTATUS_TRANSACCION,
    FONDEAR_CUPO,
    OBTENER_ESTATUS_TARJETA,
    CONSULTAR_SALDO_ADQ,
    ENVIAR_TICKET_TAEPDS,
    /**
     * WS Adquiriente
     **/
    LOGIN_ADQ,
    REGISTRO_DONGLE,
    REGISTRA_NIP,
    CONSULTA_SESION_AGENTE,
    REGISTRO_DEVICE_DATA,
    REGISTRA_NOTIFICACION,
    AUTENTICA_NIP,
    TRANSACCIONES_EMV_DEPOSIT,
    FIRMA_DE_VOUCHER,
    ENVIAR_TICKET_COMPRA,
    TRANSACCIONES_TAE,
    TRANSACCIONES_PDS,
    CONSULTA_MOVIMIENTOS_MES_ADQ,
    CONSULTA_SALDO_CUPO,
    CANCELA_TRANSACTION_EMV_DEPOSIT,
    /**
     * Servicio para crer usuario
     **/
    CREAR_USUARIO_COMPLETO,
    /**
     * Servicio para crer usuario
     **/
    DESASOCIAR_DISPOSITIVO,

    /**
     * Servicios CUPO
     * */
    CONSULTA_STATUS_REGISTRO_CUPO,
    CREA_SOLICITUD_CUPO,
    CARGA_DOCUMENTOS_CUPO,
    OBTENER_ESTADO_DOCUMENTOS_CUPO




}

