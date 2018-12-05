package com.pagatodo.yaganaste.modules.register.RegistroCompleto


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.ui._manager.GenericFragment

class RegistroCompletoFragment : GenericFragment() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistroCompletoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = RegistroCompletoFragment().apply {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_completo, container, false)
    }

    override fun initViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
