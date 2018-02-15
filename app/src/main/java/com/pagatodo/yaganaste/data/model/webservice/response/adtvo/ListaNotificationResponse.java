package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Manzo on 21/03/2017.
 * Encargada de crear el objeto de respuesta del proceso de registro de Token
 */

public class ListaNotificationResponse extends GenericResponse {

    private DataListaNotification Respuesta;
    private List<DataListaNotificationArray> Notificaciones;

    public ListaNotificationResponse() {
        Respuesta = new DataListaNotification();
        Notificaciones = new ArrayList<>();
    }

    public DataListaNotification getRespuesta() {
        return Respuesta;
    }

    public List<DataListaNotificationArray> getNotificaciones() {
        return Notificaciones;
    }
}
