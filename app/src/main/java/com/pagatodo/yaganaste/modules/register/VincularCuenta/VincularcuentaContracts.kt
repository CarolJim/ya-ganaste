package com.pagatodo.yaganaste.modules.register.VincularCuenta

import com.pagatodo.yaganaste.data.model.MessageValidation

class VincularcuentaContracts {

    interface Presenter {
        fun onUserCreated()
        fun onAccountAssigned()
        fun onNipAssigned()
        fun onAgentCreated()
        fun onSmsNumberObtained(obj: MessageValidation)
        fun onVerificationSmsSuccess()
        fun onVerificationSmsFailed(message: String)
        fun onSessionUpdate()
        fun onAprovSuccess()
        fun onAprovFailed(obj: Any?, tag: String)
        fun showLoader(message: String)
        fun hideLoader()
        fun onErrorService(message: String)
    }

    interface Iteractor {
        fun createUser()
        fun createClient()
        fun assignNip()
        fun createAgent()
        fun getNumberOfSms()
        fun verifyActivationSms()
        fun updateSession()
        fun provisionDevice()
        /* Firebase Process */
        fun registerUserFirebase()
    }

    interface Router {
        fun presentLinkedAccountScreen()
        fun presentOnboardingScreen()
    }
}