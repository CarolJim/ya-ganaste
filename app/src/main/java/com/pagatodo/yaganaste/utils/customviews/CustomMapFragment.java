package com.pagatodo.yaganaste.utils.customviews;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jordan on 17/05/2017.
 */

public class CustomMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private Location myLocation;
    private boolean firstLocation;
    private GoogleMap map;

    public CustomMapFragment() {
        super();
    }

    public static CustomMapFragment newInstance() {
        CustomMapFragment customMapFragment = new CustomMapFragment();
        return customMapFragment;
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View v = super.onCreateView(layoutInflater, viewGroup, bundle);
        initMap();
        return v;
    }

    private void initMap() {
        getMapAsync(this);
        if (myLocation == null) {
            firstLocation = true;
        } else {
            firstLocation = false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);

        map.getUiSettings().setMapToolbarEnabled(false);

        //map.setOnMyLocationChangeListener(new LocationListenerForMap());
    }

   /* @Override
    public void onMyLocationChange(Location location) {

    }

    private class LocationListenerForMap implements GoogleMap.OnMyLocationChangeListener {
        @Override
        public void onMyLocationChange(Location location) {
            if (location != null) {
                myLocation = location;
                myLocation.setAltitude(0);
                if (firstLocation) {
                    LatLng latlon = new LatLng(location.getLatitude(), location.getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 16));
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
    }*/

}
