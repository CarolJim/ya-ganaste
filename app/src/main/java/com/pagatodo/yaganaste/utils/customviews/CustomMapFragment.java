package com.pagatodo.yaganaste.utils.customviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Jordan on 17/05/2017.
 */

public class CustomMapFragment extends SupportMapFragment {

    private static OnMapReadyCallback callback;
    private boolean firstLocation;


    public CustomMapFragment() {
        super();
    }

    public static CustomMapFragment newInstance(OnMapReadyCallback onMapReadyCallback) {
        CustomMapFragment customMapFragment = new CustomMapFragment();
        callback = onMapReadyCallback;
        return customMapFragment;
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View v = super.onCreateView(layoutInflater, viewGroup, bundle);
        initMap();
        return v;
    }

    private void initMap() {
        getMapAsync(callback);
        /*if (myLocation == null) {
            firstLocation = true;
        } else {
            firstLocation = false;
        }*/
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
