package com.pagatodo.yaganaste.modules.register.VincularCuenta

import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.data.DataSourceResult
import com.pagatodo.yaganaste.data.model.Card
import com.pagatodo.yaganaste.data.model.RegisterUserNew
import com.pagatodo.yaganaste.data.model.SingletonUser
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CrearUsuarioClienteRequest
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarCuentaDisponibleRequest
import com.pagatodo.yaganaste.data.model.webservice.request.trans.AsignarNIPRequest
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
import com.pagatodo.yaganaste.utils.Recursos.CODE_OK
import com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA
import com.pagatodo.yaganaste.utils.Utils
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
                registerUserSingleton.getPaisNacimiento().getIdPais(),/*Nacionalidad*/
                registerUserSingleton.getIdEstadoNacimineto(),
                registerUserSingleton.getEmail(),
                "",/*Telefono*/
                "",/*Telefono Celular*/
                registerUserSingleton.idColonia,
                registerUserSingleton.colonia,
                registerUserSingleton.codigoPostal,
                registerUserSingleton.calle,
                registerUserSingleton.numExterior,
                registerUserSingleton.numInterior,
                registerUserSingleton.getPaisNacimiento().getId())
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
                    presenter.onNipAssigned()
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