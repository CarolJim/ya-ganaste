package com.pagatodo.yaganaste.modules.register.RegistroCompleto

class RegistroCompletoContracts {

    interface Presenter {
        fun onDataSaved()
    }

    interface Iteractor {
        fun saveData()
    }

    interface Router {
        fun presentMainScreen()
    }
}