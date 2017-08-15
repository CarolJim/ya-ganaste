package com.pagatodo.yaganaste.ui.preferuser;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import butterknife.BindView;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_ABOUT;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHelpContactanos extends GenericFragment implements View.OnClickListener {


    @BindView(R.id.btnllamar)
    LinearLayout btnllamarc;


    public MyHelpContactanos() {
        // Required empty public constructor
    }
    public static MyHelpContactanos newInstance() {

        MyHelpContactanos fragmentcontactanos = new MyHelpContactanos();
        return fragmentcontactanos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_help_contactanos, container, false);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case (R.id.btnllamar):
                String number = getString(R.string.numero_telefono_contactanos);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + number));

                if (!ValidatePermissions.isAllPermissionsActives(getActivity(), ValidatePermissions.getPermissionsCheck())) {
                    ValidatePermissions.checkPermissions(getActivity(), new String[]{
                            Manifest.permission.CALL_PHONE},PERMISSION_GENERAL);
                } else {
                    getActivity().startActivity(callIntent);
                }
                break;
            case (R.id.ll_contactanos_correo):
               // onEventListener.onEvent(PREFER_USER_HELP_CORREO, 1);
                break;
        }

    }

    @Override
    public void initViews() {
        btnllamarc.setOnClickListener(this);

    }
}
