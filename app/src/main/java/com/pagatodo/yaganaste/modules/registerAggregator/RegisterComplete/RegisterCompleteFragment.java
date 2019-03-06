package com.pagatodo.yaganaste.modules.registerAggregator.RegisterComplete;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IAccountIteractorNew;
import com.pagatodo.yaganaste.modules.registerAggregator.LinkedQRs.LinkedQRsFragment;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.register.Iteractor.UpdateTokenFirebaseIteractor;
import com.pagatodo.yaganaste.ui.account.register.interfaces.ActualizarToken;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import static com.pagatodo.yaganaste.ui._controllers.BussinesActivity.EVENT_GO_BUSSINES_ADDRESS_BACK;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_CONFIG_DONGLE;
import static com.pagatodo.yaganaste.utils.Recursos.MODE_CONNECTION_DONGLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCompleteFragment extends GenericFragment implements View.OnClickListener{
    private View rootView;
    private StyleButton btn_finish_register;


    public static RegisterCompleteFragment newInstance() {
        return new RegisterCompleteFragment();
    }

    public RegisterCompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_register_complete2, container, false);
        btn_finish_register=(StyleButton)rootView.findViewById(R.id.btn_finish_register);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        btn_finish_register.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finish_register:
                App.getInstance().getPrefs().saveDataBool(HAS_CONFIG_DONGLE, false);
                App.getInstance().getPrefs().saveDataInt(MODE_CONNECTION_DONGLE, QPOSService.CommunicationMode.BLUETOOTH.ordinal());
                onEventListener.onEvent(EVENT_GO_BUSSINES_ADDRESS_BACK,null);
                break;
        }
    }

}
