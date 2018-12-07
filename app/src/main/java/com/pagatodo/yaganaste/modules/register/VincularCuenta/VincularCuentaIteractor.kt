package com.pagatodo.yaganaste.modules.register.VincularCuenta

import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.data.DataSourceResult
import com.pagatodo.yaganaste.data.dto.ErrorObject
import com.pagatodo.yaganaste.data.model.Card
import com.pagatodo.yaganaste.data.model.MessageValidation
import com.pagatodo.yaganaste.data.model.RegisterUserNew
import com.pagatodo.yaganaste.data.model.SingletonUser
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
        } catch (e: Exception) {
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
        //var request = CrearAgenteRequest(registerUserSingleton.)
        try {
            //ApiAdtvo.crearAgente(request, this)
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

    override fun registerUserFirebase() {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(RegisterUserNew.getInstance().email, "123456").addOnCompleteListener { task ->
            App.getInstance().prefs.saveDataBool(HAS_FIREBASE_ACCOUNT, true)
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = auth.currentUser
                App.getInstance().prefs.saveData(TOKEN_FIREBASE_AUTH, user!!.uid)
                val users = HashMap<String, String?>()
                users["Mbl"] = SingletonUser.getInstance().dataUser.emisor.cuentas[0].telefono.replace(" ", "")
                users["DvcId"] = FirebaseInstanceId.getInstance().token
                FirebaseDatabase.getInstance(URL_BD_ODIN_USERS).reference.child(user.uid).setValue(users)
            }
        }
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
            }
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