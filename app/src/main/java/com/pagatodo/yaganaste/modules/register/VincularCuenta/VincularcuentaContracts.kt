package com.pagatodo.yaganaste.modules.register.VincularCuenta

class VincularcuentaContracts {

    interface Presenter {
        fun onUserCreated()
        fun onAccountAsigned()
        fun onNipAssigned()
        fun onAgentCreated()
        fun showLoader(message: String)
        fun hideLoader()
        fun onErrorService(message: String)
    }

    interface Iteractor {
        fun createUser()
        fun createClient()
        fun assignNip()
        fun createAgent()
        fun onSendSMS()
    }

    interface Router {
        fun presentLinkedAccountScreen()
        fun presentOnboardingScreen()
    }
}