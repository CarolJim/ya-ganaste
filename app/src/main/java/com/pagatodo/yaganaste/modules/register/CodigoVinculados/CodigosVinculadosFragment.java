package com.pagatodo.yaganaste.modules.register.CodigoVinculados;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.QRs;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;
import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;
import com.pagatodo.yaganaste.modules.register.RegActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.ui_wallet.views.ItemOffsetDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodigosVinculadosFragment extends GenericFragment implements OnHolderListener<QrItem> {

    @BindView(R.id.qr_rcv)
    public RecyclerView qrRcv;

    private RegActivity activity;
    private View rootView;

    public  static  CodigosVinculadosFragment newInstance(){
        return new CodigosVinculadosFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (RegActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_codigos_vinculados, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this,rootView);
        // Inflate the layout for this fragment
        qrRcv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        qrRcv.setHasFixedSize(true);
        ItemOffsetDecoration decoration = new ItemOffsetDecoration(5);
        qrRcv.addItemDecoration(decoration);
        QrItemAdapters adapter = new QrItemAdapters(this);
        qrRcv.setAdapter(adapter);


        ArrayList<QrItem> qrItems = new ArrayList<>();
        for (QRs qrs:RegisterUserNew.getInstance().getqRs()){
            qrItems.add(new QrItem(qrs,R.drawable.qr_code));
        }
        qrItems.add(new QrItem(new QRs("","Agregar QR",true),R.drawable.ic_camera_plus));
        adapter.setQrItems(qrItems);
        rootView.findViewById(R.id.button_continue).setOnClickListener(view -> activity.getRouter().showSMSAndroid());
        //android:src="@drawable/qr_code"
    }

    @Override
    public void onClickItem(QrItem item) {
        if (item.getResImage() == R.drawable.ic_camera_plus){
            activity.getRouter().showScanQR();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
