package com.pagatodo.yaganaste.modules.qr;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;
import com.pagatodo.yaganaste.modules.qr.Adapter.QRAdapter;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._manager.GenericFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.WINDOW_SERVICE;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;

public class QrManagerFragment extends GenericFragment implements QrManagerContracts.Listener, OnHolderListener<QrItems> {

    private View rootView;
    private TabActivity activity;
    private QrManagerRouter router;
    ArrayList<MyQrData> list = new ArrayList<>();
    private QRAdapter adapter;

    private QrManagerIteractor iteractor;


    @BindView(R.id.rcv_qr)
    RecyclerView rcv_qr;


    public static QrManagerFragment newInstance() {
        return new QrManagerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (TabActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.router = new QrManagerRouter(activity);

        iteractor = new QrManagerIteractor(this, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_qr, container, false);
        initViews();
        return rootView;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, rootView);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcv_qr.setLayoutManager(llm);
        rcv_qr.setHasFixedSize(true);

        adapter = new QRAdapter(this);

        //rcv_qr.setAdapter(new QRAdapter(list));
        rcv_qr.setAdapter(adapter);

        Log.d("TOKEN", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE));
        Log.d("TOKEN_SESION", App.getInstance().getPrefs().loadData(TOKEN_FIREBASE_SESSION));

        iteractor.getMyQrs();
    }

    public QrManagerIteractor getIteractor() {
        return iteractor;
    }


    @Override
    public void onClickItem(QrItems item) {

    }

    @Override
    public void onSuccessQRs(ArrayList<QrItems> listQRs) {
        //adapter.setQrUser();
        adapter.setQrUser(listQRs);
    }
}
