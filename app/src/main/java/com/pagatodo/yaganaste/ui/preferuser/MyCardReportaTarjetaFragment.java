package com.pagatodo.yaganaste.ui.preferuser;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCardReportaTarjetaFragment extends GenericFragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_PHONE = 100;
    @BindView(R.id.call_phone)
    StyleTextView call_phone;

    View rootview;

    public static MyCardReportaTarjetaFragment newInstance() {
        return new MyCardReportaTarjetaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_my_card_reporta_tarjeta, container, false);
        initViews();
        return rootview;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.call_phone):
                createCallIntent();
                break;
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        call_phone.setOnClickListener(this);
    }

    private void showDialogCallIntent() {
        boolean isValid = true;
        int permissionCall = ContextCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.CALL_PHONE);
        // Si no tenemos el permiso lo solicitamos y pasamos la bandera a falso
        if (permissionCall == -1) {
            ValidatePermissions.checkPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_PHONE);
            isValid = false;
        }
        if (isValid) {
            UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                    doubleActions, true, true);
        }
    }

    private void createCallIntent() {
        String number = getString(R.string.numero_telefono_contactanos);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ValidatePermissions.checkPermissions(getActivity(), new String[]{
                    Manifest.permission.CALL_PHONE}, PERMISSION_GENERAL);
            return;
        }
        getActivity().startActivity(callIntent);
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
