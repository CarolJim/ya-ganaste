package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.LocalizarSucursalesResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.ui._controllers.manager.ToolBarActivity;
import com.pagatodo.yaganaste.ui.maintabs.adapters.RecyclerSucursalesAdapter;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositMapManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.DepositMapPresenter;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IDepositMapPresenter;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.customviews.ClickListener;
import com.pagatodo.yaganaste.utils.customviews.CustomMapFragment;
import com.pagatodo.yaganaste.utils.customviews.DividerItemDecoration;
import com.pagatodo.yaganaste.utils.customviews.RecyclerTouchListener;
import com.pagatodo.yaganaste.utils.customviews.StyleEdittext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;

/**
 * Created by Jordan on 17/05/2017.
 */

public class DepositsMapFragment extends SupportFragment implements DepositMapManager,
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    protected Location actualLocation;
    CustomMapFragment customMapFragment;
    IDepositMapPresenter depositMapPresenter;
    @BindView(R.id.sucurasalesList)
    RecyclerView sucurasalesList;
    @BindView(R.id.txtInfoSucursales)
    TextView txtInfoSucursales;
    @BindView(R.id.swipeMap)
    SwipeRefreshLayout swipeMap;
    @BindView(R.id.frag_depositos_mapa_et)
    StyleEdittext etBuscar;
    boolean isBackAvailable = false;
    private View rootView;
    private TabActivity parentActivity;
    private GoogleMap map;
    private RecyclerSucursalesAdapter adapter;
    private List<DataLocalizaSucursal> sucursales;
    private List<Marker> markers;
    boolean onlineGPS;

    public static DepositsMapFragment newInstance() {
        DepositsMapFragment depositsMapFragment = new DepositsMapFragment();
        Bundle args = new Bundle();
        depositsMapFragment.setArguments(args);
        return depositsMapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositMapPresenter = new DepositMapPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onEventListener.onEvent(TabActivity.EVENT_HIDE_MANIN_TAB, null);
        onEventListener.onEvent(ToolBarActivity.EVENT_CHANGE_TOOLBAR_VISIBILITY, false);

        View v = inflater.inflate(R.layout.fragment_depositos_mapa, container, false);
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
        parentActivity = (TabActivity) getActivity();
        if (status == ConnectionResult.SUCCESS) {
            actualLocation = parentActivity.getLocation();
            FragmentManager fm = getChildFragmentManager();
            customMapFragment = CustomMapFragment.newInstance(this);
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.my_map_fragment, customMapFragment);
            fragmentTransaction.commit();
            fm.executePendingTransactions();
        }

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
        swipeMap.setOnRefreshListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sucurasalesList.setLayoutManager(mLayoutManager);
        sucurasalesList.setItemAnimator(new DefaultItemAnimator());
        sucurasalesList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        /**
         * Agregamos el Listener que detecta cada vez que escribimos en el campo de busqueda
         */
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //  Toast.makeText(getContext(), "Texto " + s, Toast.LENGTH_SHORT).show();
                String myFilter = etBuscar.getText().toString();
                adapter.filter(myFilter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Verificamos que el GPS este encendido
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        onlineGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (onlineGPS) {
            getSucursales();
        } else {
            showDialogMesage(getActivity().getResources().getString(R.string.ask_permission_gps));
        }
    }

    @Override
    public void onRefresh() {
        ((TabActivity) getActivity()).showProgressLayout("Cargando");
        if (onlineGPS) {
            getSucursales();
        } else {
            showDialogMesage(getActivity().getResources().getString(R.string.ask_permission_gps));
        }
    }

    private void showLastFragment() {
        // ((TabActivity) getActivity()).goHome();
        parentActivity.onBackPressed();
    }

    @Override
    public void printSucursales(List<DataLocalizaSucursal> sucursalList) {
        sucursales = sucursalList;
        this.sucurasalesList.setVisibility(View.VISIBLE);
        txtInfoSucursales.setVisibility(View.GONE);
        prinSucursalesOnMap(sucursalList);
        printSucursalesOnRecycler(sucursalList);
        swipeMap.setRefreshing(false);
        parentActivity.hideProgresLayout();
    }

    @Override
    public void setOnSucursalesNull() {
        this.sucurasalesList.setVisibility(View.GONE);
        txtInfoSucursales.setVisibility(View.VISIBLE);
        swipeMap.setRefreshing(false);
        txtInfoSucursales.setVisibility(View.VISIBLE);
        parentActivity.hideProgresLayout();
    }

    @Override
    public void onServiceError(DataSourceResult rescponse) {
        String errorTxt = null;
        isBackAvailable = true;
        try {
            LocalizarSucursalesResponse response = (LocalizarSucursalesResponse) rescponse.getData();
            if (response.getMensaje() != null)
                errorTxt = response.getMensaje();
            //Toast.makeText(this, response.getMensaje(), Toast.LENGTH_LONG).show();

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        swipeMap.setRefreshing(false);
        parentActivity.hideProgresLayout();
        txtInfoSucursales.setVisibility(View.VISIBLE);
        ((DepositsFragment) getParentFragment()).showErrorMessage(errorTxt != null ? errorTxt : getString(R.string.error_respuesta));
        ((DepositsFragment) getParentFragment()).onBtnBackPress();
    }

    private void prinSucursalesOnMap(List<DataLocalizaSucursal> sucursalList) {
        isBackAvailable = true;
        markers = new ArrayList<>();
        for (DataLocalizaSucursal sucursal : sucursalList) {
            addMarker(sucursal);
        }
    }

    private void printSucursalesOnRecycler(final List<DataLocalizaSucursal> sucursalList) {
        if (sucursalList.size() > 0) {

            /*
            Lineas para probar el buscador de Filtro, eliminar en versiones posteriores
            sucursalList.add(new DataLocalizaSucursal("Direccion1", "Direccion2", "Horario", 2.0, 3.0, "Plaza Loreto", "12345678"));
            sucursalList.add(new DataLocalizaSucursal("Direccion1", "Direccion2", "Horario", 2.0, 3.0, "Plaza Lorena", "12345678"));
            sucursalList.add(new DataLocalizaSucursal("Direccion1", "Direccion2", "Horario", 2.0, 3.0, "Plaza Meave", "12345678"));
            sucursalList.add(new DataLocalizaSucursal("Direccion1", "Direccion2", "Horario", 2.0, 3.0, "Parque Tezonte", "12345678"));
            sucursalList.add(new DataLocalizaSucursal("Direccion1", "Direccion2", "Horario", 2.0, 3.0, "Parque Linda vista", "12345678"));
            sucursalList.add(new DataLocalizaSucursal("Direccion1", "Direccion2", "Horario", 2.0, 3.0, "Perisur", "12345678"));*/

            adapter = new RecyclerSucursalesAdapter(sucursalList, actualLocation);
            sucurasalesList.setAdapter(adapter);
            sucurasalesList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), sucurasalesList, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //DataLocalizaSucursal mySucursal = sucursalList.get(position);
                    //Toast.makeText(getContext(), mySucursal.getNombre(), Toast.LENGTH_SHORT).show();
                    onClickSucursal(sucursalList.get(position));
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        } else {
            setOnSucursalesNull();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);

        map.getUiSettings().setMapToolbarEnabled(false);
        if (actualLocation != null) {
            LatLng latlon = new LatLng(actualLocation.getLatitude(), actualLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 12));

            getSucursales();
        } else {
            parentActivity.hideProgresLayout();
            if (!onlineGPS) {
                //  Toast.makeText(getActivity(), "GPS apagado", Toast.LENGTH_SHORT).show();
             //   showDialogMesage(getActivity().getResources().getString(R.string.ask_permission_gps));
            }
        }

        //map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);


    }

    private void getSucursales() {
        try {
            depositMapPresenter.getSucursales(actualLocation);
        } catch (OfflineException e) {
            e.printStackTrace();
            //Toast.makeText(getContext(), "Sin Conexión", Toast.LENGTH_SHORT).show();
            ((DepositsFragment) getParentFragment()).showErrorMessage(getString(R.string.no_internet_access));
            ((DepositsFragment) getParentFragment()).onBtnBackPress();
        }
    }

    private void showDialogMesage(final String mensaje) {
        UI.createSimpleCustomDialog("", mensaje, getFragmentManager(),
                new DialogDoubleActions() {
                    @Override
                    public void actionConfirm(Object... params) {
                        getActivity().onBackPressed();
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }

                    @Override
                    public void actionCancel(Object... params) {

                    }
                },
                true, false);
    }

    public void addMarker(DataLocalizaSucursal sucursal) {
        Marker m = map.addMarker(new MarkerOptions()
                .position(new LatLng(sucursal.getLatitud(), sucursal.getLongitud()))
                .title(sucursal.getNombre())
                .icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360))));

        m.setTag(sucursal);
        markers.add(m);
    }

    private void onClickSucursal(DataLocalizaSucursal mySucursal) {
        ((DepositsFragment) getParentFragment()).loadDescriptionFragment(mySucursal);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for (Marker mark : markers) {
            if (marker.equals(mark)) {
                onClickSucursal((DataLocalizaSucursal) mark.getTag());
                break;
            }
        }
    }
}
