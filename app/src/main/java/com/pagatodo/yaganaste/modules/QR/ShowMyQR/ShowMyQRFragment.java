package com.pagatodo.yaganaste.modules.QR.ShowMyQR;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.QR.QRActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowMyQRFragment extends GenericFragment {

    private static QRActivity qrActivity;
    private View rootView;

    public static ShowMyQRFragment newInstance(){
        return new ShowMyQRFragment();
    }

    public ShowMyQRFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qrActivity=(QRActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_show_my_qr,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {

    }

}
