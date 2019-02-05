package com.pagatodo.yaganaste.modules.registerAggregator.LinkedQRs;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.QRs;
import com.pagatodo.yaganaste.data.model.RegisterAggregatorNew;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;
import com.pagatodo.yaganaste.modules.register.CodigoVinculados.QrItemAdapters;
import com.pagatodo.yaganaste.modules.registerAggregator.AggregatorActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedQRsFragment extends GenericFragment implements
        OnHolderListener<QrItem> {

    private RecyclerView qrRcv;
    private AggregatorActivity activity;
    private View rootView;
    ArrayList<QrItem> qrItems;

    public static LinkedQRsFragment newInstance(){
        return new LinkedQRsFragment();
    }

    public LinkedQRsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(AggregatorActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_linked_qrs, container, false);
        qrRcv=(RecyclerView)rootView.findViewById(R.id.qr_rcv);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        qrRcv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        qrRcv.setHasFixedSize(true);
        ItemOffsetDecoration decoration = new ItemOffsetDecoration(5);
        qrRcv.addItemDecoration(decoration);
        QrItemAdapters adapter = new QrItemAdapters(this);
        qrRcv.setAdapter(adapter);

        qrItems = new ArrayList<>();
        for (QRs qrs: RegisterAggregatorNew.getInstance().getqRs()){
            qrItems.add(new QrItem(qrs,R.drawable.qr_code));
        }
        qrItems.add(new QrItem(new QRs("","Agregar QR",true,""),R.drawable.ic_camera_plus));
        adapter.setQrItems(qrItems);
        //rootView.findViewById(R.id.button_continue).setOnClickListener(view -> activity.getRouter().showSMSAndroid());
    }

    @Override
    public void onClickItem(QrItem item) {
        if (item.getResImage()==R.drawable.ic_camera_plus){
            activity.getRouter().showScanQR(Direction.BACK);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
