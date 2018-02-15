package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/03/2017.
 * Encargada de obtener los datos de la respuesta de registro de Token
 */

public class DataListaNotificationArray implements Serializable {

    String FechaMovimiento;
    String HoraMovimiento;
    int IdNotificacion;
    boolean Leido;
    String Mensaje;
    String Titulo;

    public String getFechaMovimiento() {
        return FechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        FechaMovimiento = fechaMovimiento;
    }

    public String getHoraMovimiento() {
        return HoraMovimiento;
    }

    public void setHoraMovimiento(String horaMovimiento) {
        HoraMovimiento = horaMovimiento;
    }

    public int getIdNotificacion() {
        return IdNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        IdNotificacion = idNotificacion;
    }

    public boolean isLeido() {
        return Leido;
    }

    public void setLeido(boolean leido) {
        Leido = leido;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
}
