package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.UtilsLocation;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * Created by Jordan on 24/05/2017.
 */

public class DepositSucursalDetail extends SupportFragment implements View.OnClickListener {

    private static String SUCURSAL_OBJECT = "SucursalObject";
    @BindView(R.id.imgSucursal)
    ImageView imgSucursal;

    @BindView(R.id.sucursaltelefono)
    ImageView imgtelefonosucursal;

    @BindView(R.id.txtSucursalTitle)
    TextView title;
    @BindView(R.id.txtSucursalAddress)
    TextView address;
    @BindView(R.id.txtSucursalDate)
    TextView date;
    @BindView(R.id.txtSucursalHour)
    TextView hour;
    @BindView(R.id.txtSucursalPhone)
    TextView phone;
    @BindView(R.id.btnShare)
    ImageView btnShare;
    @BindView(R.id.btnRoute)
    FloatingActionButton btnRoute;
    private View rootView;
    private DataLocalizaSucursal sucursal;
    private Location myLocation;
    private String number;


    public static DepositSucursalDetail newInstance(DataLocalizaSucursal sucursal) {
        DepositSucursalDetail depositSucursalDetail = new DepositSucursalDetail();
        Bundle args = new Bundle();
        args.putSerializable(SUCURSAL_OBJECT, sucursal);
        depositSucursalDetail.setArguments(args);
        return depositSucursalDetail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_descripcion_sucursal, container, false);
        sucursal = (DataLocalizaSucursal) getArguments().getSerializable(SUCURSAL_OBJECT);
        myLocation = ((TabActivity) getActivity()).getLocation();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.rootView = view;
        initViews();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, false);

        String dis = UtilsLocation.getDistance2Points(new LatLng(myLocation.getLatitude(),
                myLocation.getLongitude()), new LatLng(sucursal.getLatitud(), sucursal.getLongitud()));
        title.setText(sucursal.getNombre() + " - " + dis);
        address.setText(sucursal.getDireccion1());
        date.setText(" / ");
        hour.setText(sucursal.getHorario());
        phone.setText(sucursal.getNumTelefonico());
        imgtelefonosucursal.setOnClickListener(this);
        btnRoute.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sucursaltelefono:
                showDialogCallIntent();
                break;

            case R.id.btnRoute:

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:" + myLocation.getLatitude() + ","
                                + myLocation.getLongitude() + "?q="
                                + sucursal.getLatitud() + ","
                                + sucursal.getLongitud()));
                getContext().startActivity(intent);

                break;
            case R.id.btnShare:
                Intent intentSend = new Intent();
                intentSend.setAction(Intent.ACTION_SEND);
                intentSend.setType("text/plain");
                intentSend.putExtra(Intent.EXTRA_SUBJECT, sucursal.getNombre().toString());
                intentSend.putExtra(Intent.EXTRA_TEXT, "http://maps.google.com/maps?q="
                        + sucursal.getLatitud() + "," + sucursal.getLongitud() + "&iwloc=A");
                startActivity(Intent.createChooser(intentSend, "Compartir Sucursal"));
                break;
        }
    }

    private void showDialogCallIntent() {
        UI.createSimpleCustomDialog("", getResources().getString(R.string.deseaRealizarLlamada), getFragmentManager(),
                doubleActions, true, true);
    }

    private void createCallIntent() {
        number=sucursal.getNumTelefonico();
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
