package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;

import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.utils.StringConstants.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.StringConstants.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.StringConstants.ID_CUENTA;
import static com.pagatodo.yaganaste.utils.StringConstants.NAME_USER;
import static com.pagatodo.yaganaste.utils.StringConstants.SIMPLE_NAME;
import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

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

    private SingletonUser() {
        dataUser = new DataIniciarSesion();
        dataExtraUser = new ExtraInfoUser();
        datosSaldo = new DatosSaldo();
    }

    public static synchronized SingletonUser getInstance() {
        if (user == null)
            user = new SingletonUser();
        return user;
    }


    public DataIniciarSesion getDataUser() {
        return dataUser;
    }

    public void setDataUser(DataIniciarSesion dataUser) {
        this.dataUser = dataUser;

        Preferencias prefs = App.getInstance().getPrefs();

        if (dataUser.isConCuenta()) {
            prefs.saveDataBool(HAS_SESSION, true);
            String name = dataUser.getUsuario().getNombre();
            prefs.saveData(SIMPLE_NAME, name.contains(" ") ? name.substring(0, name.indexOf(" ")) : name + SPACE + dataUser.getUsuario().getPrimerApellido());
            prefs.saveData(NAME_USER, dataUser.getUsuario().getNombre());
            prefs.saveData(FULL_NAME_USER, dataUser.getUsuario().getNombre().concat(SPACE).
                    concat(dataUser.getUsuario().getPrimerApellido().concat(SPACE).
                            concat(dataUser.getUsuario().getSegundoApellido())));
            prefs.saveData(CARD_NUMBER, dataUser.getUsuario().getCuentas().get(0).getTarjeta());
            prefs.saveData(ID_CUENTA, String.valueOf(dataUser.getUsuario().getCuentas().get(0).getIdCuenta()));

        }

        dataUser.getUsuario().setTipoAgente(17);
        if (dataUser.isEsAgente() && dataUser.getEstatusAgente() != CRM_DOCTO_APROBADO
                && dataUser.getEstatusDocumentacion() != STATUS_DOCTO_PENDIENTE) {
            App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        }

        if (dataUser.getUsuario() != null && dataUser.getUsuario().getImagenAvatarURL() != null) {
            String mUserImage = dataUser.getUsuario().getImagenAvatarURL();
            String[] urlSplit = mUserImage.split("_");
            if (urlSplit.length > 1) {
                dataUser.getUsuario().setImagenAvatarURL(urlSplit[0] + "_M.png");
            }
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
