package com.pagatodo.yaganaste.ui.adquirente.interactores;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarInformacionSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataIniciarSesionUYU;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCobrosMensualesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CobrosMensualesResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCobrosMensualesResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.adquirente.interactores.interfaces.IinfoAdicionalInteractor;
import com.pagatodo.yaganaste.ui.adquirente.presenters.interfaces.IinfoAdicionalPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_COBROS;
import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_DESTINO;
import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_MONTOS;
import static com.pagatodo.yaganaste.interfaces.enums.SpinnerPLD.SPINNER_PLD_ORIGEN;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_INFO_SESION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CREAR_AGENTE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_COBROS_MENSUALES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_DESTINO_RECURSOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_MONTOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_ORIGEN_RECURSOS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.OBTENER_PAISES;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.TIPO_AGENTE;

/**
 * Created by Jordan on 04/08/2017.
 */

public class InfoAdicionalInteractor implements IinfoAdicionalInteractor {

    IinfoAdicionalPresenter infoAdicionalPresenter;

    public InfoAdicionalInteractor(IinfoAdicionalPresenter adicionalPresenter) {
        infoAdicionalPresenter = adicionalPresenter;
    }

    @Override
    public List<Paises> getPaisesList() {
        ObtenerCobrosMensualesRequest request = new ObtenerCobrosMensualesRequest();
        createProccesRequest(request, App.getContext().getString(R.string.obtenerCobrosMensuales), OBTENER_PAISES, App.getInstance().getString(R.string.no_internet_access));
        try {
            return new DatabaseManager().getPaisesList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void updateSession() {
        try {
            ApiAdtvo.actualizarInformacionSesion(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registrarAdquirente() {
        CrearAgenteRequest request = new CrearAgenteRequest(RegisterAgent.getInstance(), 1, null);
        try {
            ApiAdtvo.crearAgente(request, this);
        } catch (OfflineException e) {
            infoAdicionalPresenter.onWSError(CREAR_AGENTE, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void setSpinner(SpinnerPLD sp) {
        ObtenerCobrosMensualesRequest request = new ObtenerCobrosMensualesRequest();
        request.setIdTipoRegimenFiscal("1");
        if (sp == SPINNER_PLD_COBROS) {
            createProccesRequest(request, App.getContext().getString(R.string.obtenerCobrosMensuales), OBTENER_COBROS_MENSUALES, App.getInstance().getString(R.string.no_internet_access));
        } else if (sp == SPINNER_PLD_MONTOS) {
            createProccesRequest(request, App.getContext().getString(R.string.obtenerMontos), OBTENER_MONTOS, App.getInstance().getString(R.string.no_internet_access));
        } else if (sp == SPINNER_PLD_ORIGEN) {
            createProccesRequest(request, App.getContext().getString(R.string.obtenerOrigenRecursos), OBTENER_ORIGEN_RECURSOS, App.getInstance().getString(R.string.no_internet_access));
        } else if (sp == SPINNER_PLD_DESTINO) {
            createProccesRequest(request, App.getContext().getString(R.string.obtenerDestinoRecursos), OBTENER_DESTINO_RECURSOS, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void setPaises() {
        ObtenerCobrosMensualesRequest request = new ObtenerCobrosMensualesRequest();
        request.setIdTipoRegimenFiscal("1");
        createProccesRequest(request, App.getContext().getString(R.string.obtenerPaisesPLD), OBTENER_PAISES, App.getInstance().getString(R.string.no_internet_access));
    }

    private void createProccesRequest(ObtenerCobrosMensualesRequest request, String urlComplet, WebService ws, String msjError) {
        try {
            ApiAdtvo.obtenerCobrosMensuales(request, this, urlComplet, ws);
        } catch (OfflineException e) {
            infoAdicionalPresenter.onWSError(ws, msjError);
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        if (result.getWebService() == CREAR_AGENTE) {
            infoAdicionalPresenter.onSuccessCreateUsuarioAdquirente(result);
        }
        if (result.getWebService() == ACTUALIZAR_INFO_SESION) {
            proccesDataSession(result);
        }
        if (result.getWebService() == OBTENER_PAISES) {
            proccesPaises(result);
        }
        if (result.getWebService() == OBTENER_COBROS_MENSUALES) {
            processSpinnerResult(result, SPINNER_PLD_COBROS);
        }

        if (result.getWebService() == OBTENER_MONTOS) {
            processSpinnerResult(result, SPINNER_PLD_MONTOS);
        }

        if (result.getWebService() == OBTENER_ORIGEN_RECURSOS) {
            processSpinnerResult(result, SPINNER_PLD_ORIGEN);
        }

        if (result.getWebService() == OBTENER_DESTINO_RECURSOS) {
            processSpinnerResult(result, SPINNER_PLD_DESTINO);
        }

    }

    private void proccesPaises(DataSourceResult response) {
        ObtenerCobrosMensualesResponse data = (ObtenerCobrosMensualesResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<CobrosMensualesResponse> listaCobrosMensuales = data.getData();
            if (listaCobrosMensuales != null && !listaCobrosMensuales.isEmpty()) {
                infoAdicionalPresenter.onSuccessPaisesList(response);
            } else {
                infoAdicionalPresenter.onWSError(response.getWebService(), "Verifica tu Informacion");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            infoAdicionalPresenter.onWSError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }
    }

    private void processSpinnerResult(DataSourceResult response, SpinnerPLD sp) {
        ObtenerCobrosMensualesResponse data = (ObtenerCobrosMensualesResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            List<CobrosMensualesResponse> listaCobrosMensuales = data.getData();
            if (listaCobrosMensuales != null && !listaCobrosMensuales.isEmpty()) {
                infoAdicionalPresenter.onSuccessSpinnerList(response, sp);
            } else {
                infoAdicionalPresenter.onWSError(response.getWebService(), "Verifica tu Informacion");//Retornamos mensaje de error.
            }
        } else {
            //TODO manejar respuesta no exitosa. Se retorna el Mensaje del servicio.
            infoAdicionalPresenter.onWSError(response.getWebService(), data.getMensaje());//Retornamos mensaje de error.
        }

    }

    @Override
    public void onFailed(DataSourceResult error) {
        infoAdicionalPresenter.onWSError(error.getWebService(), error);

    }

    private void proccesDataSession(DataSourceResult response) {
        ActualizarInformacionSesionResponse data = (ActualizarInformacionSesionResponse) response.getData();
        if (data.getCodigoRespuesta() == CODE_OK) {
            DataIniciarSesionUYU newSessionData = data.getData();
            if (newSessionData.getAdquirente().getAgentes() != null && newSessionData.getAdquirente().getAgentes().size() > 0) {
                new DatabaseManager().insertAgentes(newSessionData.getAdquirente().getAgentes());
            }
            SingletonUser userInfo = SingletonUser.getInstance();
            newSessionData.getUsuario().setTokenSesionAdquirente(RequestHeaders.getTokenAdq());
            userInfo.setDataUser(newSessionData);
            App.getInstance().getPrefs().saveDataInt(ID_ESTATUS_EMISOR, newSessionData.getUsuario().getIdEstatusEmisor());
        }
    }
}
