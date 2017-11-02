package com.pagatodo.yaganaste.ui.adquirente.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.Referencias;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoSolicitud;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IAdqRegisterView;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.IdEstatus;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.account.AccountAdqPresenter;
import com.pagatodo.yaganaste.ui.cupo.fragments.CupoDomicilioPersonalFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CupoDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewStatusRegisterCupo;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DocumentsContainerFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.InviteAdquirenteFragment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;
import com.pagatodo.yaganaste.utils.customviews.StatusViewCupo;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_ERROR;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;
import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;
import static com.pagatodo.yaganaste.utils.Recursos.CRM_DOCTO_APROBADO;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_PENDIENTE;
import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;


/**
 * Created by Dell on 25/07/2017.
 */

public class StatusRegisterAdquirienteFragment extends GenericFragment  implements View.OnClickListener {

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
    public static final int ESTATUS_GENERAL_PENDIENTE_DOC= 2;  // Documentos en validación
    public static final int ESTATUS_GENERAL_RECHAZADO_DOC = 3;  // Documentos rechazados (parcial o total)
    public static final int ESTATUS_GENERAL_PENDIENTE_REF = 4;  // Referencias en validación
    public static final int ESTATUS_GENERAL_RECHAZADO_REF = 5;  // Referencias rechazadas (parcial o total)
    public static final int ESTATUS_GENERAL_PENDIENTE_LINEA = 6;  // Validando línea de crédito
    public static final int ESTATUS_GENERAL_SUCCESS = 7;  // Línea de crédito aprobada
    public static final int ESTATUS_GENERAL_ABORTED = 8;  // Rechazo definitivo


    private int ESTADO_ACTUAL = 0;

    @BindView(R.id.status_view)StatusViewCupo statusViewCupo;
    @BindView(R.id.status_view_revisando_docs)StatusViewCupo statusViewadqrevdocs;
    @BindView(R.id.status_view_revisando_sol)StatusViewCupo statusViewadqrevsolc;
    @BindView(R.id.txt_status)StyleTextView statusText;
    @BindView(R.id.txt_status_info)StyleTextView statusTextInfo;
    @BindView(R.id.txt_contactanos)StyleTextView mTextContact;
    @BindView(R.id.btnNextScreen)Button mButtonContinue;
    @BindView(R.id.txt_importe) MontoTextView importe;

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
        mTextContact.setOnClickListener(this);

          if ( Idestatus == IdEstatus.I7.getId()) {
              importe.setVisibility(View.GONE);
              statusTextInfo.setText(getText(R.string.txt_validate_solic));
              statusText.setText("Revisando Tus \nDocuemtos\n1/2");
              statusViewadqrevdocs.setVisibility(View.VISIBLE);
              statusViewadqrevdocs.updateStatus(50,0);
              mTextContact.setVisibility(View.VISIBLE);
              mButtonContinue.setVisibility(View.GONE);
              mTextContact.setTextSize(12);
        } else if ( Idestatus == IdEstatus.I8.getId()) {
              importe.setVisibility(View.GONE);
              statusTextInfo.setText(getText(R.string.txt_validate_solic));
              statusText.setText("Aprobando tu\nSolicitud\n1/2");
              statusViewadqrevsolc.updateStatus(50,50);
              statusViewadqrevsolc.setVisibility(View.VISIBLE);
              mButtonContinue.setVisibility(View.GONE);
              mTextContact.setVisibility(View.VISIBLE);
              mTextContact.setTextSize(12);
        } else if (Idestatus == IdEstatus.I9.getId()) {
              statusTextInfo.setText("Ocurrió un Error\nCon Tu Documentación");
              statusText.setText("Reenvía Tus\nDocumentos\n1/2");
              statusViewCupo.updateError(50,0);
              mTextContact.setVisibility(View.VISIBLE);
              mTextContact.setTextSize(12);
              statusViewCupo.setVisibility(View.VISIBLE);
              mTextContact.setVisibility(View.GONE);
              mButtonContinue.setVisibility(View.VISIBLE);
        } else {
            importe.setVisibility(View.GONE);
            statusTextInfo.setText(getText(R.string.txt_validate_solic));
            statusText.setText("Revisando Tus \nDocuemtos\n1/2");
            statusViewadqrevdocs.setVisibility(View.VISIBLE);
            statusViewadqrevdocs.updateStatus(50,0);
            mTextContact.setVisibility(View.VISIBLE);
              mTextContact.setTextSize(12);
            mButtonContinue.setVisibility(View.GONE);
        }
    }
    private void showDialogCallIntent() {
        UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                doubleActions, true, true);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnNextScreen) {
            int Idestatus;

            Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();

            if ( Idestatus == IdEstatus.I7.getId()) {
                if (onEventListener != null) {
                    onEventListener.onEvent(TabActivity.EVENT_ERROR_DOCUMENTS, null);
                }
            } else if ( Idestatus == IdEstatus.I8.getId()) {
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
