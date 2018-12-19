package com.pagatodo.yaganaste.modules.qr.operations.EditQr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditQrFragment extends GenericFragment implements View.OnClickListener {

    private static String TAG_QR_ITEM = "TAG_QR_ITEM";
    private QrOperationActivity activity;
    private View rootView;
    private QrItems item;
    private EditQrInteractor interactor;

    @BindView(R.id.btn_continue_edit_qr)
    StyleButton btnContinue;

    public static EditQrFragment newInstance(QrItems item){
        EditQrFragment fragment = new EditQrFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_QR_ITEM,item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            item = (QrItems) getArguments().getSerializable(TAG_QR_ITEM);
            assert item != null;
        }
        interactor = new EditQrInteractor(this)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (QrOperationActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.edit_qr_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
