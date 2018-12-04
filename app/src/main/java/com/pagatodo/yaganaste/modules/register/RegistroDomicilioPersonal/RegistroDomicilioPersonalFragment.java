package com.pagatodo.yaganaste.modules.register.RegistroDomicilioPersonal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**btnNextSelectZip
 * A simple {@link Fragment} subclass.
 */
public class RegistroDomicilioPersonalFragment extends GenericFragment {

    @BindView(R.id.btnNextSelectZip)
    StyleButton btnNextSelectZip;

    View rootView;


    private static RegActivity activityf;

    public RegistroDomicilioPersonalFragment() {
        // Required empty public constructor
    }

    public static RegistroDomicilioPersonalFragment newInstance (RegActivity activity) {
        activityf = activity;
        return new RegistroDomicilioPersonalFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_registro_domicilio_personal, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        btnNextSelectZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityf.showFragmentDomicilioSelectCP();
            }
        });

    }
}
