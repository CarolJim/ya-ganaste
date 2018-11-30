package com.pagatodo.yaganaste.modules.register.VincularCuenta

class VincularcuentaContracts {

    interface Presenter {
        fun onLinkedSuccess()
        fun goToLoginAlert(message : String)
        fun showLoader(message: String)
        fun hideLoader()
    }

    interface Iteractor {
        fun onCreateUserClient()
        fun onCreateAgent()
        fun onSendSMS()
    }

    interface Router {
        fun presentLinkedAccountScreen()
        fun presentOnboardingScreen()
    }
}