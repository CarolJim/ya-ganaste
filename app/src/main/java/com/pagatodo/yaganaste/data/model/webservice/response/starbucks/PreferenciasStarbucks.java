package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

/**
 * Created by asandovals on 20/04/2018.
 */

public class PreferenciasStarbucks {

    boolean recibirEmails,recibirMensajes,recibirNotificaciones;

    public boolean isRecibirEmails() {
        return recibirEmails;
    }

    public void setRecibirEmails(boolean recibirEmails) {
        this.recibirEmails = recibirEmails;
    }

    public boolean isRecibirMensajes() {
        return recibirMensajes;
    }

    public void setRecibirMensajes(boolean recibirMensajes) {
        this.recibirMensajes = recibirMensajes;
    }

    public boolean isRecibirNotificaciones() {
        return recibirNotificaciones;
    }

    public void setRecibirNotificaciones(boolean recibirNotificaciones) {
        this.recibirNotificaciones = recibirNotificaciones;
    }
}
