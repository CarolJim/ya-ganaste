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
    VALIDAR_DATOS_PERSONAHOMO,
    CREAR_AGENTE,
    CREAR_USUARIO_FWS,
    CREAR_USUARIO_FWS_LOGIN,
    CREAR_USUARIO_CLIENTE,
    ELIMINAR_AVATAR,
    INICIAR_SESION,
    INICIAR_SESION_SIMPLE,
    INICIAR_SESION_SIMPLE_UYU,
    CHANGE_PASS_6,
    LOCALIZAR_SUCURSALES,
    OBTENER_CATALOGOS,
    OBTENER_BANCOSBIN,
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
    GENERAR_CODIGO_RECUPERACION,
    ENVIARCORREO_CONTACTANOS,
    OBTENER_COBROS_MENSUALES,
    OBTENER_PAISES,
    OBTENER_MONTOS,
    OBTENER_ORIGEN_RECURSOS,
    OBTENER_DESTINO_RECURSOS,

    /**
     * WS Transaccional
     **/
    ASIGNAR_CUENTA_DISPONIBLE,
    ASIGNAR_NIP,
    ASIGNAR_NEW_NIP,
    ASOCIAR_TARJETA_CUENTA,
    BLOQUEO_TARJETA_DEFINITIVO,
    BLOQUEAR_TEMPORALMENTE_TARJETA,
    CONSULTAR_ASIGNACION_TARJETA,
    REMPLAZAR_TARJETA_DIRECTO,
    CONSULTAR_SALDO,
    CONSULTAR_TITULAR_CUENTA,
    CREAR_CLIENTE,
    EJECUTAR_TRANSACCION,
    VALIDAR_ESTATUS_TRANSACCION,
    FONDEAR_CUPO,
    OBTENER_ESTATUS_TARJETA,
    CONSULTAR_SALDO_ADQ,
    CONSULTAR_SALDO_ADQ_ADM,
    ENVIAR_TICKET_TAEPDS,
    ENVIAR_TICKET,
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
    ENVIAR_TICKET_COMPRA_AUTOM, // Envío Automático al Agente
    ENVIAR_TICKET_COMPRA,
    SHARED_TICKET_COMPRA,
    TRANSACCIONES_TAE,
    TRANSACCIONES_PDS,
    CONSULTA_MOVIMIENTOS_MES_ADQ,
    CONSULTA_SALDO_CUPO,
    CANCELA_TRANSACTION_EMV_DEPOSIT,
    REVERSA_TRANSACTION_EMV_DEPOSIT,
    OBTIENE_DATOS_CUPO,

    /**
     *
     * ws operadores
     */
    CHANGE_STATUS_OPERADOR,
    GET_OPERADOR,
    GET_RESUMENDIA,

    /**
     * WS STARBUCKS
     */
    CONSULTAR_MOV_SB,
    /**
     * Servicios para consultar el saldo de SB
     */
    CONSULTAR_SALDO_SB,
    /**
     * Servicio para crer usuario
     **/
    CREAR_USUARIO_COMPLETO,
    /**
     * Servicio para crer usuario
     **/
    DESASOCIAR_DISPOSITIVO,
    /**
     * Servicio para obtener los datos de la cuenta.
     **/
    UPDATE_DATOS_CUENTA,
    /**
     * Servicio para Bloquear y Desbloquear la TDC de la cuenta.
     **/
    BLOQUEAR_CUENTA,
    /**
     * Servicio para ESTATUS la TDC de la cuenta.
     **/
    ESTATUS_CUENTA,
    /**
     * Servicio para Add Favorites de pagos.
     **/
    ADD_FAVORITES,
    /**
     * Servicio para Add New Favorites de pagos.
     **/
    ADD_NEW_FAVORITES,
    /**
     * Servicio para Add New Foto Favorites de pagos.
     **/
    ADD_NEW_FOTO_FAVORITES,
    /**
     * Servicio para Add New Foto Favorites de pagos.
     **/
    EDIT_FAVORITES,
    /**
     * Servicio para Borrar Favorites de pagos.
     **/
    DELETE_FAVORITE,
    /**
     * Servicios CUPO
     */
    CONSULTA_STATUS_REGISTRO_CUPO,
    CREA_SOLICITUD_CUPO,
    CARGA_DOCUMENTOS_CUPO,
    OBTENER_ESTADO_DOCUMENTOS_CUPO,
    ACTUALIZA_DOCUMENTOS_CUPO,
    ACTUALIZA_REFERENCIAS,
    /**
     * Servicios para Favoritos
     */
    OBTENER_FAVORITOS,
    /**
     * Operaciones FBM
     **/
    FB_REGISTER_TOKEN,
    GET_FIRST_DATA_NOTIFICATION, // Lista de notificaciones del usuario
    GET_NEXT_DATA_NOTIFICATION, // Lista de notificaciones del usuario
    /* Obtiene lista de tipos de reembolso */
    GET_TYPE_REPAYMENT,
    /* Actualiza el tipo de reembolso */
    UPDATE_TYPE_REPAYMENT,
    SEND_REEMBOLSO,
    GET_INFORMACION_AGENTE,
    /* Login para starbucks */
    LOGINSTARBUCKS,
    /* Login para starbucks */
    PREREGISTRO,
    /* Login para starbucks */
    FORGETPASSWORD,
    /* Login para starbucks */
    REGISTROCOMPLETE,
    /* Datos persona para registro starbucks */
    DATOSPERSONAREGISTROSTAR,
    /* Obtener Sucursales Starbucks */
    GET_STARBUCKS_STORES,
    /*cancelar cuenta*/
    CANCELACCOUNT,
    /* Detalle de movimiento */
    DETAIL_MOVEMENT,
    /* Obtener Informacion Comercio */
    INFO_COMERCIO,
    /* Consultar Saldo UyU */
    CONSULT_BALANCE_UYU,
    /* validacion de plates*/
    VALIDATE_PLATE,
    /* Vinculacion de qr*/
    LINKED_QR,
    /* Actualizar Token Firebase*/
    UPDATEFIREBASETOKEN,
    /*Nuevo QR*/
    NEW_QR,
    /* Actualizar QR*/
    UPDATE_QR,
    /*Validate QR*/
    DATA_QR,
    /*Notification*/
    NOTIFICATION
}

