package com.pagatodo.yaganaste.modules.register.VincularCuenta

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.data.model.MessageValidation
import com.pagatodo.yaganaste.data.model.RegisterUserNew
import com.pagatodo.yaganaste.databinding.FragmentVincularCuentaBinding
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions
import com.pagatodo.yaganaste.modules.register.RegActivity
import com.pagatodo.yaganaste.ui._controllers.DetailsActivity.MY_PERMISSIONS_REQUEST_SEND_SMS
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER
import com.pagatodo.yaganaste.ui._manager.GenericFragment
import com.pagatodo.yaganaste.utils.Constants.*
import com.pagatodo.yaganaste.utils.Recursos
import com.pagatodo.yaganaste.utils.UI
import com.pagatodo.yaganaste.utils.Utils
import com.pagatodo.yaganaste.utils.ValidatePermissions
import kotlinx.android.synthetic.main.fragment_vincular_cuenta.*

private const val CHECK_SMS_VALIDATE_DELAY: Long = 3000

class VincularCuentaFragment : GenericFragment(), VincularcuentaContracts.Presenter, View.OnClickListener {

    var permissionSms: Int = 0
    var permissionCall: Int = 0
    //private lateinit var binding: FragmentVincularCuentaBinding
    private lateinit var binding: FragmentVincularCuentaBinding
    private lateinit var iteractor: VincularcuentaContracts.Iteractor
    private lateinit var router: VincularcuentaContracts.Router
    private lateinit var broadcastReceiver: BroadcastReceiver
    internal var counterRetry = 1

    companion object {
        @JvmStatic
        fun newInstance() = VincularCuentaFragment().apply { }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = VincularCuentaRouter(activity!! as RegActivity)
        iteractor = VincularCuentaIteractor(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vincular_cuenta, container, false)
        binding.btnSendSms.setOnClickListener(this)
        binding.btnClose.setOnClickListener(this)

        (activity!! as RegActivity).nextStep()
        (activity!! as RegActivity).backvisivility(false)

        var registerUserSingleton = RegisterUserNew.getInstance()

        if (registerUserSingleton.isBreakregister){
            binding.imageView15.setVisibility(View.VISIBLE)
            binding.txtTitleAssociatePhone.setText("¡Tu Registro está en Proceso!")
            binding.txtSubtitleAssociatePhone.setText("Estamos validando tu información,\npodría llevarnos hasta 1 día hábil \n" +
                    "\n" +
                    "Te pedimos estar al pendiente del\ncorreo electrónico que registraste \nya que probablemente\nnecesitemos contactarte")
            binding.btnSendSms.setText("Entendido")
            /*(activity!! as RegActivity).progressvisivility()*/
                iteractor.createUser()

        }
        return binding.root
    }

    override fun initViews() {






    }

    override fun onResume() {
        super.onResume()
        permissionSms = ContextCompat.checkSelfPermission(context!!, Manifest.permission.SEND_SMS)
        permissionCall = ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_PHONE_STATE)
        if (permissionSms == -1 || permissionCall == -1) {
            ValidatePermissions.checkPermissions(activity,
                    arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE),
                    MY_PERMISSIONS_REQUEST_SEND_SMS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            activity!!.unregisterReceiver(broadcastReceiverSend)
            activity!!.unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnSendSms.id -> {
                if (Utils.isDeviceOnline()) {

                    var registerUserSingleton = RegisterUserNew.getInstance()

                    if (registerUserSingleton.isBreakregister){
                        iteractor.createUser()
                    }else {


                        if (registerUserSingleton.isRegincomplete) {
                            RegisterUserNew.getInstance().statusRegistro = USUARIO_CREADO
                            when (RegisterUserNew.getInstance().statusRegistro) {
                                USUARIO_CREADO -> iteractor.createClient()
                                CUENTA_ASIGNADA -> iteractor.assignNip()
                                NIP_ASIGNADO -> iteractor.createAgent()
                                AGENTE_CREADO -> iteractor.getNumberOfSms()
                                else -> iteractor.createUser()
                            }
                        } else {
                            when (RegisterUserNew.getInstance().statusRegistro) {
                                SIN_REGISTRO -> iteractor.createUser()
                                USUARIO_CREADO -> iteractor.createClient()
                                CUENTA_ASIGNADA -> iteractor.assignNip()
                                NIP_ASIGNADO -> iteractor.createAgent()
                                AGENTE_CREADO -> iteractor.getNumberOfSms()
                                else -> iteractor.createUser()
                            }
                        }

                    }

                } else {
                    UI.showErrorSnackBar(activity!!, getString(R.string.no_internet_access), com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                }
            } binding.btnClose.id -> {
              activity!!.finish()
            }
        }
    }

    override fun showLoader(message: String) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message)
    }

    override fun hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null)
    }

    override fun onUserCreated() {
        hideLoader()
        var registerUserSingleton = RegisterUserNew.getInstance()
        if (!registerUserSingleton.isBreakregister) {
            iteractor.createClient()
        }else{
            RegisterUserNew.getInstance().statusRegistro = SIN_REGISTRO

            var registerUserSingleton = RegisterUserNew.getInstance()
            registerUserSingleton.email=""
            registerUserSingleton.nombre=""
            registerUserSingleton.apellidoPaterno=""
            registerUserSingleton.apellidoMaterno=""
            registerUserSingleton.genero=""
            registerUserSingleton.fechaNacimiento=""
            registerUserSingleton.fechaNacimientoToShow=""
            registerUserSingleton.nombreNegocio=""
            registerUserSingleton.curp=""
            registerUserSingleton.colonia=""
            registerUserSingleton.calle=""
            registerUserSingleton.numExterior=""
            registerUserSingleton.codigoPostal=""
            registerUserSingleton.idColonia=""


            UI.showSuccessSnackBar(activity!!, "¡Tu Registro está en Proceso!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
            binding.txtSubtitleAssociatePhone.setText("Estamos validando tu información,\npodría llevarnos hasta 1 día hábil \n" +
                    "\n" +
                    "Te pedimos estar al pendiente del\ncorreo electrónico que registraste \nya que probablemente\nnecesitemos contactarte")
            binding.btnSendSms.setVisibility(View.GONE)
            binding.btnClose.setVisibility(View.VISIBLE)
        }



    }

    override fun onAccountAssigned() {
        hideLoader()
        iteractor.assignNip()
    }

    override fun onNipAssigned() {
        hideLoader()
        iteractor.createAgent()
    }

    override fun onAgentCreated() {
        hideLoader()
        validatePermissions()
        //iteractor.assignmentQrs()
    }


    override fun onSmsNumberObtained(obj: MessageValidation) {
        sendSMS(obj.phone, obj.message)
    }

    override fun onVerificationSmsSuccess() {
        iteractor.updateSession()
    }

    override fun onSessionUpdate() {
        iteractor.provisionDevice()
    }

    override fun onAprovSuccess() {
        hideLoader()
        iteractor.assignmentQrs()
        router.presentLinkedAccountScreen()
        var registerUserSingleton = RegisterUserNew.getInstance()
        registerUserSingleton.email=""
        registerUserSingleton.nombre=""
        registerUserSingleton.apellidoPaterno=""
        registerUserSingleton.apellidoMaterno=""
        registerUserSingleton.genero=""
        registerUserSingleton.fechaNacimiento=""
        registerUserSingleton.fechaNacimientoToShow=""
        registerUserSingleton.nombreNegocio=""
        registerUserSingleton.curp=""
        registerUserSingleton.colonia=""
        registerUserSingleton.calle=""
        registerUserSingleton.numExterior=""
        registerUserSingleton.codigoPostal=""
        registerUserSingleton.idColonia=""

    }

    override fun onAprovFailed(obj: Any?, tag: String) {
        hideLoader()
        if (obj != null && !obj.toString().isEmpty()) {
            UI.showAlertDialog(activity, resources.getString(R.string.app_name), obj.toString(),
                    R.string.title_aceptar) { dialogInterface, i ->
                if (obj.toString() == Recursos.MESSAGE_OPEN_SESSION) {
                    onEventListener.onEvent(tag, 1)
                }
            }
        }
    }

    private fun validatePermissions() {
        // Si no tenemos el permiso lo solicitamos, en cawso contrario entramos al proceso de envio del MSN
        if (permissionSms == -1 || permissionCall == -1) {
            ValidatePermissions.checkPermissions(activity,
                    arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE),
                    MY_PERMISSIONS_REQUEST_SEND_SMS)
        } else {
            onRequestPermissionsResult()
        }
    }

    private fun onRequestPermissionsResult() {
        iteractor.getNumberOfSms()
    }

    override fun onErrorService(message: String) {
        hideLoader()
        UI.showErrorSnackBar(activity!!, message, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
    }

    override fun onVerificationSmsFailed(message: String) {
        if (counterRetry < 4) {
            counterRetry++
            showLoader(getString(R.string.verificando_sms_esperanuevo))
            Handler().postDelayed({ iteractor.verifyActivationSms() }, CHECK_SMS_VALIDATE_DELAY)
        } else {
            goToLoginAlert(message)
        }
    }

    override fun onVerificationCreateUserFailed() {
        RegisterUserNew.getInstance().statusRegistro = SIN_REGISTRO
    }

    private fun goToLoginAlert(message: String) {
        hideLoader()
        if (message.isNotEmpty()) {
            UI.createCustomDialogSMS("", message, fragmentManager, fragmentTag, object : DialogDoubleActions {
                override fun actionConfirm(vararg params: Any) {
                    counterRetry = 1
                    validatePermissions()
                }

                override fun actionCancel(vararg params: Any) {
                    App().cerrarAppsms()
                    router.presentOnboardingScreen()
                }
            }, "Reintentar", "Cancelar")
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val SENT = "SMS_SENT"
        val DELIVERED = "SMS_DELIVERED"
        val sentPI = PendingIntent.getBroadcast(activity, 0, Intent(SENT), 0)
        val deliveredPI = PendingIntent.getBroadcast(activity, 0, Intent(DELIVERED), 0)
        //---when the SMS has been sent---
        activity!!.registerReceiver(broadcastReceiverSend, IntentFilter(SENT))
        //---when the SMS has been delivered---
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->
                        UI.showSuccessSnackBar(activity!!, "SMS entregado", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                    Activity.RESULT_CANCELED -> {
                        UI.showErrorSnackBar(activity!!, "SMS no entregado", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                    }
                }
                activity!!.unregisterReceiver(this)
            }
        }
        activity!!.registerReceiver(broadcastReceiver, IntentFilter(DELIVERED))
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI)
    }

    private var broadcastReceiverSend: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, arg1: Intent) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    hideLoader()
                    UI.showSuccessSnackBar(activity!!, "Mensaje Enviado", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                    Handler().postDelayed({ iteractor.verifyActivationSms() }, CHECK_SMS_VALIDATE_DELAY)
                }
                SmsManager.RESULT_ERROR_GENERIC_FAILURE -> goToLoginAlert(getString(R.string.fallo_envio))
                SmsManager.RESULT_ERROR_NO_SERVICE -> goToLoginAlert(getString(R.string.sin_servicio))
                SmsManager.RESULT_ERROR_NULL_PDU -> goToLoginAlert(getString(R.string.null_pdu))
                SmsManager.RESULT_ERROR_RADIO_OFF -> goToLoginAlert(getString(R.string.sin_senial))
            }
            activity!!.unregisterReceiver(this)
        }
    }

    override fun onAsignQrPhysical() {

    }
}
