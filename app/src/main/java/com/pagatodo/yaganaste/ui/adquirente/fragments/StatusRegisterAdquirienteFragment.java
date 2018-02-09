package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StatusViewCupo;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;


/**
 * Created by Dell on 25/07/2017.
 */

public class StatusRegisterAdquirienteFragment extends GenericFragment implements View.OnClickListener {

    public static final int PASO_CUPO_DOCUMENTACION_INCOMPLETA = 1;
    public static final int PASO_CUPO_VALIDACION_DE_DOCUMENTOS = 2;
    public static final int PASO_CUPO_VALIDACION_DE_REFERENCIAS = 3;
    public static final int PASO_CUPO_VALIDACION_DE_LINEA_DE_CREDITO = 4;

    public static final int ID_ESTATUS_PASO_NA = 0;
    public static final int ID_ESTATUS_PASO_EN_VALIDACION = 1;
    public static final int ID_ESTATUS_PASO_APROVADO = 2;
    public static final int ID_ESTATUS_PASO_RECHAZADO = 3;
    public static final int ID_ESTATUS_PASO_RECHAZO_DEFINITIVO = 4;

    public static final int ESTADO_RENEENVIA_DOCUMENTOS = 0;
    public static final int ESTADO_RENEENVIA_REFERENCIAS = 1;


    public static final int ESTATUS_GENERAL_DATOS_CAP = 1;  // Datos Capturados
    public static final int ESTATUS_GENERAL_PENDIENTE_DOC = 2;  // Documentos en validación
    public static final int ESTATUS_GENERAL_RECHAZADO_DOC = 3;  // Documentos rechazados (parcial o total)
    public static final int ESTATUS_GENERAL_PENDIENTE_REF = 4;  // Referencias en validación
    public static final int ESTATUS_GENERAL_RECHAZADO_REF = 5;  // Referencias rechazadas (parcial o total)
    public static final int ESTATUS_GENERAL_PENDIENTE_LINEA = 6;  // Validando línea de crédito
    public static final int ESTATUS_GENERAL_SUCCESS = 7;  // Línea de crédito aprobada
    public static final int ESTATUS_GENERAL_ABORTED = 8;  // Rechazo definitivo

    private static final int MY_PERMISSIONS_REQUEST_PHONE = 100;
    private int ESTADO_ACTUAL = 0;

    @BindView(R.id.status_view_sol_rechazada)
    StatusViewCupo status_view_sol_rechazada;
    @BindView(R.id.status_view)
    StatusViewCupo statusViewCupo;
    @BindView(R.id.status_view_revisando_docs)
    StatusViewCupo statusViewadqrevdocs;
    @BindView(R.id.status_view_revisando_sol)
    StatusViewCupo statusViewadqrevsolc;
    @BindView(R.id.txt_status)
    StyleTextView statusText;
    @BindView(R.id.txt_status_info)
    StyleTextView statusTextInfo;
    @BindView(R.id.txt_contactanos)
    StyleTextView mTextContact;
    @BindView(R.id.btnNextScreen)
    Button mButtonContinue;
    @BindView(R.id.txt_importe)
    MontoTextView importe;

    private View rootview;


    public static StatusRegisterAdquirienteFragment newInstance() {
        Bundle args = new Bundle();
        StatusRegisterAdquirienteFragment fragment = new StatusRegisterAdquirienteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootview == null) {
            rootview = inflater.inflate(R.layout.activity_adq_status, container, false);
            initViews();
        }
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonContinue.setOnClickListener(this);
    }


    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        int Idestatus;

        Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();

        statusViewCupo.setVisibility(View.GONE);
        statusViewadqrevsolc.setVisibility(View.GONE);
        statusViewadqrevdocs.setVisibility(View.GONE);
        status_view_sol_rechazada.setVisibility(View.GONE);
        mTextContact.setOnClickListener(this);

        if (Idestatus == IdEstatus.I7.getId()) {
            importe.setVisibility(View.GONE);
            statusTextInfo.setText(getText(R.string.txt_validate_solic));
            statusText.setText("Revisando Tus \nDocumentos");
            statusViewadqrevdocs.setVisibility(View.VISIBLE);
            statusViewadqrevdocs.updateStatus(50, 0);
            mTextContact.setVisibility(View.VISIBLE);
            mButtonContinue.setVisibility(View.GONE);
            mTextContact.setTextSize(12);
        } else if (Idestatus == IdEstatus.I8.getId()) {
            importe.setVisibility(View.GONE);
            statusTextInfo.setText(getText(R.string.txt_validate_solic));
            statusText.setText("Aprobando tu\nSolicitud");
            statusViewadqrevsolc.updateStatus(50, 50);
            statusViewadqrevsolc.setVisibility(View.VISIBLE);
            mButtonContinue.setVisibility(View.GONE);
            mTextContact.setVisibility(View.VISIBLE);
            mTextContact.setTextSize(12);
        } else if (Idestatus == IdEstatus.I11.getId()) {
            importe.setVisibility(View.GONE);
            statusTextInfo.setText(getText(R.string.txt_validate_solic));
            statusText.setText("Aprobando tu\nSolicitud");
            statusViewadqrevsolc.updateStatus(50, 50);
            statusViewadqrevsolc.setVisibility(View.VISIBLE);
            mButtonContinue.setVisibility(View.GONE);
            mTextContact.setVisibility(View.VISIBLE);
            mTextContact.setTextSize(12);
        } else if (Idestatus == IdEstatus.I9.getId()) {
            statusTextInfo.setText("Ocurrió un Error\nCon Tu Documentación");
            statusText.setText("Reenvía Tus\nDocumentos");
            statusViewCupo.updateError(50, 0);
            mTextContact.setVisibility(View.VISIBLE);
            mTextContact.setTextSize(12);
            statusViewCupo.setVisibility(View.VISIBLE);
            mTextContact.setVisibility(View.GONE);
            mButtonContinue.setVisibility(View.VISIBLE);
        } else if (Idestatus == IdEstatus.I10.getId()) {
            statusTextInfo.setText("Tu Solicitud no\nPudo Ser Completada");
            statusText.setText("Solicitud\nInterrumpida");
           // statusViewCupo.updateError(50, 0);
            status_view_sol_rechazada.updateStatus(0, 0);
            status_view_sol_rechazada.setVisibility(View.VISIBLE);
            mButtonContinue.setVisibility(View.GONE);
            mTextContact.setVisibility(View.VISIBLE);
            mTextContact.setText(getString(R.string.txt_contacto_registro_circulos_rechazada));
            mTextContact.setTextSize(12);
        } else if (Idestatus == IdEstatus.I13.getId()) {
            statusTextInfo.setText("Tu Solicitud no\nPudo Ser Completada");
            statusText.setText("Solicitud\nInterrumpida");
            // statusViewCupo.updateError(50, 0);
            status_view_sol_rechazada.updateStatus(0, 0);
            status_view_sol_rechazada.setVisibility(View.VISIBLE);
            mButtonContinue.setVisibility(View.GONE);
            mTextContact.setVisibility(View.VISIBLE);
            mTextContact.setText(getString(R.string.txt_contacto_registro_circulos_rechazada));
            mTextContact.setTextSize(12);
        }else {
            importe.setVisibility(View.GONE);
            statusTextInfo.setText(getText(R.string.txt_validate_solic));
            statusText.setText("Revisando Tus \nDocumentos");
            statusViewadqrevdocs.setVisibility(View.VISIBLE);
            statusViewadqrevdocs.updateStatus(50, 0);
            mTextContact.setVisibility(View.VISIBLE);
            mTextContact.setTextSize(12);
            mButtonContinue.setVisibility(View.GONE);
        }
        SpannableString ss;
        if (Idestatus == IdEstatus.I10.getId()){
            ss = new SpannableString(getString(R.string.txt_contacto_registro_circulos_rechazada));
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.circlecarousel1)), 65, 81, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextContact.setText(ss);
        }else {
            ss = new SpannableString(getString(R.string.txt_contacto_registro_circulos));
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.circlecarousel1)), 27, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextContact.setText(ss);
        }

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
        if(isValid){
            UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                    doubleActions, true, true);
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnNextScreen) {
            int Idestatus;

            Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();

            if (Idestatus == IdEstatus.I7.getId()) {
                if (onEventListener != null) {
                    onEventListener.onEvent(TabActivity.EVENT_ERROR_DOCUMENTS, null);
                }
            } else if (Idestatus == IdEstatus.I8.getId()) {
                if (onEventListener != null) {
                    onEventListener.onEvent(TabActivity.EVENT_ERROR_DOCUMENTS, null);
                }
            } else if (Idestatus == IdEstatus.I9.getId()) {

                if (onEventListener != null) {
                    onEventListener.onEvent(TabActivity.EVENT_ERROR_DOCUMENTS, null);
                }
            }
        }
        if (id == R.id.txt_contactanos) {

            showDialogCallIntent();

        }

    }

    private void createCallIntent() {
        String number = getString(R.string.numero_telefono_contactanos);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + number));

        if (!ValidatePermissions.isAllPermissionsActives(getActivity(), ValidatePermissions.getPermissionsCheck())) {
            ValidatePermissions.checkPermissions(getActivity(), new String[]{
                    Manifest.permission.CALL_PHONE}, PERMISSION_GENERAL);
        } else {
            if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
