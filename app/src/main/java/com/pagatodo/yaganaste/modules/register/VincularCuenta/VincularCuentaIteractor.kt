package com.pagatodo.yaganaste.modules.register.VincularCuenta

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.data.DataSourceResult
import com.pagatodo.yaganaste.data.model.Card
import com.pagatodo.yaganaste.data.model.RegisterUserNew
import com.pagatodo.yaganaste.data.model.SingletonUser
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearAgenteRequest
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearAgenteResponse
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CrearUsuarioClienteResponse
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaUyUResponse
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarCuentaDisponibleResponse
import com.pagatodo.yaganaste.data.model.webservice.response.trans.AsignarNIPResponse
import com.pagatodo.yaganaste.exceptions.OfflineException
import com.pagatodo.yaganaste.interfaces.IRequestResult
import com.pagatodo.yaganaste.interfaces.enums.WebService
import com.pagatodo.yaganaste.net.ApiAdtvo
import com.pagatodo.yaganaste.net.ApiTrans
import com.pagatodo.yaganaste.net.RequestHeaders
import com.pagatodo.yaganaste.utils.Constants.*
import com.pagatodo.yaganaste.utils.Recursos.*
import com.pagatodo.yaganaste.utils.Utils
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class VincularCuentaIteractor(var presenter: VincularcuentaContracts.Presenter) : VincularcuentaContracts.Iteractor,
        IRequestResult<DataSourceResult> {

    override fun createUser() {
        presenter.showLoader("Creando usuario")
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
        presenter.showLoader("Asignando cuenta\nProceso 1 de 2")
        var request = AsignarCuentaDisponibleRequest(Card.getInstance().idAccount)
        try {
            ApiTrans.asignarCuentaDisponible(request, this)
        } catch (e: OfflineException) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun assignNip() {
        presenter.showLoader("Asignando cuenta\nProceso 2 de 2")
        var request = AsignarNIPRequest(Utils.cipherRSA("7485", PUBLIC_KEY_RSA))
        try {
            ApiTrans.asignarNip(request, this, WebService.ASIGNAR_NIP)
        } catch (e: Exception) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun createAgent() {
        presenter.showLoader("Creando agente")
        var request = CrearAgenteRequest()
        try {
            ApiAdtvo.crearAgente(request, this)
        } catch (e: Exception) {
            e.printStackTrace()
            presenter.onErrorService(App.getContext().getString(R.string.no_internet_access))
        }
    }

    override fun onSendSMS() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
                    presenter.onAccountAsigned()
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
                    presenter.onAgentCreated()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
        }
    }

    override fun onFailed(error: DataSourceResult?) {
        presenter.onErrorService(error!!.data.toString())
    }
}