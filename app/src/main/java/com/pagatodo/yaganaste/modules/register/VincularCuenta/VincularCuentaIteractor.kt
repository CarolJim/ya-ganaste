package com.pagatodo.yaganaste.modules.register.VincularCuenta

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.data.DataSourceResult
import com.pagatodo.yaganaste.data.dto.ErrorObject
import com.pagatodo.yaganaste.data.model.*
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.*
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse
import com.pagatodo.yaganaste.data.room_db.DatabaseManager
import com.pagatodo.yaganaste.exceptions.OfflineException
import com.pagatodo.yaganaste.interfaces.IAprovView
import com.pagatodo.yaganaste.interfaces.IRequestResult
import com.pagatodo.yaganaste.interfaces.enums.WebService
import com.pagatodo.yaganaste.net.ApiAdtvo
import com.pagatodo.yaganaste.net.ApiTrans
import com.pagatodo.yaganaste.net.RequestHeaders
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED
import com.pagatodo.yaganaste.ui.account.AprovPresenter
import com.pagatodo.yaganaste.utils.Constants.*
import com.pagatodo.yaganaste.utils.Recursos.*
import com.pagatodo.yaganaste.utils.Utils
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class VincularCuentaIteractor(var presenter: VincularcuentaContracts.Presenter) : VincularcuentaContracts.Iteractor,
        IRequestResult<DataSourceResult>, AprovPresenter(App.getContext(), false),
        IAprovView<Any> {

    override fun createUser() {
        presenter.showLoader(App.getContext().getString(R.string.creating_user))
        var registerUserSingleton = RegisterUserNew.getInstance()
        var request = CrearUsuarioClienteRequest(
                registerUserSingleton.email,
                Utils.cipherRSA(registerUserSingleton.contrasenia, PUBLIC_KEY_RSA),
                registerUserSingleton.nombre,
                registerUserSingleton.apellidoPaterno,
                registerUserSingleton.apellidoMaterno,
                registerUserSingleton.genero,
                registerUserSingleton.fechaNacimiento,
                "",/*RFC*/
                "",/*CURP*/
                registerUserSingleton.paisNacimiento.idPais,/*Nacionalidad*/
                registerUserSingleton.idEstadoNacimineto,
                registerUserSingleton.email,
                "",/*Telefono*/
                "",/*Telefono Celular*/
                registerUserSingleton.idColonia,
                registerUserSingleton.colonia,
                registerUserSingleton.codigoPostal,
                registerUserSingleton.calle,
                registerUserSingleton.numExterior,
                registerUserSingleton.numInterior,
                registerUserSingleton.paisNacimiento.id)
        RequestHeaders.TokenDispositivo = Utils.getTokenDevice(App.getContext())
        try {
            ApiAdtvo.crearUsuarioCliente(request, this)
        } catch (e: OfflineException) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun createClient() {
        presenter.showLoader(App.getContext().getString(R.string.assigning_account_1))
        var request = AsignarCuentaDisponibleRequest(Card.getInstance().idAccount)
        try {
            ApiTrans.asignarCuentaDisponible(request, this)
        } catch (e: OfflineException) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun assignNip() {
        presenter.showLoader(App.getContext().getString(R.string.assigning_account_2))
        var request = AsignarNIPRequest(Utils.cipherRSA("7485", PUBLIC_KEY_RSA))
        try {
            ApiTrans.asignarNip(request, this, WebService.ASIGNAR_NIP)
        } catch (e: Exception) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun createAgent() {
        presenter.showLoader(App.getContext().getString(R.string.creating_agent))
        var registerUserSingleton = RegisterUserNew.getInstance()
        var registerAgent = RegisterAgent.getInstance()
        registerAgent.nombre = registerUserSingleton.nombreNegocio
        registerAgent.giro = Giros(registerUserSingleton.idGiro, registerUserSingleton.giro, null)
        registerAgent.telefono = "5555555555"
        var request = CrearAgenteRequest(registerAgent, 1, null)
        try {
            ApiAdtvo.crearAgenteWallet(request, this)
        } catch (e: Exception) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun getNumberOfSms() {
        presenter.showLoader(App.getContext().getString(R.string.verificando_sms_espera))
        try {
            ApiAdtvo.obtenerNumeroSMS(this)
        } catch (e: OfflineException) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun verifyActivationSms() {
        presenter.showLoader(App.getContext().getString(R.string.verificando_sms_esperanuevo))
        App.getInstance().prefs.clearPreference(HAS_PROVISIONING)
        App.getInstance().prefs.clearPreference(HAS_PUSH)
        App.getInstance().prefs.clearPreference(USER_PROVISIONED)
        SingletonUser.getInstance().setNeedsReset(false)
        try {
            ApiAdtvo.verificarActivacion(this)
        } catch (e: OfflineException) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun updateSession() {
        try {
            ApiAdtvo.actualizarInformacionSesion(this)
        } catch (e: OfflineException) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun provisionDevice() {
        super.setAprovView(this)
        super.doProvisioning()
    }

    override fun onSuccess(data: DataSourceResult?) {
        when (data) {
            is CrearUsuarioClienteResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    RegisterUserNew.getInstance().statusRegistro = USUARIO_CREADO
                    val dataUser = data.getData()
                    RequestHeaders.setTokensesion(dataUser.usuario.tokenSesion)
                    val user = SingletonUser.getInstance()
                    val dataIniciarSesion = user.dataUser
                    dataIniciarSesion.adquirente = dataUser.adquirente
                    dataIniciarSesion.cliente = dataUser.cliente
                    dataIniciarSesion.control = dataUser.control
                    dataIniciarSesion.emisor = dataUser.emisor
                    dataIniciarSesion.usuario = dataUser.usuario
                    presenter.onUserCreated()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is AsignarCuentaDisponibleResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    RegisterUserNew.getInstance().statusRegistro = CUENTA_ASIGNADA
                    val dataUser = data.getData()
                    val cuenta = dataUser.cuenta
                    val user = SingletonUser.getInstance()
                    if (user.dataUser.emisor.cuentas == null) {
                        user.dataUser.emisor.cuentas = ArrayList()
                    }
                    if (user.dataUser.emisor.cuentas.isEmpty()) {
                        user.dataUser.emisor.cuentas.add(CuentaUyUResponse())
                    }
                    user.dataUser.emisor.cuentas[0].idCuenta = cuenta.idCuenta
                    RequestHeaders.setIdCuenta(String.format("%s", cuenta.idCuenta))
                    Card.getInstance().idAccount = cuenta.idCuenta
                    presenter.onAccountAssigned()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is AsignarNIPResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    RegisterUserNew.getInstance().statusRegistro = NIP_ASIGNADO
                    val bundle = Bundle()
                    bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection())
                    bundle.putString(EMAIL_REGISTER, RequestHeaders.getUsername())
                    FirebaseAnalytics.getInstance(App.getContext()).setUserId(RequestHeaders.getUsername())
                    FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_REGISTER_YG, bundle)
                    val props = JSONObject()
                    if (!BuildConfig.DEBUG) {
                        try {
                            props.put(CONNECTION_TYPE, Utils.getTypeConnection())
                            props.put(EMAIL_REGISTER, RequestHeaders.getUsername())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        App.mixpanel.track(EVENT_REGISTER_YG, props)
                    }
                    presenter.onNipAssigned()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is CrearAgenteResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    RegisterUserNew.getInstance().statusRegistro = AGENTE_CREADO
                    presenter.onAgentCreated()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is ObtenerNumeroSMSResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    val user = SingletonUser.getInstance()
                    val phone = data.getData().numeroTelefono
                    val tokenValidation = user.dataUser.usuario.semilla + RequestHeaders.getUsername() + RequestHeaders.getTokendevice()
                    if (App.getInstance().prefs.loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.d("WSC", "TokenValidation: $tokenValidation")
                    }
                    val tokenValidationSHA = Utils.bin2hex(Utils.getHash(tokenValidation))
                    if (App.getInstance().prefs.loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.d("WSC", "TokenValidation SHA: $tokenValidationSHA")
                    }
                    val message = String.format("%sT%sT%s", user.dataUser.usuario.idUsuario,
                            user.dataUser.emisor.cuentas[0].idCuenta, tokenValidationSHA)
                    if (App.getInstance().prefs.loadDataBoolean(SHOW_LOGS_PROD, false)) {
                        Log.d("WSC", "Token Firebase ID: " + FirebaseInstanceId.getInstance().token!!)
                    }
                    if (FirebaseInstanceId.getInstance().token != null) {
                        App.getInstance().prefs.saveData(TOKEN_FIREBASE, FirebaseInstanceId.getInstance().token)
                    }
                    val messageValidation = MessageValidation(phone, message)
                    presenter.onSmsNumberObtained(messageValidation)
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is VerificarActivacionResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    val user = SingletonUser.getInstance()
                    user.dataExtraUser.phone = data.getData().numeroTelefono
                    RequestHeaders.setTokenauth(data.getData().tokenAutenticacion)
                    presenter.onVerificationSmsSuccess()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is ActualizarInformacionSesionResponse -> {
                if (data.codigoRespuesta == CODE_OK) {
                    val newSessionData = data.getData()
                    if (newSessionData.adquirente.agentes != null && newSessionData.adquirente.agentes.size > 0) {
                        DatabaseManager().insertAgentes(newSessionData.adquirente.agentes)
                    }
                    val userInfo = SingletonUser.getInstance()
                    newSessionData.usuario.tokenSesionAdquirente = RequestHeaders.getTokenAdq()
                    userInfo.dataUser = newSessionData
                    App.getInstance().prefs.saveDataInt(ID_ESTATUS_EMISOR, newSessionData.usuario.idEstatusEmisor)
                    presenter.onSessionUpdate()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }/*
            is DataSourceResult -> {
                var result = data.data as IniciarSesionUYUResponse
                if (result.codigoRespuesta == CODE_OK) {
                    val dataUser = result.data
                    val user = SingletonUser.getInstance()
                    user.dataUser = dataUser// Si Usuario
                    App.getInstance().prefs.saveDataInt(ID_ESTATUS_EMISOR, dataUser.usuario.idEstatusEmisor)
                    val pswcph = "654321" + "-" + Utils.getSHA256("654321") + "-" + System.currentTimeMillis()
                    App.getInstance().prefs.saveData(PSW_CPR, Utils.cipherAES(pswcph, true))
                    RequestHeaders.setUsername(dataUser.usuario.nombreUsuario)
                    RequestHeaders.setTokensesion(dataUser.usuario.tokenSesion)//Guardamos Token de sesion
                    RequestHeaders.setTokenAdq(dataUser.usuario.tokenSesion)
                    RequestHeaders.setIdCuentaAdq(dataUser.usuario.idUsuarioAdquirente)
                    val adquiriente = dataUser.adquirente
                    if (adquiriente.agentes != null && adquiriente.agentes.size > 0 && !App.getInstance().prefs.loadDataBoolean(HAS_CONFIG_DONGLE, false)) {
                        App.getInstance().prefs.saveDataBool(HAS_CONFIG_DONGLE, true)
                        App.getInstance().prefs.saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal)
                    }
                    if (dataUser.cliente.conCuenta) {// Si Cuenta
                        RequestHeaders.setIdCuenta(String.format("%s", dataUser.emisor.cuentas[0].idCuenta))
                    }
                    presenter.onAprovSuccess()
                } else {
                    presenter.onErrorService(result.mensaje)
                }
            }*/
        }
    }

    override fun onFailed(error: DataSourceResult?) {
        if (error!!.webService == WebService.VERIFICAR_ACTIVACION) {
            presenter.onVerificationSmsFailed(error!!.data.toString())
        } else {
            presenter.onErrorService(error!!.data.toString())
        }
    }

    /**
     * IAprov Interface Methods
     */
    override fun showErrorAprov(error: ErrorObject?) {
        presenter.onAprovFailed(error, EVENT_SHOW_ERROR)
    }

    override fun finishAssociation() {
        /* Firebase Track Event */
        val bundle = Bundle()
        bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection())
        FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_APROV, bundle)
        var props: JSONObject? = null
        if (!BuildConfig.DEBUG) {
            try {
                props = JSONObject().put(CONNECTION_TYPE, Utils.getTypeConnection())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            App.mixpanel.track(EVENT_APROV, props)
        }
        RegisterUserNew.getInstance().statusRegistro = USUARIO_APROVISIONADO
        presenter.onAprovSuccess()
    }

    override fun showLoader(message: String?) {
        presenter.showLoader(message!!)
    }

    override fun showError(error: Any?) {
        presenter.onAprovFailed(error, EVENT_SESSION_EXPIRED)
    }

    override fun hideLoader() {
        presenter.hideLoader()
    }

    override fun errorSessionExpired(response: DataSourceResult?) {}

    override fun goToNextStepAccount(event: String?, data: Any?) {}

    override fun onSuccesBalance() {}

    override fun onSuccesChangePass6(dataSourceResult: DataSourceResult?) {}

    override fun onSuccesStateCuenta() {}

    override fun onSuccesBalanceAdq() {}

    override fun onSuccessBalanceStarbucks() {}

    override fun onSuccessDataPerson() {}

    override fun onHomonimiaDataPerson() {}
}