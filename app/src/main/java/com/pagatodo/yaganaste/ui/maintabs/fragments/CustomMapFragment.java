package com.pagatodo.yaganaste.ui.maintabs.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Juan Guerra on 17/05/2017.
 */

public class CustomMapFragment extends com.google.android.gms.maps.SupportMapFragment implements OnMapReadyCallback {

    private ProgressDialog dialog;
    private GoogleMap map;
    public static String TAG = "CustomMapFragment";

    private Location myLocation;
    private boolean firstLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initComponents();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initComponents() {
        dialog = new ProgressDialog(this.getActivity());
        dialog.setMessage("Obteniendo Mapa");
        dialog.setCancelable(false);
        if (this.map == null)
            dialog.show();
        getMapAsync(this);
        if (myLocation == null) {
            firstLocation = true;
        } else {
            firstLocation = false;
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        dialog.dismiss();
        this.map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);


        map.getUiSettings().setMapToolbarEnabled(false);
        if (this.myLocation == null) {
            dialog = new ProgressDialog(this.getActivity());
            dialog.setMessage("Obteniendo Ubicacion");
            dialog.setCancelable(false);
            dialog.show();
        } else {
            //Callback para indicar que el mapa esta cargado
        }
        map.setOnMyLocationChangeListener(new LocationListenerForMap());
    }

    private class LocationListenerForMap implements GoogleMap.OnMyLocationChangeListener {
        @Override
        public void onMyLocationChange(Location location) {
            if (location != null) {
                myLocation = location;
                myLocation.setAltitude(0);
                if (firstLocation) {
                    LatLng latlon = new LatLng(location.getLatitude(), location.getLongitude());
                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 16));
                    if (dialog != null) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }

                    firstLocation = false;
                }
            }

        }
    }

    public float getZoomLevel(int radius) {
        return (float) (16 - Math.log(1) / Math.log(2));
    }


    public Location getMyLocation() {
        return myLocation;
    }


}