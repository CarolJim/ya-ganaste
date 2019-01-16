package com.pagatodo.yaganaste.ui._controllers.manager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.ui._controllers.manager.interfase.ILocationChanged;

import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

/**
 * Created by Jordan on 19/05/2017.
 */

public abstract class ToolBarPositionActivity extends LoaderActivity implements OnEventListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ILocationChanged {

    private static final String REQUESTING_LOCATION_UPDATES_KEY = "location_key";
    private static final String LOCATION_KEY = "lastLocation";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    ILocationChanged iLocationChanged;

    private boolean mRequestingLocationUpdates = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateValuesFromBundle(savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        iLocationChanged = this;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            mLocation = mLastLocation;
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.i(getLocalClassName().toString(), "onConnectionFailed: " + connectionResult);
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public Location getLocation() {
        return mLocation;
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (mLocation == null) {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
//        iLocationChanged.onLocationChanged(location);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        outState.putParcelable(LOCATION_KEY, mLocation);
        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                //setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
        }
    }

}
