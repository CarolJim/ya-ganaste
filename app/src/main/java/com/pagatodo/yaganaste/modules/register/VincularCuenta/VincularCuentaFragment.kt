package com.pagatodo.yaganaste.modules.register.VincularCuenta


import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.databinding.FragmentVincularCuentaBinding
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER
import com.pagatodo.yaganaste.ui._manager.GenericFragment
import com.pagatodo.yaganaste.utils.UI
import com.pagatodo.yaganaste.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val CHECK_SMS_VALIDATE_DELAY: Long = 3000

class VincularCuentaFragment : GenericFragment(), VincularcuentaContracts.Presenter, View.OnClickListener {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentVincularCuentaBinding
    private lateinit var router: VincularcuentaContracts.Router

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                VincularCuentaFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vincular_cuenta, container, false)
        initViews()
        return binding.root
    }

    override fun initViews() {
        router = VincularCuentaRouter(activity!!)
        binding.btnSendSms.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnSendSms.id -> {
                if (Utils.isDeviceOnline()){

                } else {
                    UI.showErrorSnackBar(activity!!, getString(R.string.no_internet_access), Snackbar.LENGTH_LONG)
                }
            }
        }
    }

    override fun showLoader(message: String) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message)
    }

    override fun hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null)
    }

    override fun onLinkedSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goToLoginAlert(message: String) {
        hideLoader()
        if (message.isNotEmpty()) {
            UI.createCustomDialogSMS("", message, fragmentManager, fragmentTag, object : DialogDoubleActions {
                override fun actionConfirm(vararg params: Any) {
                }

                override fun actionCancel(vararg params: Any) {
                    //No-Op
                    App().cerrarAppsms()
                    router.presentOnboardingScreen()
                }
            }, "Reintentar", "Cancelar")
        }
    }

    internal var broadcastReceiverSend: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, arg1: Intent) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    UI.showSuccessSnackBar(activity!!, "Mensaje Enviado", Snackbar.LENGTH_SHORT)
                    //Handler().postDelayed({ accountPresenter.doPullActivationSMS(getString(R.string.verificando_sms_esperanuevo)) }, CHECK_SMS_VALIDATE_DELAY)
                }
                SmsManager.RESULT_ERROR_GENERIC_FAILURE -> goToLoginAlert(getString(R.string.fallo_envio))
                SmsManager.RESULT_ERROR_NO_SERVICE -> goToLoginAlert(getString(R.string.sin_servicio))
                SmsManager.RESULT_ERROR_NULL_PDU -> goToLoginAlert(getString(R.string.null_pdu))
                SmsManager.RESULT_ERROR_RADIO_OFF -> goToLoginAlert(getString(R.string.sin_senial))
            }
            activity!!.unregisterReceiver(this)
        }
    }
}