package com.pagatodo.yaganaste.modules.qr.operations.DetailQr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.qr.operations.QrOperationActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import butterknife.ButterKnife;

public class DetailQrFragment extends GenericFragment {

    private View rootView;
    private QrOperationActivity activity;

    public static DetailQrFragment newInstance(){
        return new DetailQrFragment();
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
    }
}
