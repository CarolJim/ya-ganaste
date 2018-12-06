package com.pagatodo.yaganaste.modules.register.RegistroCompleto

import android.app.Activity
import android.content.Intent
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.ui._controllers.TabActivity

class RegistroCompletoRouter(var activity: Activity) : RegistroCompletoContracts.Router {

    override fun presentMainScreen() {
        activity.startActivity(Intent(App.getContext(), TabActivity::class.java))
        activity.finish()
    }
}