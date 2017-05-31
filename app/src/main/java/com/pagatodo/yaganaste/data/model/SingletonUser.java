package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;

import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;

/**
 * @author flima
 */

public class SingletonUser {

    public static SingletonUser user;

    private DataIniciarSesion dataUser;
    private ExtraInfoUser dataExtraUser;
    private DatosSaldo datosSaldo;
    private String pathPictureTemp = "";
    private String activacionCodeFreja = "";

    private SingletonUser(){
        dataUser = new DataIniciarSesion();
        dataExtraUser = new ExtraInfoUser();
        datosSaldo = new DatosSaldo();
    }

    public static synchronized SingletonUser getInstance(){
        if(user == null)
            user = new SingletonUser();
        return user;
    }


    public DataIniciarSesion getDataUser() {
        return dataUser;
    }

    public void setDataUser(DataIniciarSesion dataUser) {
        this.dataUser = dataUser;


        dataUser.getUsuario().setTipoAgente(17);
        if (dataUser.isEsAgente() && dataUser.getEstatusAgente() != CRM_DOCTO_APROBADO
                && dataUser.getEstatusDocumentacion() != STATUS_DOCTO_PENDIENTE) {
            App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        }
    }

    public ExtraInfoUser getDataExtraUser() {
        return dataExtraUser;
    }

    public void setDataExtraUser(ExtraInfoUser dataExtraUser) {
        this.dataExtraUser = dataExtraUser;
    }

    public String getPathPictureTemp() {
        return pathPictureTemp;
    }

    public void setPathPictureTemp(String pathPictureTemp) {
        this.pathPictureTemp = pathPictureTemp;
    }

    public String getActivacionCodeFreja() {
        return activacionCodeFreja;
    }

    public void setActivacionCodeFreja(String activacionCodeFreja) {
        this.activacionCodeFreja = activacionCodeFreja;
    }

    public DatosSaldo getDatosSaldo() {
        return datosSaldo;
    }

    public void setDatosSaldo(DatosSaldo datosSaldo) {
        this.datosSaldo = datosSaldo;
    }
}
