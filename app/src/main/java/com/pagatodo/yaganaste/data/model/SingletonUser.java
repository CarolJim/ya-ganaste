package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.utils.StringUtils;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.COMPANY_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.ESTADO_RECHAZADO;
import static com.pagatodo.yaganaste.utils.Recursos.ES_AGENTE;
import static com.pagatodo.yaganaste.utils.Recursos.FULL_NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.ID_CUENTA;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ROL;
import static com.pagatodo.yaganaste.utils.Recursos.IS_CUPO;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.LAST_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.NAME_USER;
import static com.pagatodo.yaganaste.utils.Recursos.PASSWORD_CHANGE;
import static com.pagatodo.yaganaste.utils.Recursos.SECOND_LAST_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.SIMPLE_NAME;
import static com.pagatodo.yaganaste.utils.Recursos.SPACE;
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
            /* Cuando el array de Agentes llega vacío singifica que el usuario es emisor solamente,
             *  en caso de que llegue con algún elemento entonces significa que realizó su proceso adq */
            prefs.saveDataBool(ES_AGENTE, dataUser.getAdquirente().getAgentes() != null
                    && dataUser.getAdquirente().getAgentes().size() > 0);
            prefs.saveDataInt(ID_ROL, dataUser.getUsuario().getRoles().get(0).getIdRol());
            if (dataUser.getUsuario().getRoles().get(0).getIdRol() == 129) {
                prefs.saveDataBool(IS_OPERADOR, true);
            } else {
                prefs.saveDataBool(IS_OPERADOR, false);
            }

            prefs.saveData(COMPANY_NAME, dataUser.getAdquirente().getAgentes() != null && !dataUser.getAdquirente().getAgentes().isEmpty() ? (dataUser.getAdquirente().getAgentes().get(0).getNombreNegocio()) : "");
            //prefs.saveDataBool(PASSWORD_CHANGE, false);
            prefs.saveData(NAME_USER, dataUser.getCliente().getNombre());
            prefs.saveData(FULL_NAME_USER, dataUser.getCliente().getNombre().concat(SPACE).
                    concat(dataUser.getCliente().getPrimerApellido().concat(SPACE).
                            concat(dataUser.getCliente().getSegundoApellido())));
            prefs.saveData(LAST_NAME, dataUser.getCliente().getPrimerApellido().concat(SPACE));
            prefs.saveData(SECOND_LAST_NAME, dataUser.getCliente().getSegundoApellido().concat(SPACE));
            dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).setNumero(dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero().replaceAll(" ", ""));
            prefs.saveData(CARD_NUMBER, dataUser.getEmisor().getCuentas().get(0).getTarjetas().get(0).getNumero());
            prefs.saveData(ID_CUENTA, String.valueOf(dataUser.getEmisor().getCuentas().get(0).getIdCuenta()));
        }
        App.getInstance().getPrefs().saveDataInt(TIPO_AGENTE, 1);
        if (dataUser.getUsuario() != null && dataUser.getUsuario().getImagenAvatarURL() != null) {
            String mUserImage = dataUser.getUsuario().getImagenAvatarURL();
            String[] urlSplit = mUserImage.split("_");
            if (urlSplit.length > 1) {
                dataUser.getUsuario().setImagenAvatarURL(urlSplit[0] + "_M.png");
                prefs.saveData(URL_PHOTO_USER, dataUser.getUsuario().getImagenAvatarURL());
            }
        }
        if (App.getInstance().getPrefs().loadDataInt(ID_ESTATUS_EMISOR) == IdEstatus.I10.getId() ||
                App.getInstance().getPrefs().loadDataInt(ID_ESTATUS_EMISOR) == IdEstatus.I13.getId()) {
            prefs.saveDataBool(ESTADO_RECHAZADO, true);
        }

        if (App.getInstance().getPrefs().loadDataInt(ID_ESTATUS_EMISOR) == IdEstatus.CUPO.getId()) {
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
