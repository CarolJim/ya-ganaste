package com.pagatodo.yaganaste.ui_wallet.fragments;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapsView;
import com.pagatodo.yaganaste.ui_wallet.presenter.StarbucksMapPresenter;
import com.pagatodo.yaganaste.utils.UI;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui.adquirente.fragments.GetMountFragment.REQUEST_ID_MULTIPLE_PERMISSIONS;
import static com.pagatodo.yaganaste.ui_wallet.WalletMainActivity.REQUEST_CHECK_SETTINGS;

public class StarbucksMapFragment extends GenericFragment implements OnCompleteListener<LocationSettingsResponse>,
        IStarbucksMapsView {

    private View rootView;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.edt_search_place)
    EditText edtSearch;
    private GoogleMap map;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private Location currentLocation;
    private StarbucksMapPresenter presenter;
    private boolean hasSearchAll = false;

    public static StarbucksMapFragment newInstance() {
        StarbucksMapFragment starbucksMapFragment = new StarbucksMapFragment();
        Bundle args = new Bundle();
        starbucksMapFragment.setArguments(args);
        return starbucksMapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_starbucks_map, container, false);
            ButterKnife.bind(this, rootView);
            presenter = new StarbucksMapPresenter(this);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            mMapView.onCreate(savedInstanceState);
            initViews();
        }
        return rootView;
    }

    @Override
    public void initViews() {
        mMapView.onResume();
        try {
            MapsInitializer.initialize(App.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setMapToolbarEnabled(false);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ValidatePermissions.checkPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO},
                            REQUEST_ID_MULTIPLE_PERMISSIONS);
                    return;
                }
                setLocationSetting();
                map.setMyLocationEnabled(true);
                getLastLocation();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                } else {
                    currentLocation = locationResult.getLastLocation();
                    if(!hasSearchAll){
                        presenter.getAllStores();
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(),
                                currentLocation.getLongitude())).zoom(10).build();
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                    /*for (Location location : locationResult.getLocations()) {
                        map.clear();
                        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                        map.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                        // For zooming automatically to the location of the marker
                    }*/
                }
            }

            ;
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, null);
    }

    @Override
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
        try {
            LocationSettingsResponse response = task.getResult(ApiException.class);
        } catch (final ApiException exception) {
            switch (exception.getStatusCode()) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) exception;
                        resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException | ClassCastException e) {
                        e.printStackTrace();
                    }
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocationSetting();
                }
                break;
        }
    }

    @Override
    public void setStoresInMap() {

    }

    private void setLocationSetting() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(getContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(this);
    }
}
