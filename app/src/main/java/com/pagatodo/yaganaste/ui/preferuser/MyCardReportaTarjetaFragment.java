package com.pagatodo.yaganaste.ui.preferuser;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCardReportaTarjetaFragment extends GenericFragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_PHONE = 100;
    @BindView(R.id.textView2)
    StyleTextView txtTitle;

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

        switch (view.getId()) {
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
                //onEventListener.onEvent(PREFER_USER_HELP_CORREO_REPORTA_TARJETA, 1);
                ((TarjetaActivity) getActivity()).onSendEmail();
                break;
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        if (SingletonUser.getInstance().getDataUser().getUsuario().getCuentas().get(0).getTarjeta().equals(""))
            txtTitle.setText(getString(R.string.tarjeta_cancelada));
        ll_llamar1.setOnClickListener(this);
        ll_correo.setOnClickListener(this);
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
