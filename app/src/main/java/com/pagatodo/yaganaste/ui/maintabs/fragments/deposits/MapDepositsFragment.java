package com.pagatodo.yaganaste.ui.maintabs.fragments.deposits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.MapView;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragment;
import com.pagatodo.yaganaste.utils.customviews.CustomMapFragment;

/**
 * Created by Jordan on 17/05/2017.
 */

public class MapDepositsFragment extends SupportFragment {
    private View rootView;

    public static MapDepositsFragment newInstance(){
        MapDepositsFragment mapDepositsFragment = new MapDepositsFragment();
        Bundle args = new Bundle();
        mapDepositsFragment.setArguments(args);
        return mapDepositsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_depositos_mapa, container, false);

        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if(status == ConnectionResult.SUCCESS){
            FragmentManager fm = getChildFragmentManager();
            CustomMapFragment customMapFragment = CustomMapFragment.newInstance();
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

    }

}
