package com.pagatodo.yaganaste.modules.register.RegistroCompleto

import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.data.model.Card
import com.pagatodo.yaganaste.data.model.RegisterUser
import com.pagatodo.yaganaste.data.model.SingletonUser
import com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER
import com.pagatodo.yaganaste.utils.Recursos.PHONE_NUMBER

class RegistroCompletoIteractor(var presenter: RegistroCompletoContracts.Presenter) : RegistroCompletoContracts.Iteractor {

    override fun saveData() {
        RegisterUser.resetRegisterUser()
        Card.resetCardData()
        /*
                 * Verificamos si las condiciones de Adquirente ya han sido cumplidas para mostrar pantalla
                 */
        val user = SingletonUser.getInstance()
        val dataUser = user.dataUser
        val prefs = App.getInstance().prefs
        prefs.saveData(PHONE_NUMBER, SingletonUser.getInstance().dataUser.emisor.cuentas[0].telefono)
        prefs.saveData(CLABE_NUMBER, SingletonUser.getInstance().dataUser.emisor.cuentas[0].clabe)
        presenter.onDataSaved()
    }
}