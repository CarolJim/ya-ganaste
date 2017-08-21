package com.pagatodo.yaganaste.ui.cupo.fragments;

import android.content.Context;
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
import com.pagatodo.yaganaste.data.model.webservice.request.adq.AdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoSolicitud;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.interactores.CupoInteractor;
import com.pagatodo.yaganaste.ui.cupo.interactors.StatusRegisterCupoInteractor;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.ui.cupo.presenters.CupoDomicilioPersonalPresenter;
import com.pagatodo.yaganaste.ui.cupo.presenters.StatusRegisterCupoPresenter;
import com.pagatodo.yaganaste.ui.cupo.view.IViewStatusRegisterCupo;
import com.pagatodo.yaganaste.utils.JsonManager;
import com.pagatodo.yaganaste.utils.customviews.StatusViewCupo;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import org.json.JSONObject;

import java.text.BreakIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_HIDE_LOADER;
import static com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity.EVENT_SHOW_LOADER;


/**
 * Created by Dell on 25/07/2017.
 */

public class StatusRegisterCupoFragment extends GenericFragment  implements IViewStatusRegisterCupo, View.OnClickListener {

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

    private int ESTADO_ACTUAL = 0;

    @BindView(R.id.status_view)StatusViewCupo statusViewCupo;
    @BindView(R.id.txt_status)StyleTextView statusText;
    @BindView(R.id.txt_status_info)StyleTextView statusTextInfo;
    @BindView(R.id.txt_contactanos)StyleTextView mTextContact;
    @BindView(R.id.btnNextScreen)Button mButtonContinue;

    private View rootview;

    private CupoActivityManager cupoActivityManager;

    private CupoDomicilioPersonalPresenter presenter;

    public static StatusRegisterCupoFragment newInstance() {
        Bundle args = new Bundle();
        StatusRegisterCupoFragment fragment = new StatusRegisterCupoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
        presenter = new CupoDomicilioPersonalPresenter(this, getContext());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         if (rootview == null) {
             rootview = inflater.inflate(R.layout.activity_cupo_status, container, false);
             initViews();
        }
        return rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getEstadoSolicitudCupo();
        mButtonContinue.setOnClickListener(this);
    }


    public void requestLimitCreditSuccess(String mount){
        String txt1 =getString(R.string.txt_congratulations);
        SpannableString span1 = new SpannableString(getString(R.string.txt_congratulations));
        span1.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_normal)), 0, txt1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new RelativeSizeSpan(2f),txt1.length(), txt1.length() , 0);


        //String mount =getString(R.string.txt_mount_credit,"10,100,500.00");
        SpannableString span2 = new SpannableString(mount);
        span2.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_title)), 0, mount.length()-2, SPAN_INCLUSIVE_INCLUSIVE);
        span2.setSpan(new SuperscriptSpan(),mount.length()-2,mount.length() ,SPAN_INCLUSIVE_INCLUSIVE);



        SpannableString span3 = new SpannableString(getString(R.string.txt_limit_credit));
        span3.setSpan(new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.text_size_normal)), 0, txt1.length(), SPAN_INCLUSIVE_INCLUSIVE);

        CharSequence finalText = TextUtils.concat(span1, " ", span2," ",span3);



        statusText.setText(finalText);

        mButtonContinue.setVisibility(View.VISIBLE);
        mTextContact.setVisibility(View.GONE);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        statusTextInfo.setText(getText(R.string.txt_validate_info));
    }

    @Override
    public void showStatusRegister() {
        statusText.setText("Validando\nReferencias\n1/3");
        statusViewCupo.updateStatus(33,0);
        statusViewCupo.updateError(66, 33);


    }

    @Override
    public void setResponseEstadoCupo(DataEstadoSolicitud dataEstadoSolicitud) {
        Log.e("Data respuesta fragment", createParams(false, dataEstadoSolicitud).toString());

        int pasoActual = dataEstadoSolicitud.getIdPaso();
        int idEstado   = dataEstadoSolicitud.getIdEstatusPaso();

        switch (pasoActual) {
            case PASO_CUPO_DOCUMENTACION_INCOMPLETA:

                break;
            case PASO_CUPO_VALIDACION_DE_DOCUMENTOS:
                setValidandoDocumentos(idEstado);
                break;
            case PASO_CUPO_VALIDACION_DE_REFERENCIAS:
                setValidandoReferecias(idEstado);

        }


    }

    private void setValidandoReferecias(int idEstado) {
        switch (idEstado){
            case ID_ESTATUS_PASO_NA:
                break;
            case ID_ESTATUS_PASO_EN_VALIDACION:
                statusTextInfo.setText(getText(R.string.txt_validate_info));
                statusText.setText("Validando\nReferencias\n2/3");
                statusViewCupo.updateStatus(66,33);
                break;
            case ID_ESTATUS_PASO_APROVADO:
                statusTextInfo.setText(getText(R.string.txt_validate_info));
                statusText.setText("Documentos\nAprovados\n1/3");
                statusViewCupo.updateStatus(0,66);
                break;
            case ID_ESTATUS_PASO_RECHAZADO:
                statusTextInfo.setText("Ocurrió un Error\nCon Tus Referencias");
                statusText.setText("Envia Nuevas\nReferencias\n2/3");
                statusViewCupo.updateError(66,33);
                break;
        }
    }


    private void setValidandoDocumentos(int idEstado) {

        switch (idEstado){
            case ID_ESTATUS_PASO_NA:
                break;
            case ID_ESTATUS_PASO_EN_VALIDACION:
                statusTextInfo.setText(getText(R.string.txt_validate_info));
                statusText.setText("Validando\nDocumentos\n1/3");
                statusViewCupo.updateStatus(33,0);
                break;
            case ID_ESTATUS_PASO_APROVADO:
                statusTextInfo.setText(getText(R.string.txt_validate_info));
                statusText.setText("Documentos\nAprovados\n1/3");
                statusViewCupo.updateStatus(0,33);
                break;
            case ID_ESTATUS_PASO_RECHAZADO:
                statusTextInfo.setText("Ocurrió un Error\nCon Tu Documentación");
                statusText.setText("Reenvía Tus\nDocumentos\n1/3");
                statusViewCupo.updateError(33,0);
                mTextContact.setVisibility(View.GONE);
                mButtonContinue.setVisibility(View.VISIBLE);
                ESTADO_ACTUAL = ESTADO_RENEENVIA_DOCUMENTOS;
                break;
        }
    }


    private static JSONObject createParams(boolean envolve, Object oRequest) {
        if (oRequest != null) {
            JSONObject tmp = JsonManager.madeJsonFromObject(oRequest);
            if (envolve) {
                if (oRequest instanceof AdqRequest) {
                    return JsonManager.madeJsonAdquirente(tmp);
                } else {
                    return JsonManager.madeJson(tmp);
                }
            } else {
                return tmp;
            }
        }
        return null;
    }

    @Override
    public void nextScreen(String event, Object data) {

    }

    @Override
    public void backScreen(String event, Object data) {

    }

    @Override
    public void showLoader(String message) {
        onEventListener.onEvent(EVENT_SHOW_LOADER, message);
    }

    @Override
    public void hideLoader() {
        onEventListener.onEvent(EVENT_HIDE_LOADER, null);
    }

    @Override
    public void showError(Object error) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnNextScreen) {
            switch (ESTADO_ACTUAL) {
                case ESTADO_RENEENVIA_DOCUMENTOS:
                    Log.e("Test", "Abrereenvia Documentos");
                    cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REENVIAR_COMPROBANTES, null);
                    break;
            }
        }

    }
}
