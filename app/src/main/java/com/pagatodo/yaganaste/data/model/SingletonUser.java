package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesion;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;

import static com.pagatodo.yaganaste.utils.Recursos.ADQ_PROCESS;
import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTADO_RECHAZADO;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_DOCUMENTACION;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.ID_CUENTA;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;
import static com.pagatodo.yaganaste.utils.Recursos.IS_CUPO;
import static com.pagatodo.yaganaste.utils.Recursos.LAST_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.SIMPLE_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.SPACE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.TIPO_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;

/**
 * @author flima
 */

public class SingletonUser {

    public static SingletonUser user;

    private DataIniciarSesionUYU dataUserUyu;
    private ExtraInfoUser dataExtraUser;
    private String pathPictureTemp = "";
    private String activacionCodeFreja = "";
    private boolean needsReset;
    private String cardStatusId = null;
    private String UltimaTransaccion = "";

    private SingletonUser() {
        dataExtraUser = new ExtraInfoUser();
        dataUserUyu = new DataIniciarSesionUYU();
    }

    public static synchronized SingletonUser getInstance() {
        if (user == null)
            user = new SingletonUser();
        return user;
    }

    public DataIniciarSesionUYU getDataUser() {
        return dataUserUyu;
    }

    public void setDataUser(DataIniciarSesionUYU dataUser) {
        this.dataUserUyu = dataUser;
        Preferencias prefs = App.getInstance().getPrefs();
        if (dataUser.getCliente().getConCuenta()) {
            prefs.saveDataBool(HAS_SESSION, true);
            prefs.saveData(SIMPLE_NAME, StringUtils.getFirstName(dataUser.getCliente().getNombre())
                    .concat(SPACE).concat(dataUser.getCliente().getPrimerApellido()));
            prefs.saveDataBool(PASSWORD_CHANGE, dataUser.getUsuario().getPasswordAsignado());
            //prefs.saveDataBool(PASSWORD_CHANGE, false);
            prefs.saveData(NAME_USER, dataUser.getCliente().getNombre());
            prefs.saveData(FULL_NAME_USER, dataUser.getCliente().getNombre().concat(SPACE).
                    concat(dataUser.getCliente().getPrimerApellido().concat(SPACE).
                            concat(dataUser.getCliente().getSegundoApellido())));
            prefs.saveData(LAST_NAME, dataUser.getCliente().getPrimerApellido().concat(SPACE));
            dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).setNumero(dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero().replaceAll(" ", ""));
            prefs.saveData(CARD_NUMBER, dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero());
            prefs.saveData(ID_CUENTA, String.valueOf(dataUser.getEmisor().getCuentas().get(0).getIdCuenta()));
        }

        App.getInstance().getPrefs().saveDataInt(TIPO_AGENTE, 1);
        if (App.getInstance().getPrefs().loadDataBoolean(ES_AGENTE, false)
                && App.getInstance().getPrefs().loadDataInt(ESTATUS_AGENTE) != CRM_DOCTO_APROBADO
                && App.getInstance().getPrefs().loadDataInt(ESTATUS_DOCUMENTACION)!=STATUS_DOCTO_PENDIENTE) {
            App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        }

        if (dataUser.getUsuario() != null && dataUser.getUsuario().getImagenAvatarURL() != null) {
            String mUserImage = dataUser.getUsuario().getImagenAvatarURL();
            String[] urlSplit = mUserImage.split("_");
            if (urlSplit.length > 1) {
                dataUser.getUsuario().setImagenAvatarURL(urlSplit[0] + "_M.png");
                prefs.saveData(URL_PHOTO_USER, dataUser.getUsuario().getImagenAvatarURL());
            }

        }

        if (App.getInstance().getPrefs().loadDataInt(ID_ESTATUS) == IdEstatus.I10.getId() ||
                App.getInstance().getPrefs().loadDataInt(ID_ESTATUS) == IdEstatus.I13.getId()) {
            prefs.saveDataBool(ESTADO_RECHAZADO, true);
        }

        if (App.getInstance().getPrefs().loadDataInt(ID_ESTATUS) == IdEstatus.CUPO.getId()) {
            prefs.saveDataBool(IS_CUPO, true);
        }
    }

    public void setDataUser(DataIniciarSesion dataUser) {
        //this.dataUser = dataUser;

        Preferencias prefs = App.getInstance().getPrefs();

        if (dataUser.isConCuenta()) {
            prefs.saveDataBool(HAS_SESSION, true);

            prefs.saveData(SIMPLE_NAME, StringUtils.getFirstName(dataUser.getUsuario().getNombre())
                    .concat(SPACE).concat(dataUser.getUsuario().getPrimerApellido()));
            prefs.saveDataBool(PASSWORD_CHANGE, dataUser.getUsuario().getPasswordAsignado());
            prefs.saveData(NAME_USER, dataUser.getUsuario().getNombre());
            prefs.saveData(FULL_NAME_USER, dataUser.getUsuario().getNombre().concat(SPACE).
                    concat(dataUser.getUsuario().getPrimerApellido().concat(SPACE).
                            concat(dataUser.getUsuario().getSegundoApellido())));
            prefs.saveData(LAST_NAME, dataUser.getUsuario().getPrimerApellido().concat(SPACE));
            dataUser.getUsuario().getCuentas().get(0).setTarjeta(dataUser.getUsuario().getCuentas().get(0).getTarjeta().replaceAll(" ",""));
            prefs.saveData(CARD_NUMBER, dataUser.getUsuario().getCuentas().get(0).getTarjeta());
            prefs.saveData(ID_CUENTA, String.valueOf(dataUser.getUsuario().getCuentas().get(0).getIdCuenta()));
        }

        //dataUser.getUsuario().setTipoAgente(1);
        if (dataUser.isEsAgente() && dataUser.getEstatusAgente() != CRM_DOCTO_APROBADO
                && dataUser.getEstatusDocumentacion() != STATUS_DOCTO_PENDIENTE) {
            App.getInstance().getPrefs().saveDataBool(ADQ_PROCESS, true);
        }

        if (dataUser.getUsuario() != null && dataUser.getUsuario().getImagenAvatarURL() != null) {
            String mUserImage = dataUser.getUsuario().getImagenAvatarURL();
            String[] urlSplit = mUserImage.split("_");
            if (urlSplit.length > 1) {
                dataUser.getUsuario().setImagenAvatarURL(urlSplit[0] + "_M.png");
                prefs.saveData(URL_PHOTO_USER, dataUser.getUsuario().getImagenAvatarURL());
            }

        }

        if (dataUser.getIdEstatus() == IdEstatus.I10.getId() ||dataUser.getIdEstatus() == IdEstatus.I13.getId()) {
            prefs.saveDataBool(ESTADO_RECHAZADO, true);
        }

        if (dataUser.getIdEstatus() == IdEstatus.CUPO.getId()) {
            prefs.saveDataBool(IS_CUPO, true);
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

    public boolean needsReset() {
        return needsReset;
    }

    public void setNeedsReset(boolean needsReset) {
        this.needsReset = needsReset;
    }

    public String getCardStatusId() {
        return cardStatusId;
    }

    public void setCardStatusId(String cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    public String getUltimaTransaccion() {
        return UltimaTransaccion;
    }

    public void setUltimaTransaccion(String ultimaTransaccion) {
        UltimaTransaccion = ultimaTransaccion;
    }
}
