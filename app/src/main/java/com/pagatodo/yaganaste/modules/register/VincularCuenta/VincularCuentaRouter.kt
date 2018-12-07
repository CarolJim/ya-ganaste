package com.pagatodo.yaganaste.modules.register.VincularCuenta

import com.pagatodo.yaganaste.modules.register.RegActivity

class VincularCuentaRouter(val activity: RegActivity) : VincularcuentaContracts.Router {

    override fun presentLinkedAccountScreen() {
        //activity.router
    }

    override fun presentOnboardingScreen() {
        activity.finish()
    }
}