package com.pagatodo.yaganaste.modules.qr.operations.DetailQr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.components.FeetQrDetail;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailQrFragment extends GenericFragment implements View.OnClickListener{

    private static String TAG_QR_ITEM = "TAG_QR_ITEM";
    private View rootView;
    private QrOperationActivity activity;
    private QrItems item;

    @BindView(R.id.btn_edit)
    ImageView btnEdit;
    @BindView(R.id.feet_qr_detail)
    FeetQrDetail feetQrDetail;
    @BindView(R.id.text_alias)
    StyleTextView textAlias;

    public static DetailQrFragment newInstance(QrItems item){
        DetailQrFragment fragment = new DetailQrFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_QR_ITEM,item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (QrOperationActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.detail_qr_fragment,container,false);
        initViews();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_qr_delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);
        if (getArguments() != null){
            item = (QrItems) getArguments().getSerializable(TAG_QR_ITEM);
            assert item != null;
            Log.d("QR_ITEM",item.getQrUser().getAlias());
            textAlias.setText(item.getQrUser().getAlias());
            feetQrDetail.setPlate(item.getQrUser().getPlate());
        }
        btnEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (item != null) {
            activity.getRouter().showEditQr(Direction.NONE,item);
        }
    }

    public void generateQR(){

    }
}
