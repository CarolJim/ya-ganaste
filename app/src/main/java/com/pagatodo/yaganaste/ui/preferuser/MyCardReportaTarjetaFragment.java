package com.pagatodo.yaganaste.ui.preferuser;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_CORREO;
import static com.pagatodo.yaganaste.ui._controllers.PreferUserActivity.PREFER_USER_HELP_CORREO_REPORTA_TARJETA;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCardReportaTarjetaFragment extends GenericFragment implements View.OnClickListener {

    @BindView(R.id.ll_contactanos_llamasr)
    LinearLayout ll_llamar1;

    @BindView(R.id.ll_contactanos_correo)
    LinearLayout ll_correo;
    View rootview;

    public static MyCardReportaTarjetaFragment newInstance() {
        MyCardReportaTarjetaFragment fragmentreportatarjeta = new MyCardReportaTarjetaFragment();
        return fragmentreportatarjeta;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_card_reporta_tarjeta, container, false);
        initViews();
        return rootview;

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case (R.id.btnllamar):
                showDialogCallIntent();
                break;
            case (R.id.ll_contactanos_llamasr):
                showDialogCallIntent();
                break;
            case (R.id.imgtelefonocontactanos):
                showDialogCallIntent();
                break;
            case (R.id.ll_contactanos_correo):
                onEventListener.onEvent(PREFER_USER_HELP_CORREO_REPORTA_TARJETA, 1);
                break;
            case R.id.btnEmail:
                onEventListener.onEvent(PREFER_USER_HELP_CORREO_REPORTA_TARJETA, 1);
                break;
        }

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        ll_llamar1.setOnClickListener(this);
        ll_correo.setOnClickListener(this);
    }

    private void showDialogCallIntent() {
        UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                doubleActions, true, true);
    }

    private void createCallIntent() {
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
    }


    /**
     * Acciones para controlar el Dialog de OnClick, al aceptar inciamos el proceso
     */
    DialogDoubleActions doubleActions = new DialogDoubleActions() {
        @Override
        public void actionConfirm(Object... params) {
            createCallIntent();
        }

        @Override
        public void actionCancel(Object... params) {

        }
    };
}
