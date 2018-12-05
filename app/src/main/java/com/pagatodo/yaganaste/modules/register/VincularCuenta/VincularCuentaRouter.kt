package com.pagatodo.yaganaste.modules.register.VincularCuenta

import android.app.Activity

class VincularCuentaRouter(val activity: Activity) : VincularcuentaContracts.Router {

    override fun presentLinkedAccountScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun presentOnboardingScreen() {
        activity.finish()
    }
}