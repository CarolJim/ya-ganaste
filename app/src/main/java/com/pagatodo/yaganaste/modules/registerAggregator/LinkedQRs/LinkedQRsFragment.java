package com.pagatodo.yaganaste.modules.registerAggregator.LinkedQRs;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.QRs;
import com.pagatodo.yaganaste.data.model.RegisterAggregatorNew;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;
import com.pagatodo.yaganaste.modules.register.CodigoVinculados.QrItemAdapters;
import com.pagatodo.yaganaste.modules.register.RegContracts;
import com.pagatodo.yaganaste.modules.register.RegInteractor;
import com.pagatodo.yaganaste.modules.registerAggregator.AggregatorActivity;
import com.pagatodo.yaganaste.ui._controllers.BussinesActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.ui._controllers.TabActivity.EVENT_SCAN_QR;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedQRsFragment extends GenericFragment implements View.OnClickListener,
        OnHolderListener<QrItem> ,RegContracts.Listener {

    private RecyclerView qrRcv;
    private BussinesActivity activity;
    private View rootView;
    private StyleButton button_continue;
    private RegInteractor regInteractor;

    public static LinkedQRsFragment newInstance() {
        return new LinkedQRsFragment();
    }

    public LinkedQRsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BussinesActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_linked_qrs, container, false);
        button_continue = (StyleButton) rootView.findViewById(R.id.button_continue);
        qrRcv = (RecyclerView) rootView.findViewById(R.id.qr_rcv);
        regInteractor=new RegInteractor(this,getContext());
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        button_continue.setOnClickListener(this::onClick);
        qrRcv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        qrRcv.setHasFixedSize(true);
        ItemOffsetDecoration decoration = new ItemOffsetDecoration(5);
        qrRcv.addItemDecoration(decoration);
        QrItemAdapters adapter = new QrItemAdapters(this);
        qrRcv.setAdapter(adapter);
        ArrayList<QrItem> qrItems = new ArrayList<>();

        for (QRs qrs : RegisterAggregatorNew.getInstance().getqRs()) {
            qrItems.add(new QrItem(qrs, R.drawable.qr_code));
        }
        qrItems.add(new QrItem(new QRs("", "Agregar QR", true, ""), R.drawable.ic_camera_plus));
        adapter.setQrItems(qrItems);
        //rootView.findViewById(R.id.button_continue).setOnClickListener(view -> activity.getRouter().showSMSAndroid());
    }

    @Override
    public void onClickItem(QrItem item) {
        if (item.getResImage() == R.drawable.ic_camera_plus) {
            //activity.getRouter().showScanQR(Direction.BACK);
            activity.onEvent(EVENT_SCAN_QR, null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_continue:
                regInteractor.createAgent();
                regInteractor.assignmentQrs();

                break;
        }
    }

    @Override
    public void showLoader(String message) {

    }

    @Override
    public void onErrorService(String error) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void onSuccessValidatePlate(String plate) {

    }

    @Override
    public void onErrorValidatePlate(String error) {

    }
}
