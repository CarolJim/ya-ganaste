package com.pagatodo.yaganaste.ui_wallet.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.adapters.BranchList;
import com.pagatodo.yaganaste.ui_wallet.adapters.ShopsAdapter;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindPromoFragment extends GenericFragment implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    @BindView(R.id.rcv_promo)
    RecyclerView rcv_promo;
    @BindView(R.id.center_map)
    StyleButton button;

    ArrayList<BranchList> list = new ArrayList<>();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;

    public static FindPromoFragment newInstance(){
        return new FindPromoFragment();
    }


    View rootView;

    public FindPromoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_find_promo, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        list.add(new BranchList("Abarrotes Mi Lupita","Blvd. Manuel Ávila Camacho 66"));
        list.add(new BranchList("La Ventanita","Calle Volcan 7"));
        list.add(new BranchList("Don Pepe","Montes Urales 35"));
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_promo.setLayoutManager(llm);
        rcv_promo.setHasFixedSize(true);
        rcv_promo.setAdapter(new ShopsAdapter(list));



    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

        }

        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMyLocationButtonClickListener(this);

        LatLng coordinate = new LatLng(19.425012, -99.194883);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
        mMap.animateCamera(yourLocation);


        //Button center_map = (Button)rootView.findViewById(R.id.center_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", "Click buton center map");


                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                Location location = mMap.getMyLocation();
                LatLng myLocation;
                if (location != null) {
                    myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                }else {
                    myLocation=null;
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15));




            }
        });



    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
