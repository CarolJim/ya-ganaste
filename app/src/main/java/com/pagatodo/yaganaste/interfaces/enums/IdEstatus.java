package com.pagatodo.yaganaste.interfaces.enums;

/**
 * Created by Armando Sandoval on 17/08/2017.
 */

public enum IdEstatus {

    I0(0, "No Registrado (EsUsuario = FALSE)"),
    I1(1, "Usuario con registro en el Framework de Seguridad (EsUsuario =TRUE)"),
    I2(2, "Usuario Emisor registrado como cliente en Banco PagaTodo (EsCliente =TRUE)"),
    I3(3, "Usuario Emisor con cuenta en Banco PagaTodo (ConCuenta = TRUE)"),
    I4(4, "Usuario Emisor requiere activación SMS (RequiereActivacionSMS = TRUE, SOLAMENTE PARA DISPOSITIVOS MOVILES)"),
    I5(5, "Usuario Emisor con cuenta en Banco PagaTodo, con Dispositivo Asociado (RequiereActivacionSMS = FALSE, SOLAMENTE PARA DISPOSITIVOS MOVILES)"),
    I6(6, "Usuario Profesionista con datos capturados (EsAgente = TRUE)"),
    I7(7, "Usuario Profesionista con documentación pendiente de revisión\n" + "(Recién subió toda la documentación pero mesa de control aún no ha revisado)\n" + "(EstatusDocumentacion)"),
    I8(8, "Usuario Profesionista en proceso de revisión de documentación\n" + "(Mesa de control inició con la revisión de documentos, aquí es donde cada documento empezará a cambiar de estatus)\n" + "(EstatusDocumentacion y EstatusAgente)"),
    I9(9, "Usuario Profesionista con documentación rechazada (aún podría subir el o los documentos rechazados) (EstatusDocumentacion y EstatusAgente)"),
    I10(10, "Usuario Profesionista cancelado (rechazo definitivo del trámite adquirente en SAV, el usuario deberá prevalecer solo como Emisor) (EstatusAgente)"),
    I11(11, "Usuario Profesionista con su Id Agente en trámite (el trámite ha pasado hacia el CRM) (EstatusAgente)"),
    I12(12, "Usuario Profesionista con su Id Agente autorizado (significa que puede vender TAE y PDS) (EstatusAgente)"),
    I13(13, "Usuario Profesionista rechazado (rechazo definitivo del trámite adquirente en CRM, el usuario deberá prevalecer solo como Emisor) (EstatusAgente)"),
    I14(14, "Usuario Comercio con datos capturados"),
    I15(15, "Usuario Comercio rechazado (rechazo definitivo del trámite CUPO, el usuario deberá prevalecer solo como Profesionista)"),
    I16(16, "Usuario Comercio autorizado, con límite de crédito");

    private String descripcion;
    private int id;

    IdEstatus(int id, String des) {
        this.descripcion = des;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
