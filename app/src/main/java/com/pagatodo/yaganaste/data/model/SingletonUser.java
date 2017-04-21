package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;

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
