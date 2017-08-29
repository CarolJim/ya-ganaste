package com.pagatodo.yaganaste.ui.cupo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.Referencias;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.DataEstadoSolicitud;
import com.pagatodo.yaganaste.data.model.webservice.response.cupo.RefereciasResponse;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.RegistryCupoActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui.cupo.managers.CupoActivityManager;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ReferenciaView;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.customviews.ReferenciaView.ESTADO_APROVADO;
import static com.pagatodo.yaganaste.utils.customviews.ReferenciaView.ESTADO_RECHAZADO;
import static com.pagatodo.yaganaste.utils.customviews.ReferenciaView.ESTADO_REVISION;

/**
 * A simple {@link Fragment} subclass.
 */
public class CupoReenviarReferenciasFragment extends GenericFragment implements View.OnClickListener {

    public static final String referencias = "referencias";
    private DataEstadoSolicitud dataEstadoSolicitud;

    protected View rootview;
    @BindView(R.id.familiarStatus)
    ReferenciaView familiarStatus;
    @BindView(R.id.personalStatus)
    ReferenciaView personalStatus;
    @BindView(R.id.proveedorStatus)
    ReferenciaView prooveedorStatus;
    @BindView(R.id.containerFamiliar)
    LinearLayout containerFamiliar;
    @BindView(R.id.btnActualizarReferencias)
    StyleButton btnActualizarReferencias;

    private List<RefereciasResponse> referecias;

    private CupoActivityManager cupoActivityManager;
    private Boolean actualizarFamiliar = false;
    private Boolean actualizarPersonal = false;
    private Boolean actualizarProveedor = false;

    public static CupoReenviarReferenciasFragment newInstance(DataEstadoSolicitud dataEstadoSolicitud) {
        CupoReenviarReferenciasFragment fragment = new CupoReenviarReferenciasFragment();
        Bundle args = new Bundle();
        args.putSerializable(referencias, dataEstadoSolicitud);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cupoActivityManager = ((RegistryCupoActivity) getActivity()).getCupoActivityManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_cupo_reenviar_referencias, container, false);
        this.dataEstadoSolicitud = (DataEstadoSolicitud) getArguments().getSerializable(referencias);
        initViews();
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Referencias.getInstance().getPersonaActualizado()) {
            personalStatus.setCargaLista();
        }

        if (Referencias.getInstance().getFamiliarActualizado()) {
            familiarStatus.setCargaLista();
        }

        if (Referencias.getInstance().getProveedorActualizado()) {
            prooveedorStatus.setCargaLista();
        }
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootview);
        btnActualizarReferencias.setOnClickListener(this);
        referecias = dataEstadoSolicitud.getReferencias();
        for (RefereciasResponse referencia : referecias) {
            int tipoReferenica = referencia.getIdTipoReferencia();
            int idEstatusReferencia = referencia.getIdEstatusReferencia();
            int estadoActual = 0;
            switch (idEstatusReferencia) {
                case 1:
                    estadoActual = ESTADO_APROVADO;
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    estadoActual = ESTADO_RECHAZADO;
                    break;
                case 8:
                    estadoActual = ESTADO_REVISION;
                    break;
            }

            Referencias sigletonReferencias = Referencias.getInstance();
            switch (tipoReferenica) {
                case 1: // Familiar
                    familiarStatus.setStatus(estadoActual);
                    if (estadoActual == ESTADO_RECHAZADO) {
                        familiarStatus.setOnClickListener(this);
                        sigletonReferencias.setFamiliarNombre(referencia.getNombre());
                        sigletonReferencias.setFamiliarApellidoPaterno(referencia.getPrimerApellido());
                        sigletonReferencias.setFamiliarApellidoMaterno(referencia.getSegundoApellido());
                        sigletonReferencias.setFamiliarTelefono(referencia.getTelefono());
                        sigletonReferencias.setFamiliarIdRelacion(referencia.getIdRelacion());
                        actualizarFamiliar = true;
                    } else {
                        familiarStatus.setOnClickListener(null);
                    }
                    break;
                case 2: // Personal
                    personalStatus.setStatus(estadoActual);
                    if (estadoActual == ESTADO_RECHAZADO) {
                        personalStatus.setOnClickListener(this);
                        sigletonReferencias.setPersonalNombre(referencia.getNombre());
                        sigletonReferencias.setPersonalApellidoPaterno(referencia.getPrimerApellido());
                        sigletonReferencias.setPersonalApellidoMaterno(referencia.getSegundoApellido());
                        sigletonReferencias.setPersonalTelefono(referencia.getTelefono());
                        sigletonReferencias.setPersonalIdRelacion(referencia.getIdRelacion());
                        actualizarPersonal = true;
                    } else {
                        personalStatus.setOnClickListener(null);
                    }
                    break;
                case 3: // Proveedor
                    prooveedorStatus.setStatus(estadoActual);
                    if (estadoActual == ESTADO_RECHAZADO) {
                        prooveedorStatus.setOnClickListener(this);
                        sigletonReferencias.setProveedorNombre(referencia.getNombre());
                        sigletonReferencias.setProveedorApellidoPaterno(referencia.getPrimerApellido());
                        sigletonReferencias.setProveedorApellidoMaterno(referencia.getSegundoApellido());
                        sigletonReferencias.setProveedorTelefono(referencia.getTelefono());
                        sigletonReferencias.setProveedorProductoServicio(referencia.getProductoServicioProveedor());
                        actualizarProveedor = true;
                    } else {
                        prooveedorStatus.setOnClickListener(null);
                    }
                    break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.familiarStatus:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_FAMILIAR_REENVIAR, null);
                break;
            case R.id.personalStatus:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_PERSONAL_REENVIAR, null);
                break;
            case R.id.proveedorStatus:
                cupoActivityManager.callEvent(RegistryCupoActivity.EVENT_GO_CUPO_REFERENCIA_PROVEEDOR_REENVIAR, null);
                break;
            case R.id.btnActualizarReferencias:
                Log.e("Test", "Click en next");

                Referencias referencias = Referencias.getInstance();

                if (actualizarFamiliar && !referencias.getFamiliarActualizado()) {
                    creaMensajeError(getString(R.string.actualiza_familiar));
                    return;
                } else if (actualizarPersonal && !referencias.getPersonaActualizado()) {

                    creaMensajeError(getString(R.string.actualiza_personal));
                    return;
                } else if (actualizarProveedor && !referencias.getProveedorActualizado()) {

                    creaMensajeError(getString(R.string.actualiza_proveedor));
                    return;
                } else {
                    Log.e("Test", "Cargar los datos");
                }

                break;

        }
    }

    private void creaMensajeError(String error) {
        UI.createSimpleCustomDialog("", error, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {

                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }
}

