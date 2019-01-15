package com.pagatodo.yaganaste.modules.register.RegistroCompleto


import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.databinding.FragmentRegistroCompletoBinding
import com.pagatodo.yaganaste.modules.register.RegActivity
import com.pagatodo.yaganaste.ui._manager.GenericFragment

class RegistroCompletoFragment : GenericFragment(), RegistroCompletoContracts.Presenter, View.OnClickListener {

    private lateinit var binding: FragmentRegistroCompletoBinding
    private lateinit var router: RegistroCompletoContracts.Router
    private lateinit var iteractor: RegistroCompletoContracts.Iteractor
    private lateinit var activity:RegActivity

    companion object {
        @JvmStatic
        fun newInstance() = RegistroCompletoFragment().apply {}
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = context as RegActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = RegistroCompletoRouter(activity!!)
        iteractor = RegistroCompletoIteractor(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registro_completo, container, false)
        initViews()
        return binding.root
    }

    override fun initViews() {
        activity.hideStepBar()
        binding.btnFinishRegister.setOnClickListener(this)
    }

    override fun onDataSaved() {
        router.presentMainScreen()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnFinishRegister.id -> /*iteractor.saveData()*/ router.presentMainScreen()
        }
    }
}
